package devices.door;

public class LockedState implements LockState {
    static LockedState instance;

    private LockedState() {}

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
    public LockState open() {
        System.out.println("Intrusion detected!");
        return OpenedState.getInstance();
    }
    
    @Override
    public LockState close() {
        System.out.println("Door is already closed and locked!");
        return instance;
    }
}
