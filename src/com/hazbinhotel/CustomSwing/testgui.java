package com.hazbinhotel.CustomSwing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;



public class testgui extends JFrame{
    public static void main(String[] args){
        testgui tg = new testgui();
    }

    public testgui(){
        setSize(500 , 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null); // Use null layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        YearChooser yc = new YearChooser();
        MonthChooser mc = new MonthChooser();

        DayChooser dayChooser = new DayChooser(yc, mc);
        dayChooser.setBounds(20, 20, 300, 30);
        add(dayChooser);

        
        setVisible(true);
    }

}
