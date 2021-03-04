package model.users;

// Represents a player with id, preferred courts, time slots, level and status
public class Player extends User {

    // REQUIRES: id is not taken by other user
    // EFFECTS: constructs player with given id and empty timeslot, empty court list
    //          and novice level
    public Player(int id, String userName) {
        super(id, userName);
        type = "player";
    }


}


