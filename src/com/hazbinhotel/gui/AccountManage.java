package com.hazbinhotel.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.time.LocalDate;
import java.time.Period;
import java.util.Hashtable;
import java.util.Map;

import com.hazbinhotel.App;
import com.hazbinhotel.CustomSwing.Button;
import com.hazbinhotel.CustomSwing.ComboBox;
import com.hazbinhotel.CustomSwing.DayChooser;
import com.hazbinhotel.CustomSwing.ImageAvatar;
import com.hazbinhotel.CustomSwing.MonthChooser;
import com.hazbinhotel.CustomSwing.NumberOnlyTextField;
import com.hazbinhotel.CustomSwing.OutlineLabel;
import com.hazbinhotel.CustomSwing.PasswordField;
import com.hazbinhotel.CustomSwing.TextField;
import com.hazbinhotel.CustomSwing.YearChooser;
import com.hazbinhotel.entity.Staff;
import com.zimlewis.ImageBytes;
import com.zimlewis.InputValidation;
import com.zimlewis.Signal;
import com.zimlewis.ZQL;

public class AccountManage extends BasePanel {
    ImageAvatar avatar;
    JPanel userInteractionPanel, changePasswordPanel;
    PasswordField oldPasswordField, newPasswordField, repeatPasswordField;
    OutlineLabel accountNameLabel;
    TextField nameField, phoneField, emailField, addressField, personalIDField;
    ComboBox<String> genderComboBox;
    YearChooser yearOfBirthChooser;
    MonthChooser monthOfBirthChooser;
    DayChooser dayOfBirthChooser;

    JLabel IDLabel;
    OutlineLabel titleLabel;
    public Signal backButtonPressed = new Signal();

