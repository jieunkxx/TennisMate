package model;

import model.courts.Court;
import model.users.User;
import persistence.Writable;
import org.json.JSONArray;
import org.json.JSONObject;


import java.util.Collection;
import java.util.HashSet;

public class Location implements Writable {

    private Collection<Court> courts;
    //private String name;
    private Collection<User> users;


    //EFFECTS: constructs location with its courtlist
    public Location() {
        courts = new HashSet<>();
        users = new HashSet<>();
    }

    // getters

    //public String getName() {
    //    return name;
    //}

    public Collection<Court> getCourts() {
        return courts;
    }

    // REQUIRES: courtName is the name of an existing court
    // EFFECTS: returns court object that has name courtName
    public Court lookingUpCourtByName(String courtName) {
        Court court = null;
        for (Court c: courts) {
            if (c.getCourtName().equalsIgnoreCase(courtName)) {
                court = c;
            }
        }
        return court;
    }


    // REQUIRES: court != null
    // MODIFIES: this
    // EFFECTS: adds a court in the location
    public void addCourt(Court court) {
        courts.add(court);
    }

    @Override
    public JSONObject toJson() {
        JSONArray jsonArray = new JSONArray();
        JSONObject json = new JSONObject();
        json.put("Location", "Vancouver");
        json.put("Courts", courtsToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray courtsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Court c : courts) {
            jsonArray.put(c.toJson());
        }
        return jsonArray;
    }
}
