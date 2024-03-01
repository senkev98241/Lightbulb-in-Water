import java.util.ArrayList;

public class Optimizer implements Runnable {
    
    private double initAlpha;
    private double endAlpha;
    private double initBeta;
    private double endBeta;

    static ArrayList<Double> tempVtime = new ArrayList<Double>();
    static ArrayList<Double> data = Data.data;
    // private ArrayList<Statistics> statistics = new ArrayList<Statistics>();

    public Optimizer(double recInitAlpha, double recEndAlpha, double recInitBeta, double recEndBeta) {
        this.initAlpha = recInitAlpha;
        this.endAlpha = recEndAlpha;
        this.initBeta = recInitBeta;
        this.endBeta = recEndBeta;
    }
    public void run() {

    }
    
    public void pseudoTrapezoid() {
        for (int i = 0; i <= 61; i += 1) {
            tempVtime.add(0.0);
        }

        double alpha = this.initAlpha;
        double beta = this.initBeta;
        double sumRSquare = 0;
        double deltaTime = 1;
        final double SUBINT = 3660 * deltaTime; // For 3600 seconds
        final double INITTEMP = 294.65; // Initial water temp 294.65 K
        
        double deltaBeta = 0.00000000001;
        // Optimization of arbitary coefficient for radiation Beta
        for (beta = initBeta; beta <= endBeta + deltaBeta / (byte) 2; beta += deltaBeta /* Should be 10^-11 */) {// (beta = 0.0000000001; beta <= 0.000000001; beta += 0.00000000001 ) { // Increase percision to 16 decimals later
            
            double deltaAlpha = 0.001;
            // Optimization of arbitrary coefficient for conduction Alpha
            for (alpha = initAlpha; alpha <= endAlpha + deltaAlpha / 2; alpha += deltaAlpha) {// (alpha = 0.2; alpha <= 1.0/3.0; alpha += 0.001) { // Increase percision to 7 decimals later
                // Initialize variables
                double sumTemp = INITTEMP; // Initialize starting tempterature
                double deltaTemp = 0; // For first instance only at zero
                double prevDelta; // Prepare for adjustments needed sto trapezoidal
                // alpha = 0;

                // Perform trapezoid reimann sum
                for (double time = 0; time <= SUBINT + deltaTime / 2; time += deltaTime) {
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
                    deltaTemp = (DiffFunc.diffFunc(alpha, beta, sumTemp + prevDelta)) * deltaTime; // Overestimated Trapezoidal Reimann
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
                System.out.println("Alpha is " + alpha + "\t + Beta is " + beta + "\t sumTemp is " + sumTemp + "\t sumR is " + sumRSquare);
                // System.out.println(sumRSquare);
                Statistics.statistics.add(new Statistics(alpha, beta, sumRSquare, "PsedoTrapez"));
                // break;
            } 
            // break;
        }
    }

    public void midEuler() {
        for (int i = 0; i <= 61; i += 1) {
            tempVtime.add(0.0);
        }

        double alpha = this.initAlpha;
        double beta = this.initBeta;
        double sumRSquare = 0;
        double deltaTime = 1;
        final double SUBINT = 3660 * deltaTime; // For 3600 seconds
        final double INITTEMP = 294.65; // Initial water temp 294.65 K
        
        double deltaBeta = 0.00000000001;
        // Optimization of arbitary coefficient for radiation Beta
        for (beta = initBeta; beta <= endBeta + deltaBeta / (byte) 2; beta += deltaBeta /* Should be 10^-11 */) {// (beta = 0.0000000001; beta <= 0.000000001; beta += 0.00000000001 ) { // Increase percision to 16 decimals later
            
            double deltaAlpha = 0.001;
            // Optimization of arbitrary coefficient for conduction Alpha
            for (alpha = initAlpha; alpha <= endAlpha + deltaAlpha / 2; alpha += deltaAlpha) {// (alpha = 0.2; alpha <= 1.0/3.0; alpha += 0.001) { // Increase percision to 7 decimals later
                // Initialize variables
                double sumTemp = INITTEMP; // Initialize starting tempterature
                double deltaTemp = 0; // For first instance only at zero
                double preDelta; // Prepare for adjustments needed sto trapezoidal
                // alpha = 0;

                // Perform trapezoid reimann sum
                for (double time = 0; time <= SUBINT + deltaTime / 2; time += deltaTime) {
                    // Add supposed data point at minute timestamps
                    if ( (int) (time % 60) == 0) {
                        tempVtime.set( (int) (time / 60), sumTemp); // Set minute data
                        
                        // Debugging only
                        // System.out.println("------------------");
                        // System.out.println(tempVtime.get((int) (time / 60)));
                        // System.out.println(tempVtime.indexOf(sumTemp));
                    }
                    
                    // Get lost deltaTemp, make new Delta temp, linear approximate
                    preDelta = (DiffFunc.diffFunc(alpha, beta, sumTemp)) * deltaTime ; // Add lost deltaTemp to pseudotrapezoid
                    deltaTemp = preDelta / 2.0; // Overestimated Trapezoidal Reimann
                    sumTemp = (sumTemp + deltaTemp); // Linear approzimation from trapezoidal reimann
                    
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
                System.out.println("Alpha is " + alpha + "\t + Beta is " + beta + "\t sumTemp is " + sumTemp + "\t sumR is " + sumRSquare);
                // System.out.println(sumRSquare);
                Statistics.statistics.add(new Statistics(alpha, beta, sumRSquare, "Midpoint Euler"));
                // break;
            } 
            // break;
        }
    }

    public void rungKuttaOrderTwo() {
        for (int i = 0; i <= 61; i += 1) {
            tempVtime.add(0.0);
        }

        double alpha = this.initAlpha;
        double beta = this.initBeta;
        double sumRSquare = 0;
        double deltaTime = 1;
        final double SUBINT = 3660 * deltaTime; // For 3600 seconds
        final double INITTEMP = 294.65; // Initial water temp 294.65 K
        
        double deltaBeta = 0.00000000001;
        // Optimization of arbitary coefficient for radiation Beta
        for (beta = initBeta; beta <= endBeta + deltaBeta / (byte) 2; beta += deltaBeta /* Should be 10^-11 */) {// (beta = 0.0000000001; beta <= 0.000000001; beta += 0.00000000001 ) { // Increase percision to 16 decimals later
            
            double deltaAlpha = 0.001;
            // Optimization of arbitrary coefficient for conduction Alpha
            for (alpha = initAlpha; alpha <= endAlpha + deltaAlpha / 2; alpha += deltaAlpha) {// (alpha = 0.2; alpha <= 1.0/3.0; alpha += 0.001) { // Increase percision to 7 decimals later
                // Initialize variables
                double sumTemp = INITTEMP; // Initialize starting tempterature
                double deltaTemp = 0; // For first instance only at zero
                double preDelta; // Prepare for adjustments needed sto trapezoidal
                // alpha = 0;

                // Perform Second-Order Runge-Kutta
                // k_1 = h * f( x_n, y_n )
                // k_2 = h * f( x_n + 0.5 * h, y_n + 0.5 * k_1)
                // y_(n+1) = y_n + k_2 + O(h^3)
                for (double time = 0; time <= SUBINT + deltaTime / 2; time += deltaTime) {
                    // Add supposed data point at minute timestamps
                    if ( (int) (time % 60) == 0) {
                        tempVtime.set( (int) (time / 60), sumTemp); // Set minute data
                        
                        // Debugging only
                        // System.out.println("------------------");
                        // System.out.println(tempVtime.get((int) (time / 60)));
                        // System.out.println(tempVtime.indexOf(sumTemp));
                    }
                    // Second-Order Runge-Katta
                    preDelta = deltaTime * (DiffFunc.diffFunc(alpha, beta, sumTemp));
                    deltaTemp = deltaTime * (DiffFunc.diffFunc(alpha, beta, sumTemp + (0.5) * preDelta)); // Overestimated Trapezoidal Reimann
                    sumTemp = (sumTemp + deltaTemp);
                    
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
                System.out.println("Alpha is " + alpha + "\t + Beta is " + beta + "\t sumTemp is " + sumTemp + "\t sumR is " + sumRSquare);
                // System.out.println(sumRSquare);
                Statistics.statistics.add(new Statistics(alpha, beta, sumRSquare, "2ndOrderRungeKutta"));
                // break;
            } 
            // break;
        }
    }

    public void rungKuttaOrderFour() {
        for (int i = 0; i <= 61; i += 1) {
            tempVtime.add(0.0);
        }

        double alpha = this.initAlpha;
        double beta = this.initBeta;
        double sumRSquare = 0;
        double deltaTime = 1;
        final double SUBINT = 3660 * deltaTime; // For 3600 seconds
        final double INITTEMP = 294.65; // Initial water temp 294.65 K
        
        double deltaBeta = 0.00000000001;
        // Optimization of arbitary coefficient for radiation Beta
        for (beta = initBeta; beta <= endBeta + deltaBeta / (byte) 2; beta += deltaBeta /* Should be 10^-11 */) {// (beta = 0.0000000001; beta <= 0.000000001; beta += 0.00000000001 ) { // Increase percision to 16 decimals later
            
            double deltaAlpha = 0.001;
            // Optimization of arbitrary coefficient for conduction Alpha
            for (alpha = initAlpha; alpha <= endAlpha + deltaAlpha / 2; alpha += deltaAlpha) {// (alpha = 0.2; alpha <= 1.0/3.0; alpha += 0.001) { // Increase percision to 7 decimals later
                // Initialize variables
                double sumTemp = INITTEMP; // Initialize starting tempterature
                double deltaTemp = 0; // For first instance only at zero
                double preDelta; // Prepare for adjustments needed sto trapezoidal
                // alpha = 0;

                // Perform Second-Order Runge-Kutta
                // k_1 = h * f( x_n, y_n )
                // k_2 = h * f( x_n + 0.5 * h, y_n + 0.5 * k_1)
                // y_(n+1) = y_n + k_2 + O(h^3)
                for (double time = 0; time <= SUBINT + deltaTime / 2; time += deltaTime) {
                    // Add supposed data point at minute timestamps
                    if ( (int) (time % 60) == 0) {
                        tempVtime.set( (int) (time / 60), sumTemp); // Set minute data
                        
                        // Debugging only
                        // System.out.println("------------------");
                        // System.out.println(tempVtime.get((int) (time / 60)));
                        // System.out.println(tempVtime.indexOf(sumTemp));
                    }
                    // Second-Order Runge-Katta
                    double k_1 = deltaTime * (DiffFunc.diffFunc(alpha, beta, sumTemp));
                    double k_2 = deltaTime * (DiffFunc.diffFunc(alpha, beta, sumTemp + (deltaTime / 2.0) * k_1));
                    double k_3 = deltaTime * (DiffFunc.diffFunc(alpha, beta, sumTemp + (deltaTime / 2.0) * k_2));
                    double k_4 = deltaTime * (DiffFunc.diffFunc(alpha, beta, sumTemp + deltaTime * k_3));
                    double deltaRungeKutta = k_1 + k_2 + k_3 + k_4;
                    sumTemp = (sumTemp + deltaRungeKutta);
                    
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
                System.out.println("Alpha is " + alpha + "\t + Beta is " + beta + "\t sumTemp is " + sumTemp + "\t sumR is " + sumRSquare);
                // System.out.println(sumRSquare);
                Statistics.statistics.add(new Statistics(alpha, beta, sumRSquare, "4thOrderRungeKutta"));
                // break;
            } 
            // break;
        }
    }
    // Moved to won class
    // static double diffFunc(double alpha, double beta, double temp) {
    //     final double MASSBEAKER = 0.11851; // Mass of beaker (kg)
    //     final short HEATCAPBEAKER = 830; // Heat Capacity of Beaker
    //     final double HEATWATER = 0.09979; // Mass of water initially (kg)
    //     final short HEATCAPWATER = 4186; // Heat Capacity of Water

    //     final double PHO = Math.pow(MASSBEAKER * HEATCAPBEAKER + HEATWATER * HEATCAPWATER, -1);

    //     final byte VOLT = 10;
    //     final double RESIST = 10.8;
    //     final double OUTSIDE = 294.95;

    //     // (1 / PHO) * (wattage - (alpha * conduction + beta * radiation) )
    //     double value = PHO * ( (Math.pow(VOLT, 2) / RESIST) - (alpha * (temp - OUTSIDE) + beta * (Math.pow(temp, 4) - Math.pow(OUTSIDE, 4) ) ) );
    //     return value;
    // }
}
