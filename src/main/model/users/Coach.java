package model.users;

/*
User type Coach
fee will be implemented in the future.
*/

public class Coach extends User {
    private int fees;

    // EFFECTS: constructs coach with given id and empty timeslot, empty court list
    //          and novice level, type "coach"
    public Coach(int id, String userName) {
        super(id, userName);
        type = "coach";
        fees = 0;
    }

    // getters
    /* Coachs's specific methods are not utilized in this version */
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
