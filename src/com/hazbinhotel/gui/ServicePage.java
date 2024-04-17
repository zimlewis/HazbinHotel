package com.hazbinhotel.gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
import com.hazbinhotel.CustomSwing.NumberOnlyTextField;
import com.hazbinhotel.CustomSwing.OutlineLabel;
import com.hazbinhotel.CustomSwing.ScrollPane;
import com.hazbinhotel.CustomSwing.Table;
import com.hazbinhotel.CustomSwing.TextArea;
import com.hazbinhotel.CustomSwing.TextBoxSearchFilter;
import com.hazbinhotel.CustomSwing.TextField;
import com.hazbinhotel.entity.Service;
import com.zimlewis.Signal;

public class ServicePage extends BasePanel{
    Button addButton, removeButton, editButton;
    DefaultTableModel serviceTableModel;
    TextField serviceNameField, servicePriceField;
    TextArea serviceDescriptionArea;
    int id = -1;
    public Signal backButtonPressed = new Signal();

    public ServicePage(App a){
        super(a);
        // user interaction panel
        JPanel userInteractionPanel = new JPanel();
        userInteractionPanel.setLayout(null);
        userInteractionPanel.setBackground(new Color(0, 0, 0, 0));
        userInteractionPanel.setBounds(getWidth() / 2, 135, getWidth() / 2 - 20, 391);
        add(userInteractionPanel);

        // name
        JLabel nameLabel = new JLabel("Name: ", SwingConstants.RIGHT);
        nameLabel.setBounds(16, 16, 160, 50);
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setFont(plainFont.deriveFont(30f));
        userInteractionPanel.add(nameLabel);


        serviceNameField = new TextField();
        serviceNameField.setBounds(180, 16, 250, 50);
        serviceNameField.setFont(plainFont.deriveFont(30f));
        serviceNameField.setForeground(new Color(201, 38, 84));
        userInteractionPanel.add(serviceNameField);

        // price
        JLabel priceLabel = new JLabel("Price: ", SwingConstants.RIGHT);
        priceLabel.setBounds(16, 82, 160, 50);
        priceLabel.setForeground(Color.BLACK);
        priceLabel.setFont(plainFont.deriveFont(30f));
        userInteractionPanel.add(priceLabel);


        servicePriceField = new NumberOnlyTextField();
        servicePriceField.setBounds(180, 82, 250, 50);
        servicePriceField.setFont(plainFont.deriveFont(30f));
        servicePriceField.setForeground(new Color(201, 38, 84));
        userInteractionPanel.add(servicePriceField);

        // capacity
        JLabel descriptionLabel = new JLabel("Description: ", SwingConstants.RIGHT);
        descriptionLabel.setBounds(16, 148, 160, 50);
        descriptionLabel.setForeground(Color.BLACK);
        descriptionLabel.setFont(plainFont.deriveFont(30f));
        userInteractionPanel.add(descriptionLabel);


        serviceDescriptionArea = new TextArea();
        serviceDescriptionArea.setFont(plainFont.deriveFont(30f));
        serviceDescriptionArea.setForeground(new Color(201, 38, 84));
        serviceDescriptionArea.setLineWrap(true);
        serviceDescriptionArea.setWrapStyleWord(true);
        userInteractionPanel.add(serviceDescriptionArea);

        ScrollPane serviceDescriptionScroll = new ScrollPane(serviceDescriptionArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        serviceDescriptionScroll.setBounds(180, 148, 250, 180);
        userInteractionPanel.add(serviceDescriptionScroll);

        // Add button
        Button addButton = new Button();
        addButton.setText("Add");
        addButton.setFont(plainFont.deriveFont(30f));
        addButton.setBounds(16 , 346, 100, 40);
        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                Map<String, Object> dict = getForm();

                Service s = new Service(
                    0, 
                    String.valueOf(dict.get("name")),
                    String.valueOf(dict.get("note")),
                    Double.valueOf(String.valueOf(dict.get("price")))
                );

                app.serviceDAO.insert(s);
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

                Service s = new Service(
                    id, 
                    String.valueOf(dict.get("name")),
                    String.valueOf(dict.get("note")),
                    Double.valueOf(String.valueOf(dict.get("price")))
                );

                app.serviceDAO.update(s);
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
                app.serviceDAO.delete(id);
                id = -1;
                serviceDescriptionArea.setText("");
                serviceNameField.setText("");
                servicePriceField.setText("");
            }
            
        });
        userInteractionPanel.add(removeButton);



        // table panel
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(null);
        tablePanel.setBackground(new Color(0, 0, 0, 0));
        tablePanel.setBounds(20, 135, getWidth() / 2 - 20, 391);
        add(tablePanel);



        // Table
        serviceTableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells uneditable
                return false;
            }
        };
        serviceTableModel.addColumn("Name");
        serviceTableModel.addColumn("Price");
        serviceTableModel.addColumn("Description");



        Table serviceTable = new Table(serviceTableModel);
        serviceTable.getTableHeader().setReorderingAllowed(false);
        serviceTable.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = serviceTable.getSelectedRow();
                    Service s = app.servicesList.get(row);
                    id = s.getId();

                    serviceNameField.setText(s.getName());
                    servicePriceField.setText(String.valueOf(s.getPrice()));
                    serviceDescriptionArea.setText(s.getNote());
                }
            }
        });
        
        ScrollPane scroll = new ScrollPane(serviceTable);
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
                TextBoxSearchFilter search = new TextBoxSearchFilter(searchField.getText(), 0);
                TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(serviceTableModel);
                rowSorter.setRowFilter(search);
                serviceTable.setRowSorter(rowSorter);
            }
            
        });
        tablePanel.add(searchButton);

        // Back bu'on
        Button backButton = new Button();
        backButton.setText("<-");
        backButton.setBounds(40, 460, 50, 50);
        backButton.setFont(plainFont.deriveFont(20f));
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
                        
                        
                        headerRow.createCell(1).setCellValue("Name");
                        headerRow.createCell(2).setCellValue("Note");
                        headerRow.createCell(3).setCellValue("Price");

                        int rowCount = 1;

                        for (Service service : app.servicesList){
                            Row row = sheet.createRow(rowCount++);
                            int cellCount = 0;
                            row.createCell(cellCount++).setCellValue(rowCount - 1);
                            row.createCell(cellCount++).setCellValue(service.getName());
                            row.createCell(cellCount++).setCellValue(service.getNote());
                            row.createCell(cellCount++).setCellValue(String.format("%,.2f", service.getPrice()));
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
        OutlineLabel titleLabel = new OutlineLabel("SERVICE");
        titleLabel.setOutlineColor(new Color(201, 38, 84));
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(30, 0, getWidth() - 30, 135);
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(monogramFont.deriveFont(100f));
        add(titleLabel);
        


        setBackGroundImage("src/com/hazbinhotel/image/tab.png");

        app.serviceChanged.connectSignal((arg) -> {
            fillToTable();
        });
    }

    void fillToTable(){
        serviceTableModel.setRowCount(0);
        for (Service rt : app.servicesList){
            serviceTableModel.addRow(new Object[]{rt.getName(), rt.getPrice(), rt.getNote()});
        }
    }

    Map<String, Object> getForm(){
        Map<String, Object> dict = new HashMap<>();

        dict.put("name", serviceNameField.getText());
        dict.put("price", servicePriceField.getText());
        dict.put("note", serviceDescriptionArea.getText());

        return dict;
    }
}
