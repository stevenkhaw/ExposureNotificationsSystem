/**
 * NAME: Steven Khaw
 * ID: A16669117
 * EMAIL: skhaw@ucsd.edu
 */
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
        if (this.id < 0 || this.distance < 0 || this.time < 0) {
            return false;
        }

        return true;
    }
}
