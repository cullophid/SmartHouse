package timer;

import javax.swing.event.EventListenerList;

public class Sleeper extends Thread {
    
    private int id;
    private long time;
    private long end;
    private TimeoutListener listener;
    
    public static void main(String args[]) throws InterruptedException {
        System.out.println("here we go...");
        new Sleeper(1, 1000);
        new Sleeper(2, 2000);
        new Sleeper(2, 2000);
        new Sleeper(3, 3000).join();
        System.out.println("all done");
    }
    
    public Sleeper(int id, long time) {
        this.id = id;
        this.time = time;
        this.end = System.currentTimeMillis() + time;
        this.start();
    }
    
    public Sleeper(int id, long time, TimeoutListener l) {
        this(id, time);
        this.listener = l;
    }
    
    public long getEnd() {
        return end;
    }
    
    public void run() {
        try {
            sleep(time);
            System.out.println(id + ": done");
            
            if (listener != null) {
                listener.TimeoutEventOccurred(new TimeoutEvent(id));
                System.out.println(id + ": event fired");
            }
        } catch (InterruptedException ex) {
            return;
        }
    }
}
