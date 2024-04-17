package com.hazbinhotel.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.hazbinhotel.App;
import com.hazbinhotel.CustomSwing.Button;
import com.hazbinhotel.CustomSwing.ModifiedFlowLayout;
import com.hazbinhotel.CustomSwing.RoomLabel;
import com.hazbinhotel.CustomSwing.RoundBorder;
import com.hazbinhotel.CustomSwing.ScrollPane;
import com.zimlewis.Signal;

public class RoomCountPanel extends BasePanel{

    public Signal backButtonPressed = new Signal();
    JPanel roomPanel;

    public RoomCountPanel(App app) {
        super(app);

        roomPanel = new JPanel();
        roomPanel.setLayout(new ModifiedFlowLayout(FlowLayout.LEFT, 10, 10));
        roomPanel.setBackground(new Color(201, 38, 84));
        roomPanel.setAutoscrolls(false);



        JScrollPane roomScrollPane = new JScrollPane(roomPanel);
        roomScrollPane.setBounds(20, 25, getWidth() - 40, 400);
        roomScrollPane.getViewport().setBackground(new Color(201, 38, 84));
        roomScrollPane.setOpaque(false);
        roomScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        roomScrollPane.setVerticalScrollBar(new com.hazbinhotel.CustomSwing.ScrollBar());
        roomScrollPane.setHorizontalScrollBar(new com.hazbinhotel.CustomSwing.ScrollBar());
        // roomScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        


        add(roomScrollPane);


        // Back bu'on
        Button backButton = new Button();
        backButton.setText("<-");
        backButton.setFont(plainFont.deriveFont(20f));
        backButton.setBounds(40, 460, 50, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                backButtonPressed.emitSignal();
            }
        });
        add(backButton);
        

        setBackGroundImage("src/com/hazbinhotel/image/authentication-bg.png");
    
        this.app.mainMenuPanel.roomCountPressed.connectSignal((arg) -> {
            System.out.println(1);
            fill();
        });
    }
    

    void fill(){
        roomPanel.removeAll();
        for (int i = 0; i < this.app.roomsList.size(); i++){
            RoomLabel label = new RoomLabel(app, this.app.roomsList.get(i).getId());
            label.setFont(plainFont.deriveFont(30f));
            roomPanel.add(label);
        }
    }

}
