package com.hazbinhotel.CustomSwing;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.zimlewis.Signal;

public class DayChooser extends ComboBox<String> implements ItemListener,ChangeListener{
    YearChooser yearChooser;
    MonthChooser monthChooser;
    private Signal monthOrYearChanged = new Signal();
    private int chooseRanges = 31;
    private int day = 0;

    public DayChooser(YearChooser yearChooser , MonthChooser monthChooser) {
        super();
        this.yearChooser = yearChooser;
        this.monthChooser = monthChooser;
        
        for (int i = 1 ; i <= this.chooseRanges; i++) {
            this.addItem(String.valueOf(i));
        }
        
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        this.setSelectedIndex(cal.get(Calendar.DAY_OF_MONTH) - 1);


        monthOrYearChanged.connectSignal((arg) -> {
            onMonthOrYearChanged();
        });

        this.yearChooser.addPropertyChangeListener("year" , evt -> {
            monthOrYearChanged.emitSignal();
        });


        this.monthChooser.monthChanged.connectSignal((arg) -> {
            monthOrYearChanged.emitSignal();
        });
        onMonthOrYearChanged();
    }

    public YearChooser getYearChooser() {
        return yearChooser;
    }

    public void setYearChooser(YearChooser yearChooser) {
        this.yearChooser = yearChooser;
    }

    public MonthChooser getMonthChooser() {
        return monthChooser;
    }

    public void setMonthChooser(MonthChooser monthChooser) {
        this.monthChooser = monthChooser;
    }

    private void setChooseRanges(int chooseRanges) {
        this.chooseRanges = chooseRanges;
        int currentSelected = getSelectedIndex();


        
        this.removeAllItems();
        for (int i = 1 ; i <= this.chooseRanges; i++) {
            this.addItem(String.valueOf(i));
        }

        this.setSelectedIndex(currentSelected);
    }

    public void onMonthOrYearChanged(){
        int month = this.monthChooser.getMonth() + 1;
        int year = this.yearChooser.getYear();


        this.setChooseRanges(31);
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            this.setChooseRanges(30);
        }
        if (month == 2) {
            this.setChooseRanges(28);

            if (isLeapYear(year)) {
                this.setChooseRanges(29);
            }
        }
    }

    boolean isLeapYear(int year) {
        // A leap year is divisible by 4 but not by 100, or it is divisible by 400
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public int getDay() {
        return getSelectedIndex();
    }
    
    public void setDay(int day){
        this.day = day;
        setSelectedIndex(day);
    }
    
    @Override
    public void itemStateChanged(ItemEvent arg0) {
        day = getSelectedIndex();
    }

    public String[] getItemArray(){
        ArrayList<String> arrayList = new ArrayList<>();

        for (int i = 0; i < getItemCount(); i++){
            arrayList.add(getItemAt(i));
        }

        String[] items = arrayList.toArray(new String[arrayList.size()]);

        return items;
    }

    @Override
    public void stateChanged(ChangeEvent arg0) {

    }
}
