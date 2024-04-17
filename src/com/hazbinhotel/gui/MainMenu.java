package com.hazbinhotel.gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.hazbinhotel.App;
import com.hazbinhotel.CustomSwing.*;
import com.hazbinhotel.CustomSwing.Button;
import com.hazbinhotel.CustomSwing.ScrollPane;
import com.hazbinhotel.CustomSwing.TextField;
import com.hazbinhotel.entity.Bill;
import com.hazbinhotel.entity.Booking;
import com.hazbinhotel.entity.Customer;
import com.hazbinhotel.entity.Room;
import com.zimlewis.ImageBytes;
import com.zimlewis.Signal;

public class MainMenu extends BasePanel{
    // Font
    DefaultTableModel statusTableModel = new DefaultTableModel();
    Button 
        roomTypeButton, 
        roomButton, 
        serviceButton, 
        bookingButton, 
        customerButton, 
        paymentButton, 
        historyButton, 
        incomeButton, 
        staffManageButton;
    
        TextField searchField;
    JLabel nameLabel;
    byte[] avatarData;
    ImageAvatar avatar;
    JLabel occupiedRoomLabel; 
    JLabel emptyRoomLabel;
    JLabel residentsLabel;
    
    public Signal 
        roomTypeButtonPressed = new Signal(),
        roomButtonPressed = new Signal(),
        serviceButtonPressed = new Signal(),
        bookingButtonPressed = new Signal(),
        customerButtonPressed = new Signal(),
        paymentButtonPressed = new Signal(),
        historyButtonPressed = new Signal(), 
        incomeButtonPressed = new Signal(), 
        staffManageButtonPressed = new Signal(),
        accountPanelPressed = new Signal(),
        roomCountPressed = new Signal();

