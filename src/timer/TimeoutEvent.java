package timer;

import java.util.EventObject;

public class TimeoutEvent extends EventObject {

    public TimeoutEvent(int id) {
        super(id);
    }
    
}
