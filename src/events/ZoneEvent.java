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
    }
    
    public ZoneEvent(List<Event> zone) {
        super(0);
        
        ids = new int[zone.size()];
        for(int i = 0; i < zone.size(); i++)
            ids[i] = zone.get(i).getID();
        
        Arrays.sort(ids);
        this.ids = ids;
    }
    
    public int[] getIDs() {
        return ids;
    }
    
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
}