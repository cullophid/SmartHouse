package events;

public class SensorEvent extends Event {

    public SensorEvent(int id, long ts) {
        super(id, ts);
    }
    
    public SensorEvent(int id) {
        super(id);
    }
    
    public String toString() {
        return tsString() + " Sensor event " + this.id;
    }
    
    public boolean equals(Object o) {
        if (!super.equals(o))
            return false;

        if (!(o instanceof SensorEvent))
            return false;
        
        return true;
    }
    
}
