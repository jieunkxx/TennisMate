package model.users;

import model.Locations.Location;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

public class Admin implements Writable {

    int userId;
    String userName;
    private Location location;

    private List<Integer> userIdList;
    private List<String> userNameList;
    private List<User> userList;

    public Admin(Location location) {
        this.location = location;
        userList = new ArrayList<>();
        userNameList = new ArrayList<>();
        userIdList = new ArrayList<>();
    }

    //getter
    public List<User> getUserList() {
        return userList;
    }

    public List<String> getUserNameList() {
        return userNameList;
    }

    public List<Integer> getUserIdList() {
        return userIdList;
    }

    public Location getLocation() {
        return location;
    }

    public String getLocationName() {
        return location.getLocationName();
    }

    // MODIFIES: this
    // EFFECTS: add user to userList
    public void addUser(User user) {
        userList.add(user);
        addUserName(user.getUserName());
        addUserId(user.getId());
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


