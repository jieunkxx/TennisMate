package model.users;

import model.courts.Court;

import java.util.*;

// Represents a player with id, preferred courts, time slots, level and status
public class Player {

    private int id;                     // user's id
    private String userName;            // user's name
    private boolean status;             // user's status
    private List<Integer> timeSlots;    // user's time slot
    private String level;               // user's level
    private Collection<Court> courts;   // user's preferred courts


    // REQUIRES: id is not taken by other user
    // EFFECTS: constructs player with given id and empty timeslot, empty court list
    //          and novice level
    public Player(int id, String userName) {
        this.id = id;
        this.userName = userName;
        courts = new HashSet<>();
        level = "novice";
        status = false;
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

    // EFFECTS: return a list of assigned time slot
    public List<Integer> getTimeSlot() {
        List<Integer> availableTimeSlot = new ArrayList<>();
        for (int i = 0; i < timeSlots.size(); i++) {
            if (timeSlots.get(i) != null) {
                availableTimeSlot.add(i);
            }
        }
        Collections.sort(availableTimeSlot);
        return availableTimeSlot;
    }

    // REQUIRES: there is no players using the same id
    // MODIFIES: this
    // EFFECTS: set this player's id
    public void setID(int id) {
        this.id = id;
    }

    // REQUIRES: there is no players using the same user name
    // MODIFIES: this
    // EFFECTS: set this player's user name
    public void setUserName(String userName) {
        this.userName = userName;
    }

    // REQUIRES: level should be one of "novice", "intermediate", "advance"
    // MODIFIES: this
    // EFFECTS: set this player's level
    public void setLevel(String level) {
        this.level = level;
    }

    //REQUIRES: court != null
    //MODIFIES: this
    //EFFECTS: adds the given court to player's preferred court list
    public void addPreferredCourt(Court court) {
        courts.add(court);
    }

    //REQUIRES: court != null
    //MODIFIES: this
    //EFFECTS: removes the given court from player's preferred court list
    public void removePreferredCourt(Court court) {
        courts.remove(court);
    }

    //REQUIRES: time slots : 0 ~ 23
    //MODIFIES: this
    //EFFECTS: adds time slot to player's available time
    public void addTimeSlot(int time) {
        timeSlots.add(time, time);
    }

    //MODIFIES: this
    //EFFECTS: removes the time slot from player's available time
    public void removeTimeSlot(int time) {
        timeSlots.remove(time);
    }

    // MODIFIES: this
    // EFFECTS: sets status
    public boolean setStatus(boolean status) {
        this.status = status;
        return status;
    }
}


