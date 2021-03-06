package ui;

import model.locations.Location;
import model.users.Admin;
import model.users.Coach;
import model.users.Player;
import model.courts.Court;
import model.users.User;
import persistence.JsonReaderAdmin;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class TennisMateApp {

    private static final String JSON_STORE = "./data/vancouver.json";

    private static final String LOCATION_NAME = "vancouver";
    private static final String DEFAULT_COURT_NAME = "NONE";
    private static final String UBC_COURT_NAME = "UBC";
    private static final String KITS_COURT_NAME = "Kits";
    private static final String STANLEY_PARK_COURT_NAME = "StanleyPark";


    private Scanner input;
    private boolean runProgram;
    private JsonWriter jsonWriter;
    //private JsonReaderLocation jsonReaderLocation;
    private JsonReaderAdmin jsonReaderAdmin;

    private Admin admin;
    private Location vancouver;
    private Court court;
    private Player player;
    private Coach coach;
    private User user;
    private User loginUser;
    private int userId;
    private String userName;
//    private Collection<Integer> userIdList;
//    private Collection<String> userNameList;
//    private Collection<User> userList;
    //private List<String> userInfo;



    // code referred TellerApp & FitLifeGymChain
    // MODIFIES: this
    // EFFECTS: runs the tennisMate application. initialize setup
    public TennisMateApp() throws FileNotFoundException {
        init();
        runTennisMate();
    }

    // code referred TellerApp & FitLifeGymChain
    //EFFECTS: processes user input
    public void runTennisMate() {
        displayMenu();
        input = new Scanner(System.in);
        String cmd;
        while (runProgram) {
            cmd = getUserInputString();
            if (cmd.equals("q")) {
                runProgram = false;
                endProgram();
            } else {
                loginCommand(cmd);
            }
        }
    }

    // code referred TellerApp
    // MODIFIES: this
    // EFFECTS: processes login command
    private void loginCommand(String cmd) {
        if (cmd.equals("s")) {
            doSignUp();
            doLogIn();
        } else if (cmd.equals("l")) {
            doLogIn();
        } else if (cmd.equals("save")) {
            //saveLocation();
            saveAdmin();
        } else if (cmd.equals("load")) {
            //loadLocation();
            loadAdmin();

        } else {
            System.out.println("Selection not valid...");
            displayMenu();
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("Please login to TennisMate");
        System.out.println("Enter 'l' to logIn");
        System.out.println("Enter 's' to signUp if you don't have account");
        System.out.println("Enter 'q' to quit");
        System.out.println("Enter 'save' to save");
        System.out.println("Enter 'load' to save");
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
        runProgram = true;
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

    // EFFECTS: prints signUp page
    private void printSignUpPage() {
        System.out.println("\n ========== SignUp to Tennis Mate  ========== ");
        System.out.println("\nSelect User Type:");
        System.out.println("\tEnter 'p' -> player");
        System.out.println("\tEnter 'c' -> coach");
    }

    private String userType() {
        String userType = getUserInputString();
        if (!userType.equals("p") && !userType.equals("c")) {
            System.out.println("Input Invalid. Please choose 'p' or 'b'");
            userType();
        }
        return userType;
    }

    // code referred TellerApp & FitLifeGymChain
    // MODIFIES: this
    // EFFECTS: signup for new user
    private void doSignUp() {
        printSignUpPage();
        String userType = userType();
        System.out.println("\nEnter User Name:");
        userName = getUserInputString();
        if (admin.getUserNameList().contains(userName)) {
            System.out.println("user Name exist.. Move to Login Page");
            doLogIn();
        } else {
            int userId = admin.generateUserId();
            //admin.addUserName(userName);
            //admin.addUserId(userId);
            if (userType.equals("p")) {
                user = new Player(userId, userName);
            } else if (userType.equals("c")) {
                user = new Coach(userId, userName);
            }
            admin.addUser(user);
            System.out.println("\nSignUp Completed.");
            System.out.println("user Name : " + userName + " (usertype: " + user.getType() + ")");
        }
    }

    // MODIFIES: this
    // EFFECTS: login Page for user
    private void doLogIn() {
        System.out.println("\nLogin: Press your userName");
        String userName = getUserInputString();
        if (!admin.getUserNameList().contains(userName)) {
            System.out.println("userName not founded");
            System.out.println("Move to SignUp...");
            doSignUp();
        }
        for (User u : admin.getUserList()) {
            if (u.getUserName().equals(userName)) {
                loginUser = u;
            }
        }
        mainPage();
    }

    //EFFECTS: prints out main page
    private void displayMainPage() {
        System.out.println("\nMain Page -------- User: " + loginUser.getUserName());
        System.out.println("Press '1' for user account setup");
        System.out.println("Enter '2' for court information");
        System.out.println("Enter '3' to go back login page");
    }


    //EFFECTS: main page. handle user input on main menu page
    private void mainPage() {
        displayMainPage();
        String cmd = getUserInputString();
        if (cmd.length() > 0) {
            switch (cmd) {
                case "1":
                    loginUserMenu();
                    //userSetup();
                    break;
                case "2":
                    courtMainMenu();
                    break;
                case "3":
                    displayMenu();
                    break;
                default:
                    System.out.println("Selection not valid...");
                    mainPage();
                    break;
            }
        }
    }

    //EFFECTS: prints user account information
    private void userAccount() {
        System.out.println("\nuser name ---------------- :" + loginUser.getUserName());
        System.out.println("Level -------------------- :" + loginUser.getLevel());
        System.out.println("Available Time ----------- :" + loginUser.getTimeSlot());
        System.out.println("Looking for a TennisMate - :" + loginUser.getStatus());
        if (loginUser.getPreferredCourt().isEmpty()) {
            System.out.println("Assigned court ----------- : None");
        } else {
            System.out.println("Assigned court ------------  ");
            for (Court c : loginUser.getPreferredCourt()) {
                System.out.println(c.getCourtName());
            }
        }
    }

    //EFFECTS: prints user setup menu
    private void printSetupPage() {
        System.out.println("\nEnter 'level' to change level");
        System.out.println("Enter 'court' to change preferred court");
        System.out.println("Enter 'time' to change available time");
        System.out.println("Enter 'status' to change status to lookup a tennisMate");
        System.out.println("Enter 'menu' to see menu selection");
    }

    //EFFECTS: handle user input for user setup menu
    private void userSetup() {
        printSetupPage();
        String cmd = getUserInputString();
        if (cmd.length() > 0) {
            if (cmd.equals("level") || cmd.equals("court") || cmd.equals("time") || cmd.equals("status")) {
                userSetupMenu(cmd);
            } else if (cmd.equals("menu")) {
                loginUserMenu();
            } else {
                System.out.println("Invalid input");
                userSetup();
            }
        }
    }

    private void userSetupMenu(String cmd) {
        switch (cmd) {
            case "level":
                accountSetupLevel();
                userSetup();
                break;
            case "court":
                accountSetupCourt();
                userSetup();
                break;
            case "time":
                accountSetupTime();
                userSetup();
                break;
            case "status":
                accountSetupStatus();
                userSetup();
                break;
        }
    }

    //EFFECTS: prints login user menu
    private void printLoginUserMenu() {
        System.out.println("\n============ USER MENU ============");
        System.out.println("Enter 'info' to see your account information");
        System.out.println("Enter 'setup' to set up your account");
        System.out.println("Enter 'main' to go back to Main Page");
    }

    //EFFECTS: handle user input for login user menu
    private void loginUserMenu() {
        printLoginUserMenu();
        String cmd = getUserInputString();
        if (cmd.length() > 0) {
            switch (cmd) {
                case "info":
                    userAccount();
                    loginUserMenu();
                    break;
                case "setup":
                    userSetup();
                    break;
                case "main":
                    mainPage();
                    break;
                default:
                    System.out.println("Selection not valid...");
                    loginUserMenu();
                    break;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: change user's level
    private void accountSetupLevel() {
        System.out.println("Enter 'novice' or 'intermediate' or 'advance'");
        String level = getUserInputString();
        if (level.equals("novice") || level.equals("intermediate") || level.equals("advance")) {
            loginUser.setLevel(level);
            System.out.println("level has been set to '" + loginUser.getLevel() + "'");
            System.out.println("Enter 'level' to change your level again");
            System.out.println("Enter any key to go back to user setup menu");
            String cmd = getUserInputString();
            if (cmd.equals("level")) {
                accountSetupLevel();
            }
        } else {
            System.out.println("invalid input");
            accountSetupLevel();
        }
    }

    // MODIFIES: this
    // EFFECTS: add/remove court from user's preferred court list
    private void accountSetupCourt() {
        System.out.println("Enter 'add' to Add court");
        System.out.println("Enter 'remove' to remove court");
        System.out.println("Enter 'menu' to see User menu");
        String cmd = getUserInputString();
        switch (cmd) {
            case "add":
                addCourt();
                break;
            case "remove":
                removeCourt();
                break;
            case "menu":
                loginUserMenu();
                break;
            default:
                System.out.println("Selection invalid...");
                accountSetupCourt();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: add court to user's courtlist
    private void addCourt() {
        printCourtInfo();
        System.out.println("\nSelect preferred Court: ");
        String courtName = getUserInputString();
        court = vancouver.lookingUpCourtByName(courtName);
        if (court == null) {
            System.out.println(courtName + " is not a registered court in TennisMate");
            System.out.println("please Enter valid court name");
            addCourt();
        }
        loginUser.addPreferredCourt(court);
        court.addUser(loginUser);
        printUpdatedCourt(loginUser);
        System.out.println("Enter 'add' to add a court");
        System.out.println("Enter any key to go back to user setup menu");
        String cmd = getUserInputString();
        if (cmd.equals("add")) {
            addCourt();
        }
    }

    // MODIFIES: this
    // EFFECTS: remove court from user's courtlist
    private void removeCourt() {
        System.out.println("\nSelect Court to remove from your list: ");
        System.out.println("Your preferred courts are:");
        for (Court c : loginUser.getPreferredCourt()) {
            System.out.println(c.getCourtName());
        }
        String courtName = getUserInputString();
        court = vancouver.lookingUpCourtByName(courtName);
        if (loginUser.getPreferredCourt().contains(court)) {
            loginUser.removePreferredCourt(court);
            court.removeUser(loginUser);
            printUpdatedCourt(loginUser);
        } else {
            System.out.println(courtName + "is Not in your preferred Court. Please choose again");
            accountSetupCourt();
        }
        System.out.println("Enter 'remove' to remove a court");
        System.out.println("Enter any key to go back to user setup menu");
        String cmd = getUserInputString();
        if (cmd.equals("remove")) {
            removeCourt();
        }
    }

    // EFFECTS: prints out user's preferred court list
    private void printUpdatedCourt(User loginUser) {
        System.out.println("\nYour preferred court list has been updated");
        System.out.println("Your preferred courts are:");
        for (Court c : loginUser.getPreferredCourt()) {
            System.out.println(c.getCourtName());
        }
    }

    // MODIFIES: this
    // EFFECTS: prints set up time slot menu
    private void printAccountSetUpTime() {
        System.out.println("Assigned time :" + loginUser.getTimeSlot());
        System.out.println("Enter 'add' to add time slot");
        System.out.println("Enter 'remove' to remove time slot");
        System.out.println("Enter 'menu' to go back to user menu");
    }

    // MODIFIES: this
    // EFFECTS: sets up user's timeslot
    private void accountSetupTime() {
        printAccountSetUpTime();
        String cmd = getUserInputString();
        switch (cmd) {
            case "add":
                accountAddSetupTimeSlot();
                break;
            case "remove":
                accountRemoveSetupTime();
                break;
            case "menu":
                loginUserMenu();
                break;
            default:
                System.out.println("Invalid input...");
                accountSetupTime();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: adds selected time to user's timeslot
    private void accountAddSetupTimeSlot() {
        System.out.println("Add timeSlot: Enter 0 - 23");
        String t = getUserInputString();
        int time = -1;
        if (t.matches("-?\\d+(\\.\\d+)?")) {
            time = Integer.parseInt(t);
        } else {
            System.out.println("Invalid input");
            accountAddSetupTimeSlot();
        }
        if (time < 0 || time > 23) {
            System.out.println("Invalid input");
            accountAddSetupTimeSlot();
        }
        loginUser.addTimeSlot(time);
        System.out.println("Your available time has been updated :" + loginUser.getTimeSlot());
        System.out.println("Enter 'add' to add more timeslot");
        System.out.println("Enter any key to go back to user setup menu");
        String cmd = getUserInputString();
        if (cmd.equals("add")) {
            accountAddSetupTimeSlot();
        }
    }

    // REQUIRES: time [0 - 23]
    // MODIFIES: this
    // EFFECTS: removes selected time from user's timeslot. 0 means 0 - 1
    private void accountRemoveSetupTime() {
        System.out.println("Remove timeSlot: Enter 0 - 23");
        String t = getUserInputString();
        int time = Integer.parseInt(t);
        if (time < 0 || time > 23) {
            System.out.println("Invalid input");
            accountAddSetupTimeSlot();
        }
        loginUser.removeTimeSlot(time);
        System.out.println("Your available time has been updated :" + loginUser.getTimeSlot());
        System.out.println("Enter 'remove' to remove more timeslot");
        System.out.println("Enter any key to go back to user setup menu");
        String cmd = getUserInputString();
        if (cmd.equals("remove")) {
            accountAddSetupTimeSlot();
        }
    }

    // MODIFIES: this
    // EFFECTS: sets up user's status
    private void accountSetupStatus() {
        System.out.println("Enter 'y' if you are looking for a partner");
        System.out.println("Enter 'n' if you are not looking for a partner");
        String cmd = getUserInputString();
        if (cmd.equals("y")) {
            loginUser.setStatus(true);
        } else if (cmd.equals("n")) {
            loginUser.setStatus(true);
        } else {
            System.out.println("Invalid Input");
            accountSetupStatus();
        }
    }

    //EFFECTS: prints court in vancouver
    private void printCourtInfo() {
        System.out.println("\nCourts in Vancouver");
        for (Court c : vancouver.getCourts()) {
            System.out.println(c.getCourtName());
        }
    }

    // MODIFIES: this
    // EFFECTS: handle user input for court main menu
    private void courtMainMenu() {
        printCourtInfo();
        System.out.println("Enter court Name  ex) 'UBC' ");
        String courtName = getUserInputString();
        court = null;
        court = vancouver.lookingUpCourtByName(courtName);
        if (court == null) {
            System.out.println(courtName + "is not a registered court in TennisMate");
            System.out.println("Please Enter valid court name");
            courtMainMenu();
        } else {
            courtSetup();
        }
    }

    //EFFECTS: print court setup menu
    private void printCourtSetup() {
        System.out.println("\nYou are in court" + court.getCourtName());
        System.out.println("Enter 'all' to lookup all players");
        System.out.println("Enter 'active' to lookup players who are looking for a tennis mate");
        System.out.println("Enter 'time' to lookup players who are available in selected time slot");
        System.out.println("Enter 'main' to go back to main menu");
    }

    // MODIFIES: this
    // EFFECTS: handle user input for court set up menu
    private void courtSetup() {
        printCourtSetup();
        String cmd = getUserInputString();
        if (cmd.length() > 0) {
            switch (cmd) {
                case "all":
                    courtSetupAllUsers();
                    courtSetup();
                    break;
                case "active":
                    courtSetupActiveUsers();
                    courtSetup();
                    break;
                case "time":
                    courtSetupPlayersInSelectedTimeSlot();
                    courtSetup();
                    break;
                case "main":
                    mainPage();
                    break;
                default:
                    courtSetup();
            }
        }
    }


    // EFFECTS: prints all userName assigned to this court
    private void courtSetupAllUsers() {
        System.out.println("In " + court.getCourtName() + " court,");
        courtSetupAllPlayers();
        courtSetupAllCoaches();
        //TreeSet<String> sortedAllPlayersList = new TreeSet<>(allPlayersList);
        //System.out.println(sortedAllPlayersList);
    }

    // EFFECTS: prints all players name assigned to this court
    private void courtSetupAllPlayers() {
        Collection<String> allPlayersList = new TreeSet<>();
        for (User u : court.getUsers()) {
            if (u.getType().equals("player")) {
                allPlayersList.add(u.getUserName());
            }
        }
        //if (allPlayersList.isEmpty()) {
        //    System.out.println("There is no one signed in " + court.getCourtName());
        //} else {
        System.out.println("Players: " + allPlayersList);
        //}
    }

    // EFFECTS: prints all coach name assigned to this court
    private void courtSetupAllCoaches() {
        Collection<String> allCoachesList = new TreeSet<>();
        for (User u : court.getCoaches()) {
            if (u.getType().equals("coach")) {
                allCoachesList.add(u.getUserName());
            }
        }
        System.out.println("Coaches: " + allCoachesList);
    }


    // EFFECTS: prints players name whose status are true in this court
    private void courtSetupActiveUsers() {
        System.out.println("players who are looking for a tennis mate:");
        HashSet<String> activePlayersList = new HashSet<>();
        for (User u : court.lookupUserByStatusTrue()) {
            activePlayersList.add(u.getUserName());
        }
        if (activePlayersList.isEmpty()) {
            System.out.println("There is no one looking for a tennis mate in " + court.getCourtName());
        }
        TreeSet<String> sortedActivePlayersList = new TreeSet<>(activePlayersList);
        System.out.println(sortedActivePlayersList);
    }

    // EFFECTS: prints players name whose time slot contains selected time slot
    private void courtSetupPlayersInSelectedTimeSlot() {
        System.out.println("Enter time slot From (0 - 23) :");
        int from = Integer.parseInt(getUserInputString());
        System.out.println("To (0 - 23): ");
        int to = Integer.parseInt(getUserInputString());
        if (from < 0 || from > 23 || to < 0 || to > 23) {
            System.out.println("Input invalid");
            courtSetupPlayersInSelectedTimeSlot();
        }
        List<Integer> selectedTime = new ArrayList<>();
        for (int i = from; i <= to; i++) {
            selectedTime.add(i);
        }
        Collection<String> usersInSelectedTimeSlot = new HashSet<>();
        for (Player p : court.getPlayers()) {
            for (Integer i : selectedTime) {
                if (p.getTimeSlot().contains(i)) {
                    usersInSelectedTimeSlot.add(p.getUserName());
                }
            }
        }
        TreeSet<String> sortedUserInSelectedTimeSlotList = new TreeSet<>(usersInSelectedTimeSlot);
        System.out.println("Players from " + from + " to " + to + " are: " + sortedUserInSelectedTimeSlotList);
        printUserTimeSlot(sortedUserInSelectedTimeSlotList);
    }

    // EFFECTS: prints players name with their time slot
    private void printUserTimeSlot(TreeSet list) {
        System.out.println("Enter 'y' to see the users' all available time");
        System.out.println("Enter any key to go back");
        String cmd = getUserInputString();
        if (cmd.equals("y")) {
            Iterator<String> it = list.iterator();
            User u = null;
            while (it.hasNext()) {
                u = court.lookingUpUserByName(it.next());
                System.out.println(u.getUserName() + ":" + u.getTimeSlot());
            }
        }
    }

    // code referred FitLifeGymChain
    //EFFECTS: removes white space and quotation marks around s
    private String makePrettyString(String s) {
        s = s.toLowerCase();
        s = s.trim();
        s = s.replaceAll("\"|\'", "");
        return s;
    }

    // code referred FitLifeGymChain
    // EFFECTS: processes user input
    private String getUserInputString() {
        String cmd = "";
        if (input.hasNext()) {
            cmd = input.nextLine();
            cmd = makePrettyString(cmd);
        }
        return cmd;
    }


    // code referred FitLifeGymChain
    //EFFECTS: stops receiving user input
    public void endProgram() {
        System.out.println("\nGoodbye!");
        input.close();
    }

//    // EFFECTS: saves the location to file
//    private void saveLocation() {
//        try {
//            jsonWriter.open();
//            jsonWriter.write(vancouver);
//            jsonWriter.close();
//            System.out.println("Saved " + JSON_STORE);
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to write to file: " + JSON_STORE);
//        }
//    }


    //This code is referred to the JSonSerializationDemo example
    // EFFECTS: saves the admin to file
    private void saveAdmin() {
        try {
            jsonWriter.open();
            jsonWriter.write(admin);
            jsonWriter.close();
            System.out.println("Saved " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

//    // MODIFIES: this
//    // EFFECTS: loads location from file
//    private void loadLocation() {
//        try {
//            vancouver = jsonReaderLocation.read();
//            System.out.println("Loaded " + JSON_STORE);
//        } catch (IOException e) {
//            System.out.println("Unable to read from file: " + JSON_STORE);
//        }
//    }

    //This code is referred to the JSonSerializationDemo example
    // MODIFIES: this
    // EFFECTS: loads admin from file
    private void loadAdmin() {
        try {
            admin = jsonReaderAdmin.readAdmin();
            vancouver = admin.getLocation();
            System.out.println("Loaded " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}



