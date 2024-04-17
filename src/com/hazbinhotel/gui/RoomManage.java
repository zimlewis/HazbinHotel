package com.hazbinhotel.gui;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hazbinhotel.App;
import com.hazbinhotel.CustomSwing.*;
import com.hazbinhotel.CustomSwing.Button;
import com.hazbinhotel.CustomSwing.ScrollPane;
import com.hazbinhotel.CustomSwing.TextField;
import com.hazbinhotel.entity.Room;
import com.hazbinhotel.entity.RoomType;
import com.hazbinhotel.entity.Service;
import com.hazbinhotel.entity.Booking;
import com.zimlewis.Signal;
import com.zimlewis.ZQL;

public class RoomManage extends BasePanel {

    TextField roomNameField;
    ComboBox<String> serviceComboBox, roomTypeComboBox, statusComboBox;
    int id = -1;
    DefaultTableModel roomTableModel = new DefaultTableModel(){
        @Override
        public boolean isCellEditable(int row, int column) {
            // Make all cells uneditable
            return false;
        }
    };
    public Signal backButtonPressed = new Signal();

    public RoomManage(App a){
        super(a);
        // user interaction panel
        JPanel userInteractionPanel = new JPanel();
        userInteractionPanel.setLayout(null);
        userInteractionPanel.setBackground(new Color(0, 0, 0, 0));
        userInteractionPanel.setBounds(getWidth() / 2, 135, getWidth() / 2 - 20, 391);
        add(userInteractionPanel);

        // Name
        JLabel nameLabel = new JLabel("Name: ", SwingConstants.RIGHT);
        nameLabel.setBounds(16, 16, 160, 50);
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setFont(plainFont.deriveFont(30f));
        userInteractionPanel.add(nameLabel);


        roomNameField = new TextField();
        roomNameField.setBounds(180, 16, 250, 50);
        roomNameField.setFont(plainFont.deriveFont(30f));
        roomNameField.setForeground(new Color(201, 38, 84));
        userInteractionPanel.add(roomNameField);

        // Service
        JLabel serviceLabel = new JLabel("Service: ", SwingConstants.RIGHT);
        serviceLabel.setBounds(16, 82, 160, 50);
        serviceLabel.setForeground(Color.BLACK);
        serviceLabel.setFont(plainFont.deriveFont(30f));
        userInteractionPanel.add(serviceLabel);
     
        serviceComboBox = new ComboBox<String>();
        serviceComboBox.setBounds(180, 82, 250, 50);
        serviceComboBox.setFont(plainFont.deriveFont(30f));
        serviceComboBox.setForeground(new Color(201, 38, 84));
        userInteractionPanel.add(serviceComboBox);

        // Room type
        JLabel roomTypeLabel = new JLabel("Room type: ", SwingConstants.RIGHT);
        roomTypeLabel.setBounds(16, 148, 160, 50);
        roomTypeLabel.setForeground(Color.BLACK);
        roomTypeLabel.setFont(plainFont.deriveFont(30f));
        userInteractionPanel.add(roomTypeLabel);
     
        roomTypeComboBox = new ComboBox<String>();
        roomTypeComboBox.setBounds(180, 148, 250, 50);
        roomTypeComboBox.setFont(plainFont.deriveFont(30f));
        roomTypeComboBox.setForeground(new Color(201, 38, 84));
        userInteractionPanel.add(roomTypeComboBox);


        // Status
        JLabel statusLabel = new JLabel("Status: ", SwingConstants.RIGHT);
        statusLabel.setBounds(16, 214, 160, 50);
        statusLabel.setForeground(Color.BLACK);
        statusLabel.setFont(plainFont.deriveFont(30f));
        userInteractionPanel.add(statusLabel);
     
        statusComboBox = new ComboBox<String>(new String[]{"In used", "Ready"});
        statusComboBox.setBounds(180, 214, 250, 50);
        statusComboBox.setEnabled(false);
        statusComboBox.setFont(plainFont.deriveFont(30f));
        statusComboBox.setForeground(new Color(201, 38, 84));
        userInteractionPanel.add(statusComboBox);


        // Add button
        Button addButton = new Button();
        addButton.setText("Add");
        addButton.setFont(plainFont.deriveFont(30f));
        addButton.setBounds(16 , 346, 100, 40);
        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                Map<String, Object> dict = getForm();

                Room r = new Room(
                    0, 
                    String.valueOf(dict.get("name")), 
                    (int) dict.get("service"),
                    (int) dict.get("room_type")
                );

                if (ZQL.excuteQueryToArrayList(app.connection, "select * from room where name = ?", r.getName()).size() != 0){
                    JOptionPane.showMessageDialog(null, "this room has already added");
                    return;
                }

                app.roomDAO.insert(r);
            }
            
        });
        userInteractionPanel.add(addButton);

        // Edit button
        Button editButton = new Button();
        editButton.setText("Edit");
        editButton.setFont(plainFont.deriveFont(30f));
        editButton.setBounds(172 , 346, 100, 40);
        editButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                Map<String, Object> dict = getForm();

                Room r = new Room(
                    id, 
                    String.valueOf(dict.get("name")), 
                    (int) dict.get("service"),
                    (int) dict.get("room_type")
                );

                if (ZQL.excuteQueryToArrayList(app.connection, "select * from room where name = ?", r.getName()).size() != 0 && !app.roomDAO.get(id).getName().equals(r.getName())){
                    JOptionPane.showMessageDialog(null, "this room has already added");
                    return;
                }

                app.roomDAO.update(r);
            }
            
        });

        userInteractionPanel.add(editButton);

        // Remove button
        Button removeButton = new Button();
        removeButton.setText("Remove");
        removeButton.setFont(plainFont.deriveFont(30f));
        removeButton.setBounds(328 , 346, 100, 40);
        removeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                app.roomDAO.delete(id);
                id = -1;
                roomNameField.setText("");
                serviceComboBox.setSelectedIndex(0);
                roomTypeComboBox.setSelectedItem(0);
                statusComboBox.setSelectedItem(0);            
            }
            
        });

        userInteractionPanel.add(removeButton);

        // table panel
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(null);
        tablePanel.setBackground(new Color(0, 0, 0, 0));
        tablePanel.setBounds(20, 135, getWidth() / 2 - 20, 391);
        add(tablePanel);



        // title label
        OutlineLabel titleLabel = new OutlineLabel("ROOM");
        titleLabel.setOutlineColor(new Color(201, 38, 84));
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(30, 0, getWidth() - 30, 135);
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(monogramFont.deriveFont(100f));
        add(titleLabel);


        // Table
        roomTableModel.addColumn("Name");
        roomTableModel.addColumn("Room type");
        roomTableModel.addColumn("Status");
        roomTableModel.addColumn("Service");
        roomTableModel.addColumn("Total");


        Table roomTypeTable = new Table(roomTableModel);
        roomTypeTable.getTableHeader().setReorderingAllowed(false);
        roomTypeTable.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = roomTypeTable.getSelectedRow();


                    Room room = app.roomsList.get(row);
                    RoomType rt = app.roomTypeDAO.get(room.getType());
                    Service s = app.serviceDAO.get(room.getService());

                    roomNameField.setText(room.getName());
                    statusComboBox.setSelectedItem(app.roomDAO.isInUsed(room.getId())?"In used":"Ready");
                    serviceComboBox.setSelectedItem(String.valueOf(room.getService()) + " - " + s.getName() + ": " + String.valueOf(s.getPrice()));
                    roomTypeComboBox.setSelectedItem(String.valueOf(room.getType()) + " - " + rt.getName() + ": " + String.valueOf(rt.getPrice()));

                    id = room.getId();
                }
            }
        });
        
        ScrollPane scroll = new ScrollPane(roomTypeTable);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBounds(16 , 66 , tablePanel.getWidth() - 32 , 200);

        tablePanel.add(scroll);


        // Total button
        Button totalButton = new Button();
        totalButton.setText("Total");
        totalButton.setFont(plainFont.deriveFont(25f));
        totalButton.setBounds(16 , 16, 130, 40);
        totalButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                TextBoxSearchFilter search = new TextBoxSearchFilter("", 2);
                TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(roomTableModel);
                rowSorter.setRowFilter(search);
                roomTypeTable.setRowSorter(rowSorter);
            }
            
        });
        tablePanel.add(totalButton);

        // Booked button
        Button bookedButton = new Button();
        bookedButton.setText("Booked room");
        bookedButton.setFont(plainFont.deriveFont(25f));
        bookedButton.setBounds(172 , 16, 130, 40);
        bookedButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                TextBoxSearchFilter search = new TextBoxSearchFilter("In used", 2);
                TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(roomTableModel);
                rowSorter.setRowFilter(search);
                roomTypeTable.setRowSorter(rowSorter);
            }
            
        });
        tablePanel.add(bookedButton);

        // Unbooked button
        Button unbookedButton = new Button();
        unbookedButton.setText("Unbooked room");
        unbookedButton.setFont(plainFont.deriveFont(25f));
        unbookedButton.setBounds(328 , 16, 130, 40);
        unbookedButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                TextBoxSearchFilter search = new TextBoxSearchFilter("Ready", 2);
                TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(roomTableModel);
                rowSorter.setRowFilter(search);
                roomTypeTable.setRowSorter(rowSorter);
            }
            
        });
        tablePanel.add(unbookedButton);
        
        // Back bu'on
        Button backButton = new Button();
        backButton.setText("<-");
        backButton.setFont(plainFont.deriveFont(20f));
        backButton.setBounds(40, 460, 50, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                backButtonPressed.emitSignal();
            }
        });
        add(backButton);

        // Back bu'on
        Button exportButton = new Button();
        exportButton.setIcon(
            new ImageIcon(new ImageIcon("src/com/hazbinhotel/image/excel.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH))
        );
        exportButton.setBounds(40, 405, 50, 50);
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
                        
                        
                        headerRow.createCell(++headerCellCount).setCellValue("Name");
                        headerRow.createCell(++headerCellCount).setCellValue("service");
                        headerRow.createCell(++headerCellCount).setCellValue("type");

                        int rowCount = 1;

                        for (Room room : app.roomsList){
                            Row row = sheet.createRow(rowCount++);
                            int cellCount = 0;
                            row.createCell(cellCount++).setCellValue(rowCount - 1);
                            row.createCell(cellCount++).setCellValue(room.getName());
                            row.createCell(cellCount++).setCellValue(app.serviceDAO.get(room.getService()).getName());
                            row.createCell(cellCount++).setCellValue(app.roomTypeDAO.get(room.getType()).getName());
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

        setBackGroundImage("src/com/hazbinhotel/image/tab.png");

        app.mainMenuPanel.roomButtonPressed.connectSignal((arg) -> {
            serviceComboBox.removeAllItems();
            roomTypeComboBox.removeAllItems();

            for (Service s : app.servicesList){
                serviceComboBox.addItem(String.valueOf(s.getId()) + " - " + s.getName() + ": " + String.valueOf(s.getPrice()));
            }

            for (RoomType rt : app.roomTypesList){
                roomTypeComboBox.addItem(String.valueOf(rt.getId()) + " - " + rt.getName() + ": " + String.valueOf(rt.getPrice()));
            }
        });

        app.roomChanged.connectSignal((arg) -> {
            fillToTable();
        });
    }


    public void fillToTable(){
        roomTableModel.setRowCount(0);
        for (Room r : app.roomsList){
            boolean inUse = app.roomDAO.isInUsed(r.getId());
            roomTableModel.addRow(new Object[]{
                r.getName(), 
                String.valueOf(r.getType()) + " - " + app.roomTypeDAO.get(r.getType()).getName(),
                inUse?"In used":"Ready",
                String.valueOf(r.getService()) + " - " + app.serviceDAO.get(r.getService()).getName(),
                app.roomTypeDAO.get(r.getType()).getPrice() + app.serviceDAO.get(r.getService()).getPrice()
            });
        }
    }

    public Map<String, Object> getForm(){
        Map<String, Object> dict = new HashMap<>();
        
        dict.put("name", roomNameField.getText());
        dict.put("room_type", App.extractInteger(String.valueOf(roomTypeComboBox.getSelectedItem())));
        dict.put("service", App.extractInteger(String.valueOf(serviceComboBox.getSelectedItem())));
        
        return dict;
    }
}
