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
    
}
