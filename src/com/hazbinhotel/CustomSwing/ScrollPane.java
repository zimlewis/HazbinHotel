package com.hazbinhotel.CustomSwing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JScrollPane;

public class ScrollPane extends JScrollPane{
    public ScrollPane(Component view, int vsbPolicy, int hsbPolicy){
        super(view, vsbPolicy, hsbPolicy);
        initialize();
    }

    public ScrollPane(Component view){
        super(view);
        initialize();
    }

    public ScrollPane(int vsbPolicy, int hsbPolicy){
        super(vsbPolicy, hsbPolicy);
        initialize();
    }

    public ScrollPane(){
        super();
        initialize();
    }

    public void initialize(){
        getViewport().setBackground(new Color(0 , 0 , 0 , 255));
        setBorder(new RoundBorder());
        setOpaque(false);
        setVerticalScrollBar(new com.hazbinhotel.CustomSwing.ScrollBar());
        setHorizontalScrollBar(new com.hazbinhotel.CustomSwing.ScrollBar());
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1,
                20, 20);
        super.paintComponent(g);
    }   
}
