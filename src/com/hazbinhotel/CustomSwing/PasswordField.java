package com.hazbinhotel.CustomSwing;


import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.awt.*;

public class PasswordField extends JPasswordField {
    boolean hidden;
    JLabel showToggleLabel = new JLabel();
    Font textFont = new JLabel().getFont();

    public PasswordField() {
        super();
        setOpaque(false);
        setBorder(new RoundBorder());
        setBackground(Color.BLACK);
        setForeground(new Color(201, 38, 84));
        setLayout(new BorderLayout());
        setHidden(true);


        showToggleLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        showToggleLabel.setBackground(getBackground());
        showToggleLabel.setOpaque(false);
        showToggleLabel.setPreferredSize(new Dimension(20, 20));
        showToggleLabel.setFont(getFont());
        showToggleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    setHidden(!isHidden());
                }
            }
        });
        
        add(showToggleLabel, BorderLayout.EAST);

    }

    public void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1,
                20, 20);
        super.paintComponent(g);
    }

    public void setHidden(boolean hidden){
        this.hidden = hidden;
        this.setEchoChar(!this.hidden ? '\u0000' : '•');//• ඞ
        showToggleLabel.setText(this.hidden ? "Ø" : "O");
        this.setFont(this.hidden ? new JLabel().getFont() : textFont);
    }

    public boolean isHidden(){
        return this.hidden;
    }

    public void setTextFont(Font f){
        textFont = f;
    }

}
