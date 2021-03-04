package model.courts;

import model.users.User;
import model.users.Coach;
import model.users.Player;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;


import java.util.*;

public class Court implements Writable {

    private String courtName;               // court name
    private Collection<Player> players;     // list of players assigned to this court
    private Collection<Coach> coaches;
    private Collection<User> users;     // list of players assigned to this court

    //REQUIRES: courtName has a non-zero length
    //EFFECTS: constructs court with given name, courtName, and empty player list.
    public Court(String courtName) {
        this.courtName = courtName;
        players = new HashSet<>();
        coaches = new HashSet<>();
        users = new HashSet<>();
    }

    //getter
    public String getCourtName() {
        return courtName;
    }

    public Collection<Player> getPlayers() {
        return players;
    }

    public Collection<Coach> getCoaches() {
        return coaches;
    }

    public Collection<User> getUsers() {
        return users;
    }

    // REQUIRES: player != null
    // MODIFIES: this
    // EFFECTS: adds player to this court
    public void addUser(User user) {
        users.add(user);
    }

    // REQUIRES: player != null && The player is assigned to the court
    // MODIFIES: this
    // EFFECTS: removes player from this court
    public void removeUser(User user) {
        users.remove(user);
    }

    // EFFECTS: return player who has name "username"
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

    public Collection<User> lookupUserByType(String type) {
        Collection<User> userType = new HashSet<>();
        for (User u : users) {
            if (u.getType().equals(type)) {
                userType.add(u);
            }
        }
        return userType;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("courtName", courtName);
        json.put("users", usersToJson());
        return json;
    }

    public JSONArray usersToJson() {
        JSONArray jsonArray = new JSONArray();
        for (User u : users) {
            jsonArray.put(u.toJson());
        }
        return jsonArray;
    }

//
//    // EFFECTS: return player who has name "username"
//    public Player lookingUpPlayerByName(String userName) {
//        Player player = null;
//        for (Player p : players) {
//            if (p.getUserName().equalsIgnoreCase(userName)) {
//                player = p;
//            }
//        }
//        return player;
//    }
//
//    // EFFECTS: return a list of players who has status true
//    public Collection<Player> lookupPlayersByStatusTrue() {
//        Collection<Player> playerStatusTrue = new HashSet<>();
//        for (Player p : players) {
//            if (p.getStatus()) {
//                playerStatusTrue.add(p);
//            }
//        }
//        return playerStatusTrue;
//    }
//
//    // Coach option is not provided in this version
//    // REQUIRES: player != null
//    // MODIFIES: this
//    // EFFECTS: adds coach to this court
//    public void addCoach(Coach coach) {
//        coaches.add(coach);
//    }
//
//    // Coach option is not provided in this version
//    // REQUIRES: player != null
//    // MODIFIES: this
//    // EFFECTS: removes coach from this court
//    public void removeCoach(Coach coach) {
//        coaches.remove(coach);
//    }
//
//    // Coach option is not provided in this version
//    public Collection<Coach> lookupCoachByStatusTrue() {
//        Collection<Coach> coachStatusTrue = new HashSet<>();
//        for (Coach c : coaches) {
//            if (c.getStatus()) {
//                coachStatusTrue.add(c);
//            }
//        }
//        return coachStatusTrue;
//    }


}

