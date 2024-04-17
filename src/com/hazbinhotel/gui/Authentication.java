package com.hazbinhotel.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.Period;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.hazbinhotel.App;
import com.hazbinhotel.CustomSwing.Button;
import com.hazbinhotel.CustomSwing.ComboBox;
import com.hazbinhotel.CustomSwing.DayChooser;
import com.hazbinhotel.CustomSwing.MonthChooser;
import com.hazbinhotel.CustomSwing.NumberOnlyTextField;
import com.hazbinhotel.CustomSwing.PasswordField;
import com.hazbinhotel.CustomSwing.TextField;
import com.hazbinhotel.CustomSwing.YearChooser;
import com.hazbinhotel.dao.StaffDAO;
import com.hazbinhotel.entity.Staff;
import com.zimlewis.InputValidation;
import com.zimlewis.ZQL;

import java.util.*;


public class Authentication extends BasePanel{
    JPanel loginPanel, registerPanel;
    TextField registerNameField;
    TextField registerEmailField;
    PasswordField registerPasswordField;
    PasswordField registerConfirmPasswordField;
    NumberOnlyTextField registerPhoneField;
    YearChooser registerYearTextField;
    MonthChooser registerMonthChooser;
    DayChooser registerDayChooser;
    TextField registerAddressField;
    ComboBox<String> registerGenderComboBox;
    NumberOnlyTextField loginPhoneNumberField;
    PasswordField loginPasswordField;
    public Authentication(App a){
        super(a);
        //Authentication panel
        setLayout(null);
        setSize(960 , 540);
        setBackground(Color.BLACK);

        //Register panel
        registerPanel = new JPanel();
        registerPanel.setLayout(null);
        registerPanel.setBounds(0, 0, 960, 540);
        registerPanel.setVisible(false);
        registerPanel.setBackground(new Color(0 , 0 , 0 , 0));

        JLabel registerTitleLabel = new JLabel("REGISTER" , SwingConstants.CENTER);
        registerTitleLabel.setBounds(getWidth() / 2 - 320 / 2, 200 , 320 , 100);
        registerTitleLabel.setForeground(Color.black);
        registerTitleLabel.setFont(monogramFont.deriveFont(60f));

        registerPanel.add(registerTitleLabel);

        //Toggle
        JLabel switchToLoginLabel = new JLabel("ALREADY A MEMBER? LOGIN HERE");
        switchToLoginLabel.setBounds(40, 490, 260, 40);
        switchToLoginLabel.setFont(plainFont.deriveFont(20f));
        switchToLoginLabel.setForeground(Color.black);
        switchToLoginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        switchToLoginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    toggleAuthentication();
                }
            }
        });

        registerPanel.add(switchToLoginLabel);

        //Name
        JLabel registerNameLabel = new JLabel("NAME", SwingConstants.RIGHT);
        registerNameLabel.setBounds(50 , 300 , 140 , 30);
        registerNameLabel.setVerticalAlignment(SwingConstants.CENTER);
        registerNameLabel.setForeground(Color.black);
        registerNameLabel.setFont(plainFont.deriveFont(20f));

        registerPanel.add(registerNameLabel);

        registerNameField = new TextField();
        registerNameField.setBounds(200, 300, 230, 30);
        registerNameField.setFont(plainFont.deriveFont(17f));
        registerPanel.add(registerNameField);

        //Email
        JLabel registerEmailLabel = new JLabel("EMAIL", SwingConstants.RIGHT);
        registerEmailLabel.setBounds(50 , 340 , 140 , 30);
        registerEmailLabel.setVerticalAlignment(SwingConstants.CENTER);
        registerEmailLabel.setForeground(Color.black);
        registerEmailLabel.setFont(plainFont.deriveFont(20f));

        registerPanel.add(registerEmailLabel);
        
        registerEmailField = new TextField();
        registerEmailField.setBounds(200, 340, 230, 30);
        registerEmailField.setFont(plainFont.deriveFont(17f));
        registerPanel.add(registerEmailField);

        //Password
        JLabel registerPasswordLabel = new JLabel("PASSWORD", SwingConstants.RIGHT);
        registerPasswordLabel.setBounds(50 , 380 , 140 , 30);
        registerPasswordLabel.setVerticalAlignment(SwingConstants.CENTER);
        registerPasswordLabel.setForeground(Color.black);
        registerPasswordLabel.setFont(plainFont.deriveFont(20f));

        registerPanel.add(registerPasswordLabel);
        
        registerPasswordField = new PasswordField();
        registerPasswordField.setBounds(200, 380, 230, 30);
        registerPasswordField.setTextFont(plainFont.deriveFont(20f));
        registerPanel.add(registerPasswordField);

        //Confirm password
        JLabel registerConfirmPasswordLabel = new JLabel("CONFIRM", SwingConstants.RIGHT);
        registerConfirmPasswordLabel.setBounds(50 , 420 , 140 , 30);
        registerConfirmPasswordLabel.setVerticalAlignment(SwingConstants.CENTER);
        registerConfirmPasswordLabel.setForeground(Color.black);
        registerConfirmPasswordLabel.setFont(plainFont.deriveFont(20f));

        registerPanel.add(registerConfirmPasswordLabel);

        registerConfirmPasswordField = new PasswordField();
        registerConfirmPasswordField.setBounds(200, 420, 230, 30);
        registerConfirmPasswordField.setTextFont(plainFont.deriveFont(20f));
        registerPanel.add(registerConfirmPasswordField);

        //Phone Number
        JLabel registerPhoneLabel = new JLabel("PHONE", SwingConstants.RIGHT);
        registerPhoneLabel.setBounds(getWidth() / 2 + 50 , 300 , 140 , 30);
        registerPhoneLabel.setVerticalAlignment(SwingConstants.CENTER);
        registerPhoneLabel.setForeground(Color.black);
        registerPhoneLabel.setFont(plainFont.deriveFont(20f));

        registerPanel.add(registerPhoneLabel);

        registerPhoneField = new NumberOnlyTextField();
        registerPhoneField.setBounds(getWidth() / 2 + 200, 300, 230, 30);
        registerPhoneField.setFont(plainFont.deriveFont(17f));

        registerPanel.add(registerPhoneField);

        //Date of birth
        JLabel registerDateOfBirthLabel = new JLabel("DATE OF BIRTH", SwingConstants.RIGHT);
        registerDateOfBirthLabel.setBounds(getWidth() / 2 + 50 , 340 , 140 , 30);
        registerDateOfBirthLabel.setVerticalAlignment(SwingConstants.CENTER);
        registerDateOfBirthLabel.setForeground(Color.black);
        registerDateOfBirthLabel.setFont(plainFont.deriveFont(20f));

        registerPanel.add(registerDateOfBirthLabel);

        //Year
        registerYearTextField = new YearChooser();
        registerYearTextField.setBounds(getWidth() / 2 + 200, 340, 70, 30);
        registerYearTextField.setFont(plainFont.deriveFont(17f));
        registerPanel.add(registerYearTextField);

        //Month
        registerMonthChooser = new MonthChooser();
        registerMonthChooser.setBounds(getWidth() / 2 + 280, 340, 70, 30);
        registerMonthChooser.setFont(plainFont.deriveFont(17f));
        registerPanel.add(registerMonthChooser);

        // Day
        registerDayChooser = new DayChooser(registerYearTextField, registerMonthChooser);
        registerDayChooser.setBounds(getWidth() / 2 + 360, 340, 70, 30);
        registerDayChooser.setFont(plainFont.deriveFont(17f));
        registerPanel.add(registerDayChooser);

        // Address
        JLabel registerAddressLabel = new JLabel("ADDRESS", SwingConstants.RIGHT);
        registerAddressLabel.setBounds(getWidth() / 2 + 50 , 380 , 140 , 30);
        registerAddressLabel.setVerticalAlignment(SwingConstants.CENTER);
        registerAddressLabel.setForeground(Color.black);
        registerAddressLabel.setFont(plainFont.deriveFont(20f));

        registerPanel.add(registerAddressLabel);

        registerAddressField = new TextField();
        registerAddressField.setBounds(getWidth() / 2 + 200, 380, 230, 30);
        registerAddressField.setFont(plainFont.deriveFont(17f));
        registerPanel.add(registerAddressField);

        // Gender
        JLabel registerGenderLabel = new JLabel("GENDER", SwingConstants.RIGHT);
        registerGenderLabel.setBounds(getWidth() / 2 + 50 , 420 , 140 , 30);
        registerGenderLabel.setVerticalAlignment(SwingConstants.CENTER);
        registerGenderLabel.setForeground(Color.black);
        registerGenderLabel.setFont(plainFont.deriveFont(20f));

        registerPanel.add(registerGenderLabel);

        registerGenderComboBox = new ComboBox<>(new String[] {"Male", "Female"});
        registerGenderComboBox.setBounds(getWidth() / 2 + 200, 420, 230, 30);
        registerGenderComboBox.setFont(plainFont.deriveFont(17f));
        registerPanel.add(registerGenderComboBox);

        //Register button
        Button registerButton = new Button();
        registerButton.setText("Enter");
        registerButton.setBounds(getWidth()/2 + 200, 470, 100, 40);
        registerButton.setFont(plainFont.deriveFont(15f));
        registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                boolean validated = true;

                String errorMessage = "";
                registerButton.setEnabled(false);

                if (!InputValidation.isNameValid(String.valueOf(getRegisterForm().get("name")))){
                    validated = false;
                    errorMessage += "\n + Wrong name format";
                    registerNameField.setText("");
                }

                if (!InputValidation.isPhoneValid(String.valueOf(getRegisterForm().get("phone")))){
                    validated = false;
                    errorMessage += "\n + Wrong phone format";
                    registerPhoneField.setText("");
                }

                if (!InputValidation.isEmailValid(String.valueOf(getRegisterForm().get("email")))){
                    validated = false;
                    errorMessage += "\n + Wrong email format";
                    registerEmailField.setText("");
                }
            
                if (!(String.valueOf(getRegisterForm().get("password")).length() >= 8)){
                    validated = false;
                    errorMessage += "\n + Password must be at least 8 characters";
                    registerPasswordField.setText("");
                    registerConfirmPasswordField.setText("");
                }

                if (!(String.valueOf(getRegisterForm().get("password")).equals(String.valueOf(getRegisterForm().get("confirm"))))){
                    validated = false;
                    errorMessage += "\n + Confirm password doesn't match";
                    registerConfirmPasswordField.setText("");
                    registerPasswordField.setText("");
                }
            
                if (!(Period.between((LocalDate) getRegisterForm().get("birthday"), LocalDate.now()).getYears() >= 18)){
                    validated = false;
                    errorMessage += "\n + You must be at least 18 year";
                    registerYearTextField.requestFocus();
                }

                if (!(ZQL.excuteQueryToArrayList(app.connection, "select * from staff where phone = ?", getRegisterForm().get("phone")).size() == 0)){
                    validated = false;
                    errorMessage += "\n + Your phone number has already been registered";
                    registerPhoneField.setText("");
                }

                List<Staff> managerStaff = app.staffDAO.getManagers();
                
                int index = Math.abs(new Random().nextInt()) % managerStaff.size();
                Integer managerID = (Integer) managerStaff.get(index).getId();

                if (validated){
                    System.out.println();
                    Staff staff = new Staff(
                        0, 
                        String.valueOf(getRegisterForm().get("name")), 
                        String.valueOf(getRegisterForm().get("email")), 
                        String.valueOf(getRegisterForm().get("phone")), 
                        String.valueOf(getRegisterForm().get("password")),
                        null,
                        (LocalDate) getRegisterForm().get("birthday"), 
                        String.valueOf(getRegisterForm().get("address")), 
                        (boolean) getRegisterForm().get("gender"), 
                        new byte[]{0}, 
                        managerID, 
                        0
                    );

                    

                    int success = app.staffDAO.insert(staff);



                    if (success == 0){
                        validated = false;
                        errorMessage += "\n + Database error, please try again later";
                    }
                }



                if (!validated){
                    JOptionPane.showMessageDialog(null, "Error sign you in:" + errorMessage);
                    registerButton.setEnabled(true);
                    return;
                }

                ArrayList<Map<String, Object>> a = ZQL.excuteQueryToArrayList(app.connection, "select * from staff where phone = ?", getRegisterForm().get("phone"));
                app.authenticated.emitSignal((int) a.get(0).get("id"));
            }
            
        });
        
        registerPanel.add(registerButton);
        
        //Exit button
        Button registerExitButton = new Button();
        registerExitButton.setText("Exit");
        registerExitButton.setBounds(getWidth()/2 + 325, 470, 100, 40);
        registerExitButton.setFont(plainFont.deriveFont(15f));
        registerExitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerExitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
            
        });


        registerPanel.add(registerExitButton);




        //Login panel
        loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBounds(0, 0, 960, 540);
        loginPanel.setVisible(true);
        loginPanel.setBackground(new Color(0 , 0 , 255 , 0));

        //Title
        JLabel loginTitleLabel = new JLabel("LOGIN" , SwingConstants.CENTER);
        loginTitleLabel.setBounds(getWidth() / 2 - 320 / 2, 200 , 320 , 100);
        loginTitleLabel.setForeground(Color.black);
        loginTitleLabel.setFont(monogramFont.deriveFont(60f));
        
        loginPanel.add(loginTitleLabel);
        
        //Toggle
        JLabel switchToRegisterLabel = new JLabel("NOT A MEMBER? REGISTER HERE");
        switchToRegisterLabel.setBounds(40, 490, 260, 40);
        switchToRegisterLabel.setFont(plainFont.deriveFont(20f));
        switchToRegisterLabel.setForeground(Color.black);
        switchToRegisterLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        switchToRegisterLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    toggleAuthentication();
                }
            }
        });

        loginPanel.add(switchToRegisterLabel);

        //Phone number
        JLabel loginPhoneNumberLabel = new JLabel("PHONE NUMBER", SwingConstants.RIGHT);
        loginPhoneNumberLabel.setVerticalAlignment(SwingConstants.CENTER);
        loginPhoneNumberLabel.setBounds(250, 300, 140, 30);
        loginPhoneNumberLabel.setForeground(Color.black);
        loginPhoneNumberLabel.setFont(plainFont.deriveFont(20f));
        loginPanel.add(loginPhoneNumberLabel);

        loginPhoneNumberField = new NumberOnlyTextField();
        loginPhoneNumberField.setFont(plainFont.deriveFont(17f));
        loginPhoneNumberField.setBounds(400, 300, 260, 30);
        loginPanel.add(loginPhoneNumberField);

        //Password
        JLabel loginPasswordLabel = new JLabel("PASSWORD", SwingConstants.RIGHT);
        loginPasswordLabel.setVerticalAlignment(SwingConstants.CENTER);
        loginPasswordLabel.setBounds(250, 350, 140, 30);
        loginPasswordLabel.setForeground(Color.black);
        loginPasswordLabel.setFont(plainFont.deriveFont(20f));
        loginPanel.add(loginPasswordLabel);

        loginPasswordField = new PasswordField();
        loginPasswordField.setTextFont(plainFont.deriveFont(20f));
        loginPasswordField.setBounds(400, 350, 260, 30);
        loginPanel.add(loginPasswordField);

        //Login button
        Button loginButton = new Button();
        loginButton.setText("Enter");
        loginButton.setBounds(getWidth()/2 - 100 - 30, 400, 100, 40);
        loginButton.setFont(plainFont.deriveFont(15f));
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                ArrayList<Map<String, Object>> a = ZQL.excuteQueryToArrayList(app.connection, "select * from staff where phone = ? and password = ?", getLoginForm().get("phone"), getLoginForm().get("password"));

                if (a.size() > 0){
                    app.authenticated.emitSignal((int) a.get(0).get("id"));
                }
                else{
                    JOptionPane.showMessageDialog(null, "Wrong username or password");
                    loginPhoneNumberField.requestFocus();
                }
            }
            
        });
        
        loginPanel.add(loginButton);
        
        //Exit button
        Button loginExitButton = new Button();
        loginExitButton.setText("Exit");
        loginExitButton.setBounds(getWidth()/2 + 30, 400, 100, 40);
        loginExitButton.setFont(plainFont.deriveFont(15f));
        loginExitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginExitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
            
        });


        loginPanel.add(loginExitButton);


        //Background panel
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setLayout(null);
        backgroundPanel.setBackground(new Color(0, 0, 0, 0));
        backgroundPanel.setBounds(0, 0, 960, 540);


        //Logo
        JLabel logoLabel;
        ImageIcon logoLabelImage = new ImageIcon(
            new ImageIcon("src/com/hazbinhotel/image/logo_black_background.png")
                .getImage()
                    .getScaledInstance(324, 194, Image.SCALE_SMOOTH)
        );

        logoLabel = new JLabel(logoLabelImage);
        logoLabel.setBounds(getWidth() / 2 - 324 / 2, 20, 324, 194);
        backgroundPanel.add(logoLabel);

        //Charlie
        JLabel charlieLabel;
        ImageIcon charlieIcon = new ImageIcon(
            new ImageIcon("src/com/hazbinhotel/image/charlie.png")
                .getImage()
                    .getScaledInstance(70, 70, Image.SCALE_SMOOTH)
        );

        charlieLabel = new JLabel(charlieIcon);
        charlieLabel.setBounds(240 , getHeight() - 114 + 30 , 70 , 70);
        backgroundPanel.add(charlieLabel);
        

        //Set up panels
        add(registerPanel);
        add(loginPanel);
        add(backgroundPanel);


        setBackGroundImage("src/com/hazbinhotel/image/authentication-bg.png");
        
        

        
    }

    void toggleAuthentication(){
        loginPanel.setVisible(!loginPanel.isVisible());
        registerPanel.setVisible(!registerPanel.isVisible());
    }

    Map<String, Object> getLoginForm(){
        Map<String, Object> dict = new Hashtable<String, Object>();
        dict.put("phone", (String)loginPhoneNumberField.getText());
        dict.put("password", String.valueOf(loginPasswordField.getPassword()));
        return dict;
    }

    Map<String, Object> getRegisterForm(){
        Map<String, Object> dict = new Hashtable<String, Object>();

        String name = registerNameField.getText();
        String email = registerEmailField.getText();
        String password = String.valueOf(registerPasswordField.getPassword());
        String passwordConfirm = String.valueOf(registerConfirmPasswordField.getPassword());
        String registerPhone = registerPhoneField.getText();
        LocalDate birthday = LocalDate.of(registerYearTextField.getYear(), registerMonthChooser.getMonth() + 1, registerDayChooser.getDay() + 1);
        String address = registerAddressField.getText();
        boolean gender = registerGenderComboBox.getSelectedIndex() == 1;

        dict.put("name", name);
        dict.put("email", email);
        dict.put("password", password);
        dict.put("confirm", passwordConfirm);
        dict.put("phone", registerPhone);
        dict.put("birthday", birthday);
        dict.put("address", address);
        dict.put("gender", gender);

        return dict;
    }
}
