package com.hazbinhotel.CustomSwing;

import com.toedter.calendar.*;

import javax.swing.border.AbstractBorder;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import java.util.Calendar;


/**
 * JYearChooser is a bean for choosing a year.
 *
 * @version $LastChangedRevision: 85 $
 * @version $LastChangedDate: 2006-04-28 13:50:52 +0200 (Fr, 28 Apr 2006) $
 */
public class YearChooser extends SpinField {
	private static final long serialVersionUID = 2648810220491090064L;
	protected JDayChooser dayChooser;
    protected int oldYear;
    protected int startYear;
    protected int endYear;

    /**
     * Default JCalendar constructor.
     */
    public YearChooser() {
        setName("JYearChooser");
        Calendar calendar = Calendar.getInstance();
        dayChooser = null;
        setMinimum(calendar.getMinimum(Calendar.YEAR));
        setMaximum(calendar.getMaximum(Calendar.YEAR));
        setValue(calendar.get(Calendar.YEAR));
        setOpaque(false);
        setBorder(new RoundBorder());
        setBackground(Color.BLACK);
        setForeground(new Color(201, 38, 84));
    }

    /**
     * Sets the year. This is a bound property.
     *
     * @param y the new year
     *
     * @see #getYear
     */
    @SuppressWarnings("removal")
    public void setYear(int y) {
        super.setValue(y, true, false);

        if (dayChooser != null) {
            dayChooser.setYear(value);
        }

        spinner.setValue(new Integer(value));
        firePropertyChange("year", oldYear, value);
        oldYear = value;
    }

    /**
     * Sets the year value.
     *
     * @param value the year value
     */
    public void setValue(int value) {
        setYear(value);
    }

    /**
     * Returns the year.
     *
     * @return the year
     */
    public int getYear() {
        return super.getValue();
    }

    /**
     * Convenience method set a day chooser that might be updated directly.
     *
     * @param dayChooser the day chooser
     */
    public void setDayChooser(JDayChooser dayChooser) {
        this.dayChooser = dayChooser;
    }

    /**
     * Returns the endy ear.
     *
     * @return the end year
     */
    public int getEndYear() {
        return getMaximum();
    }

    /**
     * Sets the end ear.
     *
     * @param endYear the end year
     */
    public void setEndYear(int endYear) {
        setMaximum(endYear);
    }

    /**
     * Returns the start year.
     *
     * @return the start year.
     */
    public int getStartYear() {
        return getMinimum();
    }

    /**
     * Sets the start year.
     *
     * @param startYear the start year
     */
    public void setStartYear(int startYear) {
        setMinimum(startYear);
    }

    /**
     * Creates a JFrame with a JYearChooser inside and can be used for testing.
     *
     * @param s command line arguments
     */

    public void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1,
                20, 20);
        super.paintComponent(g);
    }
}

// public class YearChooser extends JYearChooser {

//     // Constructor
//     public YearChooser() {
//         super();
//         setOpaque(false);
//         setBorder(new RoundBorder());
//         setBackground(Color.BLACK);
//         setForeground(new Color(201, 38, 84));

//     }

//     public void paintComponent(Graphics g) {
//         g.setColor(getBackground());
//         g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1,
//                 20, 20);
//         super.paintComponent(g);
//     }
// }

