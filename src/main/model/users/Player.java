package model.users;

/*
User type Player
*/

public class Player extends User {

    // EFFECTS: constructs player with given id and empty timeslot, empty court list
    //          and novice level, type "player"
    public Player(int id, String userName) {
        super(id, userName);
        type = "player";
    }
}


