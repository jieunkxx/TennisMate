# Tennis Mate

## Jieun Kim

This application will allow users to find tennis mates in the preferred tennis courts among registered vancouver courts in TennisMate system. 
It indicates tennis players/coaches who are looking for tennis partners/clients with their level and available time slots in the chosen tennis courts or location. 
The intended audience includes tennis players from novice to advanced level in metro Vancouver area. 

The application will generate setup, and will allow the user to:
- SignUp/Login to the system with user name. 
- Choose tennis courts among the registered courts in the location
- Check their account information
- Assign available time slot
- Define their skill level - novice/intermediate/advanced
- Declare their status - looking for a tennis mate (y/n)
- Choose tennis courts among the registered courts in vancouver in the system

This application will provide a user information in the chosen courts:
- Find all assigned players
- Find players who are available in selected timeslot
- Find players who are looking for a tennis mate
- Assigned coaches

This application aims to minimize the effort to find a right match based on users’ availability, level and a court. 


## User Stories
- As a user, I want to be able to register with user name.
- As a user, I want to be able to login to the generated user by signup.
- As a user, I want to be able to check a user information
- As a user, I want to be able to change user information (preferred courts, level, time slots, status)
- As a user, I want to be able to assign to a court in the location.
- As a user, I want to be able to see assigned courts in the location. 
- As a user, I want to be able to see players assigned in my preferred courts.
- As a user, I want to be able to filter players by their timeslot.
- As a user, I want to be able to filter players by their status.
- As a user, I want to be able to save all user accounts information to file.
- As a user, I want to be able to load my user account from file, and login with the information to the system.
- As a user, I want to be able to load and save the state of the application.


##Phase 4: Task 2
- Bi-directional association between Court and User. Court class calls addUser method and removeUsers method to maintain its user list, and User class calls addPreferredCourt method and removePreferredCourt method to maintain the corresponding court (assigned court).


##Phase 4: Task 3
- UML diagram added : UML_Design_Diagram.pdf
- Coupling in TennisMateApp and TennisMateAppGUI : there are some methods used in both classes. 
    - generate super class, and have TennisMateApp and TennisMateGUI as subclass would minimize the duplication and coupling issue
    - ex) shared method: init(), loadCourt(), login(), loadData(), saveData()
- Cohesion in TennisMateApp and TennisMateAppGUI :  
    - court specific and user specific method can be implemented to Court class and User class. ex) getCourts to getCourtsByName method in Court.
- Readability : TennisMateApp and TennisMateGUI have conditional statements and iterations upon user's input which possibly cause readability issue. New method can be extracted from the codes.  
- Reduction of duplication : There are duplicated code used in some methods in TennisMateApp and TennisMateGUI, especially for print statements. 
    - The duplicated part can be extracted as a method to reduce the duplication.
    - create Map<userName, User> would reduce redundant code implementation and redundant iterations when searching user's info. 
- Updated: 
    - generated printErrMsg method to reduce the duplicated code when print error message and error icon in TennisMateAppGUI
    - extracted methods to improve readability in TennisMateApp. ex) findUserFromUserList(), generateSortedUserListInSelectedTimeSlot() 
    - move getCourtsByName in TennisGUI to Court to improve cohesion

             