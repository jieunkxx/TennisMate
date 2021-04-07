package ui;

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

/* GUI format tennis mate app. main functions */

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
    JPanel statusMsgPanel = new JPanel();
    JPanel loginUserPanel = new JPanel();


    /* Label */
    JLabel userNameL = new JLabel("user name");
    JLabel courtsL = new JLabel("courts");
    JLabel loginUserL = new JLabel("login User : ");
    JLabel statusMsg = new JLabel("users in the courts");
    JLabel loginUserInfo = new JLabel("");
    /* TextField */
    JTextField userNameF = new JTextField();


    /* Button */

    JButton signupBtn = new JButton("signUp");
    JButton loginBtn = new JButton("login");

    JButton saveBtn = new JButton("save");
    JButton loadBtn = new JButton("load");
    JButton userInfoBtn = new JButton("userInfo");

    JButton selectCourtBtn = new JButton("add");
    JButton removeCourtBtn = new JButton("remove");
    JButton checkCourtBtn = new JButton("courtInfo");
    JButton addTimeBtn = new JButton("addTime");
    JButton removeTimeBtn = new JButton("removeTime");

    /* Combo Box */
    private JComboBox<String> courts = new JComboBox<>();
    private JComboBox<String> level = new JComboBox<>();
    private JComboBox<String> times = new JComboBox<>();

    /* Image */
    private ImageIcon popupError;


    public TennisMateGUI() {
        super("TennisMate UI");

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
        addTimeBtn.addActionListener(this);
        removeTimeBtn.addActionListener(this);
        userInfoBtn.addActionListener(this);

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
        userInfoBtn.setPreferredSize(new Dimension(75, 25));

        courts.setPreferredSize(new Dimension(200, 30));
    }


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
        //mainBottomPanel.setLayout(new FlowLayout());

        mainBottomPanel.add(statusMsgPanel, BorderLayout.NORTH);
        mainBottomPanel.add(loginUserPanel, BorderLayout.CENTER);
        statusMsgPanel.setLayout(new FlowLayout());
        loginUserPanel.setLayout(new FlowLayout());
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
        mainCenterPanel.add(addTimeBtn);
        mainCenterPanel.add(removeTimeBtn);

        statusMsgPanel.add(statusMsg);
        loginUserPanel.add(loginUserInfo);
    }

    //MODIFIES: this
    //EFFECTS: generates courts selections
    public void comboCourts() {
        this.courts.addItem(KITS_COURT_NAME);
        this.courts.addItem(STANLEY_PARK_COURT_NAME);
        this.courts.addItem(UBC_COURT_NAME);
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
            printErrMsg("login failed");
            loginUserL.setText("login User : ");
        } else {
            findUserFromUserList(userName);
            statusMsg.setText("login succeed");
            loginUserL.setText("login user  : " + loginUser.getUserName());
        }
    }

    // EFFECTS: return login user if there is a user with the user name entered in Admin's user list
    private void findUserFromUserList(String userName) {
        for (User u : admin.getUserList()) {
            if (u.getUserName().equals(userName)) {
                loginUser = u; // cast player and coach to user type in this version
            }
        }
    }


    //MODIFIES: this
    //EFFECTS: sign up using the user input in the user field, if no user input found,
    //         failed and pops up the error image and msg.
    private void signUp(String userName) {
        if (userName.length() == 0) {
            printErrMsg("SignUp Failed");
        } else if (admin.getUserNameList().contains(userName)) {
            printErrMsg("SignUp Failed! User Name exist.");
        } else {
            int userId = admin.generateUserId();
            loginUser = new Player(userId, userName);
            admin.addUser(loginUser);
            System.out.println(loginUser.getUserName());
            statusMsg.setText("signUp succeed");
            loginUserInfo.setText("");
            loginUserL.setText("login user : " + loginUser.getUserName());
        }
    }


    // EFFECTS: find user in the court selected from the court selection
    private Collection<String> getUsersNameInCourt(Court court) {
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
                loginUserInfo.setText("");
            } else {
                loginUser.addPreferredCourt(court);
                statusMsg.setText(court.getCourtName() + " is added in " + loginUser.getUserName() + " 's court");
                loginUserInfo.setText(loginUser.getUserName() + " is assigned in : " + loginUser.getCourtsByName());
            }
        } else {
            printErrMsg("login Failed! No one is on the system");
        }
    }


    // MODIFIES : this
    // EFFECTS: remove login user from the court selected from the court selection. if there is no login user, generate
    //          error msg.
    private void removeCourtFromUser() {
        if (loginUser != null) {
            if (loginUser.getPreferredCourt().contains(court)) {
                loginUser.removePreferredCourt(court);
                statusMsg.setText(court.getCourtName() + " is removed in " + loginUser.getUserName() + " 's court");
                loginUserInfo.setText(loginUser.getUserName() + " is assigned in : " + loginUser.getCourtsByName());
            } else {
                statusMsg.setText(court.getCourtName() + " is not in " + loginUser.getUserName() + " 's court");
                loginUserInfo.setText("");
            }
        } else {
            statusMsg.setText("Action Failed! No one is on the system");
        }
    }

    // MODIFIES : this
    // EFFECTS: add time slot to login user's time slot. if there is no login user, generate
    //          error msg and error icon.
    private void addTimeSlotToUser(int time) {
        if (loginUser != null) {
            if (loginUser.getTimeSlot().contains(time)) {
                statusMsg.setText(time + " is already in " + loginUser.getUserName() + " 's timeSlot");
                loginUserInfo.setText("");
            } else {
                loginUser.addTimeSlot(time);
                int i = time + 1;
                statusMsg.setText(time + " - " + i + " is added.");
                loginUserInfo.setText(loginUser.getUserName() + " 's timeSlot : "
                                        + loginUser.getTimeSlot());
            }
        } else {
            printErrMsg("Action Failed! No one is on the system");
        }
    }

    // MODIFIES : this
    // EFFECTS: remove time slot from login user's time slot. if there is no login user, generate
    //          error msg and error icon
    private void removeTimeSlotFromUser(int time) {
        if (loginUser != null) {
            if (!loginUser.getTimeSlot().contains(time)) {
                statusMsg.setText(time + " is not in " + loginUser.getUserName() + " 's timeSlot");
                loginUserInfo.setText("");
            } else {
                loginUser.removeTimeSlot(time);
                int i = time + 1;
                statusMsg.setText(time + " - " + i + " is removed.");
                loginUserInfo.setText(loginUser.getUserName() + " 's timeSlot : "
                        + loginUser.getTimeSlot());
            }
        } else {
            printErrMsg("Action Failed! No one is on the system");
        }
    }

    // EFFECTS: String type time conversion into int type
    public int timeConversion() {
        String t = (String) times.getSelectedItem();
        String[] parts = t.split(" - ");
        String t1 = parts[0];
        return Integer.parseInt(t1);
    }

    // EFFECTS: print error msg and error icon with the string entered.
    public void printErrMsg(String str) {
        JOptionPane.showMessageDialog(null, null,
                str, JOptionPane.ERROR_MESSAGE, popupError);
        statusMsg.setText(str);
        loginUserInfo.setText("");
    }

    // MODIFIES: this
    // EFFECTS: actionPerformed handler. Calls corresponding method to the button clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        String str = e.getActionCommand();
        court = vancouver.lookingUpCourtByName((String) courts.getSelectedItem());
        int time = timeConversion();
        if (str.equals("login") || str.equals("signUp")) {
            loginOptionHandler(str);
        }
        if (str.equals("userInfo")) {
            loginUserInfoHandler();
        }
        if (str.equals("courtInfo") || str.equals("add") || str.equals("remove")) {
            courtOptionHandler(str);
        }
        if (str.equals("addTime") || str.equals("removeTime")) {
            timeSlotOptionHandler(str, time);
        }
        if (str.equals("load") || str.equals("save")) {
            saveAndLoadHandler(str);
        }
    }

    // MODIFIES: this
    // EFFECTS: login or signup if the input equals to login or signup
    public void loginOptionHandler(String str) {
        if (str.equals("login")) {
            login(userNameF.getText());
        }
        if (str.equals("signUp")) {
            signUp(userNameF.getText());
        }
    }

    // EFFECTS: print login user's information
    public void loginUserInfoHandler() {
        loginUserInfo.setText("level : " + loginUser.getLevel() + "\n"
                            + " type : " + loginUser.getType() + "\n"
                            + " timeSlots: " + loginUser.getTimeSlot() + "\n"
                            + " courts: " + loginUser.getPreferredCourt());
    }

    // MODIFIES: this
    // EFFECTS: Calls court associated method that is corresponding to the button clicked
    public void courtOptionHandler(String str) {
        if (loginUser != null) {
            if (str.equals("courtInfo")) {
                statusMsg.setText("In " + court.getCourtName() + " : " + getUsersNameInCourt(court));
                loginUserInfo.setText(loginUser.getUserName() + " is assigned in : " + loginUser.getCourtsByName());
            }
            if (str.equals("add")) {
                addCourtToUser();
            }
            if (str.equals("remove")) {
                removeCourtFromUser();
            }
        } else {
            printErrMsg("Action Failed! No one is on the system");
        }
    }

    // MODIFIES: this
    // EFFECTS: Calls user's timeslot associated method that is corresponding to the button clicked. if no one login,
    //          generate error msg and icon.
    public void timeSlotOptionHandler(String str, int time) {
        if (loginUser != null) {
            if (str.equals("addTime")) {
                addTimeSlotToUser(time);
            }
            if (str.equals("removeTime")) {
                removeTimeSlotFromUser(time);
            }
        } else {
            printErrMsg("Action Failed! No one is on the system");
        }
    }


    // MODIFIES: this
    // EFFECTS: load the file or save the data
    public void saveAndLoadHandler(String str) {
        if (str.equals("load")) {
            loadData();
        }
        if (str.equals("save")) {
            saveData();
        }
    }

    // This code is referred to the JSonSerializationDemo example
    // MODIFIES: this
    // EFFECTS: loads from file
    private void loadData() {
        try {
            admin = jsonReaderAdmin.readAdmin();
            vancouver = admin.getLocation();
            statusMsg.setText("Loaded " + JSON_STORE);
            loginUserInfo.setText("");
        } catch (IOException e) {
            statusMsg.setText("Unable to read from file: " + JSON_STORE);
            loginUserInfo.setText("");
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
            loginUserInfo.setText("");
        } catch (FileNotFoundException e) {
            statusMsg.setText("Unable to write to file: " + JSON_STORE);
            loginUserInfo.setText("");
        }
    }
}

