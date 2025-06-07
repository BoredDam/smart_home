package devices.powerState;

import commands.Command;
import commands.generalPurposeCommands.TurnOnCommand;

public class OffState implements PowerState {
    static OffState instance;
    private OffState() {}
    
    /**
     * This method checks if an instance of <code>OffState</code> was already instantiated, and if not, it will instantiate it. 
     * 
     * Then, whether it was just instantiated or not, it will return the instance. 
     * This is a clear implementation of the Singleton Design Pattern by the GoF.
     * 
     * @return the <code>OffState</code> instance;
     */
    public static OffState getInstance() {
        if(instance == null) {
            instance = new OffState();
        }
        return instance;
    }

    @Override
    public PowerState turnOff() {
        System.out.println("Already off!");
        return instance;
    }

    @Override
    public PowerState turnOn() {
        System.out.println("Turned on!");
        return OnState.getInstance();
    }

    @Override
    public boolean isOn() {
        return false;
    }
    
    @Override
    public boolean isOff() {
        return true;
    }

    @Override
    public void runCommand(Command cmd) {
        if(cmd instanceof TurnOnCommand toc) {
            toc.run();
        }
    } 
}
