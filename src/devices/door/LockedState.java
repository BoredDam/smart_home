package devices.door;

public class LockedState implements LockState {
    private static LockedState instance;

    private LockedState() {}

    /**
     * Singleton pattern to ensure that there's only a single instance
     * of <code>LockedState</code>.
     * @return the only LockedState instance
     */
    public static LockedState getInstance() {
        if(instance == null) 
            instance = new LockedState();
        return instance;
    }

    @Override
    public LockState lock() {
        System.out.println("Door is already locked!");
        return instance; 
    }

    @Override
    public LockState unlock() {
        System.out.println("Door is now unlocked!");
        return ClosedState.getInstance();
    }

    @Override
    public LockState open(Door door) {
        System.out.println("Intrusion detected!");
        door.notifyObserver();
        return OpenedState.getInstance();
    }
    
    @Override
    public LockState close() {
        System.out.println("Door is already closed and locked!");
        return instance;
    }
}
