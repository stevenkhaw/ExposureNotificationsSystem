/**
 * NAME: Steven Khaw
 * ID: A16669117
 * EMAIL: skhaw@ucsd.edu
 * 
 * This file contains the Server class for PA6. It contains a simple 
 * constructor method, method to add infected ids, and a method to
 * return a deep copy of the infected ids array.
 */
import java.util.ArrayList;

/**
 * The Server class initializes an instance variable that holds all infected
 * ids. It has methods to add or get elements in this array.
 */
public class Server {
    /** INSTANCE VARIABLE */
    public ArrayList<Integer> infectedIds;

    /**
     * Construct a Server object that holds an ArryList of integers
     */
    public Server() {
        infectedIds = new ArrayList<Integer>();
    }

    /**
     * Adds every infected id in the input array to the server's instance 
     * variable
     * 
     * @param ids ArrayList of integer ids that are infected
     * @return boolean value if input is valid
     */    
    public boolean addInfectedIds(ArrayList<Integer> ids) {
        
        //Checks validity
        if (ids == null) {
            return false;
        }

        //Iterates and appends every element to instance variable
        for (int id : ids) {
            infectedIds.add(id);
        }

        return true;
    }

    /**
     * Creates a deep copy of all infected ids in the Server
     * 
     * @return deep copy of ArrayList containing all infected ids 
     */
    public ArrayList<Integer> getInfectedIds() {
        ArrayList<Integer> copyInfectedIds = new ArrayList<Integer>();

        //Iterates and appends every element to a new output array, deep copy
        for (int id : this.infectedIds) {
            copyInfectedIds.add(id);
        }

        return copyInfectedIds;
    }
}