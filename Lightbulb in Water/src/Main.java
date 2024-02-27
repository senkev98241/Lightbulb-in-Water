import java.util.ArrayList;

public class Main {
    // private ArrayList<Statistics> statistics = new ArrayList<Statistics>();
    static ArrayList<Double> tempVtime = new ArrayList<Double>();
    static ArrayList<Double> data = new ArrayList<Double>(62);
    public static void main(String[] args) throws Exception {
        data = Data.extractData();
        
        if (1 == 0) {
        // Start move to optimizier
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
        for (beta = 0.0000000001; beta <= 0.000000001; beta += 0.00000000001 ) { // Increase percision to 16 decimals later
            
            // Optimization of arbitrary coefficient for conduction Alpha
            for (alpha = 0.2; alpha <= 1.0/3.0; alpha += 0.001) { // Increase percision to 7 decimals later
                // Initialize variables
                double sumTemp = INITTEMP; // Initialize starting tempterature
                double deltaTemp = 0; // For first instance only at zero
                double prevDelta; // Prepare for adjustments needed to trapezoidal
                // alpha = 0;

                // Perform trapezoid reimann sum
                for (double time = 0; time <= SUBINT + deltaTime/2.0 ; time += deltaTime) {
                    // Add supposed data point at minute timestamps
                    if ( (int) (time % 60) == 0) {
                        tempVtime.set( (int) (time / 60), sumTemp); // Set minute data
                        
                        // Debugging only
                        // System.out.println("------------------");
                        // System.out.println(tempVtime.get((int) (time / 60)));
                        // System.out.println(tempVtime.indexOf(sumTemp));
                    }
                    
                    // Get lost deltaTemp, make new Delta temp, linear approximate
                    prevDelta = deltaTemp / 2.0 ; // Add lost deltaTemp to pseudotrapezoid
                    deltaTemp = (diffFunc(alpha, beta, sumTemp + prevDelta)) * deltaTime; // Overestimated Trapezoidal Reimann
                    sumTemp = (sumTemp + prevDelta + sumTemp + prevDelta + deltaTemp) / 2.0; // Linear approzimation from trapezoidal reimann
                    
                    // Only to verify ending data
                    if (time >= 3600) {
                        // System.out.println(time);
                        // System.out.println(sumTemp);
                    }
                }

                sumRSquare = 0; // Reset sum of residuals rquared
                short i = 0; // Reset iterations to prepare calculation of sum of r^2
                for (Double dataPoint : data) { // Calcluate sum of r^2
                    sumRSquare += Math.pow(dataPoint - tempVtime.get(i), 2);
                    i += 1;
                }
                // System.out.println("Alpha is " + alpha + "\t + Beta is " + beta + "\t sumTemp is " + sumTemp);
                // System.out.println(sumRSquare);
                // statistics.add(new Statistics(alpha, beta, sumRSquare));
                // break;
            } 
            // break;
        }
        // End mvoe to optimizer
        }
    
    }
    // Comment out later Moved to multithreadable interface
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