    public AccountManage(App a) {
        super(a);
        
        // Information panel
        JPanel informationPanel = new JPanel();
        informationPanel.setBounds(603, 14, 339, 512);
        informationPanel.setBackground(new Color(0, 0, 0, 0));
        informationPanel.setLayout(null);
        add(informationPanel);

        // Avatar
        avatar = new ImageAvatar();
        avatar.setSize(130, 130);
        avatar.setLocation(informationPanel.getWidth() / 2 - avatar.getWidth() / 2, 90);
        setAvatar(ImageBytes.convertImageToBytes(new ImageIcon("src/com/hazbinhotel/image/charlie.png").getImage()));
        avatar.setBorderSize(5);
        avatar.setForeground(Color.BLACK);

        informationPanel.add(avatar);

        // Name label
        accountNameLabel = new OutlineLabel();
        accountNameLabel.setText("Me May Beo");
        accountNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        accountNameLabel.setSize(informationPanel.getWidth(), 50);
        accountNameLabel.setLocation(0, 210);
        accountNameLabel.setFont(plainFont.deriveFont(20f));
        accountNameLabel.setForeground(Color.BLACK);

        informationPanel.add(accountNameLabel);

        // Change image button
        Button changeImageButton = new Button();
        changeImageButton.setText("Choose image");
        changeImageButton.setSize(100, 50);
        changeImageButton.setLocation(informationPanel.getWidth() / 2 - changeImageButton.getWidth() / 2, 260);
        changeImageButton.setFont(plainFont.deriveFont(20f));
        changeImageButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Image Files", "jpg", "jpeg", "png", "gif");
                fileChooser.setFileFilter(filter);
                int returnValue = fileChooser.showOpenDialog(app);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    byte[] imageData = ImageBytes.convertImageToBytes(new ImageIcon(selectedFile.getAbsolutePath()).getImage());

                    
                    setAvatar(imageData);
                }
            }
            
        });

        informationPanel.add(changeImageButton);

        // User interaction
        userInteractionPanel = new JPanel();
        userInteractionPanel.setBounds(17, 132, 567, 329);
        userInteractionPanel.setLayout(null);
        userInteractionPanel.setVisible(true);
        userInteractionPanel.setBackground(new Color(0, 0, 0, 0));

        add(userInteractionPanel);

        // ID Label
        IDLabel = new JLabel("ID: 0");
        IDLabel.setBounds(20, 0, 100, 20);
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
        yearOfBirthChooser.setBounds(userInteractionPanel.getWidth() / 2 + 20, 50, (userInteractionPanel.getWidth() / 2 - 40) / 3 - 10, 40);
        yearOfBirthChooser.setFont(plainFont.deriveFont(20f));

        userInteractionPanel.add(yearOfBirthChooser);

        monthOfBirthChooser = new MonthChooser();
        monthOfBirthChooser.setBounds(userInteractionPanel.getWidth() / 2 + 20 + (userInteractionPanel.getWidth() / 2 - 40) / 3, 50, (userInteractionPanel.getWidth() / 2 - 40) / 3 - 10, 40);
        monthOfBirthChooser.setFont(plainFont.deriveFont(20f));
        monthOfBirthChooser.setType(MonthChooser.NUMERIC);

        userInteractionPanel.add(monthOfBirthChooser);

        dayOfBirthChooser = new DayChooser(yearOfBirthChooser, monthOfBirthChooser);
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
        personalIDField.setBounds(userInteractionPanel.getWidth() / 2 + 20, 180, userInteractionPanel.getWidth() / 2 - 40, 40);
        personalIDField.setFont(plainFont.deriveFont(20f));

        userInteractionPanel.add(personalIDField);

        // Update profile button
        Button updateButton = new Button();
        updateButton.setText("Update Profile");
        updateButton.setFont(plainFont.deriveFont(20f));
        updateButton.setBounds(userInteractionPanel.getWidth() / 2 + 20, 245, userInteractionPanel.getWidth() / 2 - 40, 40);
        updateButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                boolean validated = true;
                String message = "Could not update your profile:";
                Map<String, Object> form = getUpdateForm();
                Staff oldStaff = app.staffDAO.get(app.getId());
                Staff staff = new Staff(
                    app.getId(),
                    String.valueOf(form.get("name")),
                    String.valueOf(form.get("email")),
                    String.valueOf(form.get("phone")),
                    oldStaff.getPassword(),
                    String.valueOf(form.get("personal_id")),
                    (LocalDate) form.get("birthday"),
                    String.valueOf(form.get("address")),
                    (boolean) form.get("is_female"),
                    (byte[]) form.get("avatar"),
                    oldStaff.getManager(),
                    oldStaff.getRole()
                );

                if (!InputValidation.isEmailValid(staff.getEmail())){
                    validated = false;
                    message += "\n + Wrong mail format";
                }

                if (!InputValidation.isNameValid(staff.getName())){
                    validated = false;
                    message += "\n + Wrong name format";
                }

                if (!InputValidation.isPhoneValid(staff.getPhone())){
                    validated = false;
                    message += "\n + Wrong mail format";
                }

                if (!(Period.between(staff.getBirthday(), LocalDate.now()).getYears() >= 18)){
                    validated = false;
                    message += "\n + You must be more than 18";
                }

                if (!(ZQL.excuteQueryToArrayList(app.connection, "select * from staff where phone = ?", staff.getPhone()).size() == 0) && !staff.getPhone().equals(oldStaff.getPhone())){
                    validated = false;
                    message += "\n + This phone number has been registered";
                }

                if (!(ZQL.excuteQueryToArrayList(app.connection, "select * from staff where personal_id = ?", staff.getPersonalId()).size() == 0) && !staff.getPersonalId().equals(oldStaff.getPersonalId())){
                    validated = false;
                    message += "\n + This personal id has been registered";
                }
                
                int success = 0;
                if (validated){
                    success = app.staffDAO.update(staff);

                    if (success != 1){
                        message += "\n + Could not update your profile, please try again later";
                    }
                }
                
                if (!validated || success != 1){
                    JOptionPane.showMessageDialog(null, message);
                }else {
                    JOptionPane.showMessageDialog(null, "Successfully updated your profile");
                }

            }
            
        });


        userInteractionPanel.add(updateButton);


        // Change password label
        JLabel changePasswordLabel = new JLabel("Change password", SwingConstants.CENTER);
        changePasswordLabel.setBounds(0, userInteractionPanel.getHeight() - 50, userInteractionPanel.getWidth(), 50);
        changePasswordLabel.setFont(plainFont.deriveFont(20f));
        changePasswordLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        changePasswordLabel.setForeground(Color.black);
        changePasswordLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    toggleChangePasswordPanel();
                }
            }
        });


        userInteractionPanel.add(changePasswordLabel);


        // Change password panel
        changePasswordPanel = new JPanel();
        changePasswordPanel.setBounds(17, 152, 567, 309);
        changePasswordPanel.setLayout(null);
        changePasswordPanel.setVisible(false);
        changePasswordPanel.setBackground(new Color(0, 0, 0, 0));

        add(changePasswordPanel);

        // Old password label
        JLabel oldPasswordLabel = new JLabel("Old password:");
        oldPasswordLabel.setBounds(20, 20, 120, 40);
        oldPasswordLabel.setForeground(Color.black);
        oldPasswordLabel.setFont(plainFont.deriveFont(20f));
        oldPasswordLabel.setOpaque(false);

        changePasswordPanel.add(oldPasswordLabel);

        // Old password field
        oldPasswordField = new PasswordField();
        oldPasswordField.setBounds(150, 20, 400, 40);
        oldPasswordField.setTextFont(plainFont.deriveFont(20f));

        changePasswordPanel.add(oldPasswordField);

        // New password
        // New password label
        JLabel newPasswordLabel = new JLabel("New password:");
        newPasswordLabel.setBounds(20, 70, 120, 40);
        newPasswordLabel.setForeground(Color.black);
        newPasswordLabel.setFont(plainFont.deriveFont(20f));
        newPasswordLabel.setOpaque(false);

        changePasswordPanel.add(newPasswordLabel);

        // New password field
        newPasswordField = new PasswordField();
        newPasswordField.setTextFont(plainFont.deriveFont(20f));
        newPasswordField.setBounds(150, 70, 400, 40);

        changePasswordPanel.add(newPasswordField);


        // Repeat password
        // Repeat password label
        JLabel repeatPasswordLabel = new JLabel("Repeat password:");
        repeatPasswordLabel.setBounds(20, 120, 120, 40);
        repeatPasswordLabel.setForeground(Color.black);
        repeatPasswordLabel.setFont(plainFont.deriveFont(20f));
        repeatPasswordLabel.setOpaque(false);

        changePasswordPanel.add(repeatPasswordLabel);

        // New password field
        repeatPasswordField = new PasswordField();
        repeatPasswordField.setTextFont(plainFont.deriveFont(20f));
        repeatPasswordField.setBounds(150, 120, 400, 40);

        changePasswordPanel.add(repeatPasswordField);

        // Confirm bu'on
        Button confirmButton = new Button();
        confirmButton.setText("Confirm");
        confirmButton.setSize(100, 50);
        confirmButton.setFont(plainFont.deriveFont(20f));
        confirmButton.setLocation(changePasswordPanel.getWidth() / 2 - confirmButton.getWidth() / 2, changePasswordPanel.getHeight() - (confirmButton.getHeight() + 50));
        confirmButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                String message = "Could not update your password:";
                boolean validated = true;
                Map<String, String> form = getChangePasswordForm();

                Staff oldStaff = app.staffDAO.get(app.getId());


                if (!form.get("old_password").equals(oldStaff.getPassword())){
                    message += "\n + Wrong password";
                    validated = false;
                }

                if (!(form.get("new_password").length() >= 8)){
                    message += "\n + Password must be at least 8 characters";
                    validated = false;
                }

                if (!(form.get("new_password").equals(form.get("repeat_password")))){
                    message += "\n + Passwords do not match";
                    validated = false;
                }

                int success = 0;
                if (validated){
                    oldStaff.setPassword(form.get("new_password"));
                    
                    success = app.staffDAO.update(oldStaff);

                    if (success != 1){
                        message += " + Could not change your password, please try again later";
                    }
                }

                if (!validated || success != 1){
                    JOptionPane.showMessageDialog(null, message);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Password changed success fully");

                }
            }
            
        });

        changePasswordPanel.add(confirmButton);

        // Return label
        JLabel returnLabel = new JLabel("Return", SwingConstants.CENTER);
        returnLabel.setBounds(0, changePasswordPanel.getHeight() - 50, changePasswordPanel.getWidth(), 50);
        returnLabel.setFont(plainFont.deriveFont(20f));
        returnLabel.setForeground(Color.black);
        returnLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        returnLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    toggleChangePasswordPanel();
                }
            }
        });

        changePasswordPanel.add(returnLabel);

        // title label
        titleLabel = new OutlineLabel("ACCOUNT INFO");
        titleLabel.setOutlineColor(Color.WHITE);
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(18, 15, 567, 120);
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(monogramFont.deriveFont(100f));
        add(titleLabel);

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

        setBackGroundImage("src/com/hazbinhotel/image/account-bg.png");

        app.mainMenuPanel.accountPanelPressed.connectSignal((arg) -> {
            Staff s = app.staffDAO.get(app.getId());

            setID(String.valueOf(s.getId()));
            accountNameLabel.setText(s.getName());;
            nameField.setText(s.getName()); 
            phoneField.setText(s.getPhone()); 
            emailField.setText(s.getEmail()); 
            addressField.setText(s.getAddress()); 
            personalIDField.setText(s.getPersonalId());
            genderComboBox.setSelectedIndex(s.isFemale()?1:0);
            yearOfBirthChooser.setYear(s.getBirthday().getYear());
            monthOfBirthChooser.setMonth(s.getBirthday().getMonthValue() - 1);
            dayOfBirthChooser.setDay(s.getBirthday().getDayOfMonth() - 1);

            byte[] defaultImg = ImageBytes.convertImageToBytes(new ImageIcon("src/com/hazbinhotel/image/account.jpg").getImage());
            setAvatar((s.getAvatar() == null)?defaultImg:s.getAvatar());
        });
    }

    public void setAvatar(byte[] imageData) {
        Image img = ImageBytes.convertBytesToImage(imageData);
        ImageIcon avatarIcon = new ImageIcon(img);
        avatar.setIcon(avatarIcon);
        avatar.repaint();
    }

    public void toggleChangePasswordPanel() {
        changePasswordPanel.setVisible(!changePasswordPanel.isVisible());
        userInteractionPanel.setVisible(!userInteractionPanel.isVisible());
        titleLabel.setText(new String[]{"CHANGE PASS" , "ACCOUNT INFO"}[userInteractionPanel.isVisible()?1:0]);
    }

    public void setID(String ID){
        IDLabel.setText(String.format("ID: %s", ID));
    }

    public Map<String, Object> getUpdateForm(){
        Map<String, Object> dict = new Hashtable<String, Object>();


        dict.put("name" , nameField.getText());
        dict.put("avatar", ImageBytes.convertImageToBytes(((ImageIcon) avatar.getIcon()).getImage()));
        dict.put("phone", phoneField.getText());
        dict.put("email", emailField.getText());
        dict.put("address", addressField.getText());
        dict.put("personal_id", personalIDField.getText());
        dict.put("is_female", genderComboBox.getSelectedIndex() == 1);
        dict.put("birthday", LocalDate.of(yearOfBirthChooser.getYear(), monthOfBirthChooser.getMonth() + 1, dayOfBirthChooser.getDay() + 1));

        
        return dict;
    }

    public Map<String, String> getChangePasswordForm(){
        Map<String, String> dict = new Hashtable<>();

        dict.put("old_password", String.valueOf(oldPasswordField.getPassword()));
        dict.put("new_password", String.valueOf(newPasswordField.getPassword()));
        dict.put("repeat_password", String.valueOf(repeatPasswordField.getPassword()));

        return dict;
    }
}
