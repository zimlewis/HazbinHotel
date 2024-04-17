package com.hazbinhotel.CustomSwing;

import javax.swing.JSpinner;

public class Spinner extends JSpinner {

    public void setLabelText(String text) {
        SpinnerUI.Editor editor = (SpinnerUI.Editor) getEditor();
        editor.setText(text);
    }

    public String getLabelText() {
        SpinnerUI.Editor editor = (SpinnerUI.Editor) getEditor();
        return editor.getText();
    }

    public Spinner() {
        setOpaque(false);
        setUI(new SpinnerUI());
    }
}