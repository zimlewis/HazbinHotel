package com.hazbinhotel.gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hazbinhotel.App;
import com.hazbinhotel.CustomSwing.Button;
import com.hazbinhotel.CustomSwing.ComboBox;
import com.hazbinhotel.CustomSwing.DayChooser;
import com.hazbinhotel.CustomSwing.MonthChooser;
import com.hazbinhotel.CustomSwing.NumberOnlyTextField;
import com.hazbinhotel.CustomSwing.ScrollPane;
import com.hazbinhotel.CustomSwing.Table;
import com.hazbinhotel.CustomSwing.TextBoxSearchFilter;
import com.hazbinhotel.CustomSwing.TextField;
import com.hazbinhotel.CustomSwing.YearChooser;
import com.hazbinhotel.entity.Staff;
import com.zimlewis.Signal;

public class StaffManage extends BasePanel{
    JPanel tablePanel, userInteractionPanel;
    DefaultTableModel staffTableModel;
    TextField nameField, phoneField, emailField, addressField, personalIDField;
    ComboBox<String> genderComboBox, managerComboBox, roleComboBox;
    YearChooser yearOfBirthChooser;
    MonthChooser monthOfBirthChooser;
    DayChooser dayOfBirthChooser;
    JLabel IDLabel;
    int id = -1;
    List<Staff> staffManaged; 
    List<Staff> managerList;
    public Signal backButtonPressed = new Signal();
    
