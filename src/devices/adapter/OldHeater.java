package devices.adapter;

public class OldHeater {
    private boolean state;

    
    public OldHeater() {
    
    }

    public void boot() { state = true; }
    public void shutdown() { state = false; }
}
