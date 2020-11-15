/**
 * NAME: Steven Khaw
 * ID: A16669117
 * EMAIL: skhaw@ucsd.edu
 */
import java.util.Random;
import java.util.ArrayList;

/**
 * 
 */
public class Student {
    public int id;
    public int location;
    public boolean covidPositive;
    public boolean inQuarantine;
    public ArrayList<Integer> usedIds;
    public ArrayList<ContactInfo> contactHistory;

    /**
     * 
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
     * @param newLocation
     * @return
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

        return server.addInfectedIds(usedIds);
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

    public static void main(String[] args) {
        ContactInfo testContact1 = new ContactInfo(1,5,1);
        ContactInfo testContact2 = new ContactInfo(2,2,2);
        Server testServer = new Server();
        Student me = new Student();
        testServer.infectedIds.add(testContact1.id);
        testServer.infectedIds.add(testContact2.id);
        me.contactHistory.add(testContact1);
        me.contactHistory.add(testContact2);
        
        System.out.println(me.riskCheck(testServer, 0, true));
    }
}