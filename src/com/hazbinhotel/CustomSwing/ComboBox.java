package com.hazbinhotel.CustomSwing;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;

import java.awt.*;

public class ComboBox<T> extends JComboBox<T> {
    // Constructor
    public ComboBox() {
        super();
        initialize();
    }

    public ComboBox(T[] items) {
        super(items);
        initialize();
    }

    // Custom initialization
    private void initialize() {
        setUI(new RoundComboBoxUI());
        setOpaque(false);
        setBackground(Color.BLACK);
        setForeground(new Color(201, 38, 84));
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1,
                20, 20);
        super.paintComponent(g);
    }
}

class RoundComboBoxUI extends BasicComboBoxUI {
    Color bgColor = Color.black;

    @Override
    protected void installDefaults() {
        super.installDefaults();
        comboBox.setOpaque(false);
        comboBox.setBackground(bgColor);
        comboBox.setForeground(new Color(201, 38, 84));
        comboBox.setBorder(new RoundBorder());
        UIManager.put("ComboBox.selectionForeground", new ColorUIResource(Color.white));
        UIManager.put("ComboBox.selectionBackground", new ColorUIResource(Color.BLACK));
    }

    @Override
    protected JButton createArrowButton() {
        JButton button = new BasicArrowButton(BasicArrowButton.SOUTH, bgColor, bgColor, Color.white, bgColor);
        Border emptyBorder = BorderFactory.createEmptyBorder();
        button.setBorder(emptyBorder);
        return button;
    }

    @Override
    protected ComboPopup createPopup(){
        return new ComboPopup(comboBox);
    }
}

class ComboPopup extends BasicComboPopup {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ComboPopup(JComboBox combo) {
        super(combo);
        setOpaque(false);
        setBackground(Color.black);
        setForeground(new Color(201, 38, 84));
        setBorder(new RoundBorder());
    }


    @Override
    protected JScrollPane createScroller() {
        ScrollBar sb = new ScrollBar();


        ScrollPane scroll = new ScrollPane(list);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        return scroll;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
    }
}