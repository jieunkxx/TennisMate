package model.users;

import exceptions.CourtException;
import exceptions.CourtNullException;
import model.courts.Court;
import org.json.JSONArray;
import org.json.JSONObject;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;
import persistence.Writable;

import java.util.*;

public class User implements Writable {
    protected int id;                     // user's id
    protected String userName;            // user's name
    protected boolean status;             // user's status
    protected List<Integer> timeSlots;    // user's time slot
    protected String level;               // user's level
    protected Collection<Court> courts;   // user's preferred courts
    protected String type;

    // REQUIRES: id is not taken by other user
    // EFFECTS: constructs user with given id and empty timeslot, empty court list
    //          and novice level
    public User(int id, String userName) {
        this.id = id;
        this.userName = userName;
        status = false;
        level = "novice";
        courts = new HashSet<>();
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

    public boolean lookingupCourtByName(String courtName) {
        boolean status = false;
        for (Court c : courts) {
            if (c.getCourtName().equalsIgnoreCase(courtName)) {
                status = true;
            }
        }
        return status;
    }

    public String getLevel() {
        return level;
    }

    public String getType() {
        return type;
    }

    // EFFECTS: return a list of assigned time slot
    public List<Integer> getTimeSlot() {
        List<Integer> availableTimeSlot = new ArrayList<>();
        //Collection<Integer> availableTimeSlot = new TreeSet<>();
        for (int i = 0; i < timeSlots.size(); i++) {
            if (timeSlots.get(i) != null) {
                availableTimeSlot.add(i);
            }
        }
        //Collections.sort(availableTimeSlot);
        return availableTimeSlot;
    }

    // REQUIRES: there is no user using the same id
    // MODIFIES: this
    // EFFECTS: set this player's id
    public void setID(int id) {
        this.id = id;
    }

    // REQUIRES: there is no user using the same user name
    // MODIFIES: this
    // EFFECTS: set this user's user name
    public void setUserName(String userName) {
        this.userName = userName;
    }

    // REQUIRES: level should be one of "novice", "intermediate", "advance"
    // MODIFIES: this
    // EFFECTS: set this user's level
    public void setLevel(String level) {
        this.level = level;
    }

    // bi-directional user <-> court
    //REQUIRES: court != null
    //MODIFIES: this
    //EFFECTS: adds the given court to user's preferred court list
    public void addPreferredCourt(Court court) {
        if (!courts.contains(court)) {
            courts.add(court);
            court.addUser(this);
        }
    }

    // bi-directional user <-> court
    //MODIFIES: this
    //EFFECTS: removes the given court from user's preferred court list
    public void removePreferredCourt(Court court) {
        if (courts.contains(court)) {
            courts.remove(court);
            court.removeUser(this);
        }
    }

    //REQUIRES: time slots : 0 ~ 23
    //MODIFIES: this
    //EFFECTS: adds time slot to user's available time
    public void addTimeSlot(int time) {
        timeSlots.set(time, time);
    }

    //MODIFIES: this
    //EFFECTS: removes the time slot from user's available time
    public void removeTimeSlot(int time) {
        timeSlots.remove(time);
    }

    // MODIFIES: this
    // EFFECTS: sets status
    public boolean setStatus(boolean status) {
        this.status = status;
        return this.status;
    }

//    public boolean setStatus(String status) {
//        if (status.equals("true")) {
//            this.status = true;
//        } else {
//            this.status = false;
//        }
//        return this.status;
//    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("userName", userName);
        json.put("id", id);
        json.put("type", type);
        json.put("status", status);
        json.put("level", level);
        json.put("courts", courtsToJson());
        json.put("timeSlot", timeslotsToJson());

        return json;
    }

    public JSONArray courtsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Court c : courts) {
            jsonArray.put(c.getCourtName());
        }
        return jsonArray;
    }

    // EFFECTS: returns timeslot in this user as a JSON array
    public JSONArray timeslotsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < getTimeSlot().size(); i++) {
            jsonArray.put(getTimeSlot().get(i));
        }
        return jsonArray;
    }


}
