package persistence;

import model.locations.*;
import model.courts.Court;
import model.users.*;
import model.users.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/*
This code is referred to the JSonSerializationDemo example
 */


public class JsonReaderAdmin {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReaderAdmin(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Admin readAdmin() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAdmin(jsonObject);
    }

    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses admin from JSON object and returns it
    private Admin parseAdmin(JSONObject jsonObject) {
        Location location = parseLocation(jsonObject);
        Admin admin = new Admin(location);
        addUser(admin, location, jsonObject);
        return admin;
    }

    // EFFECTS: parses location from JSON object and returns it
    private Location parseLocation(JSONObject jsonObject) {
        String locationName = jsonObject.getString("Admin");
        Location location = new Location(locationName);
        ui.TennisMateApp.loadCourt(location);
        return location;
    }

    // MODIFIES: admin, location
    // EFFECTS: parses users from JSON object and adds them to admin and location
    private void addUser(Admin admin, Location loc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("User");
        for (Object json : jsonArray) {
            JSONObject nextUser = (JSONObject) json;
            addInfo(admin, loc, nextUser);
        }
    }

    // MODIFIES: admin
    // EFFECTS: parses users from JSON object and adds it to admin and location
    private void addInfo(Admin admin, Location loc, JSONObject jsonObject) {
        String type = jsonObject.getString("type");
        int userId = jsonObject.getInt("id");
        String name = jsonObject.getString("userName");
        User user = null;
        if (type.equals("player")) {
            user = new Player(userId, name);
        } else {
            user = new Coach(userId, name);
        }

        user.setLevel(jsonObject.getString("level"));
        user.setStatus(jsonObject.getBoolean("status"));

        JSONArray timeslot = jsonObject.getJSONArray("timeSlot");
        addTimeSlot(user, timeslot);
        JSONArray courtName = jsonObject.getJSONArray("courts");
        if (!courtName.isEmpty()) {
            addCourt(loc, user, courtName);
        }

        addToAdmin(admin, loc, user);
    }

    // MODIFIES: user
    // EFFECTS: read timeslot from JSONArray and add it to user's timeslot
    private void addTimeSlot(User user, JSONArray timeslot) {
        for (Object t : timeslot) {
            user.addTimeSlot((Integer) t);
        }
    }

    // MODIFIES: location, court, user
    // EFFECTS: read court from JSONArray and add it to user's preferredCourtList and location
    private void addCourt(Location loc, User user, JSONArray courtName) {
        for (Object c : courtName) {
            String name = c.toString();
//            Court court = null;
//            if (loc.lookingUpCourtByName(name) == null) {
//                court = new Court(name);
//                loc.addCourt(court);
//            } else {
//                court = loc.lookingUpCourtByName(name);
//            }
            Court court = loc.lookingUpCourtByName(name);
            court.addUser(user);
            //user.addPreferredCourt(court);
        }
    }

    // MODIFIES: admin
    // EFFECTS: add user to admin and set the location
    private void addToAdmin(Admin admin, Location loc, User user) {
        admin.addUser(user);
        //admin.addUserName(user.getUserName());
        //admin.addUserId(user.getId());
        admin.setLocation(loc);
    }

}
