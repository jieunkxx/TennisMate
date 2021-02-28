package model.users;

import model.courts.Court;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class Coach extends User {
    private int fees;

    // REQUIRES: id is not taken by other user
    // EFFECTS: constructs coach with given id and empty timeslot, empty court list
    //          and novice level
    public Coach(int id, String userName) {
        super(id, userName);
        type = "coach";
        fees = 0;
    }

    // getters

    //EFFECTS: return the hourly rate for the lesson
    public int getFees() {
        return fees;
    }

    //MODIFIES: this
    //EFFECTS: set the hourly rate for the lesson
    public void setFees(int fees) {
        this.fees = fees;
    }

}
