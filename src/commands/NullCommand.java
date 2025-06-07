package commands;

public class NullCommand implements Command {
    private static NullCommand instance;

    private NullCommand() {}

    public static NullCommand getInstance() {
        if(instance == null)
            instance = new NullCommand();
        return instance;
    }
    @Override
    public void run() {}
}
