package model.users;

import model.courts.Court;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class Coach {

    private int id; // user id
    private String userName;
    private boolean status;
    private List<Integer> timeSlots;
    private String level;
    private Collection<Court> courts;
    private int fees;

    // REQUIRES: id is not taken by other user
    // EFFECTS: constructs coach with given id and empty timeslot, empty court list
    //          and novice level
    public Coach(int id, String userName) {
        this.id = id;
        this.userName = userName;
        courts = new HashSet<>();
        level = "novice";
        status = false;
        fees = 0;
        timeSlots = new ArrayList<>();
        for (int i = 0; i <= 23; i++) {
            timeSlots.add(i, null);
        }
    }

    // getters
    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public boolean getStatus() {
        return status;
    }

    public Collection<Court> getPreferredCourt() {
        return courts;
    }

    public String getLevel() {
        return level;
    }

    public Collection<Integer> getTimeSlot() {
        Collection<Integer> availableTimeSlot = new HashSet<Integer>();
        for (int i = 0; i < timeSlots.size(); i++) {
            if (timeSlots.get(i) != null) {
                availableTimeSlot.add(i);
            }
        }
        return availableTimeSlot;
    }

    public int getFees() {

        return fees;
    }

    // REQUIRES: there is no user using the same id
    // MODIFIES: this
    // EFFECTS: set this coach's id
    public void setID(int id) {

        this.id = id;
    }

    // REQUIRES: level should be one of "novice", "intermediate", "advance"
    // MODIFIES: this
    // EFFECTS: set this coach's level
    public void setLevel(String level) {

        this.level = level;
    }

    //REQUIRES: court != null
    //MODIFIES: this
    //EFFECTS: adds the given court to coach's preferred court list
    public void addPreferredCourt(Court court) {

        courts.add(court);
    }

    //REQUIRES: court != null
    //MODIFIES: this
    //EFFECTS: removes the given court from coach's preferred court list
    public void removePreferredCourt(Court court) {

        courts.remove(court);
    }


    //MODIFIES: this
    //EFFECTS: adds time slot to coach's available time
    public void addTimeSlot(int time) {

        timeSlots.add(time, time);
    }

    //MODIFIES: this
    //EFFECTS: removes the time slot from coach's available time
    public void removeTimeSlot(int time) {

        timeSlots.remove(time);
    }

    // MODIFIES: this
    // EFFECTS: sets status
    public void setStatus(boolean status) {

        this.status = status;
    }

    public void setFees(int fees) {

        this.fees = fees;
    }

}
