package events;

public class SwitchEvent extends Event {
    
    protected boolean cmd;
    
    public SwitchEvent(int id, long ts, boolean cmd) {
        super(id, ts);
        this.cmd = cmd;
    }
    
    public SwitchEvent(int id, boolean cmd) {
        super(id);
        this.cmd = cmd;
    }

    public boolean getCmd() {
        return cmd;
    }
    
    public String toString() {
        return tsString() + " Switch event " + this.id + 
                ((cmd) ? " on" : " off");
    }
    
    public boolean equals(Object o) {        
        if (!super.equals(o))
            return false;

        if (!(o instanceof SwitchEvent))
            return false;        

        SwitchEvent e = (SwitchEvent) o;
        if (e.cmd != this.cmd)
            return false;
        
        return true;
    }
}
