package devices;

public interface LockState {
    LockState lock();
    LockState unlock();
    LockState open();
    LockState close();
}
