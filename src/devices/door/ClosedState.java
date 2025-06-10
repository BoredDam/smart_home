package devices.door;

public class ClosedState implements LockState {
    static ClosedState instance;

    private ClosedState() {}

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
}
