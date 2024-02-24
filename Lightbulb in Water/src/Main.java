import java.util.ArrayList;

public class Main {
    private ArrayList<Statistics> statistics = new ArrayList<Statistics>();
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        double alpha = 0;
        double beta = 0;
        double sumRSquare = 0;
        double deltaTime = 1;
        final double SUBINT = 3600 * deltaTime; // For 3600 seconds
        final double INITTEMP = 294.65; // Initial water temp 294.65 K

        for (beta = 0.0; beta <= Math.pow(1, -7); beta += Math.pow(1, -16) ) {
            for (alpha = 0.0; alpha <= 1.0/3.0; alpha += Math.pow(1, -7)) {
                double firstMidTime = (deltaTime / 2.0);
                double sumTemp = INITTEMP; // Initialize starting tempterature
                for (double time = firstMidTime; time < SUBINT; time += deltaTime) {
                    double deltaTemp = diffFunc(alpha, beta, sumTemp);
                    sumTemp += deltaTemp;
                }
            }
        }
    }

    static double diffFunc(double alpha, double beta, double temp) {
        final double MASSBEAKER = 0.11851; // Mass of beaker (kg)
        final short HEATCAPBEAKER = 830; // Heat Capacity of Beaker
        final double HEATWATER = 0.09979; // Mass of water initially (kg)
        final short HEATCAPWATER = 4186; // Heat Capacity of Water

        final double PHO = Math.pow(MASSBEAKER * HEATCAPBEAKER + HEATWATER * HEATCAPWATER, -1);

        final byte VOLT = 10;
        final double RESIST = 10.8;
        final double OUTSIDE = 294.95;

        // (1 / PHO) * (wattage - (alpha * conduction + beta * radiation) )
        double value = PHO * ( (Math.pow(VOLT, 2) / RESIST) - (alpha * (temp - OUTSIDE) + beta * (Math.pow(temp, 4) - Math.pow(OUTSIDE, 4) ) ) );
        return value;
    }
}