    public StaffManage(App a){
        super(a);
        // user interaction panel
        userInteractionPanel = new JPanel();
        userInteractionPanel.setLayout(null);
        userInteractionPanel.setBackground(new Color(255, 255, 255, 0));
        userInteractionPanel.setVisible(false);
        userInteractionPanel.setBounds(20, 16, 570, 450);
        add(userInteractionPanel);


        // table panel
        tablePanel = new JPanel();
        tablePanel.setLayout(null);
        tablePanel.setBackground(new Color(255, 255, 255, 0));
        tablePanel.setVisible(true);
        tablePanel.setBounds(20, 16, 570, 450);
        add(tablePanel);

        staffTableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells uneditable
                return false;
            }
        };
        staffTableModel.addColumn("Full Name");
        staffTableModel.addColumn("Date Of Birth");
        staffTableModel.addColumn("Gender");
        staffTableModel.addColumn("Address");
        staffTableModel.addColumn("Phone Number");
        staffTableModel.addColumn("Email");
        staffTableModel.addColumn("Personal ID");

        staffTableModel.addRow(new Object[]{1, 2, 3, 4, 5, 6, 7, 8});



        Table staffTable = new Table(staffTableModel);
        staffTable.getTableHeader().setReorderingAllowed(false);
        staffTable.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {

                    managerList = app.staffDAO.getManagers();
                    managerComboBox.removeAllItems();
                    for (Staff m: managerList){
                        managerComboBox.addItem(String.format("%d - %s", m.getId(), m.getName()));
                    }
                    
                    int row = staffTable.getSelectedRow();
                    Staff s = staffManaged.get(row);
                    id = s.getId();
                    toggleTable();
                    
                    setId(id);
                    nameField.setText(s.getName());
                    phoneField.setText(s.getPhone());
                    emailField.setText(s.getEmail());
                    addressField.setText(s.getAddress());
                    personalIDField.setText(s.getPersonalId());
                    genderComboBox.setSelectedIndex(s.isFemale()?1:0);
                    managerComboBox.setSelectedItem(String.format("%d - %s", app.staffDAO.get(s.getManager()).getId(), app.staffDAO.get(s.getManager()).getName()));
                    roleComboBox.setSelectedIndex(s.getRole());
                    yearOfBirthChooser.setYear(s.getBirthday().getYear());
                    monthOfBirthChooser.setMonth(s.getBirthday().getMonthValue() - 1);
                    dayOfBirthChooser.setDay(s.getBirthday().getDayOfMonth() - 1);

                }
            }
        });
        
        ScrollPane scroll = new ScrollPane(staffTable);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBounds(16 , 66 , tablePanel.getWidth() - 32 , tablePanel.getHeight() - 66);

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
                TextBoxSearchFilter search = new TextBoxSearchFilter(searchField.getText(), 4);
                TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(staffTableModel);
                rowSorter.setRowFilter(search);
                staffTable.setRowSorter(rowSorter);
            }
            
        });
        tablePanel.add(searchButton);


        // Back to list bu'on
        Button backToListButton = new Button();
        backToListButton.setBounds(20 , userInteractionPanel.getHeight() - 40, 130, 40);
        backToListButton.setText("<- Back to list");
        backToListButton.setFont(plainFont.deriveFont(20f));
        backToListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                toggleTable();
            }
        });

        userInteractionPanel.add(backToListButton);

        // Update button
        Button updateButton = new Button();
        updateButton.setBounds(userInteractionPanel.getWidth() - 120 , userInteractionPanel.getHeight() - 40, 100, 40);
        updateButton.setText("Update");
        updateButton.setFont(plainFont.deriveFont(20f));
        updateButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                Staff oldStaff = app.staffDAO.get(id);
                
                oldStaff.setRole(roleComboBox.getSelectedIndex());
                if (oldStaff.getRole() == 0){
                    oldStaff.setManager(App.extractInteger(String.valueOf(managerComboBox.getSelectedItem())));
                }
                else{
                    oldStaff.setManager(null);
                }
        
                int success = app.staffDAO.update(oldStaff);
                if (success == 0){
                    JOptionPane.showMessageDialog(null, "Cannot update staff, please try again later");
                }
                else{
                    JOptionPane.showMessageDialog(null, "Successfully updated staff");
                    toggleTable();
                }
            }
            
        });

        userInteractionPanel.add(updateButton);

        // Remove button
        Button removeButton = new Button();
        removeButton.setBounds(userInteractionPanel.getWidth() - 240 , userInteractionPanel.getHeight() - 40, 100, 40);
        removeButton.setText("Remove");
        removeButton.setFont(plainFont.deriveFont(20f));

        userInteractionPanel.add(removeButton);

        // ID Label
        IDLabel = new JLabel("ID: 0", SwingConstants.CENTER);
        IDLabel.setBounds(0, 10, userInteractionPanel.getWidth(), 20);
        IDLabel.setFont(plainFont.deriveFont(15f));
        IDLabel.setOpaque(false);
        IDLabel.setForeground(Color.black);

        userInteractionPanel.add(IDLabel);

        // Name
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(plainFont.deriveFont(20f));
        nameLabel.setForeground(Color.black);
        nameLabel.setBounds(20, 25, 100, 30);

        userInteractionPanel.add(nameLabel);

        nameField = new TextField();
        nameField.setEnabled(false);
        nameField.setFont(plainFont.deriveFont(20f));
        nameField.setBounds(20, 50, userInteractionPanel.getWidth() / 2 - 40, 40);

        userInteractionPanel.add(nameField);

        // Email
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(plainFont.deriveFont(20f));
        emailLabel.setForeground(Color.black);
        emailLabel.setBounds(20, 90, 100, 30);

        userInteractionPanel.add(emailLabel);

        emailField = new TextField();
        emailField.setEnabled(false);
        emailField.setFont(plainFont.deriveFont(20f));
        emailField.setBounds(20, 115, userInteractionPanel.getWidth() / 2 - 40, 40);

        userInteractionPanel.add(emailField);


        // Gender
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(plainFont.deriveFont(20f));
        genderLabel.setForeground(Color.black);
        genderLabel.setBounds(20, 155, 100, 30);

        userInteractionPanel.add(genderLabel);

        genderComboBox = new ComboBox<String>(new String[]{"Male", "Female"});
        genderComboBox.setEnabled(false);
        genderComboBox.setFont(plainFont.deriveFont(20f));
        genderComboBox.setBounds(20, 180, userInteractionPanel.getWidth() / 2 - 40, 40);

        userInteractionPanel.add(genderComboBox);

        // Phone number
        JLabel phoneNumberLabel = new JLabel("Phone number:");
        phoneNumberLabel.setFont(plainFont.deriveFont(20f));
        phoneNumberLabel.setForeground(Color.black);
        phoneNumberLabel.setBounds(20, 220, 100, 30);

        userInteractionPanel.add(phoneNumberLabel);

        phoneField = new NumberOnlyTextField();
        phoneField.setEnabled(false);
        phoneField.setFont(plainFont.deriveFont(20f));
        phoneField.setBounds(20, 245, userInteractionPanel.getWidth() / 2 - 40, 40);

        userInteractionPanel.add(phoneField);

        // Date of birth
        JLabel dateOfBirthLabel = new JLabel("Date of birth:");
        dateOfBirthLabel.setBounds(userInteractionPanel.getWidth() / 2 + 20, 25, 100, 30);
        dateOfBirthLabel.setForeground(Color.black);
        dateOfBirthLabel.setFont(plainFont.deriveFont(20f));

        userInteractionPanel.add(dateOfBirthLabel);

        yearOfBirthChooser = new YearChooser();
        yearOfBirthChooser.setEnabled(false);
        yearOfBirthChooser.setBounds(userInteractionPanel.getWidth() / 2 + 20, 50, (userInteractionPanel.getWidth() / 2 - 40) / 3 - 10, 40);
        yearOfBirthChooser.setFont(plainFont.deriveFont(20f));

        userInteractionPanel.add(yearOfBirthChooser);

        monthOfBirthChooser = new MonthChooser();
        monthOfBirthChooser.setEnabled(false);
        monthOfBirthChooser.setBounds(userInteractionPanel.getWidth() / 2 + 20 + (userInteractionPanel.getWidth() / 2 - 40) / 3, 50, (userInteractionPanel.getWidth() / 2 - 40) / 3 - 10, 40);
        monthOfBirthChooser.setFont(plainFont.deriveFont(20f));
        monthOfBirthChooser.setType(MonthChooser.NUMERIC);

        userInteractionPanel.add(monthOfBirthChooser);

        dayOfBirthChooser = new DayChooser(yearOfBirthChooser, monthOfBirthChooser);
        dayOfBirthChooser.setEnabled(false);
        dayOfBirthChooser.setBounds(userInteractionPanel.getWidth() / 2 + 20 + ((userInteractionPanel.getWidth() / 2 - 40) / 3) * 2, 50, (userInteractionPanel.getWidth() / 2 - 40) / 3 - 10, 40);
        dayOfBirthChooser.setFont(plainFont.deriveFont(20f));

        userInteractionPanel.add(dayOfBirthChooser);

        // Address
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(userInteractionPanel.getWidth() / 2 + 20, 90, 100, 30);
        addressLabel.setForeground(Color.BLACK);
        addressLabel.setFont(plainFont.deriveFont(20f));

        userInteractionPanel.add(addressLabel);

        addressField = new TextField();
        addressField.setEnabled(false);
        addressField.setBounds(userInteractionPanel.getWidth() / 2 + 20, 115, userInteractionPanel.getWidth() / 2 - 40, 40);
        addressField.setFont(plainFont.deriveFont(20f));

        userInteractionPanel.add(addressField);

        // Personal ID
        JLabel personalIDLabel = new JLabel("Personal ID:");
        personalIDLabel.setBounds(userInteractionPanel.getWidth() / 2 + 20, 155, 100, 30);
        personalIDLabel.setForeground(Color.BLACK);
        personalIDLabel.setFont(plainFont.deriveFont(20f));

        userInteractionPanel.add(personalIDLabel);

        personalIDField = new NumberOnlyTextField();
        personalIDField.setEnabled(false);
        personalIDField.setBounds(userInteractionPanel.getWidth() / 2 + 20, 180, userInteractionPanel.getWidth() / 2 - 40, 40);
        personalIDField.setFont(plainFont.deriveFont(20f));

        userInteractionPanel.add(personalIDField);

        // Manager
        JLabel managerLabel = new JLabel("Manager:");
        managerLabel.setBounds(userInteractionPanel.getWidth() / 2 + 20, 220, 100, 30);
        managerLabel.setForeground(Color.BLACK);
        managerLabel.setFont(plainFont.deriveFont(20f));

        userInteractionPanel.add(managerLabel);

        managerComboBox = new ComboBox<>();
        managerComboBox.setBounds(userInteractionPanel.getWidth() / 2 + 20, 245, userInteractionPanel.getWidth() / 2 - 40, 40);
        managerComboBox.setFont(plainFont.deriveFont(20f));

        userInteractionPanel.add(managerComboBox);
        
        /*
         * x: userInteractionPanel.getWidth() / 2 + 20
         * w: userInteractionPanel.getWidth() / 2 - 40
         * end_x: (userInteractionPanel.getWidth() / 2 + 20) + (userInteractionPanel.getWidth() / 2 - 40)
         * = userInteractionPanel.getWidth() / 2 + 20 + userInteractionPanel.getWidth() / 2 - 40
         * = userInteractionPanel.getWidth() + 20 - 40
         * = userInteractionPanel.getWidth() - 20
         * 
         * x: 20
         * w = ?
         * end_x = userInteractionPanel.getWidth() - 20
         * 
         * userInteractionPanel.getWidth() - 20 = 20 + ?
         * ? = userInteractionPanel.getWidth() - 20 - 20 = userInteractionPanel.getWidth() - 40
         * w = userInteractionPanel.getWidth() - 40
         * 
         */

        // Role number
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setFont(plainFont.deriveFont(20f));
        roleLabel.setForeground(Color.black);
        roleLabel.setBounds(20, 285, 100, 30);

        userInteractionPanel.add(roleLabel);

        roleComboBox = new ComboBox<>(new String[]{"Employee", "Manager"});
        roleComboBox.setFont(plainFont.deriveFont(20f));
        roleComboBox.setBounds(
            20, 
            310, 
            userInteractionPanel.getWidth() - 40, 
            40
        );

        userInteractionPanel.add(roleComboBox);

        // Export bu'on
        Button exportButton = new Button();
        exportButton.setIcon(
            new ImageIcon(new ImageIcon("src/com/hazbinhotel/image/excel.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH))
        );
        exportButton.setBounds(tablePanel.getWidth() - 50, getHeight() - 70, 50, 50);
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
                        headerRow.createCell(++headerCellCount).setCellValue("Email");
                        headerRow.createCell(++headerCellCount).setCellValue("Phone");
                        headerRow.createCell(++headerCellCount).setCellValue("Birthday");
                        headerRow.createCell(++headerCellCount).setCellValue("Address");
                        headerRow.createCell(++headerCellCount).setCellValue("Gender");
                        headerRow.createCell(++headerCellCount).setCellValue("Role");
                        headerRow.createCell(++headerCellCount).setCellValue("Personal id");
                        headerRow.createCell(++headerCellCount).setCellValue("Manager");

                        int rowCount = 1;

                        for (Staff staff : staffManaged){
                            Row row = sheet.createRow(rowCount++);
                            int cellCount = 0;
                            row.createCell(cellCount++).setCellValue(rowCount - 1);
                            row.createCell(cellCount++).setCellValue(staff.getName());
                            row.createCell(cellCount++).setCellValue(staff.getEmail());
                            row.createCell(cellCount++).setCellValue(staff.getPhone());
                            row.createCell(cellCount++).setCellValue(String.format("%d - %s - %d", staff.getBirthday().getYear(), String.valueOf(staff.getBirthday().getMonth()), staff.getBirthday().getDayOfMonth()));
                            row.createCell(cellCount++).setCellValue(staff.getAddress());
                            row.createCell(cellCount++).setCellValue(staff.isFemale()?"Male":"Female");
                            row.createCell(cellCount++).setCellValue(staff.getRole()==1?"Manager":"Staff");
                            row.createCell(cellCount++).setCellValue(staff.getPersonalId());
                            row.createCell(cellCount++).setCellValue(app.staffDAO.get(staff.getManager()).getName());
                        }

                        FileOutputStream outputStream = new FileOutputStream(filePath);
                        workbook.write(outputStream);
                        workbook.close();
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
        backButton.setBounds(40, getHeight() - 70, 50, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                backButtonPressed.emitSignal();
                tablePanel.setVisible(true);
                userInteractionPanel.setVisible(false);
            }
        });
        add(backButton);

        app.staffChanged.connectSignal((arg) -> {
            staffManaged = app.staffDAO.getStaffOfManagers(app.getId());
            fillToTable();
        });

        app.mainMenuPanel.staffManageButtonPressed.connectSignal((arg) -> {
            staffManaged = app.staffDAO.getStaffOfManagers(app.getId());
            fillToTable();
        });
        
        setBackGroundImage("src/com/hazbinhotel/image/account-bg.png");
    }

    void toggleTable(){
        tablePanel.setVisible(!tablePanel.isVisible());
        userInteractionPanel.setVisible(!userInteractionPanel.isVisible());
    }

    public void setId(int id){
        IDLabel.setText("ID: " + String.valueOf(id));
    }

    public void fillToTable(){
        staffTableModel.setRowCount(0);
        for (Staff s : staffManaged){
            staffTableModel.addRow(new Object[]{
                s.getName(),
                s.getBirthday(),
                s.isFemale()?"Female":"Male",
                s.getAddress(),
                s.getPhone(),
                s.getEmail(),
                s.getPersonalId()
            });
        }
    }
}
