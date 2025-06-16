package devices.adapter;

/**
 * Represents an old heater device that can be turned on or off.
 * The old heater supports only booting and shutting down operations, and an adapter is needed 
 * to integrate it into the smart home system.
 */
public class OldHeater {
    
    private boolean state;

    public OldHeater() {
        this.state = false;
    }

    /**
     * Boots the old heater, turning it on.
     */
    public void boot() { 
        state = true;
    }

    /**
     * Shuts down the old heater, turning it off.
     */
    public void shutdown() { 
        state = false; 
    }

    /**
     * Checks if the heater is currently on.
     * @return true if the heater is on, false otherwise.
     */
    public boolean getState() {
        return state;
    }
    
}
