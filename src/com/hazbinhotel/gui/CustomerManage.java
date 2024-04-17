package com.hazbinhotel.gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

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

import com.hazbinhotel.App;
import com.hazbinhotel.CustomSwing.Button;
import com.hazbinhotel.CustomSwing.ComboBox;
import com.hazbinhotel.CustomSwing.DayChooser;
import com.hazbinhotel.CustomSwing.MonthChooser;
import com.hazbinhotel.CustomSwing.NumberOnlyTextField;
import com.hazbinhotel.CustomSwing.OutlineLabel;
import com.hazbinhotel.CustomSwing.ScrollPane;
import com.hazbinhotel.CustomSwing.Table;
import com.hazbinhotel.CustomSwing.TextArea;
import com.hazbinhotel.CustomSwing.TextBoxSearchFilter;
import com.hazbinhotel.CustomSwing.TextField;
import com.hazbinhotel.CustomSwing.YearChooser;
import com.hazbinhotel.entity.Customer;
import com.zimlewis.InputValidation;
import com.zimlewis.Signal;
import com.zimlewis.ZQL;

import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
public class CustomerManage extends BasePanel{
    DefaultTableModel customerTableModel;
    JPanel tablePanel, userInteractionPanel;
    JLabel customerIDLabel, bookedCountLabel;
    TextField nameField, IDField, emailField, phoneNumberField;
    ComboBox<String> customerTypeComboBox;
    TextArea customerNoteArea;
    DayChooser dayOfBirthChooser;
    MonthChooser monthOfBirthChooser;
    YearChooser yearOfBirthChooser;
    int id = -1;
    public Signal backButtonPressed = new Signal();

