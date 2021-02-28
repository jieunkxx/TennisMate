package model.users;

import java.util.Collection;
import java.util.HashSet;

public class Admin {

    int userId;
    private Collection<User> users;
    private Collection<Player> players;
    private Collection<Coach> coaches;

    public Admin() {
        users = new HashSet<>();
        players = new HashSet<>();
        coaches = new HashSet<>();
    }


    // MODIFIES: this
    // EFFECTS: generate user ID (integer 1 - 999) for new user
    private int generateId() {
        userId = (int) (Math.random() * 999) + 1;
        return userId;
    }

}


