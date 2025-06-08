package debugTools;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Environment {
    private static float temp;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static float getTemp() {
        temp = (float) ((int) (Math.random()* 800))/100 + 18;
        return temp;
    }
}
