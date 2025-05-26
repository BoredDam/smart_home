package devices.door;

public class OpenedState implements LockState {
    static OpenedState instance;
    private OpenedState() {}
    public static OpenedState getInstance() {
        if(instance == null)
            instance = new OpenedState();
        return instance;
    }
    public LockState lock() {
        System.out.println("Door is opened, cannot lock!");
        return instance; 
    };
    public LockState unlock() {
        System.out.println("Door is opened, cannot unlock!");
        return instance;
    }
    public LockState open() {
        System.out.println("Door is already opened!");
        return instance;
    }
    public LockState close() {
        System.out.println("Door closed!");
        return ClosedState.getInstance();
    }
}
