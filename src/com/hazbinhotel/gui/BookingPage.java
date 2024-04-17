package com.hazbinhotel.gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import com.hazbinhotel.entity.RoomType;
import com.hazbinhotel.entity.Service;
import com.zimlewis.Signal;
import com.zimlewis.ZQL;

public class BookingPage extends BasePanel{
    JPanel tablePanel, userInteractionPanel;
    ComboBox<String> customerComboBox, roomComboBox;
    YearChooser checkInYearChooser, checkOutYearChooser;
    MonthChooser checkInMonthChooser, checkOutMonthChooser;
    DayChooser checkInDayChooser, checkOutDayChooser;
    TimeChooser checkInTimeChooser, checkOutTimeChooser;
    int id;
    public Signal backButtonPressed = new Signal();

    DefaultTableModel bookingTableModel = new DefaultTableModel(){
        @Override
        public boolean isCellEditable(int row, int column) {
            // Make all cells uneditable
            return false;
        }
    };
    public BookingPage(App a){
        super(a);
        // user interaction panel
        userInteractionPanel = new JPanel();
        userInteractionPanel.setLayout(null);
        userInteractionPanel.setBackground(new Color(0, 0, 0, 0));
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
            }
        });

        userInteractionPanel.add(backToListButton);

        // Add button
        ImageIcon addButtonIcon = new ImageIcon(
            new ImageIcon("src/com/hazbinhotel/image/add.png")
                .getImage()
                    .getScaledInstance(40, 40, Image.SCALE_SMOOTH)
        );

        Button addButton = new Button();
        addButton.setIcon(addButtonIcon);
        addButton.setBounds(55, 125, 40, 40);
        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                boolean validated = true;
                String errorMessage = "Error creating booking ticket: ";

                Booking b = new Booking(
                    0,
                    App.extractInteger(String.valueOf(customerComboBox.getSelectedItem())),
                    App.extractInteger(String.valueOf(roomComboBox.getSelectedItem())),
                    LocalDateTime.of(LocalDate.of(checkInYearChooser.getYear(), checkInMonthChooser.getMonth() + 1, checkInDayChooser.getDay() + 1) , checkInTimeChooser.getTime()),
                    LocalDateTime.of(LocalDate.of(checkOutYearChooser.getYear(), checkOutMonthChooser.getMonth() + 1, checkOutDayChooser.getDay() + 1) , checkOutTimeChooser.getTime())
                );



                if (b.getCheckIn().isAfter(b.getCheckOut())){
                    validated = false;
                    errorMessage += "\n + Check-in time must be before check-out time";
                }
                if (Duration.between(b.getCheckIn(), b.getCheckOut()).toHours() < 5){
                    validated = false;
                    errorMessage += "\n + Room must be booked for at least 5 hours";
                }
                if (b.getCheckIn().isBefore(LocalDateTime.now())){
                    validated = false;
                    errorMessage += "\n + Check-in time must be before current time";
                }
                if (app.roomDAO.isInUsed(b.getRoom(), b.getCheckIn(), b.getCheckOut())){
                    validated = false;
                    errorMessage += "\n + Room is already booked in that time";
                }

                int success = 0;
                if (validated){
                    success = app.bookingDAO.insert(b);

                    if (success != 0){
                        errorMessage += "\n + Database error, please try again later";
                    }
                }

                if (success != 1 || !validated){
                    JOptionPane.showMessageDialog(null, errorMessage);
                }
                else {
                    System.out.printf("%s %s %s\n", String.valueOf(b.getRoom()), String.valueOf(b.getCheckIn()) , String.valueOf(b.getCheckOut()));
                    Booking insertedBooking = app.bookingDAO.getWithRoomBookTime(b.getRoom(), b.getCheckIn(), b.getCheckOut());
                    Room bookedRoom = app.roomDAO.get(insertedBooking.getRoom());
                    Service bookedService = app.serviceDAO.get(bookedRoom.getService());
                    RoomType bookedRoomType = app.roomTypeDAO.get(bookedRoom.getType());


                    Bill bi = new Bill(
                        0, 
                        insertedBooking.getId(), 
                        (double)(Duration.between(insertedBooking.getCheckIn(), insertedBooking.getCheckOut()).toHours()) * (bookedService.getPrice() + bookedRoomType.getPrice()), 
                        null, 
                        LocalDateTime.now(), 
                        null
                    );

                    int billInserted = app.billDAO.insert(bi);
                    
                    if (billInserted == 0){
                        errorMessage += "\n + Database error, please try again later";
                        JOptionPane.showMessageDialog(null, errorMessage);
                        app.bookingDAO.delete(insertedBooking.getId());
                    }
                    else{
                        toggleTable();
                    }
                }
            }
            
        });

        userInteractionPanel.add(addButton);




        // Remove button
        ImageIcon removeButtonIcon = new ImageIcon(
            new ImageIcon("src/com/hazbinhotel/image/delete.png")
                .getImage()
                    .getScaledInstance(40, 40, Image.SCALE_SMOOTH)
        );
        Button removeButton = new Button();
        removeButton.setIcon(removeButtonIcon);
        removeButton.setBounds(55, 225, 40, 40);
        removeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                Booking b = app.bookingDAO.get(id);
                Bill bill = app.billDAO.getByBooking(b.getId());
                int result = 0;
                if (bill.getPaymentDate() == null){
                    result = app.bookingDAO.delete(id);
                }
                
                toggleTable();
            }
            
        });

        userInteractionPanel.add(removeButton);

        // Customer
        JLabel customerLabel = new JLabel("Customer:", SwingConstants.RIGHT);
        customerLabel.setBounds(170, 20, 150, 40);
        customerLabel.setForeground(Color.BLACK);
        customerLabel.setFont(plainFont.deriveFont(20f));
        
        userInteractionPanel.add(customerLabel);

        customerComboBox = new ComboBox<>();
        customerComboBox.setBounds(330, 20, 190, 40);
        customerComboBox.setFont(plainFont.deriveFont(20f));

        userInteractionPanel.add(customerComboBox);

        // Room
        JLabel roomLabel = new JLabel("Room:", SwingConstants.RIGHT);
        roomLabel.setBounds(170 + (userInteractionPanel.getWidth() / 2 - 100), 20, 150, 40);
        roomLabel.setForeground(Color.BLACK);
        roomLabel.setFont(plainFont.deriveFont(20f));
        
        userInteractionPanel.add(roomLabel);

        roomComboBox = new ComboBox<>();
        roomComboBox.setBounds(330 + (userInteractionPanel.getWidth() / 2 - 100), 20, 190, 40);
        roomComboBox.setFont(plainFont.deriveFont(20f));

        userInteractionPanel.add(roomComboBox);

        // Check-in time
        JLabel checkInTimeLabel = new JLabel("Check-in time:", SwingConstants.RIGHT);
        checkInTimeLabel.setBounds(170, 120, 150, 30);
        checkInTimeLabel.setForeground(Color.BLACK);
        checkInTimeLabel.setFont(plainFont.deriveFont(20f));

        userInteractionPanel.add(checkInTimeLabel);

        checkInYearChooser = new YearChooser();
        checkInYearChooser.setBounds(330, 120, 190 / 3, 30);
        checkInYearChooser.setFont(plainFont.deriveFont(20f));

        userInteractionPanel.add(checkInYearChooser);

        checkInMonthChooser = new MonthChooser();
        checkInMonthChooser.setBounds(330 + 190 / 3 + 10, 120, 190 / 3 - 10, 30);
        checkInMonthChooser.setFont(plainFont.deriveFont(20f));
        checkInMonthChooser.setType(MonthChooser.NUMERIC);

        userInteractionPanel.add(checkInMonthChooser);

        checkInDayChooser = new DayChooser(checkInYearChooser, checkInMonthChooser);
        checkInDayChooser.setBounds(330 + 190 - (190 / 3 - 10), 120, 190 / 3 - 10, 30);
        checkInDayChooser.setFont(plainFont.deriveFont(20f));

        userInteractionPanel.add(checkInDayChooser);

        checkInTimeChooser = new TimeChooser();
        checkInTimeChooser.setSecondChoose(false);
        checkInTimeChooser.setFont(plainFont.deriveFont(20f));
        checkInTimeChooser.setBounds(330, 170, 190, 30);

        userInteractionPanel.add(checkInTimeChooser);

        // Check-out time
        JLabel checkOutTimeLabel = new JLabel("Check-out time:", SwingConstants.RIGHT);
        checkOutTimeLabel.setBounds(170 + (userInteractionPanel.getWidth() / 2 - 100), 120, 150, 30);
        checkOutTimeLabel.setForeground(Color.BLACK);
        checkOutTimeLabel.setFont(plainFont.deriveFont(20f));

        userInteractionPanel.add(checkOutTimeLabel);

        checkOutYearChooser = new YearChooser();
        checkOutYearChooser.setBounds(330 + (userInteractionPanel.getWidth() / 2 - 100), 120, 190 / 3, 30);
        checkOutYearChooser.setFont(plainFont.deriveFont(20f));

        userInteractionPanel.add(checkOutYearChooser);

        checkOutMonthChooser = new MonthChooser();
        checkOutMonthChooser.setBounds((userInteractionPanel.getWidth() / 2 - 100) + 330 + 190 / 3 + 10, 120, 190 / 3 - 10, 30);
        checkOutMonthChooser.setFont(plainFont.deriveFont(20f));
        checkOutMonthChooser.setType(MonthChooser.NUMERIC);

        userInteractionPanel.add(checkOutMonthChooser);

        checkOutDayChooser = new DayChooser(checkInYearChooser, checkInMonthChooser);
        checkOutDayChooser.setBounds((userInteractionPanel.getWidth() / 2 - 100) + 330 + 190 - (190 / 3 - 10), 120, 190 / 3 - 10, 30);
        checkOutDayChooser.setFont(plainFont.deriveFont(20f));

        userInteractionPanel.add(checkOutDayChooser);

        checkOutTimeChooser = new TimeChooser();
        checkOutTimeChooser.setSecondChoose(false);
        checkOutTimeChooser.setFont(plainFont.deriveFont(20f));
        checkOutTimeChooser.setBounds((userInteractionPanel.getWidth() / 2 - 100) + 330, 170, 190, 30);

        userInteractionPanel.add(checkOutTimeChooser);

        // table panel
        tablePanel = new JPanel();
        tablePanel.setLayout(null);
        tablePanel.setBackground(new Color(255, 255, 255, 0));
        tablePanel.setVisible(true);
        tablePanel.setBounds(20, 135, getWidth() - 40, 321);
        add(tablePanel);

        bookingTableModel.addColumn("Name");
        bookingTableModel.addColumn("Phone");
        bookingTableModel.addColumn("Room ID");
        bookingTableModel.addColumn("Check in");
        bookingTableModel.addColumn("Check out");



        Table bookingTable = new Table(bookingTableModel);
        bookingTable.getTableHeader().setReorderingAllowed(false);
        bookingTable.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    customerComboBox.removeAllItems();
                    roomComboBox.removeAllItems();
    
                    for (Customer c: app.customerDAO.getAll()){
                        customerComboBox.addItem(String.format("%d - %s", c.getId(), c.getName()));
                    }
    
                    for (Room r: app.roomDAO.getAll()){
                        roomComboBox.addItem(String.format("%d - %s", r.getId(), r.getName()));
                    }

                    int row = bookingTable.getSelectedRow();
                    Booking booking = app.bookingsList.get(row);
                    Customer customer = app.customerDAO.get(booking.getCustomer());
                    Room room = app.roomDAO.get(booking.getRoom());

                    id = booking.getId();

                    customerComboBox.setSelectedItem(String.format("%d - %s", customer.getId(), customer.getName()));
                    roomComboBox.setSelectedItem(String.format("%d - %s", room.getId(), room.getName()));
                    
                    checkInYearChooser.setYear(booking.getCheckIn().getYear());
                    checkInMonthChooser.setMonth(booking.getCheckIn().getMonthValue() - 1);
                    checkInDayChooser.setDay(booking.getCheckIn().getDayOfMonth() - 1);
                    
                    checkOutYearChooser.setYear(booking.getCheckOut().getYear());
                    checkOutMonthChooser.setMonth(booking.getCheckOut().getMonthValue() - 1);
                    checkOutDayChooser.setDay(booking.getCheckOut().getDayOfMonth() - 1);

                    checkInTimeChooser.setTime(booking.getCheckIn().toLocalTime());
                    checkOutTimeChooser.setTime(booking.getCheckIn().toLocalTime());

                    toggleTable();
                }
            }
        });

        ScrollPane scroll = new ScrollPane(bookingTable);
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
                TextBoxSearchFilter search = new TextBoxSearchFilter(searchField.getText(), 1);
                TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(bookingTableModel);
                rowSorter.setRowFilter(search);
                bookingTable.setRowSorter(rowSorter);
            }
            
        });
        tablePanel.add(searchButton);

        Button newButton = new Button();
        newButton.setText("+ New");
        newButton.setBounds(16 , 280 , tablePanel.getWidth() - 32 , 40);
        newButton.setFont(plainFont.deriveFont(20f));
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                customerComboBox.removeAllItems();
                roomComboBox.removeAllItems();

                id = -1;

                for (Customer c: app.customersList){
                    customerComboBox.addItem(String.format("%d - %s", c.getId(), c.getName()));
                }

                for (Room r: app.roomsList){
                    roomComboBox.addItem(String.format("%d - %s", r.getId(), r.getName()));
                }

                customerComboBox.setSelectedIndex(customerComboBox.getItemCount() - 1);
                roomComboBox.setSelectedIndex(roomComboBox.getItemCount() - 1);
                
                checkInYearChooser.setYear(LocalDate.now().getYear());
                checkInMonthChooser.setMonth(LocalDate.now().getMonthValue() - 1);
                checkInDayChooser.setDay(LocalDate.now().getDayOfMonth() - 1);
                
                checkOutYearChooser.setYear(LocalDate.now().getYear());
                checkOutMonthChooser.setMonth(LocalDate.now().getMonthValue() - 1);
                checkOutDayChooser.setDay(LocalDate.now().getDayOfMonth() - 1);

                checkInTimeChooser.setTime(LocalTime.now());
                checkOutTimeChooser.setTime(LocalTime.now());
                
                toggleTable();
            }
        });

        tablePanel.add(newButton);

        // Back bu'on
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

                        for (Booking booking : app.bookingsList){
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
            }
        });
        add(backButton);

        // title label
        OutlineLabel titleLabel = new OutlineLabel("BOOKING");
        titleLabel.setOutlineColor(new Color(201, 38, 84));
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(30, 0, getWidth() - 30, 135);
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(monogramFont.deriveFont(100f));
        add(titleLabel);

        app.bookingChanged.connectSignal((arg) -> {
            fillToTable();
        });

        setBackGroundImage("src/com/hazbinhotel/image/tab.png");
    }

    void toggleTable(){
        tablePanel.setVisible(!tablePanel.isVisible());
        userInteractionPanel.setVisible(!userInteractionPanel.isVisible());
    }

    void fillToTable(){
        bookingTableModel.setRowCount(0);
        // bookingTableModel.addColumn("Name");
        // bookingTableModel.addColumn("Phone");
        // bookingTableModel.addColumn("Room ID");
        // bookingTableModel.addColumn("Check in");
        // bookingTableModel.addColumn("Check out");
        for (Booking b : app.bookingsList){
            Customer c = app.customerDAO.get(b.getCustomer());
            Room r = app.roomDAO.get(b.getRoom());

            bookingTableModel.addRow(new Object[]{
                c.getName(),
                c.getPhone(),
                String.format("%d - %s", r.getId(), r.getName()),
                b.getCheckIn(),
                b.getCheckOut()
            });
        }
    }
}
