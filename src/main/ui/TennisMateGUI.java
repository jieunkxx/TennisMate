package ui;

import model.courts.Court;
import model.locations.Location;
import model.users.Admin;
import model.users.Coach;
import model.users.Player;
import model.users.User;
import persistence.JsonReaderAdmin;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import static ui.TennisMateApp.*;

public class TennisMateGUI extends JFrame implements ActionListener {

    private static final String JSON_STORE = "./data/vancouverGUI.json";

    private Admin admin;
    private Location vancouver;
    private Court court;
    private Player player;
    private Coach coach;
    private User user;
    private User loginUser;
    private int userId;
    private String userName;

    private JsonWriter jsonWriter;
    //private JsonReaderLocation jsonReaderLocation;
    private JsonReaderAdmin jsonReaderAdmin;

    private JLabel statusLabel;
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
    JLabel bottomL = new JLabel("users in the courts");

    /* TextField */
    JTextField userNameF = new JTextField();


    /* Button */

    JButton signupBtn = new JButton("signUp");
    JButton loginBtn = new JButton("login");

    JButton saveBtn = new JButton("save");
    JButton loadBtn = new JButton("load");

    JButton selectCourtBtn = new JButton("select");
    JButton checkCourtBtn = new JButton("check");

    /* Combo Box */
    private JComboBox<String> courts = new JComboBox<>();
    private JComboBox<String> level = new JComboBox<>();
    private JComboBox<String> times = new JComboBox<>();

    public TennisMateGUI() {
        super("TennisMate UI");
     //   vancouver = new Location(LOCATION_NAME);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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
        checkCourtBtn.addActionListener(this);

        pack();
        setVisible(true);

    }

    public void initPanel() {
        centerPanel.setPreferredSize(new Dimension(260, 80));
        westPanel.setPreferredSize(new Dimension(210, 75));
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
        mainNorthPanel.setLayout(new FlowLayout());
        mainCenterPanel.setLayout(new FlowLayout());
        mainBottomPanel.setLayout(new FlowLayout());

    }

    public void addButton() {

        westPanel.add(userNameL);
        westPanel.add(userNameF);

        eastPanel.add(loginBtn);
        eastPanel.add(signupBtn);

        mainNorthPanel.add(loadBtn);
        mainNorthPanel.add(saveBtn);

        mainCenterPanel.add(courtsL);
        mainCenterPanel.add(courts);
        mainCenterPanel.add(selectCourtBtn);
        mainCenterPanel.add(checkCourtBtn);
        mainCenterPanel.add(times);

        mainBottomPanel.add(bottomL);


    }

    public void comboCourts() {

        this.courts.addItem("Kits");
        this.courts.addItem("StanleyPark");
        this.courts.addItem("UBC");
    }

    public void comboTimeSlot() {
        int i;
        for (i = 1; i <= 23; i++) {
            String out = (i + " - " + (i + 1));
            this.times.addItem(out);
        }
    }

    // MODIFIES: this
    // EFFECTS: initialize location and courts
    private void init() {
        vancouver = new Location(LOCATION_NAME);
        admin = new Admin(vancouver);
        admin.setLocation(vancouver);
        //loadLocation(location);
        loadCourt(vancouver);
        //loadPlayer(player);
//        userIdList = new HashSet<>();
//        userNameList = new HashSet<>();
//        userList = new HashSet<>();
//        runProgram = true;
        jsonWriter = new JsonWriter(JSON_STORE);
        //jsonReaderLocation = new JsonReaderLocation(JSON_STORE);
        jsonReaderAdmin = new JsonReaderAdmin(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: initialize courts in location
    public static void loadCourt(Location location) {

        //Court c0 = new Court(DEFAULT_COURT_NAME);
        //location.addCourt(c0);

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

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "check":
                String courtSelected = (String) courts.getSelectedItem();
                Court court = vancouver.lookingUpCourtByName(courtSelected);
                System.out.println(court);
                Collection<User> users = court.getUsers();
                if (users != null) {
                    bottomL.setText("users in " + courtSelected + " : " + users);
                } else {
                    bottomL.setText("users in " + courtSelected + " : NONE");
                }
                break;
            default:
        }
    }
}
