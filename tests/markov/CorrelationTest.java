package markov;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class CorrelationTest {

    Correlation correlation = new Correlation();
    @Before
    public void setUp() throws Exception {
        
    }

    @Test
    public void test() {
//        System.out.print("Switches: ");
//        for (int sw : correlation.getSwitches()) {
//            System.out.print(" " + sw);
//        }
//        System.out.println();
//
//        System.out.print("Sensors: ");
//        for (int se : correlation.getSensors()) {
//            System.out.print(" " + se);
//        }
//        System.out.println();
        System.out.println(correlation);
}
    
    @Test
    public void incrementSwitchCount() {
        Map<Integer, Integer> switch_count = new HashMap<Integer, Integer>();
        int id = 42, expected, actual;        
        
        Correlation.incrementSwitchCount(switch_count, id);
        expected = 1;
        actual = switch_count.get(id);
        assertEquals(expected, actual);

        Correlation.incrementSwitchCount(switch_count, id);
        expected = 2;
        actual = switch_count.get(id);
        assertEquals(expected, actual);

        Correlation.incrementSwitchCount(switch_count, id);
        expected = 3;
        actual = switch_count.get(id);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testIncrementSensorCount() {
        Map<Integer, Map<Integer, Integer>> sensor_count = new HashMap<Integer, Map<Integer, Integer>>();
        int a = 3, b = 7, c = 42, expected, actual;
        
        Correlation.incrementSensorCount(sensor_count, a, b);
        expected = 1;
        actual = sensor_count.get(a).get(b);
        assertEquals(expected, actual);

        Correlation.incrementSensorCount(sensor_count, a, c);
        actual = sensor_count.get(a).get(b);
        assertEquals(expected, actual);
        actual = sensor_count.get(a).get(c);
        assertEquals(expected, actual);

        Correlation.incrementSensorCount(sensor_count, a, b);
        expected = 2;
        actual = sensor_count.get(a).get(b);
        assertEquals(expected, actual);
        
        Correlation.incrementSensorCount(sensor_count, a, b);
        expected = 3;
        actual = sensor_count.get(a).get(b);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testStoreCorrelation() {
        correlation.storeCorrelation(-1, -2, 0.3f);
    }
    
}
