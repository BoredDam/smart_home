package debugTools;

public class Environment {
    
    private static float temp;

    Environment() {
        temp = 20;
    }

    public static float getTemp() {
        temp += (-0.5)*Math.random();
        temp = (float) ((int)(temp*100))/100;
        return temp;
    }
}
