package com.hazbinhotel.CustomSwing;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class NumberOnlyTextField extends TextField {
    public NumberOnlyTextField() {
        ((AbstractDocument) getDocument()).setDocumentFilter(new NumberOnlyFilter());
    }

    private static class NumberOnlyFilter extends DocumentFilter {
        @Override
        public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            StringBuilder sb = new StringBuilder();
            sb.append(fb.getDocument().getText(0, fb.getDocument().getLength()));
            sb.insert(offset, string);

            if (isNumber(sb.toString())) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            StringBuilder sb = new StringBuilder();
            sb.append(fb.getDocument().getText(0, fb.getDocument().getLength()));
            sb.replace(offset, offset + length, text);

            if (isNumber(sb.toString())) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        private boolean isNumber(String s) {
            return s.isEmpty() || s.matches("\\d*\\.?\\d*");
        }
    }
}
