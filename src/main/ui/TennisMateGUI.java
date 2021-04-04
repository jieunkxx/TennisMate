package ui;


import exceptions.CourtException;
import exceptions.CourtNullException;
import model.courts.Court;
import model.locations.Location;
import model.users.Admin;

import model.users.Player;
import model.users.User;
import persistence.JsonReaderAdmin;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

import static ui.TennisMateApp.*;

public class TennisMateGUI extends JFrame implements ActionListener {

    private static final String JSON_STORE = "./data/vancouverGUI.json";
    private static final ImageIcon errorIcon = new ImageIcon("./data/error.png");

    private Admin admin;
    private Location vancouver;
    private Court court;
    private User loginUser;

    private JsonWriter jsonWriter;
    private JsonReaderAdmin jsonReaderAdmin;


    /* Panel */
    JPanel basePanel = new JPanel(new BorderLayout());
    JPanel centerPanel = new JPanel(new BorderLayout());
    JPanel mainPanel = new JPanel(new BorderLayout());
    JPanel westPanel = new JPanel();
    JPanel eastPanel = new JPanel();
    JPanel mainNorthPanel = new JPanel();
    JPanel mainCenterPanel = new JPanel();
    JPanel mainBottomPanel = new JPanel();


    /* Label */
    JLabel userNameL = new JLabel("user name");
    JLabel courtsL = new JLabel("courts");
    JLabel loginUserL = new JLabel("login User : ");
    JLabel statusMsg = new JLabel("users in the courts");

    /* TextField */
    JTextField userNameF = new JTextField();


    /* Button */

    JButton signupBtn = new JButton("signUp");
    JButton loginBtn = new JButton("login");

    JButton saveBtn = new JButton("save");
    JButton loadBtn = new JButton("load");

    JButton selectCourtBtn = new JButton("add");
    JButton removeCourtBtn = new JButton("remove");
    JButton checkCourtBtn = new JButton("courtInfo");

    /* Combo Box */
    private JComboBox<String> courts = new JComboBox<>();
    private JComboBox<String> level = new JComboBox<>();
    private JComboBox<String> times = new JComboBox<>();

    /* Image */
    private ImageIcon popupError;


    public TennisMateGUI() {
        super("TennisMate UI");
        //   vancouver = new Location(LOCATION_NAME);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initImage();
        initPanel();
        addPanel();
        addButton();
        comboCourts();
        comboTimeSlot();

        signupBtn.addActionListener(this);
        loginBtn.addActionListener(this);
        saveBtn.addActionListener(this);
        loadBtn.addActionListener(this);
        selectCourtBtn.addActionListener(this);
        removeCourtBtn.addActionListener(this);
        checkCourtBtn.addActionListener(this);

        pack();
        init();
        setVisible(true);

    }

