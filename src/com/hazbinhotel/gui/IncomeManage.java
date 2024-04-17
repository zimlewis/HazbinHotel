package com.hazbinhotel.gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.RowFilter;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.MouseInputAdapter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import org.apache.poi.ss.formula.*;
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
import com.hazbinhotel.CustomSwing.NumberOnlyTextField;
import com.hazbinhotel.CustomSwing.YearChooser;
import com.hazbinhotel.entity.Bill;
import com.hazbinhotel.entity.Booking;
import com.hazbinhotel.entity.Customer;
import com.hazbinhotel.entity.Room;
import com.hazbinhotel.entity.Staff;
import com.zimlewis.Signal;

public class IncomeManage extends BasePanel{
    JPanel tablePanel, userInteractionPanel;
    TextField customerField, staffField, roomField;
    NumberOnlyTextField incomeField;
    JLabel invoiceDateTime, billID;
    DefaultTableModel incomeTableModel;
    public Signal backButtonPressed = new Signal();
    List<Bill> paidBill = new ArrayList<>();
    
    public IncomeManage(App a){
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

        // Invoice date
        invoiceDateTime = new JLabel("This bill was created on 2024-April-1st 17:30", SwingConstants.CENTER);
        invoiceDateTime.setBounds(0, 16, userInteractionPanel.getWidth(), 30);
        invoiceDateTime.setFont(plainFont.deriveFont(20f));
        invoiceDateTime.setForeground(Color.BLACK);

        userInteractionPanel.add(invoiceDateTime);

        // Customer label
        JLabel customerLabel = new JLabel("Customer: ", SwingConstants.RIGHT);
        customerLabel.setBounds(20 + userInteractionPanel.getWidth() / 2 - 190, 70, 140, 40);
        customerLabel.setOpaque(false);
        customerLabel.setFont(plainFont.deriveFont(30f));
        customerLabel.setForeground(Color.black);

        userInteractionPanel.add(customerLabel);

        customerField = new TextField();
        customerField.setBounds(180 + userInteractionPanel.getWidth() / 2 - 190, 70, 200, 40);
        customerField.setEnabled(false);
        customerField.setFont(plainFont.deriveFont(30f));

        userInteractionPanel.add(customerField);

        // Staff label
        JLabel staffLabel = new JLabel("Staff: ", SwingConstants.RIGHT);
        staffLabel.setBounds(20 + userInteractionPanel.getWidth() / 2 - 190, 120, 140, 40);
        staffLabel.setOpaque(false);
        staffLabel.setFont(plainFont.deriveFont(30f));
        staffLabel.setForeground(Color.black);

        userInteractionPanel.add(staffLabel);

        staffField = new TextField();
        staffField.setBounds(180 + userInteractionPanel.getWidth() / 2 - 190, 120, 200, 40);
        staffField.setEnabled(false);
        staffField.setFont(plainFont.deriveFont(30f));

        userInteractionPanel.add(staffField);

        // Room label
        JLabel roomLabel = new JLabel("Room:", SwingConstants.RIGHT);
        roomLabel.setBounds(20 + userInteractionPanel.getWidth() / 2 - 190, 170, 140, 40);
        roomLabel.setOpaque(false);
        roomLabel.setFont(plainFont.deriveFont(30f));
        roomLabel.setForeground(Color.black);

        userInteractionPanel.add(roomLabel);

        roomField = new TextField();
        roomField.setBounds(180 + userInteractionPanel.getWidth() / 2 - 190, 170, 200, 40);
        roomField.setEnabled(false);
        roomField.setFont(plainFont.deriveFont(30f));

        userInteractionPanel.add(roomField);

        // Income label
        JLabel incomeLabel = new JLabel("Income: ", SwingConstants.RIGHT);
        incomeLabel.setBounds(20 + userInteractionPanel.getWidth() / 2 - 190, 220, 140, 40);
        incomeLabel.setOpaque(false);
        incomeLabel.setFont(plainFont.deriveFont(30f));
        incomeLabel.setForeground(Color.black);

        userInteractionPanel.add(incomeLabel);

        incomeField = new NumberOnlyTextField();
        incomeField.setBounds(180 + userInteractionPanel.getWidth() / 2 - 190, 220, 200, 40);
        incomeField.setEnabled(false);
        incomeField.setFont(plainFont.deriveFont(30f));

        userInteractionPanel.add(incomeField);

        // Bill
        billID = new JLabel("Bill: B00120", SwingConstants.LEFT);
        billID.setBounds(20, 70, 130, 40);
        billID.setFont(plainFont.deriveFont(20f));
        billID.setForeground(Color.black);

        userInteractionPanel.add(billID);

        // Back to list bu'on
        Button backToListButton = new Button();
        backToListButton.setBounds(20 , 20, 130, 40);
        backToListButton.setText("<- Back to list");
        backToListButton.setFont(plainFont.deriveFont(20f));
        backToListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                toggleTable();
            }
        });

        userInteractionPanel.add(backToListButton);

        // Search box
        YearChooser fromYearChooser, toYearChooser;
        MonthChooser fromMonthChooser, toMonthChooser;
        DayChooser fromDayChooser, toDayChooser;

        JLabel fromLabel = new JLabel("From: ", SwingConstants.RIGHT);
        fromLabel.setBounds(40 - 24, 16, 50, 40);
        fromLabel.setFont(plainFont.deriveFont(20f));
        fromLabel.setForeground(Color.BLACK);
        tablePanel.add(fromLabel);

        fromYearChooser = new YearChooser();
        fromYearChooser.setBounds(40 + 50 - 24, 16, 100, 40);
        fromYearChooser.setFont(plainFont.deriveFont(20f));
        tablePanel.add(fromYearChooser);

        fromMonthChooser = new MonthChooser();
        fromMonthChooser.setBounds(40 + 50 + 110 - 24, 16, 100, 40);
        fromMonthChooser.setFont(plainFont.deriveFont(20f));
        tablePanel.add(fromMonthChooser);

        fromDayChooser = new DayChooser(fromYearChooser, fromMonthChooser);
        fromDayChooser.setBounds(40 + 50 + 110 + 110 - 24, 16, 100, 40);
        fromDayChooser.setFont(plainFont.deriveFont(20f));
        tablePanel.add(fromDayChooser);


        JLabel toLabel = new JLabel("To: ", SwingConstants.RIGHT);
        toLabel.setBounds(tablePanel.getWidth() / 2 - 40 - 24, 16, 50, 40);
        toLabel.setFont(plainFont.deriveFont(20f));
        toLabel.setForeground(Color.BLACK);
        tablePanel.add(toLabel);

        toYearChooser = new YearChooser();
        toYearChooser.setBounds(tablePanel.getWidth() / 2 - 40 + 50 - 24, 16, 100, 40);
        toYearChooser.setFont(plainFont.deriveFont(20f));
        tablePanel.add(toYearChooser);

        toMonthChooser = new MonthChooser();
        toMonthChooser.setBounds(tablePanel.getWidth() / 2 - 40 + 50 + 110 - 24, 16, 100, 40);
        toMonthChooser.setFont(plainFont.deriveFont(20f));
        tablePanel.add(toMonthChooser);

        toDayChooser = new DayChooser(fromYearChooser, fromMonthChooser);
        toDayChooser.setBounds(tablePanel.getWidth() / 2 - 40 + 50 + 110 + 110 - 24, 16, 100, 40);
        toDayChooser.setFont(plainFont.deriveFont(20f));
        tablePanel.add(toDayChooser);


        // Table
        incomeTableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells uneditable
                return false;
            }
        };

        incomeTableModel.addColumn("Bill id");
        incomeTableModel.addColumn("Customer id");
        incomeTableModel.addColumn("Staff id");
        incomeTableModel.addColumn("Invoice date");
        incomeTableModel.addColumn("Payment date");
        incomeTableModel.addColumn("Income");

        Table incomeTable = new Table(incomeTableModel);
        incomeTable.getTableHeader().setReorderingAllowed(false);
        incomeTable.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = incomeTable.getSelectedRow();
                    paidBill.clear();
                    for (Bill bill : app.billsList){
                        if (bill.getPaymentDate() != null){
                            paidBill.add(bill);
                        }
                    }

                    Bill bill = paidBill.get(row);
                    Booking booking = app.bookingDAO.get(bill.getBooking());
                    Room room = app.roomDAO.get(booking.getRoom());
                    Customer customer = app.customerDAO.get(booking.getCustomer());
                    Staff staff = app.staffDAO.get(bill.getStaff());

                    // TextField customerField, staffField, roomField;
                    // NumberOnlyTextField incomeField;
                    // JLabel invoiceDateTime, billID;

                    customerField.setText(customer.getName());
                    staffField.setText(staff.getName());
                    roomField.setText(room.getName());
                    incomeField.setText(String.format("%.2f", bill.getTotal()));
                    invoiceDateTime.setText(String.valueOf(bill.getInvoiceDate()));
                    billID.setText(String.format("bill ID: %s", bill.getId()));

                    toggleTable();
                }
            }
        });

        ScrollPane scroll = new ScrollPane(incomeTable);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBounds(16 , 66 , tablePanel.getWidth() - 32 , 200);

        tablePanel.add(scroll);

        Button searchButton = new Button();
        searchButton.setText("Search");
        searchButton.setBounds(tablePanel.getWidth() - 116, 16, 100, 40);
        searchButton.setFont(plainFont.deriveFont(20f));
        searchButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                RowFilter search = new RowFilter(){

                    @Override
                    public boolean include(Entry entry) {
                        LocalDateTime from = LocalDateTime.of(LocalDate.of(fromYearChooser.getYear(), fromMonthChooser.getMonth() + 1, fromDayChooser.getDay() + 1), LocalTime.of(0, 0));
                        LocalDateTime to = LocalDateTime.of(LocalDate.of(toYearChooser.getYear(), toMonthChooser.getMonth() + 1, toDayChooser.getDay() + 1), LocalTime.of(23, 59));
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
                        LocalDateTime index = LocalDateTime.parse(entry.getStringValue(4), formatter);


                        boolean isBetween = (index.isAfter(from) || index.equals(from))
                        && (index.isBefore(to) || index.equals(to));
                        return isBetween;
                    }

                };
                TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(incomeTableModel);
                rowSorter.setRowFilter(search);
                incomeTable.setRowSorter(rowSorter);
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
                        
                        
                        headerRow.createCell(++headerCellCount).setCellValue("Booking id");
                        headerRow.createCell(++headerCellCount).setCellValue("Total");
                        headerRow.createCell(++headerCellCount).setCellValue("Billing staff");
                        headerRow.createCell(++headerCellCount).setCellValue("Invoice date");
                        headerRow.createCell(++headerCellCount).setCellValue("Payment date");

                        int rowCount = 1;

                        for (Bill bill : paidBill){
                            Row row = sheet.createRow(rowCount++);
                            int cellCount = 0;
                            row.createCell(cellCount++).setCellValue(rowCount - 1);
                            row.createCell(cellCount++).setCellValue(bill.getBooking());
                            row.createCell(cellCount++).setCellValue(String.format("%,.2f", bill.getTotal()));
                            row.createCell(cellCount++).setCellValue(app.staffDAO.get(bill.getStaff()).getName());
                            row.createCell(cellCount++).setCellValue(String.format("%d-%s-%d %02d:%02d", bill.getInvoiceDate().getYear(), String.valueOf(bill.getInvoiceDate().getMonth()), bill.getInvoiceDate().getDayOfMonth(), bill.getInvoiceDate().getHour(), bill.getInvoiceDate().getMinute()));
                            row.createCell(cellCount++).setCellValue(String.format("%d-%s-%d %02d:%02d", bill.getPaymentDate().getYear(), String.valueOf(bill.getPaymentDate().getMonth()), bill.getPaymentDate().getDayOfMonth(), bill.getPaymentDate().getHour(), bill.getPaymentDate().getMinute()));
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
            }
        });
        add(backButton);

        // title label
        OutlineLabel titleLabel = new OutlineLabel("INCOME");
        titleLabel.setOutlineColor(new Color(201, 38, 84));
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(30, 0, getWidth() - 30, 135);
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(monogramFont.deriveFont(100f));
        add(titleLabel);

        setBackGroundImage("src/com/hazbinhotel/image/tab.png");

        app.billChanged.connectSignal((arg) -> {
            fillToTable();
        });

        app.mainMenuPanel.paymentButtonPressed.connectSignal((arg) -> {
            paidBill.clear();
            for (Bill bill : app.billsList){
                if (bill.getPaymentDate() != null){
                    paidBill.add(bill);
                }
            }
        });
    }

    void toggleTable(){
        tablePanel.setVisible(!tablePanel.isVisible());
        userInteractionPanel.setVisible(!userInteractionPanel.isVisible());
    }

    void fillToTable(){
        incomeTableModel.setRowCount(0);
        paidBill.clear();
        for (Bill bill : app.billsList){
            if (bill.getPaymentDate() != null){
                paidBill.add(bill);
            }
        }
        for (Bill bill : paidBill){
            if (bill.getPaymentDate() != null){
                Booking booking = app.bookingDAO.get(bill.getBooking());
                Room room = app.roomDAO.get(booking.getRoom());
                Customer customer = app.customerDAO.get(booking.getCustomer());
                Staff staff = app.staffDAO.get(bill.getStaff());
                incomeTableModel.addRow(new Object[]{
                    bill.getId(),
                    customer.getName(),
                    staff.getName(),
                    bill.getInvoiceDate(),
                    bill.getPaymentDate(),
                    String.format("%,.2f", bill.getTotal())
                });
            }
        }
    }
}
