package com.hazbinhotel.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.hazbinhotel.App;

public class BasePanel extends JPanel{
    JLabel backgroundLabel = new JLabel();
    Font monogramFont = new JLabel().getFont();
    Font plainFont = new JLabel().getFont();
    App app;


    public BasePanel(App app){
        setLayout(null);
        setSize(960 , 540);
        setBackground(Color.BLACK);

        this.app = app;

        try{
            File monogramFontFile = new File("src/com/hazbinhotel/font/KGModernMonogram.ttf");
            monogramFont = Font.createFont(Font.TRUETYPE_FONT, monogramFontFile);

            File plainFile = new File("src/com/hazbinhotel/font/KGModernMonogramPlain.ttf");
            plainFont = Font.createFont(Font.TRUETYPE_FONT, plainFile);  
        }
        catch (Exception e){
        }
    }

    public void setBackGroundImage(String imgAddress){
        //Background
        remove(backgroundLabel);

        ImageIcon backgroundLabelImage = new ImageIcon(
            new ImageIcon(imgAddress)
                .getImage()
                    .getScaledInstance(960, 540, Image.SCALE_SMOOTH)
        );
        

        backgroundLabel = new JLabel(backgroundLabelImage);
        backgroundLabel.setBounds(0, 0, 960, 540);

        add(backgroundLabel); 
    }
    

}
