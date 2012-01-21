package events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ZoneEvent extends Event {

    protected int[] ids;
    
    public ZoneEvent(int ... ids) {
        super(0);
        
        Arrays.sort(ids);
        this.ids = ids;
        
        int sum = 0;
        for (int i : ids) 
            sum = sum*256 + i;
            
        this.id = sum;
    }
    
    public ZoneEvent(List<Event> zone) {
        super(0);
        
        ids = new int[zone.size()];
        for(int i = 0; i < zone.size(); i++)
            ids[i] = zone.get(i).getID();
        
        Arrays.sort(ids);
    }
    
    public int[] getIDs() {
        return ids;
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
}
