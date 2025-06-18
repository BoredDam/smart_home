package devices.door;

public class ClosedState implements LockState {
    private static ClosedState instance;

    private ClosedState() {}

    /**
     * Singleton pattern to ensure that there's only a single instance
     * of <code>ClosedState</code>.
     * @return the only ClosedState instance
     */
    public static ClosedState getInstance() {
        if(instance == null) 
            instance =  new ClosedState();
        return instance;
    }

    @Override
    public LockState lock() {
        System.out.println("Door is now locked!");
        return LockedState.getInstance();
    }

    @Override
    public LockState unlock() {
        System.out.println("Door is already unlocked!");
        return instance;
    }
    
    @Override
    public LockState open(Door door) {
        System.out.println("Door opened!");
        return OpenedState.getInstance();
    }
    
    @Override
    public LockState close() {
        System.out.println("Door is already closed!");
        return instance;
    }
    @Override
    public String getState() {
        return "Closed";
    }
}
