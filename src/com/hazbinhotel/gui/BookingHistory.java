package com.hazbinhotel.gui;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.MouseInputAdapter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import com.hazbinhotel.App;
import com.hazbinhotel.CustomSwing.Button;
import com.hazbinhotel.CustomSwing.ComboBox;
import com.hazbinhotel.CustomSwing.DayChooser;
import com.hazbinhotel.CustomSwing.MonthChooser;
import com.hazbinhotel.CustomSwing.OutlineLabel;
import com.hazbinhotel.CustomSwing.ScrollPane;
import com.hazbinhotel.CustomSwing.Table;
import com.hazbinhotel.CustomSwing.TextBoxSearchFilter;
import com.hazbinhotel.CustomSwing.TextField;
import com.hazbinhotel.CustomSwing.TimeChooser;
import com.hazbinhotel.CustomSwing.YearChooser;
import com.hazbinhotel.entity.Bill;
import com.hazbinhotel.entity.Booking;
import com.hazbinhotel.entity.Customer;
import com.hazbinhotel.entity.Room;
import com.hazbinhotel.entity.Staff;
import com.zimlewis.Signal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;

public class BookingHistory extends BasePanel{
    JPanel tablePanel, userInteractionPanel;
    
    TextField customerField, roomField, staffField, billField;
    YearChooser checkInYearChooser, checkOutYearChooser;
    MonthChooser checkInMonthChooser, checkOutMonthChooser;
    DayChooser checkInDayChooser, checkOutDayChooser;
    TimeChooser checkInTimeChooser, checkOutTimeChooser;


    DefaultTableModel bookingHistoryTableModel = new DefaultTableModel(){
        @Override
        public boolean isCellEditable(int row, int column) {
            // Make all cells uneditable
            return false;
        }
    };
    public Signal backButtonPressed = new Signal();

