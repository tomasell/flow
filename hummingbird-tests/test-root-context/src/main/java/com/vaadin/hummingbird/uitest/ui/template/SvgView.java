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
package com.vaadin.hummingbird.uitest.ui.template;

import com.vaadin.hummingbird.html.Div;
import com.vaadin.hummingbird.router.View;
import com.vaadin.ui.Html;
import com.vaadin.ui.Template;

public class SvgView extends Div implements View {

    public static class SvgTemplate extends Template {

    }

    public SvgView() {
        add(new SvgTemplate());
        add(new Html(
                "<div style='display: inline-block; border: 1px solid black;'><svg xmlns=\"http://www.w3.org/2000/svg\" height=\"100\" width=\"200\"\n"
                        + " viewBox=\"0 0 15 15\">\n"
                        + " <text x=\"0\" y=\"15\" fill=\"red\">SVG from file!</text>\n"
                        + "</svg></div>" + ""));
    }
}
