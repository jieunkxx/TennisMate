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

/* Run text based TennisMate app. /*/

public class TennisMateApp {

    private static final String JSON_STORE = "./data/vancouver.json";

    public static final String LOCATION_NAME = "vancouver";
    public static final String UBC_COURT_NAME = "UBC";
    public static final String KITS_COURT_NAME = "Kits";
    public static final String STANLEY_PARK_COURT_NAME = "StanleyPark";

    private Scanner input;
    private boolean runProgram;
    private JsonWriter jsonWriter;
    private JsonReaderAdmin jsonReaderAdmin;

    private Admin admin;
    private Location vancouver;
    private Court court;
    private User loginUser; // cast player and coach to user in this version
    private String userName;


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
            saveAdmin();
            displayMenu();
            cmd = getUserInputString();
            loginCommand(cmd);
        } else if (cmd.equals("load")) {
            loadAdmin();
            displayMenu();
            cmd = getUserInputString();
            loginCommand(cmd);
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
        System.out.println("Enter 'load' to load");
    }


    // MODIFIES: this
    // EFFECTS: initialize location and courts in the location
    private void init() {
        vancouver = new Location(LOCATION_NAME);
        admin = new Admin(vancouver);
        admin.setLocation(vancouver);
        loadCourt(vancouver);
        runProgram = true;
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

    // EFFECTS: prints signUp page
    private void printSignUpPage() {
        System.out.println("\n ========== SignUp to Tennis Mate  ========== ");
        System.out.println("\nSelect User Type:");
        System.out.println("\tEnter 'p' -> player");
        System.out.println("\tEnter 'c' -> coach");
    }

    // EFFECTS: prints user type (p or c)
    private String userType() {
        String userType = getUserInputString();
        if (!userType.equals("p") && !userType.equals("c")) {
            System.out.println("Input Invalid. Please choose 'p' or 'c'");
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
            if (userType.equals("p")) {
                Player newUser = new Player(userId, userName);
                admin.addUser(newUser);
            } else if (userType.equals("c")) {
                Coach newUser = new Coach(userId, userName);
                admin.addUser(newUser);
            }
            //admin.addUser(newUser);
            System.out.println("\nSignUp Completed.");
            //System.out.println("user Name : " + userName + " (usertype: " + newUser.getType() + ")");
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
        findUserFromUserList(userName);
        mainPage();
    }

    // EFFECTS: return login user if there is a user with the user name entered in Admin's user list
    private void findUserFromUserList(String userName) {
        for (User u : admin.getUserList()) {
            if (u.getUserName().equals(userName)) {
                loginUser = u; // cast player and coach to user type in this version
            }
        }
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


    // EFFECTS: user setup menu handler
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
    // EFFECTS: add court to user's court list
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
        //court.addUser(loginUser);
        printUpdatedCourt(loginUser);
        System.out.println("Enter 'add' to add a court");
        System.out.println("Enter any key to go back to user setup menu");
        String cmd = getUserInputString();
        if (cmd.equals("add")) {
            addCourt();
        }
    }

    // MODIFIES: this
    // EFFECTS: remove court from user's court list
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
            //court.removeUser(loginUser);
            printUpdatedCourt(loginUser);
        } else {
            System.out.println(courtName + " is Not in your preferred Court. Please choose again");
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
        System.out.println("Your preferred courts are : ");
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
        if (t.matches("\\d\\d?")) {
            time = Integer.parseInt(t);
            if (time < 0 || time > 23) {
                invalidTime();
            }
            loginUser.addTimeSlot(time);
            nextMoveAfterUpdateTimeslot("add");
            String cmd = getUserInputString();
            if (cmd.equals("add")) {
                accountAddSetupTimeSlot();
            } else {
                System.out.println("Move to user setup menu..");
            }
        } else {
            invalidTime();
        }
    }

    // EFFECT: recall accountAddSetupTimeSlot() method
    private void invalidTime() {
        System.out.println("Invalid input");
        accountAddSetupTimeSlot();
    }

    private void nextMoveAfterUpdateTimeslot(String str) {
        System.out.println("Your available time has been updated :" + loginUser.getTimeSlot());
        System.out.println("Enter '" + str + "' to " + str + " more timeslot");
        System.out.println("Enter any key to go back to user setup menu");
    }

    // REQUIRES: time [0 - 23]
    // MODIFIES: this
    // EFFECTS: removes selected time from user's timeslot. 0 means 0 - 1
    private void accountRemoveSetupTime() {
        System.out.println("Remove timeSlot: Enter 0 - 23");
        String t = getUserInputString();
        int time = -1;
        if (t.matches("\\d\\d?")) {
            time = Integer.parseInt(t);
            if (time < 0 || time > 23) {
                System.out.println("Invalid input");
                accountRemoveSetupTime();
            }
            loginUser.removeTimeSlot(time);
            nextMoveAfterUpdateTimeslot("remove");
            String cmd = getUserInputString();
            if (cmd.equals("remove")) {
                accountRemoveSetupTime();
            }
        } else {
            System.out.println("Invalid input");
            accountRemoveSetupTime();
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
            loginUser.setStatus(false);
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
            System.out.println(courtName + " is not a registered court in TennisMate");
            System.out.println("Please Enter valid court name");
            courtMainMenu();
        } else {
            courtSetup();
        }
    }

    //EFFECTS: print court setup menu
    private void printCourtSetup() {
        System.out.println("\nYou are in court" + court.getCourtName());
        System.out.println("Enter 'all' to lookup all users");
        System.out.println("Enter 'active' to lookup users who are looking for a tennis mate");
        System.out.println("Enter 'time' to lookup users who are available in selected time slot");
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
                    courtSetupUsersInSelectedTimeSlot();
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
    }

    // EFFECTS: prints all players name assigned to this court
    private void courtSetupAllPlayers() {
        Collection<String> allPlayersList = new TreeSet<>();
        for (User u : court.getPlayers()) {
            allPlayersList.add(u.getUserName());
        }
        System.out.println("Players: " + allPlayersList);
    }

    // EFFECTS: prints all coach name assigned to this court
    private void courtSetupAllCoaches() {
        Collection<String> allCoachesList = new TreeSet<>();
        for (User u : court.getCoaches()) {
            allCoachesList.add(u.getUserName());
        }
        System.out.println("Coaches: " + allCoachesList);
    }

    // EFFECTS: prints players name whose status are true in this court
    private void courtSetupActiveUsers() {
        System.out.println("Users who are looking for a tennis mate:");
        HashSet<String> activeUsersList = new HashSet<>();
        for (User u : court.lookupUserByStatusTrue()) {
            activeUsersList.add(u.getUserName());
        }
        if (activeUsersList.isEmpty()) {
            System.out.println("There is no one looking for a tennis mate in " + court.getCourtName());
        }
        TreeSet<String> sortedActiveUsersList = new TreeSet<>(activeUsersList);
        System.out.println(sortedActiveUsersList);
    }

    // EFFECTS: prints users name whose time slot contains selected time slot
    private void courtSetupUsersInSelectedTimeSlot() {
        System.out.println("Enter time slot From (0 - 23) :");
        int from = Integer.parseInt(getUserInputString());
        System.out.println("To (0 - 23): ");
        int to = Integer.parseInt(getUserInputString());
        if (from < 0 || from > 23 || to < 0 || to > 23) {
            System.out.println("Input invalid");
            courtSetupUsersInSelectedTimeSlot();
        }
        List<Integer> selectedTime = new ArrayList<>();
        for (int i = from; i <= to; i++) {
            selectedTime.add(i);
        }
        TreeSet<String> sortedUserInSelectedTimeSlotList = generateSortedUserListInSelectedTimeSlot(selectedTime);
        System.out.println("Users from " + from + " to " + to + " are: " + sortedUserInSelectedTimeSlotList);
        printUserTimeSlot(sortedUserInSelectedTimeSlotList);
    }

    // refactoring
    // EFFECTS: generate sorted user list in the selected time slot
    private TreeSet<String> generateSortedUserListInSelectedTimeSlot(List<Integer> selectedTime) {
        Collection<String> usersInSelectedTimeSlot = new HashSet<>();
        for (User u : court.getUsers()) {
            for (Integer i : selectedTime) {
                if (u.getTimeSlot().contains(i)) {
                    usersInSelectedTimeSlot.add(u.getUserName());
                }
            }
        }
        TreeSet<String> sortedUserInSelectedTimeSlotList = new TreeSet<>(usersInSelectedTimeSlot);
        return sortedUserInSelectedTimeSlotList;
    }

    // EFFECTS: prints users name with their time slot
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



