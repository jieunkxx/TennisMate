package model.courts;

import model.users.Coach;
import model.users.Player;


import java.util.*;

public class Court {

   private String courtName;               // court name
   private Collection<Player> players;     // list of players assigned to this court
   private Collection<Coach> coaches;

    //REQUIRES: courtName has a non-zero length
    //EFFECTS: constructs court with given name, courtName, and empty player list.
    public Court(String courtName) {
        this.courtName = courtName;
        players = new HashSet<>();
        coaches = new HashSet<>();
    }

    //getter
    public String getCourtName() {
        return courtName;
    }

    public Collection<Player> getPlayers() {
        return players;
    }

    public Collection<Coach> getCoaches() {
        return coaches;
    }

    // REQUIRES: player != null
    // MODIFIES: this
    // EFFECTS: adds player to this court
    public void addPlayer(Player player) {
        players.add(player);
    }

    // REQUIRES: player != null && The player is assigned to the court
    // MODIFIES: this
    // EFFECTS: removes player from this court
    public void removePlayer(Player player) {
        players.remove(player);
    }

    // EFFECTS: return player who has name "username"
    public Player lookingUpPlayerByName(String userName) {
        Player player = null;
        for (Player p : players) {
            if (p.getUserName().equalsIgnoreCase(userName)) {
                player = p;
            }
        }
        return player;
    }

    // EFFECTS: return a list of players who has status true
    public Collection<Player> lookupPlayersByStatusTrue() {
        Collection<Player> playerStatusTrue = new HashSet<>();
        for (Player p : players) {
            if (p.getStatus()) {
                playerStatusTrue.add(p);
            }
        }
        return playerStatusTrue;
    }

    // Coach option is not provided in this version
    // REQUIRES: player != null
    // MODIFIES: this
    // EFFECTS: adds coach to this court
    public void addCoach(Coach coach) {
        coaches.add(coach);
    }

    // Coach option is not provided in this version
    // REQUIRES: player != null
    // MODIFIES: this
    // EFFECTS: removes coach from this court
    public void removeCoach(Coach coach) {
        coaches.remove(coach);
    }

    // Coach option is not provided in this version
    public Collection<Coach> lookupCoachByStatusTrue() {
        Collection<Coach> coachStatusTrue = new HashSet<>();
        for (Coach c : coaches) {
            if (c.getStatus()) {
                coachStatusTrue.add(c);
            }
        }
        return coachStatusTrue;
    }


}

