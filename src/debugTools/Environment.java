package debugTools;

public class Environment {
    private static float temp;

    public static float getTemp() {
        temp = (float) ((int) (Math.random()* 800))/100 + 18;
        return temp;
    }
}
