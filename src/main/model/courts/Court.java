package model.courts;

import model.users.User;

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

    public Collection<User> getPlayers() {
        Collection<User> players = new HashSet<>();
        for (User u : users) {
            if (u.getType().equalsIgnoreCase("player")) {
                players.add(u);
            }
        }
        return players;
    }

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

//    @Override
//    public JSONObject toJson() {
//        JSONObject json = new JSONObject();
//        json.put("courtName", courtName);
//        json.put("users", usersToJson());
//        return json;
//    }
//
//    // EFFECTS: returns users in this court as a JSON array
//    public JSONArray usersToJson() {
//        JSONArray jsonArray = new JSONArray();
//        for (User u : users) {
//            jsonArray.put(u.toJson());
//        }
//        return jsonArray;
//    }
//
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

