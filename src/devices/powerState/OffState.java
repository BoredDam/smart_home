package devices.powerState;

import commands.Command;
import commands.generalPurposeCommands.TurnOnCommand;

public class OffState implements PowerState {
    static OffState instance;
    private OffState() {}
    
    /**
     * Singleton pattern to ensure that there's only a single instance
     * of <code>OffState</code>.
     * @return the only OffState instance
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
