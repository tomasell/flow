/*
 * Copyright 2000-2016 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.hummingbird.template;

import com.vaadin.hummingbird.StateNode;
import com.vaadin.hummingbird.dom.Element;

/**
 * A template node that generates a different number of child element depending
 * on state node contents.
 *
 * @author Vaadin Ltd
 */
public abstract class VariableTemplateNode extends TemplateNode {

    /**
     * Creates a new node.
     *
     * @param parent
     *            the parent of the new template node, not null
     */
    public VariableTemplateNode(SingleElementTemplateNode parent) {
        super(parent);
        assert parent != null : "A generator can't be the root of a template";
    }

    /**
     * Gets the parent of an element generated by this node.
     *
     * @param node
     *            the state node of the generated element, not <code>null</code>
     * @return the parent element, not <code>null</code>
     */
    public abstract Element getParent(StateNode node);

}