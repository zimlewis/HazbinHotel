package com.hazbinhotel.CustomSwing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.*;

public class Table extends JTable {
    public Table(TableModel dm) {
        super(dm);
        
        Font monogramFont = new JLabel().getFont();;
        Font plainFont = new JLabel().getFont();;

        try{
            File monogramFontFile = new File("src/com/hazbinhotel/font/KGModernMonogram.ttf");
            monogramFont = Font.createFont(Font.TRUETYPE_FONT, monogramFontFile);

            File plainFile = new File("src/com/hazbinhotel/font/KGModernMonogramPlain.ttf");
            plainFont = Font.createFont(Font.TRUETYPE_FONT, plainFile);  
        }
        catch (Exception e){
        }


        setBackground(new Color(0, 0, 0, 255));
        setRowMargin(0);

        setRowHeight(30);

        setForeground(Color.red);
        setGridColor(new Color(0, 0, 0, 0));

        getTableHeader().setDefaultRenderer(new CustomHeaderRenderer());

        setFont(plainFont.deriveFont(20f));
        

        setIntercellSpacing(new Dimension(0 , 0));
    }

    class CustomHeaderRenderer implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel headerLabel = new JLabel(value.toString());
            Font monogramFont = new JLabel().getFont();;
            Font plainFont = new JLabel().getFont();;
    
            try{
                File monogramFontFile = new File("src/com/hazbinhotel/font/KGModernMonogram.ttf");
                monogramFont = Font.createFont(Font.TRUETYPE_FONT, monogramFontFile);
    
                File plainFile = new File("src/com/hazbinhotel/font/KGModernMonogramPlain.ttf");
                plainFont = Font.createFont(Font.TRUETYPE_FONT, plainFile);  
            }
            catch (Exception e){
            }
            headerLabel.setHorizontalAlignment(JLabel.CENTER);
            headerLabel.setBorder(BorderFactory.createLineBorder(Color.RED)); // Set your desired grid color here
            headerLabel.setOpaque(true);
            headerLabel.setFont(plainFont.deriveFont(20f));
            headerLabel.setBackground(new Color(0, 0, 0, 255));
            headerLabel.setForeground(Color.red);
            return headerLabel;
        }
    }
}
