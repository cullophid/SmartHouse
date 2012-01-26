package events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ZoneEvent extends Event {

    protected int[] ids;
    
    public ZoneEvent(int ... ids) {
        super(0);
<<<<<<< HEAD
<<<<<<< HEAD

        Arrays.sort(ids);
        this.ids = ids;
=======
>>>>>>> fe34afac81b863f1ed4f4a02b278a4c13ff5b7f2
=======
>>>>>>> fe34afac81b863f1ed4f4a02b278a4c13ff5b7f2
        
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
<<<<<<< HEAD
<<<<<<< HEAD
    
    @Override
    public boolean compareID(int idx) {
        for(int id : ids) {
            if (id == idx)
                return true;
        }
        return false;
    }
    
=======
        
>>>>>>> fe34afac81b863f1ed4f4a02b278a4c13ff5b7f2
=======
        
>>>>>>> fe34afac81b863f1ed4f4a02b278a4c13ff5b7f2
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
