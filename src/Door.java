public class Door extends Device {


    enum DoorState {
        OPEN,
        CLOSED,
        LOCKED
    }

    DoorState state;

    DoorState getState() {
        return state;
    }

    void setState(DoorState state) {
        this.state = state;
    }

    boolean isOpen() {
        return state == DoorState.OPEN;
    }

    boolean isClosed() {
        return state == DoorState.CLOSED;
    }

    boolean isLocked() {
        return state == DoorState.LOCKED;
    }

    @Override
    public void performAction(String action) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'performAction'");
    }

    @Override
    public void update(Event event) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
    

}

