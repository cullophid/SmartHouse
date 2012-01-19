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
    
    public boolean compareID(int id) {
        return this.id == id;
    }
    
    /**
     * return timestamp as human readable string
     * @return
     */
    public String tsString(){
        return sdm.format(new Date(ts));
    } 

}
