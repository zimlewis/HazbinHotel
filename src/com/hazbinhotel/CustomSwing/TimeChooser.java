package com.hazbinhotel.CustomSwing;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.GapContent;

public class TimeChooser extends JPanel{
    ComboBox<String>    
    hourChooser = new ComboBox<>(), 
    minuteChooser = new ComboBox<>(), 
    secondChooser = new ComboBox<>();

    JLabel minuteGap = new OutlineLabel("H, ");
    JLabel secondGap = new OutlineLabel("M, ");
    boolean canChooseSecond = false;
    public TimeChooser(){
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.X_AXIS);


        setLayout(boxLayout);
        setBorder(new EmptyBorder(new Insets(0, 0, 0, 0)));

        ArrayList<String> oneToSixty = new ArrayList<>();
        for (int i = 0; i < 60; i++){
            oneToSixty.add(i < 10 ? "0" + String.valueOf(i) : String.valueOf(i));
        }
        LocalTime now = LocalTime.now();

        hourChooser = new ComboBox<>(new String[]{
            "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
            "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"
        });
        hourChooser.setFont(getFont());
        hourChooser.setSelectedIndex(now.getHour());
        add(hourChooser);
        
        add(minuteGap);

        minuteChooser = new ComboBox<>(oneToSixty.toArray(new String[0]));
        minuteChooser.setFont(getFont());
        minuteChooser.setSelectedIndex(now.getMinute());
        add(minuteChooser);

        add(secondGap);

        secondChooser = new ComboBox<>(oneToSixty.toArray(new String[0]));
        secondChooser.setFont(getFont());
        secondChooser.setSelectedIndex(now.getSecond());
        add(secondChooser);

        setSecondChoose(true);


    }

    public void setSecondChoose(boolean canChoose){
        this.canChooseSecond = canChoose;
        if (canChoose){
            remove(secondGap);
            remove(secondChooser);
            add(secondGap);
            add(secondChooser);
        }
        else{
            remove(secondGap);
            remove(secondChooser);
        }
    }

    public LocalTime getTime(){
        LocalTime time = LocalTime.now();
        
        return time.withHour(hourChooser.getSelectedIndex()).withMinute(minuteChooser.getSelectedIndex()).withSecond(canChooseSecond ? secondChooser.getSelectedIndex() : 0).withNano(0);
    }

    public void setTime(LocalTime time){
        hourChooser.setSelectedIndex(time.getHour());
        minuteChooser.setSelectedIndex(time.getMinute());
        secondChooser.setSelectedIndex(time.getSecond());
    }

    @Override
    public void setFont(Font font) {
        super.setFont(font);
        try{
            hourChooser.setFont(font);
            minuteChooser.setFont(font);
            secondChooser.setFont(font);
            minuteGap.setFont(font);
            secondGap.setFont(font);
        }
        catch(Exception ignore){}

    }

    @Override
    public void setForeground(Color fg) {
        super.setForeground(fg);

        try{
            hourChooser.setForeground(fg);
            minuteChooser.setForeground(fg);
            secondChooser.setForeground(fg);
            minuteGap.setForeground(fg);
            secondGap.setForeground(fg);
        }
        catch(Exception ignore){}
    }

    @Override
    public void setBackground(Color bg) {
        Color thisColor = new Color(0, 0, 0, 0);
        super.setBackground(thisColor);

        try{
            hourChooser.setBackground(bg);
            minuteChooser.setBackground(bg);
            secondChooser.setBackground(bg);
            minuteGap.setBackground(bg);
            secondGap.setBackground(bg);
        }
        catch(Exception ignore){}
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        try{
            hourChooser.setEnabled(enabled);
            minuteChooser.setEnabled(enabled);
            secondChooser.setEnabled(enabled);
            minuteGap.setEnabled(enabled);
            secondGap.setEnabled(enabled);
        }
        catch(Exception ignore){}
    }
}
