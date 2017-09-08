window.gridConnector = {
    initLazy: function(grid, pageSize) {
    	var extraPageBuffer = 2;
    	var pageCallbacks = {};
    	var cache = {};
    	var lastRequestedRange = [0, 0]
    	
    	grid.pageSize = pageSize;
    	grid.size = 0; // To avoid NaN here and there before we get proper data
		grid.dataProvider = function(params, callback) {
			if (params.pageSize != pageSize) { throw "Invalid pageSize"; }
			
			var page = params.page;
			if (cache[page]) {
				callback(cache[page]);
			} else {
				pageCallbacks[page] = callback;
			}
			
			// Determine what to fetch based on scroll position and not only what grid asked for 
    		var firstNeededPage = Math.min(page, grid._getPageForIndex(grid.$.scroller._virtualStart));
    		var lastNeededPage = Math.max(page, grid._getPageForIndex(grid.$.scroller._virtualEnd));
    		
	        var first = Math.max(0,  firstNeededPage - extraPageBuffer);
	        var last = Math.min(lastNeededPage + extraPageBuffer, Math.max(0, Math.floor(grid.size / grid.pageSize) - 1));

	        if (lastRequestedRange[0] != first || lastRequestedRange[1] != last) {
	        	lastRequestedRange = [first, last];
	        	
		        var count = 1 + last - first;
		        
				// setTimeout to avoid race condition in ServerRpcQueue
				setTimeout(() => grid.$server.setRequestedRange(first * pageSize, count * pageSize), 0);
	        }
		}
		
		var updateGridCache = function(page) {
			if (!grid._cache[page]) {
				return;
			}
			
			var items = cache[page];
			
			if (!items) {
				delete grid._cache[page];
			}
			
			// Force update unless there's a callback waiting
			if (!pageCallbacks[page]) {
				if (items) {
					// Replace existing cache page
					grid._cache[page] = items;
				} else {
					// Fake page to pass to _updateItems
					items = new Array(pageSize);
				}
				
				grid._updateItems(page, items);
			}
		}
		grid.connectorSet = function(index, items) {
			if (index % pageSize != 0) {
				throw "Got new data to index " + index + " which is not aligned with the page size of " + pageSize;
			}
			
			var firstPage = index / pageSize;
			var updatedPageCount = Math.ceil(items.length / pageSize);
			
			for (var i = 0; i < updatedPageCount; i++) {
				var page = firstPage + i;
				
				cache[page] = items.slice(i * pageSize, (i + 1) * pageSize);
				
				updateGridCache(page);
			}
		};
		grid.connectorClear = function(index, length) {
			if (index % pageSize != 0) {
				throw "Got cleared data for index " + index + " which is not aligned with the page size of " + pageSize;
			}
			
			var firstPage = index / pageSize;
			var updatedPageCount = Math.ceil(length / pageSize);
			
			for (var i = 0; i < updatedPageCount; i++) {
				var page = firstPage + i;
				
				delete cache[page];
				
				updateGridCache(page);
			}
		};
		grid.connectorUpdateSize = function(newSize) {
			grid.size = newSize;
		};
		
		grid.connectorConfirm = function(id) {
			// We're done applying changes from this batch, resolve outstanding callbacks
        	var outstandingRequests = Object.getOwnPropertyNames(pageCallbacks);
        	
        	for(var i = 0; i < outstandingRequests.length; i++) {
        		var page = outstandingRequests[i];
        		
        		// Resolve if we have data or if we don't expect to get data
        		if (cache[page] || page < lastRequestedRange[0] || page > lastRequestedRange[1]) {
            		var callback = pageCallbacks[page];
        			delete pageCallbacks[page];
            		
        			callback(cache[page] || new Array(pageSize));
        		}
        	}

        	// Let server know we're done 
			grid.$server.confirmUpdate(id);
		}
    },

	initArray: function(grid) {
		grid.items = [];
		grid.connectorSet = function(index, items) {
			for(var i = 0; i < items.length; i++) {
				grid.set("items." + (i + index), items[i]);
			} 
		};
		grid.connectorClear = function(index, length) {
			for(var i = 0; i < items.length; i++) {
				grid.set("items." + (i + index), null);
				delete grid.items[i + index];
			} 
		};
		grid.connectorUpdateSize = function(newSize) {
			var sizeDiff = newSize - grid.items.length;
			if (sizeDiff > 0) {
				var oldLength = grid.items.length;
				grid.items.length += sizeDiff;
				grid.notifySplices("items", [
					{
						index: oldLength,
						removed: [],
						addedCount: sizeDiff,
						object: grid.items,
						type: "splice"
					}
				]);
			} else if (sizeDiff < 0) {
				grid.splice("items", oldLength + sizeDiff, -sizeDiff);
			}
		};
		grid.connectorConfirm = function(id) {
			grid.$server.confirmUpdate(id);
		}
	}
}