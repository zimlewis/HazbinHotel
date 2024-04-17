package com.hazbinhotel.gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.time.LocalDateTime;

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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hazbinhotel.App;
import com.hazbinhotel.CustomSwing.Button;
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
import com.zimlewis.Signal;

public class PaymentPage extends BasePanel{
    DefaultTableModel paymentTableModel;
    JPanel tablePanel, userInteractionPanel;
    TextField customerField, roomNameField;
    DayChooser checkInDayChooser, checkOutDayChooser;
    MonthChooser checkInMonthChooser, checkOutMonthChooser;
    YearChooser checkInYearChooser, checkOutYearChooser;
    TimeChooser checkInTimeChooser, checkOutTimeChooser;
    OutlineLabel totalLabel;
    ImageIcon QRImage;
    JLabel QRLabel;
    int id;
    public Signal backButtonPressed = new Signal();


    public PaymentPage(App a){
        super(a);
        // user interaction panel
        userInteractionPanel = new JPanel();
        userInteractionPanel.setLayout(null);
        userInteractionPanel.setBackground(new Color(255, 255, 255, 0));
        userInteractionPanel.setVisible(false);
        userInteractionPanel.setBounds(20, 135, getWidth() - 40, 321);
        add(userInteractionPanel);
        
        // Back to list bu'on
        Button backToListButton = new Button();
        backToListButton.setBounds(20 , 20, 130, 40);
        backToListButton.setText("<- Back to list");
        backToListButton.setFont(plainFont.deriveFont(20f));
        backToListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                toggleTable();
                QRLabel.setVisible(false);
            }
        });

        userInteractionPanel.add(backToListButton);


        // table panel
        tablePanel = new JPanel();
        tablePanel.setLayout(null);
        tablePanel.setBackground(new Color(255, 255, 255, 0));
        tablePanel.setVisible(true);
        tablePanel.setBounds(20, 135, getWidth() - 40, 321);
        add(tablePanel);

        // Pay button
        Button payButton = new Button();
        payButton.setText("Pay");
        payButton.setFont(plainFont.deriveFont(20f));
        payButton.setBounds(20, 125, 130, 40);
        payButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                Bill bill = app.billDAO.get(id);
                bill.setPaymentDate(LocalDateTime.now());
                bill.setStaff(app.getId());

                app.billDAO.update(bill);
                toggleTable();
            }
            
        });

        userInteractionPanel.add(payButton);

        // Bank button
        Button bankButton = new Button();
        bankButton.setText("Bank Transfer");
        bankButton.setFont(plainFont.deriveFont(20f));
        bankButton.setBounds(20, 175, 130, 40);

        bankButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                QRLabel.setVisible(true);
            }
        });

        userInteractionPanel.add(bankButton);

        // Name label
        JLabel nameLabel = new JLabel("Name:", SwingConstants.RIGHT);
        nameLabel.setBounds(170, 20, 150, 40);
        nameLabel.setForeground(Color.black);
        nameLabel.setFont(plainFont.deriveFont(30f));

        userInteractionPanel.add(nameLabel);

        customerField = new TextField();
        customerField.setBounds(330, 20, 190, 40);
        customerField.setEditable(false);
        customerField.setFont(plainFont.deriveFont(30f));

        userInteractionPanel.add(customerField);

        // Room name label
        JLabel roomNameLabel = new JLabel("Room:", SwingConstants.RIGHT);
        roomNameLabel.setBounds(170, 70, 150, 40);
        roomNameLabel.setForeground(Color.black);
        roomNameLabel.setFont(plainFont.deriveFont(30f));

        userInteractionPanel.add(roomNameLabel);

        roomNameField = new TextField();
        roomNameField.setBounds(330, 70, 190, 40);
        roomNameField.setEditable(false);
        roomNameField.setFont(plainFont.deriveFont(30f));

        userInteractionPanel.add(roomNameField);


        // Check in
        JLabel checkInLabel = new JLabel("Check in:", SwingConstants.RIGHT);
        checkInLabel.setBounds(170, 120, 150, 40);
        checkInLabel.setForeground(Color.black);
        checkInLabel.setFont(plainFont.deriveFont(30f));

        userInteractionPanel.add(checkInLabel);

        checkInYearChooser = new YearChooser();
        checkInYearChooser.setBounds(330, 120, 60, 40);
        checkInYearChooser.setFont(plainFont.deriveFont(20f));
        checkInYearChooser.setEnabled(false);

        userInteractionPanel.add(checkInYearChooser);

        checkInMonthChooser = new MonthChooser();
        checkInMonthChooser.setBounds(395, 120, 60, 40);
        checkInMonthChooser.setType(MonthChooser.NUMERIC);
        checkInMonthChooser.setFont(plainFont.deriveFont(20f));
        checkInMonthChooser.setEnabled(false);

        userInteractionPanel.add(checkInMonthChooser);

        checkInDayChooser = new DayChooser(checkInYearChooser, checkInMonthChooser);
        checkInDayChooser.setBounds(460, 120, 60, 40);
        checkInDayChooser.setFont(plainFont.deriveFont(20f));
        checkInDayChooser.setEnabled(false);


        userInteractionPanel.add(checkInDayChooser);

        checkInTimeChooser = new TimeChooser();
        checkInTimeChooser.setBounds(330, 170, 190, 40);
        checkInTimeChooser.setEnabled(false);
        checkInTimeChooser.setFont(plainFont.deriveFont(20f));
        checkInTimeChooser.setSecondChoose(false);

        userInteractionPanel.add(checkInTimeChooser);

        // Check out
        JLabel checkOutLabel = new JLabel("Check out:", SwingConstants.RIGHT);
        checkOutLabel.setBounds(170, 220, 150, 40);
        checkOutLabel.setForeground(Color.black);
        checkOutLabel.setFont(plainFont.deriveFont(30f));

        userInteractionPanel.add(checkOutLabel);

        checkOutYearChooser = new YearChooser();
        checkOutYearChooser.setBounds(330, 220, 60, 40);
        checkOutYearChooser.setFont(plainFont.deriveFont(20f));
        checkOutYearChooser.setEnabled(false);

        userInteractionPanel.add(checkOutYearChooser);

        checkOutMonthChooser = new MonthChooser();
        checkOutMonthChooser.setBounds(395, 220, 60, 40);
        checkOutMonthChooser.setType(MonthChooser.NUMERIC);
        checkOutMonthChooser.setFont(plainFont.deriveFont(20f));
        checkOutMonthChooser.setEnabled(false);

        userInteractionPanel.add(checkOutMonthChooser);

        checkOutDayChooser = new DayChooser(checkOutYearChooser, checkOutMonthChooser);
        checkOutDayChooser.setBounds(460, 220, 60, 40);
        checkOutDayChooser.setFont(plainFont.deriveFont(20f));
        checkOutDayChooser.setEnabled(false);

        userInteractionPanel.add(checkOutDayChooser);

        checkOutTimeChooser = new TimeChooser();
        checkOutTimeChooser.setBounds(330, 270, 190, 40);
        checkOutTimeChooser.setEnabled(false);
        checkOutTimeChooser.setFont(plainFont.deriveFont(20f));
        checkOutTimeChooser.setSecondChoose(false);

        userInteractionPanel.add(checkOutTimeChooser);

        // Price
        totalLabel = new OutlineLabel(("100000"), SwingConstants.CENTER);
        totalLabel.setBounds(20, userInteractionPanel.getHeight()-60, 200, 30);
        totalLabel.setFont(plainFont.deriveFont(30f));
        totalLabel.setForeground(Color.BLACK);

        userInteractionPanel.add(totalLabel);

        // QR label
        QRImage = new ImageIcon(
            new ImageIcon("src/com/hazbinhotel/image/Rickrolling_QR_code.png")
                .getImage()
                    .getScaledInstance(290, 290, Image.SCALE_SMOOTH)
        );

        QRLabel = new JLabel(QRImage);
        QRLabel.setBounds((userInteractionPanel.getWidth() - 20 + 170) / 2, 20, 320, 320);
        QRLabel.setVisible(false);

        userInteractionPanel.add(QRLabel);

        // Table
        paymentTableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells uneditable
                return false;
            }
        };
        paymentTableModel.addColumn("Room Name");
        paymentTableModel.addColumn("Customer Name");
        paymentTableModel.addColumn("Customer Phone Number");
        paymentTableModel.addColumn("Check in date");
        paymentTableModel.addColumn("Check out date");
        paymentTableModel.addColumn("Paid");


        Table paymentTable = new Table(paymentTableModel);
        paymentTable.getTableHeader().setReorderingAllowed(false);
        paymentTable.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { 
                    int row = paymentTable.getSelectedRow();
                    Bill bill = app.billsList.get(row);
                    Booking booking = app.bookingDAO.get(bill.getBooking());
                    Room room = app.roomDAO.get(booking.getRoom());
                    Customer customer = app.customerDAO.get(booking.getCustomer());
                    id = bill.getId();

                    if (bill.getPaymentDate() != null){
                        payButton.setEnabled(false);
                    }
                    else{
                        payButton.setEnabled(true);
                    }

                    customerField.setText(customer.getName());
                    roomNameField.setText(room.getName());

                    checkInYearChooser.setYear(booking.getCheckIn().getYear());
                    checkInMonthChooser.setMonth(booking.getCheckIn().getMonthValue() + 1);
                    checkInDayChooser.setDay(booking.getCheckIn().getDayOfMonth() + 1);

                    checkOutYearChooser.setYear(booking.getCheckOut().getYear());
                    checkOutMonthChooser.setMonth(booking.getCheckOut().getMonthValue() + 1);
                    checkOutDayChooser.setDay(booking.getCheckOut().getDayOfMonth() + 1);

                    checkInTimeChooser.setTime(booking.getCheckIn().toLocalTime());
                    checkOutTimeChooser.setTime(booking.getCheckOut().toLocalTime());
                    
                    totalLabel.setText(String.format("%,.2f", bill.getTotal()));

                    toggleTable();
                }
            }
        });
        
        ScrollPane scroll = new ScrollPane(paymentTable);
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
                TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(paymentTableModel);
                rowSorter.setRowFilter(search);
                paymentTable.setRowSorter(rowSorter);
            }
            
        });
        tablePanel.add(searchButton);

        // new button
        // Button newButton = new Button();
        // newButton.setText("+ New");
        // newButton.setBounds(16 , 280 , tablePanel.getWidth() - 32 , 40);
        // newButton.setFont(plainFont.deriveFont(20f));
        // newButton.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent arg0) {
        //         toggleAuthentication();
        //     }
        // });

        // tablePanel.add(newButton);

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
                        headerRow.createCell(++headerCellCount).setCellValue("Paid");

                        int rowCount = 1;

                        for (Bill bill : app.billsList){
                            Row row = sheet.createRow(rowCount++);
                            int cellCount = 0;
                            row.createCell(cellCount++).setCellValue(rowCount - 1);
                            row.createCell(cellCount++).setCellValue(bill.getBooking());
                            row.createCell(cellCount++).setCellValue(String.format("%,.2f", bill.getTotal()));
                            row.createCell(cellCount++).setCellValue(bill.getStaff()!=null?app.staffDAO.get(bill.getStaff()).getName():null);
                            row.createCell(cellCount++).setCellValue(String.format("%d-%s-%d %02d:%02d", bill.getInvoiceDate().getYear(), String.valueOf(bill.getInvoiceDate().getMonth()), bill.getInvoiceDate().getDayOfMonth(), bill.getInvoiceDate().getHour(), bill.getInvoiceDate().getMinute()));
                            row.createCell(cellCount++).setCellValue(bill.getPaymentDate()==null?null:String.format("%d-%s-%d %02d:%02d", bill.getPaymentDate().getYear(), String.valueOf(bill.getPaymentDate().getMonth()), bill.getPaymentDate().getDayOfMonth(), bill.getPaymentDate().getHour(), bill.getPaymentDate().getMinute()));
                            row.createCell(cellCount++).setCellValue(bill.getPaymentDate()==null?"no":"yes");
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
        OutlineLabel titleLabel = new OutlineLabel("PAYMENT");
        titleLabel.setOutlineColor(new Color(201, 38, 84));
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(30, 0, getWidth() - 30, 135);
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(monogramFont.deriveFont(100f));
        add(titleLabel);
        
        app.billChanged.connectSignal((arg) -> {
            fillToTable();
        });

        setBackGroundImage("src/com/hazbinhotel/image/tab.png");
    }

    void setTotalPrice(double price){
        totalLabel.setText(String.format("(%f)", price));
    }

    void fillToTable(){
        // paymentTableModel.addColumn("Room Name");
        // paymentTableModel.addColumn("Customer Name");
        // paymentTableModel.addColumn("Customer Phone Number");
        // paymentTableModel.addColumn("Check in date");
        // paymentTableModel.addColumn("Check out date");
        paymentTableModel.setRowCount(0);
        
        for (Bill bill : app.billsList){
            Booking booking = app.bookingDAO.get(bill.getBooking());
            Room room = app.roomDAO.get(booking.getRoom());
            Customer customer = app.customerDAO.get(booking.getCustomer());
            paymentTableModel.addRow(new Object[]{
                room.getName(),
                customer.getName(),
                customer.getPhone(),
                booking.getCheckIn(),
                booking.getCheckOut(),
                bill.getStaff()==null?"x":"v"
            });
        }

    }

    void toggleTable(){
        tablePanel.setVisible(!tablePanel.isVisible());
        userInteractionPanel.setVisible(!userInteractionPanel.isVisible());
    }
}