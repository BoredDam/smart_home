package main;

import commands.*;
import commands.lightCommand.*;
import devices.*;

public class Client {
    public static void main(String[] args) {
        Light luce = new Light("a");
        Command accendi = new TurnOnLightCommand(luce);
        accendi.execute();
    }
}
