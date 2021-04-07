package model.courts;

import model.users.User;

import java.text.Collator;
import java.util.*;

/*
Represents a court with its name and users
*/
public class Court {
    //public class Court implements Writable {
    private String courtName;               // court name
    private Collection<User> users;     // list of players assigned to this court

    //REQUIRES: courtName has a non-zero length
    //EFFECTS: constructs court with given name, courtName, and empty player list.
    public Court(String courtName) {
        this.courtName = courtName;
        users = new HashSet<>();
    }

    //getter
    public String getCourtName() {
        return courtName;
    }

    public Collection<User> getUsers() {
        return users;
    }
/*
    // EFFECTS: return user by name in the court
    private Collection<String> getUsersByName(Court court) {
        Collection<String> usersList = new TreeSet<String>(Collator.getInstance());
        for (User u : court.getUsers()) {
            usersList.add(u.getUserName());
        }
        return usersList;
    }
*/

    // EFFECTS: find players in the court
    public Collection<User> getPlayers() {
        Collection<User> players = new HashSet<>();
        for (User u : users) {
            if (u.getType().equalsIgnoreCase("player")) {
                players.add(u);
            }
        }
        return players;
    }

    // EFFECTS: find coaches in the court
    public Collection<User> getCoaches() {
        Collection<User> coaches = new HashSet<>();
        for (User u : users) {
            if (u.getType().equalsIgnoreCase("coach")) {
                coaches.add(u);
            }
        }
        return coaches;
    }

    // bi-directional user <-> court
    // REQUIRES: user != null
    // MODIFIES: this
    // EFFECTS: adds player to this court
    public void addUser(User user) {
        if (!users.contains(user)) {
            users.add(user);
            user.addPreferredCourt(this);
        }
    }

    // bi-directional user <-> court
    // REQUIRES: user != null
    // MODIFIES: this
    // EFFECTS: removes player from this court
    public void removeUser(User user) {
        if (users.contains(user)) {
            users.remove(user);
            user.removePreferredCourt(this);
        }
    }

    // EFFECTS: return user who has name "username"
    public User lookingUpUserByName(String userName) {
        User user = null;
        for (User u : users) {
            if (u.getUserName().equalsIgnoreCase(userName)) {
                user = u;
            }
        }
        return user;
    }

    // EFFECTS: return a list of users who has status true
    public Collection<User> lookupUserByStatusTrue() {
        Collection<User> userStatusTrue = new HashSet<>();
        for (User u : users) {
            if (u.getStatus()) {
                userStatusTrue.add(u);
            }
        }
        return userStatusTrue;
    }

    // EFFECTS: return a list of users who has type passed parameter type
    public Collection<User> lookupUserByType(String type) {
        Collection<User> userType = new HashSet<>();
        for (User u : users) {
            if (u.getType().equals(type)) {
                userType.add(u);
            }
        }
        return userType;
    }
}