    public CustomerManage(App a){
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


        // Customer id
        customerIDLabel = new JLabel("Customer ID: CT05");
        customerIDLabel.setBounds(170, 20, 190, 40);
        customerIDLabel.setForeground(Color.BLACK);
        customerIDLabel.setFont(plainFont.deriveFont(30f));

        userInteractionPanel.add(customerIDLabel);


        // Booked count
        bookedCountLabel = new JLabel("Booked 5 times");
        bookedCountLabel.setBounds(170, 45, 190, 40);
        bookedCountLabel.setForeground(Color.BLACK);
        bookedCountLabel.setFont(plainFont.deriveFont(20f));

        userInteractionPanel.add(bookedCountLabel);

        // Name
        JLabel nameLabel = new JLabel("Name:", SwingConstants.RIGHT);
        nameLabel.setBounds(170, 95, 150, 40);
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setFont(plainFont.deriveFont(30f));
        
        userInteractionPanel.add(nameLabel);

        nameField = new TextField();
        nameField.setBounds(330, 95, 190, 40);
        nameField.setFont(plainFont.deriveFont(30f));

        userInteractionPanel.add(nameField);


        // personal id
        JLabel personalIDLabel = new JLabel("Personal ID:", SwingConstants.RIGHT);
        personalIDLabel.setBounds(170, 145, 150, 40);
        personalIDLabel.setForeground(Color.BLACK);
        personalIDLabel.setFont(plainFont.deriveFont(30f));
        
        userInteractionPanel.add(personalIDLabel);

        IDField = new TextField();
        IDField.setBounds(330, 145, 190, 40);
        IDField.setFont(plainFont.deriveFont(30f));

        userInteractionPanel.add(IDField);

        // date of birth
        JLabel dateOfBirthLabel = new JLabel("Date Of Birth:", SwingConstants.RIGHT);
        dateOfBirthLabel.setBounds(170, 195, 150, 40);
        dateOfBirthLabel.setForeground(Color.BLACK);
        dateOfBirthLabel.setFont(plainFont.deriveFont(30f));
        
        userInteractionPanel.add(dateOfBirthLabel);

        yearOfBirthChooser = new YearChooser();
        yearOfBirthChooser.setBounds(330, 195, 60, 40);
        yearOfBirthChooser.setFont(plainFont.deriveFont(20f));

        userInteractionPanel.add(yearOfBirthChooser);

        monthOfBirthChooser = new MonthChooser();
        monthOfBirthChooser.setBounds(395, 195, 60, 40);
        monthOfBirthChooser.setType(MonthChooser.NUMERIC);
        monthOfBirthChooser.setFont(plainFont.deriveFont(20f));

        userInteractionPanel.add(monthOfBirthChooser);

        dayOfBirthChooser = new DayChooser(yearOfBirthChooser, monthOfBirthChooser);
        dayOfBirthChooser.setBounds(460, 195, 60, 40);
        dayOfBirthChooser.setFont(plainFont.deriveFont(20f));

        userInteractionPanel.add(dayOfBirthChooser);

        // email
        JLabel emailLabel = new JLabel("Email:", SwingConstants.RIGHT);
        emailLabel.setBounds(170, 245, 150, 40);
        emailLabel.setForeground(Color.BLACK);
        emailLabel.setFont(plainFont.deriveFont(30f));
        
        userInteractionPanel.add(emailLabel);

        emailField = new TextField();
        emailField.setBounds(330, 245, 190, 40);
        emailField.setFont(plainFont.deriveFont(30f));

        userInteractionPanel.add(emailField);

        // Customer type
        JLabel customerTypeLabel = new JLabel("Customer Type:", SwingConstants.RIGHT);
        customerTypeLabel.setBounds((userInteractionPanel.getWidth() - 20 + 170) / 2, 95, 150, 40);
        customerTypeLabel.setForeground(Color.BLACK);
        customerTypeLabel.setFont(plainFont.deriveFont(30f));
        
        userInteractionPanel.add(customerTypeLabel);

        customerTypeComboBox = new ComboBox<String>(new String[]{"VIP", "New customer", "Familiar customer"});
        customerTypeComboBox.setBounds((userInteractionPanel.getWidth() - 20 + 170) / 2 + 160, 95, 190, 40);
        customerTypeComboBox.setFont(plainFont.deriveFont(30f));

        userInteractionPanel.add(customerTypeComboBox);

        // Phone
        JLabel phoneLabel = new JLabel("Phone:", SwingConstants.RIGHT);
        phoneLabel.setBounds((userInteractionPanel.getWidth() - 20 + 170) / 2, 145, 150, 40);
        phoneLabel.setForeground(Color.BLACK);
        phoneLabel.setFont(plainFont.deriveFont(30f));
        
        userInteractionPanel.add(phoneLabel);

        phoneNumberField = new NumberOnlyTextField();
        phoneNumberField.setBounds((userInteractionPanel.getWidth() - 20 + 170) / 2 + 160, 145, 190, 40);
        phoneNumberField.setFont(plainFont.deriveFont(30f));

        userInteractionPanel.add(phoneNumberField);

        // Note
        JLabel noteLabel = new JLabel("Note:", SwingConstants.RIGHT);
        noteLabel.setBounds((userInteractionPanel.getWidth() - 20 + 170) / 2, 195, 150, 40);
        noteLabel.setForeground(Color.BLACK);
        noteLabel.setFont(plainFont.deriveFont(30f));
        
        userInteractionPanel.add(noteLabel);

        customerNoteArea = new TextArea();
        customerNoteArea.setFont(plainFont.deriveFont(30f));
        ScrollPane customerNoteScroll = new ScrollPane(customerNoteArea);
        customerNoteScroll.setBounds((userInteractionPanel.getWidth() - 20 + 170) / 2 + 160, 195, 190, 90);
        
        userInteractionPanel.add(customerNoteScroll);

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
                // dict.put("name", nameField.getText());
                // dict.put("id_field", IDField.getText());
                // dict.put("birthday", LocalDate.of(yearOfBirthChooser.getYear(), monthOfBirthChooser.getMonth(), dayOfBirthChooser.getDay()));
                // dict.put("email", emailField.getText());
                // dict.put("phone", phoneNumberField.getText());
                // dict.put("customer_type", customerTypeComboBox.getSelectedItem());
                // dict.put("note", customerNoteArea);
                Customer c = new Customer(
                    0, 
                    String.valueOf(getForm().get("name")), 
                    String.valueOf(getForm().get("phone")), 
                    String.valueOf(getForm().get("pid")), 
                    (LocalDate) getForm().get("birthday"), 
                    String.valueOf(getForm().get("customer_type")), 
                    String.valueOf(getForm().get("email")), 
                    String.valueOf(getForm().get("note"))
                );

                boolean validated = true;

                String errorMessage = "Could not add customer";

                if (!InputValidation.isNameValid(c.getName())){
                    validated = false;
                    errorMessage += "\n + Wrong name format";
                }

                if (!InputValidation.isPhoneValid(c.getPhone())){
                    validated = false;
                    errorMessage += "\n + Wrong phone format";
                }

                if (!InputValidation.isEmailValid(c.getEmail())){
                    validated = false;
                    errorMessage += "\n + Wrong email format";
                }
            
            
                if (!(Period.between(c.getBirthday(), LocalDate.now()).getYears() >= 18)){
                    validated = false;
                    errorMessage += "\n + Customer must be at least 18 year"; 
                }

                if (!(ZQL.excuteQueryToArrayList(app.connection, "select * from customer where phone = ?", c.getPhone()).size() == 0)){
                    validated = false;
                    errorMessage += "\n + This phone number has been registered";
                }

                if (!(ZQL.excuteQueryToArrayList(app.connection, "select * from customer where personal_id = ?", c.getPersonalId()).size() == 0)){
                    validated = false;
                    errorMessage += "\n + This personal id number has been registered";
                }

                int success = 0;
                if (validated){
                    success = app.customerDAO.insert(c);

                    if (success != 1){
                        errorMessage += "\n + Database error, please try again";
                    }
                }

                if (!validated || success != 1){
                    JOptionPane.showMessageDialog(null, errorMessage);
                }else {
                    toggleTable();
                    JOptionPane.showMessageDialog(null, "Successfully created customer");
                }
            }
        });

        userInteractionPanel.add(addButton);

        // Edit button
        ImageIcon editButtonIcon = new ImageIcon(
            new ImageIcon("src/com/hazbinhotel/image/pen.png")
                .getImage()
                    .getScaledInstance(40, 40, Image.SCALE_SMOOTH)
        );
        Button editButton = new Button();
        editButton.setIcon(editButtonIcon);
        editButton.setBounds(55, 175, 40, 40);
        editButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // dict.put("name", nameField.getText());
                // dict.put("id_field", IDField.getText());
                // dict.put("birthday", LocalDate.of(yearOfBirthChooser.getYear(), monthOfBirthChooser.getMonth(), dayOfBirthChooser.getDay()));
                // dict.put("email", emailField.getText());
                // dict.put("phone", phoneNumberField.getText());
                // dict.put("customer_type", customerTypeComboBox.getSelectedItem());
                // dict.put("note", customerNoteArea);
                Customer oldC = app.customerDAO.get(id);

                Customer c = new Customer(
                    id, 
                    String.valueOf(getForm().get("name")), 
                    String.valueOf(getForm().get("phone")), 
                    String.valueOf(getForm().get("pid")), 
                    (LocalDate) getForm().get("birthday"), 
                    String.valueOf(getForm().get("customer_type")), 
                    String.valueOf(getForm().get("email")), 
                    String.valueOf(getForm().get("note"))
                );

                boolean validated = true;

                String errorMessage = "Could not add customer";

                if (!InputValidation.isNameValid(c.getName())){
                    validated = false;
                    errorMessage += "\n + Wrong name format";
                }

                if (!InputValidation.isPhoneValid(c.getPhone())){
                    validated = false;
                    errorMessage += "\n + Wrong phone format";
                }

                if (!InputValidation.isEmailValid(c.getEmail())){
                    validated = false;
                    errorMessage += "\n + Wrong email format";
                }
            
            
                if (!(Period.between(c.getBirthday(), LocalDate.now()).getYears() >= 18)){
                    validated = false;
                    errorMessage += "\n + Customer must be at least 18 year"; 
                }

                if (!(ZQL.excuteQueryToArrayList(app.connection, "select * from customer where phone = ?", c.getPhone()).size() == 0) && !c.getPhone().equals(oldC.getPhone())){
                    validated = false;
                    errorMessage += "\n + This phone number has been registered";
                }

                if (!(ZQL.excuteQueryToArrayList(app.connection, "select * from customer where personal_id = ?", c.getPersonalId()).size() == 0 && !c.getPersonalId().equals(oldC.getPhone()))){
                    validated = false;
                    errorMessage += "\n + This personal id number has been registered";
                }

                int success = 0;
                if (validated){
                    success = app.customerDAO.update(c);

                    if (success != 1){
                        errorMessage += "\n + Database error, please try again";
                    }
                }

                if (!validated || success != 1){
                    JOptionPane.showMessageDialog(null, errorMessage);
                }else {
                    toggleTable();
                    JOptionPane.showMessageDialog(null, "Successfully created customer");
                }
            }
        });

        userInteractionPanel.add(editButton);


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
                app.customerDAO.delete(id);
                id = -1;
                toggleTable();
            }
        });

        userInteractionPanel.add(removeButton);

        // table panel
        tablePanel = new JPanel();
        tablePanel.setLayout(null);
        tablePanel.setBackground(new Color(255, 255, 255, 0));
        tablePanel.setVisible(true);
        tablePanel.setBounds(20, 135, getWidth() - 40, 321);
        add(tablePanel);



        // Table
        customerTableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells uneditable
                return false;
            }
        };
        customerTableModel.addColumn("Personal ID");
        customerTableModel.addColumn("Full Name");
        customerTableModel.addColumn("Phone Number");
        customerTableModel.addColumn("Date Of Birth");
        customerTableModel.addColumn("Email");
        customerTableModel.addColumn("Note");


        Table customerTable = new Table(customerTableModel);
        customerTable.getTableHeader().setReorderingAllowed(false);
        customerTable.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { 
                    int row = customerTable.getSelectedRow();
                    Customer c = app.customersList.get(row);
                    id = c.getId();
                    
                    nameField.setText(c.getName());
                    IDField.setText(c.getPersonalId());
                    emailField.setText(c.getEmail());
                    phoneNumberField.setText(c.getPhone());
                    customerTypeComboBox.setSelectedItem(c.getType());
                    customerNoteArea.setText(c.getNote());
                    yearOfBirthChooser.setYear(c.getBirthday().getYear());
                    monthOfBirthChooser.setMonth(c.getBirthday().getMonthValue() - 1);
                    dayOfBirthChooser.setDay(c.getBirthday().getDayOfMonth() - 1);
                    setCustomerID(String.valueOf(c.getId()));
                    setBookCount(app.bookingDAO.getBookingByCustomer(c.getId()).size());


                    toggleTable();
                }
            }
        });
        
        ScrollPane scroll = new ScrollPane(customerTable);
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
                TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(customerTableModel);
                rowSorter.setRowFilter(search);
                customerTable.setRowSorter(rowSorter);
            }
            
        });
        tablePanel.add(searchButton);


        // new button
        Button newButton = new Button();
        newButton.setText("+ New");
        newButton.setBounds(16 , 280 , tablePanel.getWidth() - 32 , 40);
        newButton.setFont(plainFont.deriveFont(20f));
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                id = -1;
                nameField.setText("");
                IDField.setText("");
                emailField.setText("");
                phoneNumberField.setText("");
                customerTypeComboBox.setSelectedIndex(0);
                customerNoteArea.setText("");
                yearOfBirthChooser.setYear(LocalDate.now().getYear());
                monthOfBirthChooser.setMonth(LocalDate.now().getMonthValue() - 1);
                dayOfBirthChooser.setDay(LocalDate.now().getDayOfMonth() - 1);
                setCustomerID(String.valueOf(""));
                setBookCount(0);
                toggleTable();
            }
        });

        tablePanel.add(newButton);

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
                        
                        
                        headerRow.createCell(++headerCellCount).setCellValue("Name");
                        headerRow.createCell(++headerCellCount).setCellValue("Email");
                        headerRow.createCell(++headerCellCount).setCellValue("Phone");
                        headerRow.createCell(++headerCellCount).setCellValue("Birthday");
                        headerRow.createCell(++headerCellCount).setCellValue("Personal ID");
                        headerRow.createCell(++headerCellCount).setCellValue("Type");
                        headerRow.createCell(++headerCellCount).setCellValue("Note");


                        int rowCount = 1;

                        for (Customer customer : app.customersList){
                            Row row = sheet.createRow(rowCount++);
                            int cellCount = 0;
                            row.createCell(cellCount++).setCellValue(rowCount - 1);
                            row.createCell(cellCount++).setCellValue(customer.getName());
                            row.createCell(cellCount++).setCellValue(customer.getEmail());
                            row.createCell(cellCount++).setCellValue(customer.getPhone());
                            row.createCell(cellCount++).setCellValue(String.format("%d - %s - %d", customer.getBirthday().getYear(), String.valueOf(customer.getBirthday().getMonth()), customer.getBirthday().getDayOfMonth()));
                            row.createCell(cellCount++).setCellValue(customer.getPersonalId());
                            row.createCell(cellCount++).setCellValue(customer.getType());
                            row.createCell(cellCount++).setCellValue(customer.getNote());

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


        // title label
        OutlineLabel titleLabel = new OutlineLabel("CUSTOMER");
        titleLabel.setOutlineColor(new Color(201, 38, 84));
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(30, 0, getWidth() - 30, 135);
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(monogramFont.deriveFont(100f));
        add(titleLabel);
        
        app.customerChanged.connectSignal((arg) -> {
            fillToTable();
        });

        setBackGroundImage("src/com/hazbinhotel/image/tab.png");
    }

    void toggleTable(){
        tablePanel.setVisible(!tablePanel.isVisible());
        userInteractionPanel.setVisible(!userInteractionPanel.isVisible());
    }

    public void setCustomerID(String id){
        customerIDLabel.setText(String.format("Customer ID: %s", id));
    }

    public void fillToTable(){
        customerTableModel.setRowCount(0);
        for (Customer c : app.customerDAO.getAll()){
            customerTableModel.addRow(new Object[]{
                c.getPersonalId(),
                c.getName(),
                c.getPhone(),
                c.getBirthday(),
                c.getEmail(),
                c.getNote()
            });
        }
    }

    public Map<String, Object> getForm(){
        Map<String, Object> dict = new HashMap<>();

        // TextField , , , , ;
        // ComboBox<String> customerTypeComboBox;
        // TextArea customerNoteArea;

        dict.put("name", nameField.getText());
        dict.put("pid", IDField.getText());
        dict.put("birthday", LocalDate.of(yearOfBirthChooser.getYear(), monthOfBirthChooser.getMonth() + 1, dayOfBirthChooser.getDay() + 1));
        dict.put("email", emailField.getText());
        dict.put("phone", phoneNumberField.getText());
        dict.put("customer_type", customerTypeComboBox.getSelectedItem());
        dict.put("note", customerNoteArea.getText());


        return dict;
    }

    public void setBookCount(int count){
        bookedCountLabel.setText(String.format("Booked %d times", count));
    }
}
