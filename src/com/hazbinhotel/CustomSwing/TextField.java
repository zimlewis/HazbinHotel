package com.hazbinhotel.CustomSwing;

import javax.swing.*;
import java.awt.*;
/**
 * A custom JTextField with rounded corners.
 */
public class TextField extends JTextField {

    public TextField() {
        super();
        setOpaque(false);
        setBorder(new RoundBorder());
        setBackground(Color.BLACK);
        setForeground(new Color(201, 38, 84));
    }

    public void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1,
                20, 20);
        super.paintComponent(g);
    }

}