    //MODIFIES: this
    //EFFECTS: Initialize error pop-up image
    public void initImage() {
        popupError = new ImageIcon(errorIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
    }

    //MODIFIES: this
    //EFFECTS: Initialize JFrame window where the gui operates. set panels, labels, fields and buttons.
    public void initPanel() {
        centerPanel.setPreferredSize(new Dimension(260, 80));
        westPanel.setPreferredSize(new Dimension(200, 75));
        eastPanel.setPreferredSize(new Dimension(90, 75));
        mainPanel.setPreferredSize(new Dimension(260, 300));
        mainNorthPanel.setPreferredSize(new Dimension(260, 50));
        mainCenterPanel.setPreferredSize(new Dimension(260, 100));
        mainBottomPanel.setPreferredSize(new Dimension(260, 100));

        userNameL.setPreferredSize(new Dimension(80, 30));
        userNameF.setPreferredSize(new Dimension(100, 30));

        loginBtn.setPreferredSize(new Dimension(75, 25));
        signupBtn.setPreferredSize(new Dimension(75, 25));

        loadBtn.setPreferredSize(new Dimension(75, 25));
        saveBtn.setPreferredSize(new Dimension(75, 25));

        courts.setPreferredSize(new Dimension(200, 30));

    }

    /*
    public void addPanel() {

        setContentPane(basePanel);
        basePanel.add(centerPanel, BorderLayout.CENTER);
        basePanel.add(mainPanel, BorderLayout.SOUTH);

        centerPanel.add(westPanel, BorderLayout.WEST);
        centerPanel.add(eastPanel, BorderLayout.EAST);

        mainPanel.add(mainNorthPanel, BorderLayout.NORTH);
        mainPanel.add(mainCenterPanel, BorderLayout.CENTER);
        mainPanel.add(mainBottomPanel, BorderLayout.SOUTH);

        westPanel.setLayout(new FlowLayout());
        eastPanel.setLayout(new FlowLayout());
        mainNorthPanel.setLayout(new GridLayout(2, 2));
        mainCenterPanel.setLayout(new FlowLayout());
        mainBottomPanel.setLayout(new FlowLayout());

    }
    */

    //MODIFIES: this
    //EFFECTS: set layout of the panels
    public void addPanel() {

        setContentPane(basePanel);
        basePanel.add(centerPanel, BorderLayout.CENTER);
        basePanel.add(mainPanel, BorderLayout.SOUTH);

        centerPanel.add(westPanel, BorderLayout.WEST);
        centerPanel.add(eastPanel, BorderLayout.EAST);

        mainPanel.add(mainNorthPanel, BorderLayout.NORTH);
        mainPanel.add(mainCenterPanel, BorderLayout.CENTER);
        mainPanel.add(mainBottomPanel, BorderLayout.SOUTH);

        westPanel.setLayout(new FlowLayout());
        eastPanel.setLayout(new FlowLayout());
        mainNorthPanel.setLayout(new GridLayout(2, 2));
        mainCenterPanel.setLayout(new FlowLayout());
        mainBottomPanel.setLayout(new FlowLayout());

    }

    //MODIFIES: this
    //EFFECTS: Add buttons to the panels
    public void addButton() {

        westPanel.add(userNameL);
        westPanel.add(userNameF);

        eastPanel.add(loginBtn);
        eastPanel.add(signupBtn);

        mainNorthPanel.add(loadBtn);
        mainNorthPanel.add(saveBtn);
        mainNorthPanel.add(loginUserL);

        mainCenterPanel.add(courtsL);
        mainCenterPanel.add(courts);
        mainCenterPanel.add(selectCourtBtn);
        mainCenterPanel.add(removeCourtBtn);
        mainCenterPanel.add(checkCourtBtn);
        mainCenterPanel.add(times);

        mainBottomPanel.add(statusMsg);


    }

    //MODIFIES: this
    //EFFECTS: generates courts selections
    public void comboCourts() {
        this.courts.addItem("Kits");
        this.courts.addItem("StanleyPark");
        this.courts.addItem("UBC");
    }

    //MODIFIES: this
    //EFFECTS: generates time slot selections
    public void comboTimeSlot() {
        int i;
        for (i = 1; i <= 23; i++) {
            String out = (i + " - " + (i + 1));
            this.times.addItem(out);
        }
    }

    // MODIFIES: this
    // EFFECTS: initialize location, courts, file location.
    private void init() {
        vancouver = new Location(LOCATION_NAME);
        admin = new Admin(vancouver);
        admin.setLocation(vancouver);
        loadCourt(vancouver);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReaderAdmin = new JsonReaderAdmin(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: initialize courts in location
    public static void loadCourt(Location location) {

        Court c1 = new Court(UBC_COURT_NAME);
        location.addCourt(c1);

        Court c2 = new Court(KITS_COURT_NAME);
        location.addCourt(c2);

        Court c3 = new Court(STANLEY_PARK_COURT_NAME);
        location.addCourt(c3);
    }


    public static void main(String[] args) {
        new TennisMateGUI();
    }

    //MODIFIES: this
    //EFFECTS: find login user from the user input in the user field, if found, login succeed, otherwise, failed and
    //          pops up the error image and msg.
    private void login(String userName) {
        if (!admin.getUserNameList().contains(userName)) {
            loginUser = null;
            JOptionPane.showMessageDialog(null, null, "login failed", JOptionPane.ERROR_MESSAGE, popupError);
            statusMsg.setText("login failed");
            loginUserL.setText("login User : ");
        } else {
            for (User u : admin.getUserList()) {
                if (u.getUserName().equals(userName)) {
                    loginUser = u;
                }
            }
            statusMsg.setText("login succeed");
            loginUserL.setText("login user : " + loginUser.getUserName());
        }
    }

    //MODIFIES: this
    //EFFECTS: sign up using the user input in the user field, if no user input found,
    //         failed and pops up the error image and msg.
    private void signUp(String userName) {
        if (userName.length() == 0) {
            JOptionPane.showMessageDialog(null, null,
                    "SignUp Failed!", JOptionPane.ERROR_MESSAGE, popupError);
            statusMsg.setText("SignUp Failed! Please Enter valid user name");
        } else if (admin.getUserNameList().contains(userName)) {
            JOptionPane.showMessageDialog(null, null,
                    "SignUp Failed!", JOptionPane.ERROR_MESSAGE, popupError);
            statusMsg.setText("SignUp Failed! User Name exist.");
        } else {
            int userId = admin.generateUserId();
            loginUser = new Player(userId, userName);
            admin.addUser(loginUser);
            System.out.println(loginUser.getUserName());
            statusMsg.setText("signUp succeed");
            loginUserL.setText("login user : " + loginUser.getUserName());
        }
    }


    // EFFECTS: find user in the court selected from the court selection
    private Collection<String> getUsersInCourt(Court court) {
        Collection<String> usersList = new HashSet<>();
        for (User u : court.getUsers()) {
            usersList.add(u.getUserName());
        }
        return usersList;
    }

    // MODIFIES : this
    // EFFECTS: add login user to the court selected from the court selection
    private void addCourtToUser() {
        if (loginUser != null) {
            if (loginUser.getPreferredCourt().contains(court)) {
                statusMsg.setText(court.getCourtName() + " already in " + loginUser.getUserName() + " 's court");
            } else {
                loginUser.addPreferredCourt(court);
                //court.addUser(loginUser);
                statusMsg.setText(court.getCourtName() + " is added in " + loginUser.getUserName() + " 's court");
            }
        } else {
            JOptionPane.showMessageDialog(null, null,
                    "login Action Failed! No one is on the system", JOptionPane.ERROR_MESSAGE, popupError);
            statusMsg.setText("Action Failed! No one is on the system");
        }
    }


    // MODIFIES : this
    // EFFECTS: remove login user from the court selected from the court selection
    private void removeCourtFromUser() {
        if (loginUser != null) {
            if (loginUser.getPreferredCourt().contains(court)) {
                loginUser.removePreferredCourt(court);
                //court.removeUser(loginUser);
                statusMsg.setText(court.getCourtName() + "is removed in " + loginUser.getUserName() + " 's court");
            } else {
                statusMsg.setText(court.getCourtName() + "is not in " + loginUser.getUserName() + " 's court");
            }
        } else {
            //JOptionPane.showMessageDialog(null, null,
            //    "login Action Failed! No one is on the system", JOptionPane.ERROR_MESSAGE, popupError);
            statusMsg.setText("Action Failed! No one is on the system");
        }
    }


    //This code is referred to the JSonSerializationDemo example
    // MODIFIES: this
    // EFFECTS: loads from file
    private void loadData() {
        try {
            admin = jsonReaderAdmin.readAdmin();
            vancouver = admin.getLocation();
            statusMsg.setText("Loaded " + JSON_STORE);
        } catch (IOException e) {
            statusMsg.setText("Unable to read from file: " + JSON_STORE);
        }
    }

    // This code is referred to the JSonSerializationDemo example
    // EFFECTS: saves the admin to file
    private void saveData() {
        try {
            jsonWriter.open();
            jsonWriter.write(admin);
            jsonWriter.close();
            statusMsg.setText("Saved " + JSON_STORE);
        } catch (FileNotFoundException e) {
            statusMsg.setText("Unable to write to file: " + JSON_STORE);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        court = vancouver.lookingUpCourtByName((String) courts.getSelectedItem());
        if (e.getActionCommand() == "login") {
            login(userNameF.getText());
        }
        if (e.getActionCommand() == "signUp") {
            signUp(userNameF.getText());
        }
        if (e.getActionCommand() == "courtInfo") {
            statusMsg.setText("users in " + court.getCourtName() + " : " + getUsersInCourt(court));
        }
        if (e.getActionCommand() == "add") {
            addCourtToUser();
        }
        if (e.getActionCommand() == "remove") {
            removeCourtFromUser();
        }
        if (e.getActionCommand() == "load") {
            loadData();
        }
        if (e.getActionCommand() == "save") {
            saveData();
        }
    }

}

