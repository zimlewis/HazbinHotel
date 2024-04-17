package com.hazbinhotel.CustomSwing;



import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.zimlewis.Signal;


public class MonthChooser extends ComboBox<String> implements ItemListener,ChangeListener {

    public static final int NUMERIC = 0;
    public static final int FULL = 1;
    public static final int SHORTEN = 2;

    public Signal monthChanged = new Signal();
    public static String[] monthsNameFull = {
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    };
    public static String[] monthsNameNumeric = {
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "10",
        "11",
        "12"
    };
    public static String[] monthsNameShorten = {
        "JAN",
        "FEB",
        "MAR",
        "APR",
        "MAY",
        "JUN",
        "JUL",
        "AUG",
        "SEP",
        "OCT",
        "NOV",
        "DEC"
    };

    private int month = 0;

    public MonthChooser() {
        super(monthsNameFull);
        this.addItemListener(this);
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        this.setSelectedIndex(cal.get(Calendar.MONTH));
    }

    public String[] getItemArray(){
        ArrayList<String> arrayList = new ArrayList<>();

        for (int i = 0; i < getItemCount(); i++){
            arrayList.add(getItemAt(i));
        }

        String[] items = arrayList.toArray(new String[arrayList.size()]);

        return items;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int selectedMonth) {
        setSelectedIndex(selectedMonth);
    }

    public void setType(int type){
        int currentSelection = this.getSelectedIndex();
        if (type == NUMERIC){
            setItemArray(monthsNameNumeric);
        }
        if (type == FULL){
            setItemArray(monthsNameFull);
        }
        if (type == SHORTEN){
            setItemArray(monthsNameShorten);
        }
        this.setSelectedIndex(currentSelection);
    }

    public void setItemArray(String[] items){
        this.removeAllItems();
        for (int i = 0; i < items.length; i++){
            addItem(items[i]);
        }
    }

    @Override
    public void stateChanged(ChangeEvent arg0) {
       
    }

    @Override
    public void itemStateChanged(ItemEvent arg0) {
        monthChanged.emitSignal();
        month = getSelectedIndex();
    }
}

