package timer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.EventListenerList;

public class Timer implements TimeoutListener {
    

    private Map<Integer, Sleeper> timers;
    private TimeoutListener listener;
    
    public static void main(String[] args) throws Exception{
        Timer t = new Timer();
        t.setTimeout(1, 1000, t);
        t.setTimeout(2, 2000, t);
        t.setTimeout(3, 2000, t);
        Thread.sleep(1000);
        t.setTimeout(3, 2000, t);
    }
    
    public Timer() {
        timers = new HashMap<Integer, Sleeper>();        
    }
    
    public Timer(TimeoutListener l) {
        this.listener = l;
    }
    
    public void setTimeout(int id, long time) {
        setTimeout(id, time, listener);
    }
    
    public void setTimeout(int id, long time, TimeoutListener l) {
        if (timers.containsKey(id))
            timers.get(id).interrupt();
        
        timers.put(id, new Sleeper(id, time, l));
    }
    
    /**
     * set the timeout, only if a timer is already is set for the id, 
     * and the new timeout will end later than the old timeout
     * @param id
     * @param time
     */
    public void updateTimeout(int id, long time, TimeoutListener l) {
       if (!timers.containsKey(id) || !timers.get(id).isAlive())
           return;
       
       if (timers.get(id).getEnd() < System.currentTimeMillis() + time)
           setTimeout(id, time, l);
    }
    
    public void updateTimeout(int id, long time) {
        updateTimeout(id, time, listener);
    }
    
    public void stop(int id) {
        timers.get(id).interrupt();
    }

    @Override
    public void TimeoutEventOccurred(TimeoutEvent event) {
        // TODO Auto-generated method stub
        System.out.println(event.getSource() + ": event detected");
    }
    
}
