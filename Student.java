/**
 * NAME: Steven Khaw
 * ID: A16669117
 * EMAIL: skhaw@ucsd.edu
 * 
 * This file contains the Student class for PA6. It contains a simple 
 * constructor method with the end goal of determining if this student is in
 * high risk of contracting the infection. There are several instance variables
 * including: id, location, covidPositive, inQuarantine, userIds, and 
 * contactHistory.
 */
import java.util.Random;
import java.util.ArrayList;

/**
 * The student class initializes instance variables that hold this student's 
 * id, location, covidPostiive and inQuarantine boolean values, and 
 * ArrayLists of Integers and ContactInfo objects.
 */
public class Student {
    /** INSTANCE VARIABLES */
    public int id;
    public int location;
    public boolean covidPositive;
    public boolean inQuarantine;
    public ArrayList<Integer> usedIds;
    public ArrayList<ContactInfo> contactHistory;
 
    /**
     * Construct a Student object and sets each instance variable to its 
     * respective setting
     */
    public Student() {
        this.id = -1;
        this.location = -1;
        this.covidPositive = false;
        this.inQuarantine = false;
        this.usedIds = new ArrayList<Integer>();
        this.contactHistory = new ArrayList<ContactInfo>();
    }

    /**
     * Updates student's location as long as studen is not in quarantine
     * 
     * @param newLocation integer value of this student's new location
     * @return boolean value if input is valid
     */
    public boolean setLocation(int newLocation) {
        //Checks validity
        if (newLocation < 0) {
            return false;
        }

        if (this.inQuarantine == false) {
            this.location = newLocation;
            return true;
        }
        
        return false;
    }

    /**
     * Creates a random student id and stores it in instance variable
     */
    public void updateId() {
        //Initialize intstances
        int maxInt = Integer.MAX_VALUE;
        Random rand = new Random();

        //Creates random int from 0 to the max int value
        this.id = rand.nextInt(maxInt);
        this.usedIds.add(this.id);
    }

    /**
     * Adds student's contactHistory to ArrayList of ContactInfo objects
     * 
     * @param info ContactInfo object being tested
     * @return boolean value if input is valid
     */
    public boolean addContactInfo(ContactInfo info) {

        //Checks validity
        if (info == null || !info.isValid()) {
            return false;
        }

        this.contactHistory.add(info);
        return true;
    }

    /**
     * Uploads students ids onto the Server object
     * 
     * @param server Server object being tested
     * @return boolean value if input is valid
     */
    public boolean uploadAllUsedIds(Server server) {

        //Checks validity
        if (server == null) {
            return false;
        }

        return server.addInfectedIds(this.usedIds);
    }

    /**
     * Changes covidPositive and inQuarantine instance variables to true while 
     * also adding this student's used ids onto Server object
     * 
     * @param server Server object being tested
     * @return boolean value if input is valid
     */
    public boolean testPositive(Server server) { 
        this.covidPositive = true;
        this.inQuarantine = true;

        //Checks validity
        if (server == null) {
            return false;
        }

        return uploadAllUsedIds(server);
    }

    /**
     * Checks whether student has had any positive encounters with someone 
     * with the infection
     * 
     * @param server Server object being tested
     * @param fromTime Integer value for time being compared/tested
     * @return ArrayList of ContactInfo objects that are positive encounters
     */
    public ArrayList<ContactInfo> getRecentPositiveContacts(Server server,
            int fromTime) {
        
        //Check validity
        if (server == null || fromTime < 0 || 
                server.getInfectedIds() == null) {
            return null;
        }

        ArrayList<ContactInfo> output = new ArrayList<ContactInfo>();
        ArrayList<Integer> infectedList = server.getInfectedIds();

        //Iterates through contactHistory to test for used, id, and time cases
        for (int i = 0; i < contactHistory.size(); i++) {
            boolean usedCase = false;
            boolean idCase = false;
            boolean timeCase = false;

            //True if satifies used conditions
            if (!contactHistory.get(i).used) {
                usedCase = true;
            }

            //True if satifies id conditions
            if (infectedList.contains(contactHistory.get(i).id)) {
                idCase = true;
            }

            //True if satifies time conditions
            if (contactHistory.get(i).time >= fromTime) {
                timeCase = true;
            }

            //Runs if all boolean cases are true
            if (usedCase && idCase && timeCase) {
                output.add(contactHistory.get(i));
            }
        }

        return output;
    }

    /**
     * Determines student's risk of having infection and gives student the 
     * option to quarantine or not
     * 
     * @param server Server object being tested
     * @param fromTime Integer value for time being compared/tested
     * @param quarantineChoice Boolean value for student's choice to quarantine
     * @return Integer value to determine risk of student contracting infection
     */
    public int riskCheck(Server server, int fromTime, 
            boolean quarantineChoice) {
        
        //Initializes array being tested
        ArrayList<ContactInfo> positiveContactInfos =
                getRecentPositiveContacts(server, fromTime);
        
        //Checks validity
        if (positiveContactInfos == null) {
            return -1;
        }
        
        //Assess the risk of infection
        boolean highRisk = false;
        boolean changeAll = false;
        
        //Determines base case of true if more than 3 contacts had infection
        if (positiveContactInfos.size() >= 3) {
            changeAll = true;
            highRisk = true;
        }

        //Iterates through ArrayList 
        for (int i = 0; i < positiveContactInfos.size(); i++) {
            //Checks distance condition
            if (positiveContactInfos.get(i).distance <= 1) {
                positiveContactInfos.get(i).used = true;
                highRisk = true;
            }

            if (changeAll) {
                positiveContactInfos.get(i).used = true;
            }
        }
        
        //Gives student option to quarantine or not
        if (highRisk && quarantineChoice) {
            this.inQuarantine = true;
            return 1;
        }

        if (highRisk) {
            return 1;
        }
        
        return 0;
    }
}