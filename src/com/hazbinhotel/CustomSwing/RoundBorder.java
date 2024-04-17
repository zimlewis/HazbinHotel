package com.hazbinhotel.CustomSwing;

import javax.swing.border.AbstractBorder;
import java.awt.*;

public class RoundBorder extends AbstractBorder {
    public void paintBorder(Component c, Graphics g,
            int x, int y,
            int width, int height) {
        Color oldColor = g.getColor();

        g.setColor(Color.black);
        g.drawRoundRect(x, y, width - 1, height - 1,
                30, 30);

        g.setColor(oldColor);
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(4, 4, 4, 4);
    }

    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.top = insets.right = insets.bottom = 4;
        return insets;
    }

}