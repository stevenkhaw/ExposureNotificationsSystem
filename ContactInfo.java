/**
 * NAME: Steven Khaw
 * ID: A16669117
 * EMAIL: skhaw@ucsd.edu
 * 
 * This file contains the ContactInfo class for PA6. It contains a simple
 * constructor method and a method to check if each of the values are non-
 * negative.
 */

/**
 * The ContactInfo class initializes instance variables for a student's id, 
 * distance, time, and used. It also has a method to ensure validity of 
 * each variable.
 */
public class ContactInfo {
    
    /** INSTANCE VARIABLES */
    public int id;
    public int distance;
    public int time;
    public boolean used;

    /**
     * Contstruct a ContactInfo object by initializing every instance variable
     * 
     * @param id integer value of student's random id
     * @param distance integer value for student's distance during contact
     * @param time integer value that stores when contact occures
     */
    public ContactInfo(int id, int distance, int time) {
        this.id = id;
        this.distance = distance;
        this.time = time;
        this.used = false;
    }

    /**
     * Checks validity of all instance variables in the class. Any int 
     * instance variable that is negative is deemed invalid.
     * 
     * @return boolean value if input is valid
     */
    public boolean isValid() {

        //Checks validity
        if (this.id < 0 || this.distance < 0 || this.time < 0) {
            return false;
        }

        return true;
    }
}
