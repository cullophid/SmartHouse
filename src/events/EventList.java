package events;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import config.Config;

public class EventList {

    private LinkedList<Event> events;
//    private LinkedList<Event> zone;
    
    /**
     * Maximum interval between sensor events, for the event to be considered a zone event.
     *  Default value 1 sec.
     */
    private int zone_interval;
    
    /**
     * Time interval stored in the event list. 
     */
    private int pattern_interval;
    private int pattern_length;
    private boolean useZones = true;
    
    public static void main(String[] args) {
        EventList list = new EventList();
        list.sensorEvent(1);
        list.sensorEvent(2);
        list.sensorEvent(3);
        System.out.println(list);
    }
    
    public EventList() {
        events = new LinkedList<Event>();
//        zone = new LinkedList<Event>();
        this.pattern_interval = Config.patternInterval;
        this.pattern_length = Config.patternLength;
        this.zone_interval = Config.zoneInterval;
        this.useZones = Config.useZones;
    }
    
    public EventList(boolean useZones) {
        this();
        this.useZones = useZones;
    }
    
    public EventList(int zone_interval, int pattern_interval, int pattern_length) {
        this();
        this.zone_interval = zone_interval;
        this.pattern_interval = pattern_interval;
        this.pattern_length = pattern_length;
    }
        
    
    
    /**
     * Add event 
     * @param e
     */
    public void add(Event e) {
        removeOld(e.getTS());
        
        if(useZones && e instanceof SensorEvent)
            determineZone(e);
        else 
            events.add(e);

        while (events.size() > pattern_length)
            events.removeFirst();
    }
        
    /**
     * removes all events if more than pattern interval has passed since the last event
     * also mantains a maximum pattern depth
     */
    private void removeOld(long time) {
        if(events.size() > 0 && time - events.getLast().getTS() > pattern_interval)
            events.clear();

    }
        
    private int currentPatternLength() {
        int count = 0;
        for (Event e : events)
            if (e instanceof SensorEvent)
                count++;
        return count;
    }
    
    private void determineZone(Event e) {
        if (events.size() > 0 && events.getLast().getTS() + zone_interval > e.getTS()) {

            Event last = events.getLast();
            if (last instanceof ZoneEvent) {
                boolean contains = false;
                ZoneEvent z = (ZoneEvent) last;
                for (int id : z.getIDs()) { 
                    if (id == e.getID()) {
                        contains = true;
                        break;
                    }
                }
                if (!contains) {
                    z.addID(e.getID());
                    return;
                }
                        
            } else if (last instanceof SensorEvent){
                if (last.getID() != e.getID()) {
                    events.removeLast();
                    events.addLast(new ZoneEvent(last.getTS(), last.getID(), e.getID()));
                    return;
                }
            }
        }
        events.add(e);
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
    
    /**
     * get events in event list, including detected zone events
     * @return
     */
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
    
    /**
     * get only sensor and zone events
     * @return
     */
    public Event[] getPattern() {
        Event[] pattern = new Event[pattern_length];
        //if current pattern depth is less than pattern depth, fill missing with -1 
        for (int i = 0; i < pattern_length - currentPatternLength(); i++) {
            pattern[i] = new SensorEvent(-1);
        }
        
        Iterator<Event> it = events.iterator();
        for (int i = pattern_length - currentPatternLength(); i < pattern_length; i++) {
            pattern[i] = it.next();
        }
        return pattern;
    }
    
    public Event getLastEvent() {
        if (events.size() > 0)
            return events.getLast();
        
        return null;
    }
	public boolean containsZoneEvent(){
		if(useZones){
			for(Event e : events){
				if(e.getID()>256){
					return true;
				}
			}
		}
		return false;
	}
}
