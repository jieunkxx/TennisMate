package persistence;


import model.Locations.Location;
import model.courts.Court;
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

public class JsonReaderLocation {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReaderLocation(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Location read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTarget(jsonObject);
    }

    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses location from JSON object and returns it
    private Location parseTarget(JSONObject jsonObject) {
        String locationName = jsonObject.getString("Admin");
        Location loc = new Location(locationName);
        addCourts(loc, jsonObject);
        return loc;
    }


    // MODIFIES: wr
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addCourts(Location loc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("courts");
        for (Object json : jsonArray) {
            JSONObject nextCourt = (JSONObject) json;
            addCourt(loc, nextCourt);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addCourt(Location loc, JSONObject jsonObject) {
        String courtName = jsonObject.getString("courtName");
        Court court = new Court(courtName);
        loc.addCourt(court);
        JSONArray jsonArray = jsonObject.getJSONArray("users");
        for (Object json : jsonArray) {
            JSONObject user = (JSONObject) json;
            addUsers(court, user);
        }
    }

    private void addUsers(Court court, JSONObject jsonObject) {
        String userName = jsonObject.getString("userName");
        int id = jsonObject.getInt("id");
//        int id = Integer.parseInt(jsonObject.getString("id"));

//        int id = court.lookingUpUserByName(userName).getId();
        User user = new User(id, userName);
        user.setLevel(jsonObject.getString("level"));
        user.setStatus(jsonObject.getBoolean("status"));
//        if ((jsonObject.getString("status")).equals("true")) {
//            user.setStatus(true);
//        } else {
//            user.setStatus(false);
//        }

        JSONArray timeslot = jsonObject.getJSONArray("timeSlot");
        for (Object t : timeslot) {
            user.addTimeSlot((Integer) t);
        }


    }

}
