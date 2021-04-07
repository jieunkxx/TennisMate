package model.locations;

import model.courts.Court;
import model.users.User;

import java.util.Collection;
import java.util.HashSet;

/*
Location contains courts
*/

//public class Location implements Writable {
public class Location {

    private String locationName;
    private Collection<Court> courts;
    //private Collection<User> users;


    //EFFECTS: constructs location with its courtlist
    public Location(String locName) {
        this.locationName = locName;
        courts = new HashSet<>();
    }

    // getters
    public String getLocationName() {
        return locationName;
    }

    public Collection<Court> getCourts() {
        return courts;
    }

    public void setLocationName(String locName) {
        this.locationName = locName;
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
