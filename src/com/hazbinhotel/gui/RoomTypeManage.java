package com.hazbinhotel.gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.ImageConsumer;
import java.io.FileOutputStream;
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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hazbinhotel.CustomSwing.TextField;
import com.hazbinhotel.entity.RoomType;
import com.zimlewis.Signal;
import com.zimlewis.ZQL;
import com.hazbinhotel.App;
import com.hazbinhotel.CustomSwing.Button;
import com.hazbinhotel.CustomSwing.ComboBox;
import com.hazbinhotel.CustomSwing.NumberOnlyTextField;
import com.hazbinhotel.CustomSwing.OutlineLabel;
import com.hazbinhotel.CustomSwing.ScrollPane;
import com.hazbinhotel.CustomSwing.Table;
import com.hazbinhotel.CustomSwing.TextBoxSearchFilter;





public class RoomTypeManage extends BasePanel{
    TextField roomTypeNameField, priceTextField;
    ComboBox<Integer> roomCapacityDropdown;
    Button addButton, removeButton, editButton;
    DefaultTableModel roomTypeTableModel;
    Integer id = null;


    public Signal backButtonPressed = new Signal();
    
    public RoomTypeManage(App a){
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


        roomTypeNameField = new TextField();
        roomTypeNameField.setBounds(180, 16, 250, 50);
        roomTypeNameField.setFont(plainFont.deriveFont(30f));
        roomTypeNameField.setForeground(new Color(201, 38, 84));
        userInteractionPanel.add(roomTypeNameField);

        // price
        JLabel priceLabel = new JLabel("Price: ", SwingConstants.RIGHT);
        priceLabel.setBounds(16, 82, 160, 50);
        priceLabel.setForeground(Color.BLACK);
        priceLabel.setFont(plainFont.deriveFont(30f));
        userInteractionPanel.add(priceLabel);


        priceTextField = new NumberOnlyTextField();
        priceTextField.setBounds(180, 82, 250, 50);
        priceTextField.setFont(plainFont.deriveFont(30f));
        priceTextField.setForeground(new Color(201, 38, 84));
        userInteractionPanel.add(priceTextField);

        // capacity
        JLabel capacityLabel = new JLabel("Capacity: ", SwingConstants.RIGHT);
        capacityLabel.setBounds(16, 148, 160, 50);
        capacityLabel.setForeground(Color.BLACK);
        capacityLabel.setFont(plainFont.deriveFont(30f));
        userInteractionPanel.add(capacityLabel);


        roomCapacityDropdown = new ComboBox<Integer>(new Integer[]{
            1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20
        });
        roomCapacityDropdown.setBounds(180, 148, 250, 50);
        roomCapacityDropdown.setFont(plainFont.deriveFont(30f));
        roomCapacityDropdown.setForeground(new Color(201, 38, 84));
        userInteractionPanel.add(roomCapacityDropdown);

        // Add button
        Button addButton = new Button();
        addButton.setText("Add");
        addButton.setFont(plainFont.deriveFont(30f));
        addButton.setBounds(16 , 214, 100, 40);
        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                Map<String, Object> d = getForm();
                
                RoomType r = new RoomType(
                    0, 
                    String.valueOf(d.get("name")),
                    (int) d.get("capacity"), 
                    Double.parseDouble(String.valueOf(d.get("price")))
                );

                if (ZQL.excuteQueryToArrayList(app.connection, "select * from room_type where name = ?", r.getName()).size() != 0){
                    JOptionPane.showMessageDialog(null, "This room type name has already been added");
                    return;
                }

                app.roomTypeDAO.insert(r);
            }
                    
        });

        userInteractionPanel.add(addButton);

        // Edit button
        Button editButton = new Button();
        editButton.setText("Edit");
        editButton.setFont(plainFont.deriveFont(30f));
        editButton.setBounds(172 , 214, 100, 40);
        editButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                Map<String, Object> d = getForm();
                
                RoomType r = new RoomType(
                    id, 
                    String.valueOf(d.get("name")),
                    (int) d.get("capacity"), 
                    Double.parseDouble(String.valueOf(d.get("price")))
                );

                if ((ZQL.excuteQueryToArrayList(app.connection, "select * from room_type where name = ?", r.getName()).size() != 0) && !r.getName().equals(app.roomTypeDAO.get(id).getName())){
                    JOptionPane.showMessageDialog(null, "This room type name has already been added");
                    return;
                }

                app.roomTypeDAO.update(r);
            }
            
        });

        userInteractionPanel.add(editButton);

        // Edit button
        Button removeButton = new Button();
        removeButton.setText("Remove");
        removeButton.setFont(plainFont.deriveFont(30f));
        removeButton.setBounds(328 , 214, 100, 40);
        removeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                app.roomTypeDAO.delete(id);
                id = -1;
                roomTypeNameField.setText("");
                priceTextField.setText("");
                roomCapacityDropdown.setSelectedItem(1);
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
        roomTypeTableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells uneditable
                return false;
            }
        };
        roomTypeTableModel.addColumn("Name");
        roomTypeTableModel.addColumn("Price");
        roomTypeTableModel.addColumn("Capacity");

        Table roomTypeTable = new Table(roomTypeTableModel);
        roomTypeTable.getTableHeader().setReorderingAllowed(false);
        roomTypeTable.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) { 
                    int row = roomTypeTable.getSelectedRow();
                    RoomType roomType = app.roomTypeDAO.getAll().get(row);

                    id = roomType.getId();


                    roomTypeNameField.setText(roomType.getName());
                    priceTextField.setText(String.valueOf(roomType.getPrice()));
                    roomCapacityDropdown.setSelectedItem(roomType.getCapacity());
                }
            }
        });
        
        ScrollPane scroll = new ScrollPane(roomTypeTable);
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
                TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(roomTypeTableModel);
                rowSorter.setRowFilter(search);
                roomTypeTable.setRowSorter(rowSorter);
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
                        headerRow.createCell(2).setCellValue("Capacity");
                        headerRow.createCell(3).setCellValue("Price");

                        int rowCount = 1;

                        for (RoomType roomType : app.roomTypesList){
                            Row row = sheet.createRow(rowCount++);
                            int cellCount = 0;
                            row.createCell(cellCount++).setCellValue(rowCount - 1);
                            row.createCell(cellCount++).setCellValue(roomType.getName());
                            row.createCell(cellCount++).setCellValue(roomType.getCapacity());
                            row.createCell(cellCount++).setCellValue(String.format("%,.2f", roomType.getPrice()));
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
        OutlineLabel titleLabel = new OutlineLabel("ROOM TYPE");
        titleLabel.setOutlineColor(new Color(201, 38, 84));
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(30, 0, getWidth() - 30, 135);
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(monogramFont.deriveFont(100f));
        add(titleLabel);
        


        setBackGroundImage("src/com/hazbinhotel/image/tab.png");

        app.roomTypeChanged.connectSignal((arg) -> {
            fillToTable();
        });
    }
    

    void fillToTable(){
        roomTypeTableModel.setRowCount(0);
        for (RoomType rt : app.roomTypesList){
            roomTypeTableModel.addRow(new Object[]{rt.getName(), rt.getPrice(), rt.getCapacity()});
        }
    }

    public Map<String, Object> getForm(){
        Map<String, Object> dict = new HashMap<>();
        
        dict.put("name", roomTypeNameField.getText());
        dict.put("price", priceTextField.getText());
        dict.put("capacity", Integer.parseInt(String.valueOf(roomCapacityDropdown.getSelectedItem())));

        return dict;
    }
}

