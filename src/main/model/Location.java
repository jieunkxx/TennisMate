package model;

import model.courts.Court;
import model.users.Player;

import java.util.Collection;
import java.util.HashSet;

public class Location {

    private Collection<Court> courts;
    //private String name;
    //private Collection<Player> players;


    //EFFECTS: constructs location with its courtlist
    public Location() {
        courts = new HashSet<>();
        //players = new HashSet<>();
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
}
