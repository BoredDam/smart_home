package devices.door;

public class OpenedState implements LockState {
    static OpenedState instance;
    private OpenedState() {}
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
}
