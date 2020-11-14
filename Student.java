/**
 * NAME: Steven Khaw
 * ID: A16669117
 * EMAIL: skhaw@ucsd.edu
 */
import java.util.Random;
import java.util.ArrayList;

public class Student {
    public int id;
    public int location;
    public boolean covidPositive;
    public boolean inQuarantine;
    public ArrayList<Integer> usedIds;
    public ArrayList<ContactInfo> contactHistory;

    public Student() {
        this.id = -1;
        this.location = -1;
        this.covidPositive = false;
        this.inQuarantine = false;
        this.usedIds = new ArrayList<Integer>();
        this.contactHistory = new ArrayList<ContactInfo>();
    }

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

    public void updateId() {
        int maxInt = Integer.MAX_VALUE;
        Random rand = new Random();

        this.id = rand.nextInt(maxInt);
        this.usedIds.add(this.id);
    }

    public boolean addContactInfo(ContactInfo info) {
        //Checks validity
        if (info == null || info.isValid() == false) {
            return false;
        }

        this.contactHistory.add(info);
        return true;
    }

    public boolean uploadAllUsedIds(Server server) {
        //Checks validity
        if (server == null) {
            return false;
        }

        return server.addInfectedIds(usedIds);
    }

    public boolean testPositive(Server server) { 
        this.covidPositive = true;
        this.inQuarantine = true;

        //Checks validity
        if (server == null) {
            return false;
        }

        return uploadAllUsedIds(server);
    }

    public ArrayList<ContactInfo> getRecentPositiveContacts(Server server,
            int fromTime) {
        //Check validity
        if (server == null || fromTime < 0 || 
                server.getInfectedIds() == null) {
            return null;
        }

        ArrayList<ContactInfo> output = new ArrayList<ContactInfo>();
        this.usedIds = server.getInfectedIds();

        for (int i = 0; i < contactHistory.size(); i++) {
            boolean usedCase = false;
            boolean idCase = false;
            boolean timeCase = false;

            if (contactHistory.get(i).used == false) {
                usedCase = true;
            }

            for (int j = 0; j < usedIds.size(); j++) {
                if (contactHistory.get(i).id == usedIds.get(j)) {
                    idCase = true;
                }
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

    public int riskCheck(Server server, int fromTime, 
            boolean quarantineChoice) {
        boolean highRiskCase = false;
        ArrayList<ContactInfo> positiveContactInfos =
                getRecentPositiveContacts(server, fromTime);
        
        if (positiveContactInfos == null) {
            return -1;
        }
        
        int inContactCount = 0;
        for (int i = 0; i < contactHistory.size(); i++) {
            for (int j = 0; j < positiveContactInfos.size(); j++) {
                if (contactHistory.get(i).id == 
                        positiveContactInfos.get(j).id) {
                    if (contactHistory.get(i).distance >= 1) {
                        contactHistory.get(i).used = true;
                        this.inQuarantine = true;
                        return 1;
                    }
                    inContactCount++;
                } 
            }

            if (inContactCount >= 3) {
                contactHistory.get(i).used = true;
                this.inQuarantine = true;
                return 1;
            }
        }
        
        return 0;
    }
}