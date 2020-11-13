import java.util.ArrayList;

public class Server {
    public ArrayList<Integer> infectedIds;

    public Server() {
        infectedIds = new ArrayList<Integer>();
    }

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

    public ArrayList<Integer> getInfectedIds() {
        ArrayList<Integer> copyInfectedIds = new ArrayList<Integer>();

        for (int id : this.infectedIds) {
            copyInfectedIds.add(id);
        }

        return copyInfectedIds;
    }
}