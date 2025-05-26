package devices.door;

public class ClosedState implements LockState {
    static ClosedState instance;
    private ClosedState() {}
    public static ClosedState getInstance() {
        if(instance == null) 
            instance =  new ClosedState();
        return instance;
    }
    public LockState lock() {
        System.out.println("Door is now locked!");
        return LockedState.getInstance();
    }
    public LockState unlock() {
        System.out.println("Door is already unlocked!");
        return instance;
    }
    public LockState open() {
        System.out.println("Door opened!");
        return OpenedState.getInstance();
    }
    public LockState close() {
        System.out.println("Door is already closed!");
        return instance;
    }
}
