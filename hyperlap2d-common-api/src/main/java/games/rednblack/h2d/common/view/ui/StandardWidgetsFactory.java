/*
 * ******************************************************************************
 *  * Copyright 2015 See AUTHORS file.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *   http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *****************************************************************************
 */

package games.rednblack.h2d.common.view.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.TooltipManager;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.util.InputValidator;
import com.kotcrab.vis.ui.widget.*;
import com.kotcrab.vis.ui.widget.spinner.IntSpinnerModel;
import com.kotcrab.vis.ui.widget.spinner.Spinner;
import com.puremvc.patterns.facade.Facade;
import games.rednblack.h2d.common.view.ui.listener.CursorListener;
import games.rednblack.h2d.common.view.ui.listener.ScrollFocusListener;
import games.rednblack.h2d.common.view.ui.widget.TintButton;

/**
 * Creates standard widgets like labels or text fields with provided standard HyperLap2D specific visual style.
 */
public class StandardWidgetsFactory {

    private static Facade facade;
    public static void init(Facade f) {
        facade = f;
    }

    private StandardWidgetsFactory() {
    }

    public static VisLabel createLabel(String text, int alignment) {
        return createLabel(text, "small", alignment);
    }

    public static VisLabel createLabel(String text, String style, int alignment) {
        Skin skin = VisUI.getSkin();
        VisLabel visLabel = new VisLabel(text, alignment);
        visLabel.setStyle(skin.get(style, Label.LabelStyle.class));
        return visLabel;
    }

    public static VisLabel createLabel(String text) {
        return createLabel(text, Align.right);
    }

    public static VisTextField createTextField() {
        return createTextField("default");
    }
    
    public static VisTextField createTextField(String style) {
    	VisTextField visTextField = new VisTextField("", style);
    	visTextField.addListener(new CursorListener(Cursors.TEXT, facade));
    	return visTextField;
    }

    public static VisTextField createTextField(String style, boolean textCursor) {
    	VisTextField visTextField = new VisTextField();
        Skin skin = VisUI.getSkin();
        visTextField.setStyle(skin.get(style, VisTextField.VisTextFieldStyle.class));
        if(textCursor)
        	visTextField.addListener(new CursorListener(Cursors.TEXT, facade));
        return visTextField;
    }
    
    public static VisTextField createTextField(String style, VisTextField.TextFieldFilter textFieldFilter) {
        VisTextField visTextField = createTextField(style);
        visTextField.setTextFieldFilter(textFieldFilter);
        return visTextField;
    }

    public static VisTextField createTextField(String text, String style, VisTextField.TextFieldFilter textFieldFilter) {
        VisTextField visTextField = createTextField(style);
        visTextField.setText(text);
        visTextField.setTextFieldFilter(textFieldFilter);
        return visTextField;
    }
    
    public static VisValidatableTextField createValidableTextField (InputValidator inputValidator) {
        return createValidableTextField("default", inputValidator);
    }

    public static VisValidatableTextField createValidableTextField (String style, InputValidator inputValidator) {
        VisValidatableTextField visTextField = new VisValidatableTextField(inputValidator);
        Skin skin = VisUI.getSkin();
        visTextField.setStyle(skin.get(style, VisTextField.VisTextFieldStyle.class));
        visTextField.addListener(new CursorListener(Cursors.TEXT, facade));
        return visTextField;
    }

    public static VisValidatableTextField createValidableTextField (String text, String style, InputValidator inputValidator, VisTextField.TextFieldFilter textFieldFilter) {
        VisValidatableTextField visTextField = createValidableTextField(style, inputValidator);
        visTextField.setTextFieldFilter(textFieldFilter);
        visTextField.addListener(new CursorListener(Cursors.TEXT, facade));
        visTextField.setText(text);
        return visTextField;
    }

    public static Spinner createNumberSelector(int min, int max) {
        return createNumberSelector(0, min, max);
    }

    public static Spinner createNumberSelector(int initialValue, int min, int max) {
        return createNumberSelector("default", initialValue, min, max, 1);
    }

    public static Spinner createNumberSelector(String style, int min, int max) {
        return createNumberSelector(style, 0, min, max, 1);
    }

    public static Spinner createNumberSelector(String style, int initialValue, int min, int max, int step) {
        return new Spinner(style, "", new IntSpinnerModel(initialValue, min, max, step));
    }

    public static VisTextArea createTextArea() {
        return createTextArea("default");
    }

    public static VisTextArea createTextArea(String style) {
        VisTextArea visTextArea = new VisTextArea("", style);
        visTextArea.addListener(new CursorListener(Cursors.TEXT, facade));
        return visTextArea;
    }

    public static VisCheckBox createCheckBox() {
        return createCheckBox("");
    }

    public static VisCheckBox createCheckBox(String text) {
        VisCheckBox visCheckBox = new VisCheckBox(text);
        visCheckBox.addListener(new CursorListener(Cursors.FINGER, facade));
        return visCheckBox;
    }

    public static <T> VisSelectBox<T> createSelectBox(Class<T> type) {
        return createSelectBox("default", type);
    }

    public static <T> VisSelectBox<T> createSelectBox(String style, Class<T> type) {
        VisSelectBox<T> visSelectBox = new VisSelectBox<T>(style);
        visSelectBox.clearListeners();
        visSelectBox.addListener(new CursorListener(Cursors.FINGER, facade));
        visSelectBox.addListener(new ClickListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if (pointer == 0 && button != 0) return false;
                if (visSelectBox.isDisabled()) return false;
                if (visSelectBox.getScrollPane().hasParent()) {
                    visSelectBox.hideList();
                } else {
                    visSelectBox.showList();
                    if (visSelectBox.getScrollPane().getWidth() > visSelectBox.getWidth()) {
                        visSelectBox.getScrollPane().setX(visSelectBox.getScrollPane().getX() + (visSelectBox.getWidth() - visSelectBox.getScrollPane().getWidth()));
                    }
                }
                return true;
            }
        });
        return visSelectBox;
    }

    public static TintButton createTintButton() {
        return createTintButton(29,21);
    }

    public static TintButton createTintButton(int width, int height) {
        TintButton visCheckBox = new TintButton(width, height);
        visCheckBox.addListener(new CursorListener(Cursors.EYEDROPPER, facade));
        return visCheckBox;
    }

    public static VisScrollPane createScrollPane(Actor actor) {
        VisScrollPane scrollPane = new VisScrollPane(actor);
        scrollPane.addListener(new ScrollFocusListener());
        scrollPane.setFlickScroll(false);
        return scrollPane;
    }

    public static void addVisTooltip(Actor actor, String text) {
        new Tooltip.Builder(text).target(actor).build();
    }

    public static void addTooltip(Actor actor, String text) {
        TooltipManager.getInstance().initialTime = 1;
        TooltipManager.getInstance().hideAll();

        TextTooltip tooltip = new TextTooltip(text, VisUI.getSkin());
        tooltip.getContainer().pad(5);
        actor.addListener(tooltip);
    }

    public static VisTextButton createTextButton(String text) {
    	return createTextButton(text, "default");
	}

	public static VisTextButton createTextButton(String text, String style) {
        return new VisTextButton(text, style);
	}

    public static VisImageButton createImageButton(String style) {
        VisImageButton button = new VisImageButton(style);
        button.addListener(new CursorListener(Cursors.FINGER, facade));
        return button;
    }

    public static VisSlider createSlider(float min, float max, float step) {
        VisSlider slider = new VisSlider(min, max, step, false);
        slider.addListener(new CursorListener(Cursors.FINGER, facade));
        return slider;
    }
}
