package model.users;

import model.Location;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.Collection;
import java.util.HashSet;

public class Admin implements Writable {

    int userId;
    String userName;
    private Location location;

    private Collection<Integer> userIdList;
    private Collection<String> userNameList;
    private Collection<User> userList;

    public Admin() {
        location = new Location();
        userList = new HashSet<>();
        userNameList = new HashSet<>();
        userIdList = new HashSet<>();
    }

    //getter
    public Collection<User> getUserList() {
        return userList;
    }

    public Collection<String> getUserNameList() {
        return userNameList;
    }

    public Collection<Integer> getUserIdList() {
        return userIdList;
    }

    public Location getLocation() {
        return location;
    }

    // MODIFIES: this
    // EFFECTS: add user to userList
    public void addUser(User user) {
        userList.add(user);
    }

    // MODIFIES: this
    // EFFECTS: add userName to userNameList
    public void addUserName(String userName) {
        userNameList.add(userName);
    }

    // MODIFIES: this
    // EFFECTS: add userId to userIdList
    public void addUserId(int userId) {
        userIdList.add(userId);
    }

    // EFFECTS: generate user ID (integer 1 - 999) for new user
    public int generateUserId() {
        userId = (int) (Math.random() * 999) + 1;
        return userId;
    }

    // MODIFIES: this
    // EFFECTS: set location
    public void setLocation(Location vancouver) {
        this.location = vancouver;
    }


    @Override
    public JSONObject toJson() {
        JSONArray jsonArray = new JSONArray();
        JSONObject json = new JSONObject();
        json.put("Admin", "Vancouver");
        json.put("User", usersToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray usersToJson() {
        JSONArray jsonArray = new JSONArray();
        for (User u : userList) {
            jsonArray.put(u.toJson());
        }
        return jsonArray;
    }
}


