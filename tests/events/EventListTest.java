package events;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EventListTest {

    EventList events;
    SensorEvent[] se;
    SwitchEvent[] sw;
    
    @Before
    public void setUp() throws Exception {
        events = new EventList();
        se = new SensorEvent[]{new SensorEvent(1), new SensorEvent(2), new SensorEvent(3)};
        sw = new SwitchEvent[]{new SwitchEvent(11, true), new SwitchEvent(12, false)};
    }

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
        events.add(sw[1]);
        
        Event[] expected = {se[0], sw[0], sw[1]};
        Event[] actual = events.getEvents();
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i]);
        }
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
        for (int i = 0; i < 7; i++) {
            if (i < 4)
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
        assertEquals(7, events.getPattern().length);
    }
    
    @Test
    public void testCorrelationList() {
        
        se[0] = new SensorEvent(1, 123456781000L);
        se[1] = new SensorEvent(2, 123456782000L);
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
        
        assertEquals(se[0], actuals[0]);
        assertTrue(actuals[1] instanceof ZoneEvent);
        assertEquals(se[1], actuals[2]);
        assertEquals(se[2], actuals[3]);

        
    }

}
