package events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
<<<<<<< HEAD
<<<<<<< HEAD
=======

>>>>>>> fe34afac81b863f1ed4f4a02b278a4c13ff5b7f2
=======

>>>>>>> fe34afac81b863f1ed4f4a02b278a4c13ff5b7f2
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
    private int pattern_interval =  10*1000;
<<<<<<< HEAD
<<<<<<< HEAD
    private int pattern_length = 7;
=======
    private int pattern_depth = 7;
>>>>>>> fe34afac81b863f1ed4f4a02b278a4c13ff5b7f2
=======
    private int pattern_depth = 7;
>>>>>>> fe34afac81b863f1ed4f4a02b278a4c13ff5b7f2
    
    public static void main(String[] args) {
        EventList list = new EventList();
        list.sensorEvent(1);
        list.sensorEvent(2);
        list.sensorEvent(3);
        System.out.println(list);
    }
    
    public EventList() {
        events = new LinkedList<Event>();
        zone = new LinkedList<Event>();
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

<<<<<<< HEAD
<<<<<<< HEAD
        if (events.size() >= pattern_length)
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
=======
>>>>>>> fe34afac81b863f1ed4f4a02b278a4c13ff5b7f2
=======
>>>>>>> fe34afac81b863f1ed4f4a02b278a4c13ff5b7f2
        if (events.size() >= pattern_depth)
            events.remove(0);
    }
    
    private int currentPatternDepth() {
        int count = 0;
        for (Event e : events)
            if (e instanceof SensorEvent)
                count++;
        return count;
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
            if (zone.get(i).getID() == e.getID()) {
                duplicates = true;
            }
                        
            if (duplicates)
                zone.remove(i);
        }
        zone.add(e);

        //limit to 4 sensors
        while (zone.size() > 4)
            zone.remove(0);
        
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
    
<<<<<<< HEAD
<<<<<<< HEAD
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
    
=======
>>>>>>> fe34afac81b863f1ed4f4a02b278a4c13ff5b7f2
=======
>>>>>>> fe34afac81b863f1ed4f4a02b278a4c13ff5b7f2
    /**
     * get events in event list, including detected zone events
     * @return
     */
    public Event[] getEvents() {
        return events.toArray(new Event[events.size()]);
    }
    
    /**
     * get only sensor and zone events
     * @return
     */
    public Event[] getPattern() {
        //TODO  
        Event[] pattern = new Event[pattern_depth];
        //if current pattern depth is less than pattern depth, fill missing with -1 
        for (int i = 0; i < pattern_depth - currentPatternDepth(); i++) {
            pattern[i] = new SensorEvent(-1);
        }
        
        Iterator<Event> it = events.iterator();
        for (int i = pattern_depth - currentPatternDepth(); i < pattern_depth; i++) {
            pattern[i] = it.next();
        }
        return pattern;
    }
}
