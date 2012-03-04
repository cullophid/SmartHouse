package events;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import config.Config;

public class EventListTest {

    EventList events;
    SensorEvent[] se;
    SwitchEvent[] sw;
    ZoneEvent z1;
    
    @Before
    public void setUp() throws Exception {
        events = new EventList(true);
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
    
    @Test
    public void testGetEvents() {
        events.add(se[0]);
        events.add(sw[0]);
        
        Event[] expected = {se[0], sw[0]};
        Event[] actual = events.getEvents();
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i]);
        }
    }
    
    @Test
    public void testGetPatternZone() {
        events.add(z1);
        events.getPattern();
//        System.out.println(events);
//        System.out.println(events.getPattern()[6]);
    }
    
    @Test
    public void testGetPattern() {
        for (Event actual : events.getPattern()) {
            assertEquals(-1, actual.getID());
        }
        events.add(se[0]);
        events.add(se[0]);
        events.add(se[0]);
        
        Event[] actuals = events.getPattern();
        for (int i = 0; i < Config.patternLength; i++) {
            if (i < Config.patternLength - 3)
                assertEquals(-1, actuals[i].getID());
            else
                assertEquals(se[0], actuals[i]);                
        }
        
        events.add(se[0]);
        events.add(se[0]);
        events.add(se[0]);
        events.add(se[0]);

        for (Event actual : events.getPattern()) {
            assertEquals(se[0], actual);
        }
        assertEquals(Config.patternLength, events.getPattern().length);
    }
    
    @Test
    public void testCorrelationList() {
        
        se[0] = new SensorEvent(1, 123456781000L);
        se[1] = new SensorEvent(2, 123456781000L);
        se[2] = new SensorEvent(1, 123456789000L);

        events.add(se[0]);
//        System.out.println(events);
        events.add(se[1]);
//        System.out.println(events);
        events.add(se[2]);
//        System.out.println(events);
        
        Event[] actuals = events.getEvents();

//        System.out.println(actuals[0].getTS());
//        System.out.println(actuals[1].getTS());
        
        assertTrue(actuals[0] instanceof ZoneEvent);
        assertEquals(se[2], actuals[1]);

        
    }
    
    
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

}
