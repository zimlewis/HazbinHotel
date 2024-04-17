package com.hazbinhotel.CustomSwing;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JScrollBar;
import javax.swing.border.EmptyBorder;

public class ScrollBar extends JScrollBar {

    public ScrollBar() {
        setUI(new ModernScrollBarUI());
        setPreferredSize(new Dimension(8, 8));


        setForeground(new Color(180, 180, 180));
        setBackground(new Color(0, 0, 0, 255));
        setUnitIncrement(30);
        setBorder(new EmptyBorder(0, 0, 0, 0));
    }
}
