package devices.door;

public class OpenedState implements LockState {
    private static OpenedState instance;
    private OpenedState() {}

    /**
     * Singleton pattern to ensure that there's only a single instance
     * of <code>OpenedState</code>.
     * @return the only OpenedState instance
     */
    public static OpenedState getInstance() {
        if(instance == null)
            instance = new OpenedState();
        return instance;
    }
    
    @Override
    public LockState lock() {
        System.out.println("Door is opened, cannot lock!");
        return instance; 
    }

    @Override
    public LockState unlock() {
        System.out.println("Door is opened, cannot unlock!");
        return instance;
    }

    @Override
    public LockState open(Door door) {
        System.out.println("Door is already opened!");
        return instance;
    }

    @Override
    public LockState close() {
        System.out.println("Door closed!");
        return ClosedState.getInstance();
    }
    @Override
    public String getState() {
        return "Opened";
    }
}
