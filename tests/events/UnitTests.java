package events;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import config.Config;

public class UnitTests {

    EventList events;
    SensorEvent[] se;
    SwitchEvent[] sw;
    ZoneEvent z1;
    
    @Before
    public void setUp() throws Exception {
        events = new EventList(500, 10000, 7);
        se = new SensorEvent[]{new SensorEvent(1), new SensorEvent(2), new SensorEvent(3)};
        sw = new SwitchEvent[]{new SwitchEvent(11, true), new SwitchEvent(12, false)};
        z1 = new ZoneEvent(0L, 20, 21);
    }

    /**
     * test that the single integer id for zone events are the no matter, no matter the order the ids are added to the zone event.
     */
    @Test
    public void zoneIdConsistency() {
        int actual, expected = new ZoneEvent(0L, 1, 2, 3).getID();
        
        ZoneEvent z = new ZoneEvent();
        z.addID(1);
        z.addID(2);
        z.addID(3);
        actual = z.getID();
        assertEquals(expected, actual);

        z = new ZoneEvent();
        z.addID(2);
        z.addID(3);
        z.addID(1);
        actual = z.getID();
        assertEquals(expected, actual);
        
        z = new ZoneEvent();
        z.addID(3);
        z.addID(1);
        z.addID(2);
        actual = z.getID();
        assertEquals(expected, actual);
    }

    /**
     * test the equals method for sensor events
     */
    @Test
    public void testEquals() {
        SensorEvent s1 = new SensorEvent(1, 123456789);
        SensorEvent s2 = new SensorEvent(1, 123456789);        
        assertEquals(s1, s2);
        SensorEvent s3 = new SensorEvent(3, 123456789);
        assertTrue(!s1.equals(s3));
    }
    
    /**
     * basic get events test
     * the same sensor event 3 times, then one switch event 
     */
    @Test
    public void testGetEvents() {
        events.add(se[0]);
        events.add(se[0]);
        events.add(se[0]);
        events.add(sw[0]);
        
        Event[] expected = {se[0], se[0], se[0], sw[0]};
        Event[] actual = events.getEvents();
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i]);
        }
    }
    
    /**
     * tests the ordering of sensor events going into an eventlist
     * adds 7 sensor events to event list, 
     * ids are sequencial, 
     * and timestamps are 1000ms appart.
     * verifies the ordering of the entire list, after each event is added.
     * also tests the getLastEvent method 
     */
    @Test
    public void testEventOrdering() {
        Event expected, actual;
        Event[] e = new Event[7];
        for (int i = 0; i < 7; i++) {
            e[i] = new SensorEvent(i, 1000*i);
            events.add(e[i]);
            
            expected = e[i];
            actual = events.getLastEvent();
            assertEquals(expected, actual);
            
            for (int j = 0; j <= i; j++) {
                expected = e[j];
                actual = events.getEvents()[j];
                assertEquals(expected, actual);
            }
        }
        
        
    }
        
    /**
     * test getPattern, to make sure the array has fixed length, 
     * independant of events in eventlist, 
     * and that the array is properly prefixed with -1 
     */
    @Test
    public void testGetPattern() {
        assertEquals(7, events.getPattern().length);
        for (Event actual : events.getPattern()) {
            assertEquals(-1, actual.getID());
        }
        events.add(se[0]);
        events.add(se[0]);
        events.add(se[0]);
        
        Event[] actuals = events.getPattern();
        for (int i = 0; i < Config.patternLength; i++) {
            if (i < 4)
                assertEquals(-1, actuals[i].getID());
            else
                assertEquals(se[0], actuals[i]);                
        }
        
        //adds 5 more events, for a total of 8
        events.add(se[0]);
        events.add(se[0]);
        events.add(se[0]);
        events.add(se[0]);
        events.add(se[0]);

        for (Event actual : events.getPattern()) {
            assertEquals(se[0], actual);
        }
        assertEquals(7, events.getPattern().length);
    }
    
    /**
     * test of zone events:
     * 1 - eventlist is able to detect zone events, if zones are enabled. 
     * 2 -  zone events are not produced, if zones are disabled.
     */
    @Test
    public void testZoneDetection() {
        
        se[0] = new SensorEvent(1, 123456781000L);
        se[1] = new SensorEvent(2, 123456781000L);
        se[2] = new SensorEvent(1, 123456789000L);

        events.add(se[0]);
        events.add(se[1]);
        events.add(se[2]);
        
        Event[] actuals = events.getEvents();

        assertTrue(actuals[0] instanceof ZoneEvent);        
        assertTrue(actuals[0].compareID(se[0].getID()));
        assertTrue(actuals[0].compareID(se[1].getID()));
        assertEquals(se[2], actuals[1]);

        //repeats test without zone detection
        events = new EventList(0, 10000, 7);
        events.add(se[0]);
        events.add(se[1]);
        events.add(se[2]);
        
        actuals = events.getEvents();

        for (int i = 0; i < 3; i++) {
            assertEquals(se[i], actuals[i]);
        }

    }
    
    
    /**
     * tests the removal of events "pattern interval" older than the last event 
     */
    @Test
    public void testPurgeOld() {
        se[0] = new SensorEvent(1, 0L);
        se[1] = new SensorEvent(2, 123456781000L);
        
        events.add(se[0]);
        events.add(se[1]);
        
        assertEquals(1, events.getEvents().length);

        SensorEvent expected = se[1];
        Event actual = events.getEvents()[0];
        assertEquals(expected, actual);
    }
    
    /** 
     * tests that event list maintains the correct number of events, 
     * using various pattern length configurations (2, 3 and 7)
     */
    @Test
    public void testPatternLength() {
        EventList e2 = new EventList(500, 10000, 2);
        EventList e3 = new EventList(500, 10000, 3);
        EventList e7 = new EventList(500, 10000, 7);
        EventList[] es = new EventList[]{e2, e3, e7};
        
        int actual, expected = 0;

        //makes sure the length is initially zero
        for (EventList e : es) {
            actual = e.getEvents().length;
            assertEquals(expected, actual);            
        }
        
        //adds an event to each list, and verifies the length to be 1
        expected = 1;
        for (EventList e : es) {
            e.add(se[0]);
            actual = e.getEvents().length;
            assertEquals(expected, actual);
        }

        //adds the event a 2nd time, and verifies the length to be 2 
        expected = 2;
        for (EventList e : es) {
            e.add(se[0]);
            actual = e.getEvents().length;
            assertEquals(expected, actual);
        }
        
        //adds 8 more sensor events, so all lists are full
        for (EventList e : es) {
            for (int i = 0; i < 8; i++)
                e.add(se[0]);
        }
        
        //verifies that all lists are at their max capacity
        assertEquals(2, e2.getEvents().length);
        assertEquals(3, e3.getEvents().length);
        assertEquals(7, e7.getEvents().length);
        
}

}
