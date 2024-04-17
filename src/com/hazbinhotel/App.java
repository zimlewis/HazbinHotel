package com.hazbinhotel;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

import com.hazbinhotel.entity.*;
import com.hazbinhotel.dao.*;
import com.hazbinhotel.dao.CustomerDAO;
import com.hazbinhotel.gui.*;
import com.zimlewis.*;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Timestamp;



public class App extends JFrame{
    String serverName = "localhost";
    String dbName = "hazbin_hotel";
    String url = "jdbc:sqlserver://" + serverName + ":1433;DatabaseName=" + dbName
            + ";encrypt=true;trustServerCertificate=true;sendStringParametersAsUnicode=true";
    String serverUser = "sa";
    String severPassword = "Daylazim123!";
    public Connection connection;

    public Authentication authenticationPanel; 
    public MainMenu mainMenuPanel; 
    public RoomManage roomPanel;
    public RoomTypeManage roomTypePanel; 
    public ServicePage servicePanel;
    public CustomerManage customerManagePanel; 
    public PaymentPage paymentPanel;
    public AccountManage accountManagePanel; 
    public BookingPage bookingPanel;
    public BookingHistory bookingHistoryPanel;
    public IncomeManage incomeManagePanel;
    public StaffManage staffManagePanel;
    public RoomCountPanel roomCountPanel;

    public BillDAO billDAO;
    public BookingDAO bookingDAO;
    public CustomerDAO customerDAO;
    public RoomDAO roomDAO;
    public RoomTypeDAO roomTypeDAO;
    public ServiceDAO serviceDAO;
    public StaffDAO staffDAO;

    public List<Bill> billsList = new ArrayList<>();
    public List<Booking> bookingsList = new ArrayList<>();
    public List<Customer> customersList = new ArrayList<>();
    public List<Room> roomsList = new ArrayList<>();
    public List<RoomType> roomTypesList = new ArrayList<>();
    public List<Service> servicesList = new ArrayList<>();
    public List<Staff> staffsList = new ArrayList<>();

    public Signal billChanged = new Signal();
    public Signal bookingChanged = new Signal();
    public Signal customerChanged = new Signal();
    public Signal roomChanged = new Signal();
    public Signal roomTypeChanged = new Signal();
    public Signal serviceChanged = new Signal();
    public Signal staffChanged = new Signal();

    public Signal idChanged = new Signal(Integer.class);

    Integer id = null;



