package com.hazbinhotel.CustomSwing;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JTextArea;

public class TextArea extends JTextArea{
    public TextArea(){
        super();
        setOpaque(false);
        setBorder(new RoundBorder());
        setBackground(Color.BLACK);
        setForeground(new Color(201, 38, 84));
        setLineWrap(true);
        setWrapStyleWord(true);
    }

    public void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1,
                20, 20);
        super.paintComponent(g);
    }
}
