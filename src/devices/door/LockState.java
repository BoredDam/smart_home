package devices.door;

/*
 * LockState
 * 
 * is an interface for objects that wraps the logic behind the behaviour of
 * the object "Door". When "Door" is Closed, Open or Locked, 
 * the methods behave differently.
 * 
 * this interface is currently implemented by 
 * "LockedState", "ClosedState" and "OpenState"
 * 
 * this is a clear application of the "State" design pattern. 
 * 
 */


public interface LockState {
    LockState lock();
    LockState unlock();
    LockState open();
    LockState close();
}