    public Signal 
    authenticated = new Signal(int.class);

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
        idChanged.emitSignal(id);
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) throws Exception {
        UIManager.put("ComboBox.disabledBackground", new ColorUIResource(Color.BLACK));
        UIManager.put("ComboBox.disabledForeground", new ColorUIResource(new Color(201, 38, 84)));
        UIManager.put("TextField.disabledBackground", new ColorUIResource(Color.BLACK));
        UIManager.put("TextField.disabledForeground", new ColorUIResource(new Color(201, 38, 84)));
        UIManager.put("TextField.inactiveBackground", new ColorUIResource(Color.BLACK));
        UIManager.put("TextField.inactiveForeground", new ColorUIResource(new Color(201, 38, 84)));
        
        App app = new App();
    }

    public App() {
        
        setSize(960, 540);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null); // Use null layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Hazbin Hotel");

        

        

        authenticationPanel = new Authentication(this);
        mainMenuPanel = new MainMenu(this);
        roomPanel = new RoomManage(this);
        roomTypePanel = new RoomTypeManage(this);
        servicePanel = new ServicePage(this);
        customerManagePanel = new CustomerManage(this);
        paymentPanel = new PaymentPage(this);
        accountManagePanel = new AccountManage(this);
        bookingPanel = new BookingPage(this);
        bookingHistoryPanel = new BookingHistory(this);
        incomeManagePanel = new IncomeManage(this);
        staffManagePanel = new StaffManage(this);
        roomCountPanel = new RoomCountPanel(this);

        

        setContentPane(authenticationPanel);

        connection = ZQL.getConnection(url, serverUser, severPassword);

        billDAO = new BillDAO(connection);
        bookingDAO = new BookingDAO(connection);
        customerDAO = new CustomerDAO(connection);
        roomDAO = new RoomDAO(connection);
        roomTypeDAO = new RoomTypeDAO(connection);
        serviceDAO = new ServiceDAO(connection);
        staffDAO = new StaffDAO(connection);



        new Thread(() -> {
            try{
                while (true){
                    
                    List<Bill> newBills = billDAO.getAll();
                    List<Booking> newBookings = bookingDAO.getAll();
                    List<Customer> newCustomers = customerDAO.getAll();
                    List<Room> newRooms = roomDAO.getAll();
                    List<RoomType> newRoomTypes = roomTypeDAO.getAll();
                    List<Service> newServices = serviceDAO.getAll();
                    List<Staff> newStaffs = staffDAO.getAll();

                    mainMenuPanel.setStatus();

                    if (!areListsEqual(billsList, newBills)){
                        billsList = new ArrayList<>(newBills);
                        billChanged.emitSignal();
                    }
                    if (!areListsEqual(bookingsList, newBookings)){
                        mainMenuPanel.fillToTable();
                        bookingsList = new ArrayList<>(newBookings);
                        bookingChanged.emitSignal();
                    }
                    if (!areListsEqual(customersList, newCustomers)){
                        customersList = new ArrayList<>(newCustomers);
                        customerChanged.emitSignal();
                    }
                    if (!areListsEqual(roomsList, newRooms)){
                        roomsList = new ArrayList<>(newRooms);
                        roomChanged.emitSignal();
                    }
                    if (!areListsEqual(roomTypesList, newRoomTypes)){
                        
                        roomTypesList = new ArrayList<>(newRoomTypes);
                        roomTypeChanged.emitSignal();
                    }
                    if (!areListsEqual(servicesList, newServices)){
                        servicesList = new ArrayList<>(newServices);
                        serviceChanged.emitSignal();
                    }
                    if (!areListsEqual(staffsList, newStaffs)){
                        staffsList = new ArrayList<>(newStaffs);
                        staffChanged.emitSignal();
                    }

                    Thread.sleep(100);
                }
            }
            catch (Exception e){
                System.out.println(e);
            }
        }).start();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    connection.close();
                } catch (Exception e) {
                }
            }
        });

        

        setVisible(true);
        Insets insets = getInsets();
        int titleBarHeight = insets.top + insets.bottom;
        int sideWidth = insets.left + insets.right;
        setSize(960 + sideWidth, 540 + titleBarHeight);

        addKeyBinding(this.getRootPane(), "F11", new FullscreenToggleAction(this));
        mainMenuNavigationSetUp();
        onBackButtonsPressed();
        signalSetup();
    }

    public static final void addKeyBinding(JComponent c, String key, final Action action) {
        c.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key), key);
        c.getActionMap().put(key, action);
        c.setFocusable(true);
    }

    public void mainMenuNavigationSetUp(){
        mainMenuPanel.roomTypeButtonPressed.connectSignal((arg) -> {
            setContentPane(roomTypePanel);
            mainMenuPanel.fillToTable();
        });
        mainMenuPanel.roomButtonPressed.connectSignal((arg) -> {
            setContentPane(roomPanel);
            mainMenuPanel.fillToTable();
        });;
        mainMenuPanel.serviceButtonPressed.connectSignal((arg) -> {
            setContentPane(servicePanel);
            mainMenuPanel.fillToTable();
        });;
        mainMenuPanel.bookingButtonPressed.connectSignal((arg) -> {
            setContentPane(bookingPanel);
            mainMenuPanel.fillToTable();
        });;
        mainMenuPanel.customerButtonPressed.connectSignal((arg) -> {
            setContentPane(customerManagePanel);
            mainMenuPanel.fillToTable();
        });;
        mainMenuPanel.paymentButtonPressed.connectSignal((arg) -> {
            setContentPane(paymentPanel);
            mainMenuPanel.fillToTable();
        });;
        mainMenuPanel.historyButtonPressed.connectSignal((arg) -> {
            setContentPane(bookingHistoryPanel);
            mainMenuPanel.fillToTable();
        });;
        mainMenuPanel.incomeButtonPressed.connectSignal((arg) -> {
            setContentPane(incomeManagePanel);
            mainMenuPanel.fillToTable();
        });;
        mainMenuPanel.roomCountPressed.connectSignal((arg) -> {
            setContentPane(roomCountPanel);
            mainMenuPanel.fillToTable();
        });;
        mainMenuPanel.staffManageButtonPressed.connectSignal((arg) -> {
            mainMenuPanel.fillToTable();
            if (staffDAO.get(id).getRole() == 1){
                setContentPane(staffManagePanel);
            }
            else{
                JOptionPane.showMessageDialog(null, "You do not have permission to do this action");
            }
        });;
        mainMenuPanel.accountPanelPressed.connectSignal((arg) -> {
            setContentPane(accountManagePanel);
            mainMenuPanel.fillToTable();
        });
    }

    public void onBackButtonsPressed(){
        roomPanel.backButtonPressed.connectSignal((arg) -> {
            mainMenuPanel.fillToTable();
            setContentPane(mainMenuPanel);
        });
        roomTypePanel.backButtonPressed.connectSignal((arg) -> {
            mainMenuPanel.fillToTable();
            setContentPane(mainMenuPanel);
        });
        servicePanel.backButtonPressed.connectSignal((arg) -> {
            mainMenuPanel.fillToTable();
            setContentPane(mainMenuPanel);
        });
        customerManagePanel.backButtonPressed.connectSignal((arg) -> {
            mainMenuPanel.fillToTable();
            setContentPane(mainMenuPanel);
        });
        paymentPanel.backButtonPressed.connectSignal((arg) -> {
            mainMenuPanel.fillToTable();
            setContentPane(mainMenuPanel);
        });
        accountManagePanel.backButtonPressed.connectSignal((arg) -> {
            mainMenuPanel.fillToTable();
            setContentPane(mainMenuPanel);
        }); 
        bookingPanel.backButtonPressed.connectSignal((arg) -> {
            mainMenuPanel.fillToTable();
            setContentPane(mainMenuPanel);
        });
        bookingHistoryPanel.backButtonPressed.connectSignal((arg) -> {
            mainMenuPanel.fillToTable();
            setContentPane(mainMenuPanel);
        });
        incomeManagePanel.backButtonPressed.connectSignal((arg) -> {
            mainMenuPanel.fillToTable();
            setContentPane(mainMenuPanel);
        });
        staffManagePanel.backButtonPressed.connectSignal((arg) -> {
            mainMenuPanel.fillToTable();
            setContentPane(mainMenuPanel);
        });
        roomCountPanel.backButtonPressed.connectSignal((arg) -> {
            mainMenuPanel.fillToTable();
            setContentPane(mainMenuPanel);
        });
    }

    void signalSetup(){
        authenticated.connectSignal((arg) -> {
            setId((Integer) arg[0]);
            setContentPane(mainMenuPanel);
        });
    }

    private static <T> boolean areListsEqual(List<T> list1, List<T> list2) {
        if (list1 == null && list2 == null){
            return true;
        }

        if (list1.size() != list2.size()) {
            return false;
        }

        for (int i = 0; i < list1.size(); i++) {
            if (!list1.get(i).equals(list2.get(i))) {
                return false;
            }
        }

        return true;
    }

    public static int extractInteger(String str) {
        // Define a pattern to match integers at the beginning of the string
        Pattern pattern = Pattern.compile("^\\s*(\\d+)");

        Matcher matcher = pattern.matcher(str);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        } else {
            // If no integer found at the beginning, return some default value or handle it as needed
            return -1; // Or throw an exception or return null, depending on your requirement
        }
    }

}

class FullscreenToggleAction extends AbstractAction {

    private App frame;
    @SuppressWarnings("unused")
    private GraphicsDevice fullscreenDevice;

    public FullscreenToggleAction(JFrame frame) {
        this(frame, GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
    }

    public FullscreenToggleAction(JFrame frame, GraphicsDevice fullscreenDevice) {
        this.frame = (App) frame;
        this.fullscreenDevice = fullscreenDevice;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.dispose();

        if (frame.isUndecorated()) {
        } else {
        }

        frame.setVisible(true);
        frame.repaint();
    }


}