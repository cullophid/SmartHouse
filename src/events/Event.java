package events;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Event {

    private static SimpleDateFormat sdm = new SimpleDateFormat("[HH:mm:ss]");
    
    protected int id;
    protected long ts;
        
    public Event(int id, long ts) {
        this.id = id;
        this.ts = ts;
    }
    
    public Event(int id) {
        this(id, System.currentTimeMillis());
    }
        
    public int getID() {
        return id;
    }

    public long getTS() {
        return ts;
    }
    
<<<<<<< HEAD
<<<<<<< HEAD
    public boolean compareID(int id) {
        return this.id == id;
    }
    
=======
>>>>>>> fe34afac81b863f1ed4f4a02b278a4c13ff5b7f2
=======
>>>>>>> fe34afac81b863f1ed4f4a02b278a4c13ff5b7f2
    public boolean equals(Object o) {
        if (!(o instanceof Event)) {
            return false;
        }
        Event e = (Event) o;
        if (e.id != this.id)
            return false;
        if (e.ts != this.ts)
            return false;
        
        return true;
    }
    
    /**
     * return timestamp as human readable string
     * @return
     */
    public String tsString(){
        return sdm.format(new Date(ts));
    } 

}
