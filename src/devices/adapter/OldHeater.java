package devices.adapter;

public class OldHeater {
    private boolean state;

    public OldHeater() {
        this.state = false;
    }

    public void boot() { 
        state = true;
    }

    public void shutdown() { 
        state = false; 
    }

    public boolean getState() {
        return state;
    }
    
}
