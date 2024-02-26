import java.util.ArrayList;

public class Main {
    private ArrayList<Statistics> statistics = new ArrayList<Statistics>();
    static ArrayList<Double> tempVtime = new ArrayList<Double>();
    static ArrayList<Double> data = new ArrayList<Double>(62);
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        data = Data.extractData();
        for (int i = 0; i <= 61; i += 1) {
            tempVtime.add(0.0);
        }

        double alpha = 0;
        double beta = 0;
        double sumRSquare = 0;
        double deltaTime = 1;
        final double SUBINT = 3660 * deltaTime; // For 3600 seconds
        final double INITTEMP = 294.65; // Initial water temp 294.65 K
        
        // Optimization of arbitary coefficient for radiation Beta
        for (beta = 0.0; beta <= 0.0000001; beta += 0.0000000000000001 ) {
            beta = 0.0000000001;
            // Optimization of arbitrary coefficient for conduction Alpha
            for (alpha = 0.0; alpha <= 1.0/3.0; alpha += 0.0000001) {
                double sumTemp = INITTEMP; // Initialize starting tempterature
                alpha = 0.25;
                // Perform trapezoid reimann sum
                for (double time = 0; time <= SUBINT; time += deltaTime) {
                    // Add supposed data point at minute timestamps
                    if ( (int) (time % 60) == 0) {
                        // tempVtime.set( (int) (time / 60), sumTemp);
                        // System.out.println("------------------");
                        // System.out.println(tempVtime.get((int) (time / 60)));
                    }
                    System.out.println(time);
                    double deltaTemp = (diffFunc(alpha, beta, sumTemp));
                    System.out.println(deltaTemp);
                    sumTemp = (sumTemp + sumTemp + deltaTemp) / 2.0; // Trapezoidal reimann
                    System.out.println(sumTemp);
                }

                sumRSquare = 0;
                short i = 0;
                for (Double dataPoint : data) {
                    sumRSquare += Math.pow(dataPoint - tempVtime.get(i), 2);
                    i += 1;
                }
                System.out.println("Alpha is " + alpha + "\t + Beta is " + beta + "\t sumTemp is " + sumTemp);
                break;
            }
            break;
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
