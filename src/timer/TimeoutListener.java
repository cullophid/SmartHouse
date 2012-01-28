package timer;

import java.util.EventListener;

public interface TimeoutListener extends EventListener {
    
    public void TimeoutEventOccurred(TimeoutEvent event);

}
