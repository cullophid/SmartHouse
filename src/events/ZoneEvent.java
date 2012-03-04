package events;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author David
 */
public class ZoneEvent extends Event {

    protected int[] ids;
    
    public ZoneEvent(int ... ids) {
        super(0);
        Arrays.sort(ids);
        this.ids = ids;
        
        this.id = getID(ids);
    }
    
    public ZoneEvent(long ts, int ... ids) {
        this(ids);
        this.ts = ts;
        this.id = getID(ids);
    }
    
    public ZoneEvent(List<Event> zone) {
        this(zone, System.currentTimeMillis());
    }
    
    public ZoneEvent(List<Event> zone, long ts) {
        super(0);
        
        ids = new int[zone.size()];
        for(int i = 0; i < zone.size(); i++)
            ids[i] = zone.get(i).getID();
        
        Arrays.sort(ids);

        this.id = getID(ids);
        this.ts = zone.get(zone.size()-1).getTS();
    }

    private static int getID(int ...ids) {
        int sum = 0;
        for (int i : ids) 
            sum = sum*256 + i;
            
        return sum;
    }
    
    public int[] getIDs() {
        return ids;
    }
    
    public void addID(int id) {
        int[] tmp = new int[ids.length + 1];
        tmp[0] = id;
        System.arraycopy(ids, 0, tmp, 1, ids.length);
        ids = tmp;
        Arrays.sort(ids);
        this.id = getID(ids);
    }
    

    /**
     * overrides the super class method compareID, to compare idx to all the ids in the zone event
     */
    @Override
    public boolean compareID(int idx) {
        for(int id : ids) {
            if (id == idx)
                return true;
        }
        return false;
    }

    public String toString() {
        return tsString() + " Zone event " + Arrays.toString(ids);
    }
    
    public boolean equals(Object o) {
        if (!super.equals(o))
            return false;

        if (!(o instanceof ZoneEvent)) 
            return false;
        
        ZoneEvent e = (ZoneEvent) o;
        if (e.ids.length != this.ids.length)
            return false;
        
        for (int i = 0; i < e.ids.length; i++) {
            if (e.ids[i] != this.ids[i])
                return false;
        }
        return true;
    }
    
    /**
     * 
     * @param id
     * @return
     */
    public static List<Integer> getIDs (int id) {
        LinkedList<Integer> ids = new LinkedList<Integer>();
        while(id > 0) {
            ids.addFirst(id % 256);
            id /= 256;
        }
        
        return ids;
    }
    
    public static String getIDString(int id) {
        if (id < 256)
            return Integer.toString(id);
        
        StringBuffer sb = new StringBuffer("[");
        for (int i : getIDs(id))
            sb.append(i + ",");
        sb.setCharAt(sb.length()-1, ']');
        
        return sb.toString();
    }
}
