package model.users;

import java.util.Collection;
import java.util.HashSet;

public class Admin {

    int userId;
    private Collection<User> users;
    private Collection<Player> players;
    private Collection<Coach> coaches;

    private Collection<Integer> userIdList;
    private Collection<String> userNameList;
    private Collection<User> userList;
    //private List<String> userInfo;

    public Admin() {
        userList = new HashSet<>();
        //players = new HashSet<>();
        //coaches = new HashSet<>();

    }

    public Collection<User> getUserList() {
        return userList;
    }

    public void addUser(User user) {
        userList.add(user);
    }

    // MODIFIES: this
    // EFFECTS: generate user ID (integer 1 - 999) for new user
    private int generateId() {
        userId = (int) (Math.random() * 999) + 1;
        return userId;
    }

}