    public BookingHistory(App a){
        super(a);
        // user interaction panel
        userInteractionPanel = new JPanel();
        userInteractionPanel.setLayout(null);
        userInteractionPanel.setBackground(new Color(255, 255, 255, 0));
        userInteractionPanel.setVisible(false);
        userInteractionPanel.setBounds(20, 135, getWidth() - 40, 321);
        add(userInteractionPanel);

        // table panel
        tablePanel = new JPanel();
        tablePanel.setLayout(null);
        tablePanel.setBackground(new Color(255, 255, 255, 0));
        tablePanel.setVisible(true);
        tablePanel.setBounds(20, 135, getWidth() - 40, 321);
        add(tablePanel);

        // Customer
        JLabel customerLabel = new JLabel("Customer:", SwingConstants.RIGHT);
        customerLabel.setBounds(170, 20, 150, 40);
        customerLabel.setForeground(Color.BLACK);
        customerLabel.setFont(plainFont.deriveFont(20f));
        
        userInteractionPanel.add(customerLabel);

        customerField = new TextField();
        customerField.setBounds(330, 20, 190, 40);
        customerField.setFont(plainFont.deriveFont(20f));
        customerField.setEnabled(false);

        userInteractionPanel.add(customerField);

        // Room
        JLabel roomLabel = new JLabel("Room:", SwingConstants.RIGHT);
        roomLabel.setBounds(170 + (userInteractionPanel.getWidth() / 2 - 100), 20, 150, 40);
        roomLabel.setForeground(Color.BLACK);
        roomLabel.setFont(plainFont.deriveFont(20f));
        
        userInteractionPanel.add(roomLabel);

        roomField = new TextField();
        roomField.setBounds(330 + (userInteractionPanel.getWidth() / 2 - 100), 20, 190, 40);
        roomField.setFont(plainFont.deriveFont(20f));
        roomField.setEnabled(false);

        userInteractionPanel.add(roomField);

        // Check-in time
        JLabel checkInTimeLabel = new JLabel("Check-in time:", SwingConstants.RIGHT);
        checkInTimeLabel.setBounds(170, 120, 150, 40);
        checkInTimeLabel.setForeground(Color.BLACK);
        checkInTimeLabel.setFont(plainFont.deriveFont(20f));

        userInteractionPanel.add(checkInTimeLabel);

        checkInYearChooser = new YearChooser();
        checkInYearChooser.setBounds(330, 120, 190 / 3, 40);
        checkInYearChooser.setFont(plainFont.deriveFont(20f));
        checkInYearChooser.setEnabled(false);

        userInteractionPanel.add(checkInYearChooser);

        checkInMonthChooser = new MonthChooser();
        checkInMonthChooser.setBounds(330 + 190 / 3 + 10, 120, 190 / 3 - 10, 40);
        checkInMonthChooser.setFont(plainFont.deriveFont(20f));
        checkInMonthChooser.setType(MonthChooser.NUMERIC);
        checkInMonthChooser.setEnabled(false);

        userInteractionPanel.add(checkInMonthChooser);

        checkInDayChooser = new DayChooser(checkInYearChooser, checkInMonthChooser);
        checkInDayChooser.setBounds(330 + 190 - (190 / 3 - 10), 120, 190 / 3 - 10, 40);
        checkInDayChooser.setFont(plainFont.deriveFont(20f));
        checkInDayChooser.setEnabled(false);

        userInteractionPanel.add(checkInDayChooser);

        checkInTimeChooser = new TimeChooser();
        checkInTimeChooser.setSecondChoose(false);
        checkInTimeChooser.setFont(plainFont.deriveFont(20f));
        checkInTimeChooser.setBounds(330, 170, 190, 40);
        checkInTimeChooser.setEnabled(false);

        userInteractionPanel.add(checkInTimeChooser);

        // Check-out time
        JLabel checkOutTimeLabel = new JLabel("Check-out time:", SwingConstants.RIGHT);
        checkOutTimeLabel.setBounds(170 + (userInteractionPanel.getWidth() / 2 - 100), 120, 150, 40);
        checkOutTimeLabel.setForeground(Color.BLACK);
        checkOutTimeLabel.setFont(plainFont.deriveFont(20f));

        userInteractionPanel.add(checkOutTimeLabel);

        checkOutYearChooser = new YearChooser();
        checkOutYearChooser.setBounds(330 + (userInteractionPanel.getWidth() / 2 - 100), 120, 190 / 3, 40);
        checkOutYearChooser.setFont(plainFont.deriveFont(20f));
        checkOutYearChooser.setEnabled(false);

        userInteractionPanel.add(checkOutYearChooser);

        checkOutMonthChooser = new MonthChooser();
        checkOutMonthChooser.setBounds((userInteractionPanel.getWidth() / 2 - 100) + 330 + 190 / 3 + 10, 120, 190 / 3 - 10, 40);
        checkOutMonthChooser.setFont(plainFont.deriveFont(20f));
        checkOutMonthChooser.setType(MonthChooser.NUMERIC);
        checkOutMonthChooser.setEnabled(false);

        userInteractionPanel.add(checkOutMonthChooser);

        checkOutDayChooser = new DayChooser(checkInYearChooser, checkInMonthChooser);
        checkOutDayChooser.setBounds((userInteractionPanel.getWidth() / 2 - 100) + 330 + 190 - (190 / 3 - 10), 120, 190 / 3 - 10, 40);
        checkOutDayChooser.setFont(plainFont.deriveFont(20f));
        checkOutDayChooser.setEnabled(false);

        userInteractionPanel.add(checkOutDayChooser);

        checkOutTimeChooser = new TimeChooser();
        checkOutTimeChooser.setSecondChoose(false);
        checkOutTimeChooser.setFont(plainFont.deriveFont(20f));
        checkOutTimeChooser.setBounds((userInteractionPanel.getWidth() / 2 - 100) + 330, 170, 190, 40);
        checkOutTimeChooser.setEnabled(false);

        userInteractionPanel.add(checkOutTimeChooser);

        // Staff
        JLabel staffLabel = new JLabel("Staff:", SwingConstants.RIGHT);
        staffLabel.setBounds(170, 240, 150, 40);
        staffLabel.setForeground(Color.BLACK);
        staffLabel.setFont(plainFont.deriveFont(20f));

        userInteractionPanel.add(staffLabel);

        staffField = new TextField();
        staffField.setBounds(330, 240, 190, 40);
        staffField.setEnabled(false);
        staffField.setFont(plainFont.deriveFont(20f));

        userInteractionPanel.add(staffField);

        // Bill
        JLabel billLabel = new JLabel("Bill:", SwingConstants.RIGHT);
        billLabel.setBounds((userInteractionPanel.getWidth() / 2 - 100) + 170, 240, 150, 40);
        billLabel.setForeground(Color.BLACK);
        billLabel.setFont(plainFont.deriveFont(20f));

        userInteractionPanel.add(billLabel);

        billField = new TextField();
        billField.setBounds((userInteractionPanel.getWidth() / 2 - 100) + 330, 240, 190, 40);
        billField.setEnabled(false);
        billField.setFont(plainFont.deriveFont(20f));

        userInteractionPanel.add(billField);

        
        // Back to list bu'on
        Button backToListButton = new Button();
        backToListButton.setBounds(20 , 20, 130, 40);
        backToListButton.setText("<- Back to list");
        backToListButton.setFont(plainFont.deriveFont(20f));
        backToListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                toggleTable();
                fillToTable();
            }
        });

        userInteractionPanel.add(backToListButton);



        bookingHistoryTableModel.addColumn("Room Name");
        bookingHistoryTableModel.addColumn("Customer Name");
        bookingHistoryTableModel.addColumn("Bill");
        bookingHistoryTableModel.addColumn("Staff");
        bookingHistoryTableModel.addColumn("Check in date");
        bookingHistoryTableModel.addColumn("Check out date");

        Table bookingHistoryTable = new Table(bookingHistoryTableModel);
        bookingHistoryTable.getTableHeader().setReorderingAllowed(false);
        bookingHistoryTable.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = bookingHistoryTable.getSelectedRow();
                    Booking booking = app.bookingDAO.getPastBooking().get(row);
                    Bill bill = app.billDAO.getByBooking(booking.getId());
                    Room room = app.roomDAO.get(booking.getRoom());
                    Customer customer = app.customerDAO.get(booking.getCustomer());
                    Staff staff = app.staffDAO.get(bill.getStaff())==null?null:app.staffDAO.get(bill.getStaff());
                    toggleTable();

                    customerField.setText(customer.getName());
                    roomField.setText(room.getName());
                    staffField.setText(staff==null?"":staff.getName());
                    billField.setText(String.valueOf(bill.getId()));

                    checkInYearChooser.setYear(booking.getCheckIn().getYear());
                    checkInMonthChooser.setMonth(booking.getCheckIn().getMonthValue() - 1);
                    checkInDayChooser.setDay(booking.getCheckIn().getDayOfMonth() - 1);
                    
                    checkOutYearChooser.setYear(booking.getCheckOut().getYear());
                    checkOutMonthChooser.setMonth(booking.getCheckOut().getMonthValue() - 1);
                    checkOutDayChooser.setDay(booking.getCheckOut().getDayOfMonth() - 1);

                    checkInTimeChooser.setTime(booking.getCheckIn().toLocalTime());
                    checkOutTimeChooser.setTime(booking.getCheckOut().toLocalTime());
                    
                }
            }
        });
        
        ScrollPane scroll = new ScrollPane(bookingHistoryTable);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBounds(16 , 66 , tablePanel.getWidth() - 32 , 200);

        tablePanel.add(scroll);

        // Search box
        TextField searchField = new TextField();
        searchField.setBounds(16 , 16, tablePanel.getWidth() - 148, 40);
        searchField.setFont(plainFont.deriveFont(20f));
        tablePanel.add(searchField);

        Button searchButton = new Button();
        searchButton.setText("Search");
        searchButton.setBounds(tablePanel.getWidth() - 116, 16, 100, 40);
        searchButton.setFont(plainFont.deriveFont(20f));
        searchButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                TextBoxSearchFilter search = new TextBoxSearchFilter(searchField.getText(), 2);
                TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(bookingHistoryTableModel);
                rowSorter.setRowFilter(search);
                bookingHistoryTable.setRowSorter(rowSorter);
            }
            
        });
        tablePanel.add(searchButton);

        // Export bu'on
        Button exportButton = new Button();
        exportButton.setIcon(
            new ImageIcon(new ImageIcon("src/com/hazbinhotel/image/excel.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH))
        );
        exportButton.setBounds(getWidth() - 40 - 50, 460, 50, 50);
        exportButton.setFont(plainFont.deriveFont(20f));
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser fc = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files", "xlsx");
                
                fc.setFileFilter(filter);


                int result = fc.showSaveDialog(app);

                if (result == JFileChooser.APPROVE_OPTION) {
                    String filePath = fc.getSelectedFile().getAbsolutePath();
                    if (!filePath.toLowerCase().endsWith(".xlsx")) {
                        filePath += ".xlsx";
                    }
                    try {
                        XSSFWorkbook workbook = new XSSFWorkbook();
                        XSSFSheet sheet = workbook.createSheet("Java Books");
                        
                        Row headerRow = sheet.createRow(0);
                        int headerCellCount = 0;
                        
                        
                        headerRow.createCell(++headerCellCount).setCellValue("Customer");
                        headerRow.createCell(++headerCellCount).setCellValue("Room");
                        headerRow.createCell(++headerCellCount).setCellValue("Check in");
                        headerRow.createCell(++headerCellCount).setCellValue("Check out");

                        int rowCount = 1;

                        for (Booking booking : app.bookingDAO.getPastBooking()){
                            Row row = sheet.createRow(rowCount++);
                            int cellCount = 0;
                            row.createCell(cellCount++).setCellValue(rowCount - 1);
                            row.createCell(cellCount++).setCellValue(app.customerDAO.get(booking.getCustomer()).getName());
                            row.createCell(cellCount++).setCellValue(app.roomDAO.get(booking.getRoom()).getName());
                            row.createCell(cellCount++).setCellValue(String.format("%d-%s-%d %02d:%02d", booking.getCheckIn().getYear(), String.valueOf(booking.getCheckIn().getMonth()), booking.getCheckIn().getDayOfMonth(), booking.getCheckIn().getHour() , booking.getCheckIn().getMinute()));
                            row.createCell(cellCount++).setCellValue(String.format("%d-%s-%d %02d:%02d", booking.getCheckOut().getYear(), String.valueOf(booking.getCheckOut().getMonth()), booking.getCheckOut().getDayOfMonth(), booking.getCheckOut().getHour() , booking.getCheckOut().getMinute()));
                        }

                        FileOutputStream outputStream = new FileOutputStream(filePath);
                        workbook.write(outputStream);
                    } 
                    catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Error occurred while saving the file.");
                    }
                }
            }
        });
        add(exportButton);

        // Back bu'on
        Button backButton = new Button();
        backButton.setText("<-");
        backButton.setFont(plainFont.deriveFont(20f));
        backButton.setBounds(40, 460, 50, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                backButtonPressed.emitSignal();
                tablePanel.setVisible(true);
                userInteractionPanel.setVisible(false);
                fillToTable();
            }
        });
        add(backButton);

        // title label
        OutlineLabel titleLabel = new OutlineLabel("BOOKING HISTORY");
        titleLabel.setOutlineColor(new Color(201, 38, 84));
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(30, 0, getWidth() - 30, 135);
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(monogramFont.deriveFont(100f));
        add(titleLabel);

        app.bookingChanged.connectSignal((arg) -> {
            fillToTable();
        });

        app.mainMenuPanel.historyButtonPressed.connectSignal((arg) -> {
            fillToTable();
        });

        setBackGroundImage("src/com/hazbinhotel/image/tab.png");
    }

    void toggleTable(){
        tablePanel.setVisible(!tablePanel.isVisible());
        userInteractionPanel.setVisible(!userInteractionPanel.isVisible());
    }

    void fillToTable(){
        bookingHistoryTableModel.setRowCount(0);
        for (Booking booking : app.bookingDAO.getPastBooking()){
            Bill bill = app.billDAO.getByBooking(booking.getId());
            Room room = app.roomDAO.get(booking.getRoom());
            Customer customer = app.customerDAO.get(booking.getCustomer());
            bookingHistoryTableModel.addRow(new Object[]{
                room.getName(),
                customer.getName(),
                bill.getId(),
                bill.getStaff(),
                booking.getCheckIn(),
                booking.getCheckOut()
            });
        }
    }
}
