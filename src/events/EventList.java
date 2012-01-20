package events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import config.Config;

public class EventList {

    private List<Event> events;
    private List<Event> zone;
    
    /**
     * Maximum interval between sensor events, for the event to be considered a zone event.
     *  Default value 1 sec.
     */
    private int zone_interval = 1000;
    
    /**
     * Time interval stored in the event list. 
     */
    private int pattern_interval = 10*1000;
    private int pattern_depth = 7;
    
    public static void main(String[] args) {
        EventList list = new EventList();
        list.sensorEvent(1);
        list.sensorEvent(2);
        list.sensorEvent(3);
        System.out.println(list);
    }
    
    public EventList() {
        events = new ArrayList<Event>(7);
        zone = new ArrayList<Event>(3);
    }
    
    public EventList(int zone_interval, int pattern_interval) {
        this();
        this.zone_interval = zone_interval;
        this.pattern_interval = pattern_interval;
    }
    
    /**
     * Add event 
     * @param e
     */
    public void add(Event e) {
        removeOld();
        if(e instanceof SensorEvent)
            determineZone(e);
        
        events.add(e);
    }
        
    /**
     * removes all events if more than pattern interval has passed since the last event
     * also mantains a maximum pattern depth
     */
    private void removeOld() {
        if(events.size() > 0 && System.currentTimeMillis() - events.get(events.size()-1).getTS() > pattern_interval)
            events.clear();

        if (events.size() >= pattern_depth)
            events.remove(0);
    }
    
    private void determineZone(Event e) {
        if (!(e instanceof SensorEvent))
            return;

        //remove events more than zone_interval old
        while(zone.size() > 0 && (e.getTS() - zone.get(0).getTS() > zone_interval))
                zone.remove(0);
        
        //remove duplicates and older (e.g. 1,2,3,4,2 -> 3,4,2)
        boolean duplicates = false;
        for (int i = zone.size() - 1; i >= 0; i--) {
            if (zone.get(i).compareID(e.getID())) {
                duplicates = true;
            }
                        
            if (duplicates)
                zone.remove(i);
        }
        zone.add(e);
        
        if(zone.size() >= 2) {
            events.add(new ZoneEvent(zone));
        }
    }
    
    public String toString() {
        StringBuffer sb = new StringBuffer("=== Event list ===\n");
        for (Event e : events) {
            sb.append(e.toString() + "\n");
        }
        return sb.toString();
    }

    public void sensorEvent(int id) {
        add(new SensorEvent(id));
    }

    public void switchEvent(int id, int status) {
        boolean cmd = (status == 0) ? false : true;
        add(new SwitchEvent(id, cmd));
    }
    
    //TODO various get methods
    public Event[] getEvents() {
        Event[] array = new Event[events.size()];
        events.toArray(array);
        return array;
    }
    
    public Event[] getDistinctEvents() {
        HashSet<Event> set = new HashSet<Event>(events);
        Event[] array = new Event[set.size()];
        set.toArray(array);
        return array;
    }
    
}
