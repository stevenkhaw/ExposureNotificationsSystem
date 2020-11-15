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
 * The ContactInfo class initializes instance 
 */
public class ContactInfo {
    public int id;
    public int distance;
    public int time;
    public boolean used;

    /**
     * 
     * @param id
     * @param distance
     * @param time
     */
    public ContactInfo(int id, int distance, int time) {
        this.id = id;
        this.distance = distance;
        this.time = time;
        this.used = false;
    }

    /**
     * 
     * @return
     */
    public boolean isValid() {
        if (this.id < 0 || this.distance < 0 || this.time < 0) {
            return false;
        }

        return true;
    }
}
