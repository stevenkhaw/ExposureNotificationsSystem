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
 * 
 * 
 * method to set the location of this student, method
 * to update the id with a new random int, method to add student's contact
 * information to contact history, method to upload all of student's past ids
 * to server, 
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
     * 
     * @param newLocation integer value of this student's new location
     * @return boolean value if 
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
     * 
     */
    public void updateId() {
        int maxInt = Integer.MAX_VALUE;
        Random rand = new Random();

        this.id = rand.nextInt(maxInt);
        this.usedIds.add(this.id);
    }

    /**
     * 
     * @param info
     * @return
     */
    public boolean addContactInfo(ContactInfo info) {
        //Checks validity
        if (info == null || info.isValid() == false) {
            return false;
        }

        this.contactHistory.add(info);
        return true;
    }

    /**
     * 
     * @param server
     * @return
     */
    public boolean uploadAllUsedIds(Server server) {
        //Checks validity
        if (server == null) {
            return false;
        }

        return server.addInfectedIds(this.usedIds);
    }

    /**
     * 
     * @param server
     * @return
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
     * 
     * @param server
     * @param fromTime
     * @return
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

        for (int i = 0; i < contactHistory.size(); i++) {
            boolean usedCase = false;
            boolean idCase = false;
            boolean timeCase = false;

            if (contactHistory.get(i).used == false) {
                usedCase = true;
            }

            if (infectedList.contains(contactHistory.get(i).id)) {
                idCase = true;
            }

            if (contactHistory.get(i).time >= fromTime) {
                timeCase = true;
            }

            if (usedCase == true && idCase == true && timeCase == true) {
                output.add(contactHistory.get(i));
            }
        }

        return output;
    }

    /**
     * 
     * @param server
     * @param fromTime
     * @param quarantineChoice
     * @return
     */
    public int riskCheck(Server server, int fromTime, 
            boolean quarantineChoice) {
        ArrayList<ContactInfo> positiveContactInfos =
                getRecentPositiveContacts(server, fromTime);
        
        //Checks validity
        if (positiveContactInfos == null) {
            return -1;
        }
        
        //Assess the risk of infection
        boolean highRisk = false;
        boolean changeAll = false;
        
        if (positiveContactInfos.size() >= 3) {
            changeAll = true;
            highRisk = true;
        }

        for (int i = 0; i < positiveContactInfos.size(); i++) {
            if (positiveContactInfos.get(i).distance <= 1) {
                positiveContactInfos.get(i).used = true;
                highRisk = true;
            }

            if (changeAll) {
                positiveContactInfos.get(i).used = true;
            }
        }
        
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