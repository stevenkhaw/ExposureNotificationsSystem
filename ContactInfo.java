public class ContactInfo {
    public int id;
    public int distance;
    public int time;
    public boolean used;

    public ContactInfo(int id, int distance, int time) {
        this.id = id;
        this.distance = distance;
        this.time = time;
        this.used = false;
    }

    public boolean isValid() {
        if (id < 0 || distance < 0 || time < 0) {
            return false;
        }
        
        return true;
    }

    
}
