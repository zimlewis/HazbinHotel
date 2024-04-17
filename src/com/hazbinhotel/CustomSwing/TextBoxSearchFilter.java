package com.hazbinhotel.CustomSwing;

import javax.swing.RowFilter;

public class TextBoxSearchFilter extends RowFilter{
    String searchText;
    int column;
    public TextBoxSearchFilter(String searchText, int column){
        this.searchText = searchText;
        this.column = column;
    }
    @Override
    public boolean include(Entry entry) {
        return searchText.equals("") || entry.getStringValue(column).indexOf(searchText) >= 0;
    }
    
}