    public MainMenu(App a){
        super(a);
        statusTableModel.addColumn("Name");
        statusTableModel.addColumn("Phone");
        statusTableModel.addColumn("Room ID");
        statusTableModel.addColumn("Check in");
        statusTableModel.addColumn("Check out");


        setLayout(null);
        setSize(960 , 540);
        setBackground(Color.BLACK);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBounds(18, 112, 348, 406);
        buttonPanel.setBackground(new Color(0, 0, 0, 0));
        add(buttonPanel);

        // Room type button
        roomTypeButton = new Button();
        roomTypeButton.setText("Room type");
        roomTypeButton.setBounds(8, 8, 332, 37);
        roomTypeButton.setFont(plainFont.deriveFont(30f));
        buttonPanel.add(roomTypeButton);
        
        // Room button
        roomButton = new Button();
        roomButton.setText("Room");
        roomButton.setBounds(8, 53, 332, 37);
        roomButton.setFont(plainFont.deriveFont(30f));
        buttonPanel.add(roomButton);

        // Service button
        serviceButton = new Button();
        serviceButton.setText("Service");
        serviceButton.setBounds(8, 98, 332, 37);
        serviceButton.setFont(plainFont.deriveFont(30f));
        buttonPanel.add(serviceButton);

        // Booking button
        bookingButton = new Button();
        bookingButton.setText("Booking");
        bookingButton.setBounds(8, 143, 332, 37);
        bookingButton.setFont(plainFont.deriveFont(30f));
        buttonPanel.add(bookingButton);

        // Customer button
        customerButton = new Button();
        customerButton.setText("Customer");
        customerButton.setBounds(8, 188, 332, 37);
        customerButton.setFont(plainFont.deriveFont(30f));
        buttonPanel.add(customerButton);

        // Bill button
        paymentButton = new Button();
        paymentButton.setText("Payment");
        paymentButton.setBounds(8, 233, 332, 37);
        paymentButton.setFont(plainFont.deriveFont(30f));
        buttonPanel.add(paymentButton);

        // History
        historyButton = new Button();
        historyButton.setText("Booking history");
        historyButton.setBounds(8, 278, 332, 37);
        historyButton.setFont(plainFont.deriveFont(30f));
        buttonPanel.add(historyButton);

        // Income
        incomeButton = new Button();
        incomeButton.setText("Income management");
        incomeButton.setBounds(8, 323, 332, 37);
        incomeButton.setFont(plainFont.deriveFont(30f));
        buttonPanel.add(incomeButton);

        // Staff
        staffManageButton = new Button();
        staffManageButton.setText("Staffs");
        staffManageButton.setBounds(8, 368, 332, 37);
        staffManageButton.setFont(plainFont.deriveFont(30f));
        buttonPanel.add(staffManageButton);


        // Status panel
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(null);
        statusPanel.setBounds(376, 18, 562, 400);
        statusPanel.setBackground(new Color(0, 0, 0, 0));
        add(statusPanel);

        // Title Label
        OutlineLabel titleLabel = new OutlineLabel("HAZBIN HOTEL"); 
        titleLabel.setFont(monogramFont.deriveFont(100f));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(0, 0, statusPanel.getWidth(), 150);
        titleLabel.setForeground(new Color(201, 38, 84));
        titleLabel.setOutlineColor(Color.black);
        statusPanel.add(titleLabel);

        // Residents label
        residentsLabel = new JLabel("Residents: 2");
        residentsLabel.setOpaque(true);
        residentsLabel.setFont(plainFont.deriveFont(30f));
        residentsLabel.setBounds(0 , 155 , 187 , 40);
        residentsLabel.setBorder(new LineBorder(new Color(201, 38, 84) , 2));
        residentsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        residentsLabel.setBackground(Color.black);
        residentsLabel.setForeground(new Color(201, 38, 84));
        residentsLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        residentsLabel.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent arg0) {
                roomCountPressed.emitSignal();
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {}

            @Override
            public void mouseExited(MouseEvent arg0) {}

            @Override
            public void mousePressed(MouseEvent arg0) {}

            @Override
            public void mouseReleased(MouseEvent arg0) {}
            
        });
        statusPanel.add(residentsLabel);

        // Empty room
        emptyRoomLabel = new JLabel("Empty: 2");
        emptyRoomLabel.setOpaque(true);
        emptyRoomLabel.setFont(plainFont.deriveFont(30f));
        emptyRoomLabel.setBounds(190 , 155 , 181 , 40);
        emptyRoomLabel.setBorder(new LineBorder(new Color(201, 38, 84) , 2));
        emptyRoomLabel.setHorizontalAlignment(SwingConstants.CENTER);
        emptyRoomLabel.setBackground(Color.black);
        emptyRoomLabel.setForeground(new Color(201, 38, 84));
        emptyRoomLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        emptyRoomLabel.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent arg0) {
                roomCountPressed.emitSignal();
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {}

            @Override
            public void mouseExited(MouseEvent arg0) {}

            @Override
            public void mousePressed(MouseEvent arg0) {}

            @Override
            public void mouseReleased(MouseEvent arg0) {}
            
        });
        statusPanel.add(emptyRoomLabel);

        // Occupied room
        occupiedRoomLabel = new JLabel("Occupied: 2");
        occupiedRoomLabel.setOpaque(true);
        occupiedRoomLabel.setFont(plainFont.deriveFont(30f));
        occupiedRoomLabel.setBounds(374 , 155 , 187 , 40);
        occupiedRoomLabel.setBorder(new LineBorder(new Color(201, 38, 84) , 2));
        occupiedRoomLabel.setHorizontalAlignment(SwingConstants.CENTER);
        occupiedRoomLabel.setBackground(Color.black);
        occupiedRoomLabel.setForeground(new Color(201, 38, 84));
        occupiedRoomLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        occupiedRoomLabel.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent arg0) {
                roomCountPressed.emitSignal();
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {}

            @Override
            public void mouseExited(MouseEvent arg0) {}

            @Override
            public void mousePressed(MouseEvent arg0) {}

            @Override
            public void mouseReleased(MouseEvent arg0) {}
            
        });
        statusPanel.add(occupiedRoomLabel);

        // Recent resident label
        OutlineLabel recentResidentLabel = new OutlineLabel("RECENT RESIDENT:");
        recentResidentLabel.setFont(plainFont.deriveFont(25f));
        recentResidentLabel.setBounds(0 , 200 , 187 , 30);
        recentResidentLabel.setForeground(new Color(201, 38, 84));
        recentResidentLabel.setOutlineColor(Color.black);
        statusPanel.add(recentResidentLabel);




        // Status table
        Table statusTable = new Table(statusTableModel);
        statusTable.getTableHeader().setReorderingAllowed(false);
        statusTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ScrollPane scrollPane = new ScrollPane(statusTable);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(0, 275, statusPanel.getWidth(), 120);
        statusPanel.add(scrollPane);


        // Search field
        searchField = new TextField();
        searchField.setBounds(0, 240 , statusPanel.getWidth() - 120, 30);
        searchField.setFont(plainFont.deriveFont(Font.BOLD, 20f));
        statusPanel.add(searchField);

        Button searchButton = new Button();
        searchButton.setText("Search");
        searchButton.setBounds(statusPanel.getWidth() - 100 , 240 , 100 , 30);
        searchButton.setFont(plainFont.deriveFont(20f));
        searchButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                TextBoxSearchFilter search = new TextBoxSearchFilter(searchField.getText(), 1);
                TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(statusTableModel);
                rowSorter.setRowFilter(search);
                statusTable.setRowSorter(rowSorter);
            }
            
        });
        statusPanel.add(searchButton);


        // Account table
        JPanel accountPanel = new JPanel();
        accountPanel.setLayout(null);
        accountPanel.setBounds(676, 430, 262, 90);
        accountPanel.setBackground(new Color(0, 0, 0, 0));
        accountPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        accountPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    accountPanelPressed.emitSignal();
                }
            }
        });
        add(accountPanel);

        // Avatar
        avatar = new ImageAvatar();
        avatar.setIcon(new ImageIcon("src/com/hazbinhotel/image/account.jpg"));
        avatar.setBounds(172, 0, 90, 90);
        avatar.setBorderSize(5);
        avatar.setForeground(new java.awt.Color(0, 0, 0));
        accountPanel.add(avatar);

        // Name
        nameLabel = new JLabel("Me May Beo");
        nameLabel.setBounds(0, 50, 215, 40);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setFont(plainFont.deriveFont(25f));
        nameLabel.setOpaque(true);
        nameLabel.setForeground(new Color(201, 38, 84));
        nameLabel.setBackground(Color.BLACK);
        accountPanel.add(nameLabel);




        
        
        //Logo
        JLabel logoLabel;
        ImageIcon logoLabelImage = new ImageIcon(
            new ImageIcon("src/com/hazbinhotel/image/logo_black_background.png")
                .getImage()
                    .getScaledInstance(162, 97, Image.SCALE_SMOOTH)
        );

        logoLabel = new JLabel(logoLabelImage);
        logoLabel.setBounds(110, 20, 162, 97);
        add(logoLabel);

        buttonSetup();

        setBackGroundImage("src/com/hazbinhotel/image/mainmenu-bg.png");

        app.staffChanged.connectSignal((arg) -> {
            if (app.getId() != null){
                setName(app.staffDAO.get(app.getId()).getName());
                setAvatar(app.staffDAO.get(app.getId()).getAvatar());
            }
        });

        app.idChanged.connectSignal((arg) -> {
            Integer id = (Integer) arg[0];
            setName(app.staffDAO.get(id).getName());
            setAvatar(app.staffDAO.get(id).getAvatar());
        });
    }

    public void setAvatar(byte[] imgData){
        Image img = ImageBytes.convertBytesToImage(imgData);
        ImageIcon avatarIcon = new ImageIcon(img);
        avatar.setIcon(avatarIcon);
    }

    public void setName(String name){
        nameLabel.setText(name);
    }

    public void buttonSetup(){
        // staffManageButtonPressed = new Signal();
        roomTypeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                roomTypeButtonPressed.emitSignal();
            }
        });

        roomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                roomButtonPressed.emitSignal();
            }
        });

        serviceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                serviceButtonPressed.emitSignal();
            }
        });

        bookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                bookingButtonPressed.emitSignal();
            }
        });

        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                customerButtonPressed.emitSignal();
            }
        });

        paymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                paymentButtonPressed.emitSignal();
            }
        });

        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                historyButtonPressed.emitSignal();
            }
        });

        incomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                incomeButtonPressed.emitSignal();
            }
        });

        staffManageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                staffManageButtonPressed.emitSignal();
            }
        });

    }

    public void setStatus(){
        occupiedRoomLabel.setText(String.format("Occupied: %d", app.bookingDAO.getCurrentInUse().size()));
        emptyRoomLabel.setText(String.format("Empty: %d", app.roomDAO.getAll().size() - app.bookingDAO.getCurrentInUse().size()));
        residentsLabel.setText(String.format("Resident: %d", app.bookingDAO.getFutureBooking().size()));
    }

    public void fillToTable(){
        // bookingTableModel.addColumn("Name");
        // bookingTableModel.addColumn("Phone");
        // bookingTableModel.addColumn("Room ID");
        // bookingTableModel.addColumn("Check in");
        // bookingTableModel.addColumn("Check out");
        statusTableModel.setRowCount(0);
        for (Booking booking : app.bookingDAO.getFutureBooking()){
            Bill bill = app.billDAO.getByBooking(booking.getId());
            Room room = app.roomDAO.get(booking.getRoom());
            Customer customer = app.customerDAO.get(booking.getCustomer());
            statusTableModel.addRow(new Object[]{
                customer.getName(),
                customer.getPhone(),
                String.format("%d - %s", room.getId(), room.getName()),
                booking.getCheckIn(),
                booking.getCheckOut() 
            });
        }
    }
}
