/*
 * Copyright 2000-2017 Vaadin Ltd.
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
package com.vaadin.flow.component.datepicker;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.ComponentSupplier;
import javax.annotation.Generated;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import elemental.json.JsonObject;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.shared.Registration;

@Generated({ "Generator: com.vaadin.generator.ComponentGenerator#1.0-SNAPSHOT",
        "WebComponent: MonthCalendarElement#UNKNOWN", "Flow#1.0-SNAPSHOT" })
@Tag("vaadin-month-calendar")
@HtmlImport("frontend://bower_components/vaadin-date-picker/src/vaadin-month-calendar.html")
public class GeneratedVaadinMonthCalendar<R extends GeneratedVaadinMonthCalendar<R>>
        extends Component implements HasStyle, ComponentSupplier<R> {

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * A {@code Date} object defining the month to be displayed. Only year and
     * month properties are actually used.
     * <p>
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     * </p>
     * 
     * @return the {@code month} property from the webcomponent
     */
    protected JsonObject protectedGetMonth() {
        return (JsonObject) getElement().getPropertyRaw("month");
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * A {@code Date} object defining the month to be displayed. Only year and
     * month properties are actually used.
     * </p>
     * 
     * @param month
     *            the JsonObject value to set
     */
    protected void setMonth(JsonObject month) {
        getElement().setPropertyJson("month", month);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * A {@code Date} object for the currently selected date.
     * <p>
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     * </p>
     * 
     * @return the {@code selectedDate} property from the webcomponent
     */
    protected JsonObject protectedGetSelectedDate() {
        return (JsonObject) getElement().getPropertyRaw("selectedDate");
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * A {@code Date} object for the currently selected date.
     * </p>
     * 
     * @param selectedDate
     *            the JsonObject value to set
     */
    protected void setSelectedDate(JsonObject selectedDate) {
        getElement().setPropertyJson("selectedDate", selectedDate);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * A {@code Date} object for the currently focused date.
     * <p>
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     * </p>
     * 
     * @return the {@code focusedDate} property from the webcomponent
     */
    protected JsonObject protectedGetFocusedDate() {
        return (JsonObject) getElement().getPropertyRaw("focusedDate");
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * A {@code Date} object for the currently focused date.
     * </p>
     * 
     * @param focusedDate
     *            the JsonObject value to set
     */
    protected void setFocusedDate(JsonObject focusedDate) {
        getElement().setPropertyJson("focusedDate", focusedDate);
    }

    /**
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     * 
     * @return the {@code showWeekNumbers} property from the webcomponent
     */
    public boolean isShowWeekNumbers() {
        return getElement().getProperty("showWeekNumbers", false);
    }

    /**
     * @param showWeekNumbers
     *            the boolean value to set
     */
    public void setShowWeekNumbers(boolean showWeekNumbers) {
        getElement().setProperty("showWeekNumbers", showWeekNumbers);
    }

    /**
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     * 
     * @return the {@code i18n} property from the webcomponent
     */
    protected JsonObject protectedGetI18n() {
        return (JsonObject) getElement().getPropertyRaw("i18n");
    }

    /**
     * @param i18n
     *            the JsonObject value to set
     */
    protected void setI18n(JsonObject i18n) {
        getElement().setPropertyJson("i18n", i18n);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Flag stating whether taps on the component should be ignored.
     * <p>
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     * </p>
     * 
     * @return the {@code ignoreTaps} property from the webcomponent
     */
    public boolean isIgnoreTaps() {
        return getElement().getProperty("ignoreTaps", false);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Flag stating whether taps on the component should be ignored.
     * </p>
     * 
     * @param ignoreTaps
     *            the boolean value to set
     */
    public void setIgnoreTaps(boolean ignoreTaps) {
        getElement().setProperty("ignoreTaps", ignoreTaps);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * The earliest date that can be selected. All earlier dates will be
     * disabled.
     * <p>
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     * </p>
     * 
     * @return the {@code minDate} property from the webcomponent
     */
    protected JsonObject protectedGetMinDate() {
        return (JsonObject) getElement().getPropertyRaw("minDate");
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * The earliest date that can be selected. All earlier dates will be
     * disabled.
     * </p>
     * 
     * @param minDate
     *            the JsonObject value to set
     */
    protected void setMinDate(JsonObject minDate) {
        getElement().setPropertyJson("minDate", minDate);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * The latest date that can be selected. All later dates will be disabled.
     * <p>
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     * </p>
     * 
     * @return the {@code maxDate} property from the webcomponent
     */
    protected JsonObject protectedGetMaxDate() {
        return (JsonObject) getElement().getPropertyRaw("maxDate");
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * The latest date that can be selected. All later dates will be disabled.
     * </p>
     * 
     * @param maxDate
     *            the JsonObject value to set
     */
    protected void setMaxDate(JsonObject maxDate) {
        getElement().setPropertyJson("maxDate", maxDate);
    }

    /**
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     * 
     * @return the {@code disabled} property from the webcomponent
     */
    public boolean isDisabled() {
        return getElement().getProperty("disabled", false);
    }

    @DomEvent("selected-date-changed")
    public static class SelectedDateChangeEvent<R extends GeneratedVaadinMonthCalendar<R>>
            extends ComponentEvent<R> {
        public SelectedDateChangeEvent(R source, boolean fromClient) {
            super(source, fromClient);
        }
    }

    /**
     * Adds a listener for {@code selected-date-changed} events fired by the
     * webcomponent.
     * 
     * @param listener
     *            the listener
     * @return a {@link Registration} for removing the event listener
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Registration addSelectedDateChangeListener(
            ComponentEventListener<SelectedDateChangeEvent<R>> listener) {
        return addListener(SelectedDateChangeEvent.class,
                (ComponentEventListener) listener);
    }
}