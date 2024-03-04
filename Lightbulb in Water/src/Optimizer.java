import java.util.ArrayList;

public class Optimizer implements Runnable {
    
    private double initAlpha;
    private double endAlpha;
    private double initBeta;
    private double endBeta;

    private ArrayList<Double> tempVtime = new ArrayList<Double>();
    static ArrayList<Double> data = Data.data;
    // private ArrayList<Statistics> statistics = new ArrayList<Statistics>();
    
    private double deltaAlpha = 0.001;
    private double deltaBeta = 0.000000000001;

    public Optimizer(double recInitAlpha, double recEndAlpha, double recInitBeta, double recEndBeta) {
        this.initAlpha = recInitAlpha;
        this.endAlpha = recEndAlpha;
        this.initBeta = recInitBeta;
        this.endBeta = recEndBeta;
    }
    public void run() {
        resetTempVTimeList();
        pseudoTrapezoid();
        midEuler();
        rungKuttaOrderTwo();
        rungKuttaOrderFour();
    }
    
    private void resetTempVTimeList() {
        tempVtime.clear();
        for (int i = 0; i <= 61; i += 1) {
            tempVtime.add(0.0);
        }
    }
    public void pseudoTrapezoid() {
        resetTempVTimeList();
        double alpha = this.initAlpha;
        double beta = this.initBeta;
        double sumRSquare = 0;
        double prevSumR2 = Double.MAX_VALUE;
        double deltaTime = 1;
        final double SUBINT = 3660 * deltaTime; // For 3600 seconds
        final double INITTEMP = 294.65; // Initial water temp 294.65 K
        
        // Optimization of arbitary coefficient for radiation Beta
        for (beta = initBeta; beta <= endBeta + deltaBeta / (byte) 2; beta += deltaBeta /* Should be 10^-11 */) {// (beta = 0.0000000001; beta <= 0.000000001; beta += 0.00000000001 ) { // Increase percision to 16 decimals later

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
                    }
                    
                    // Get lost deltaTemp, make new Delta temp, linear approximate
                    prevDelta = deltaTemp / 2.0 ; // Add lost deltaTemp to pseudotrapezoid
                    deltaTemp = (DiffFunc.diffFunc(alpha, beta, sumTemp + prevDelta)) * deltaTime; // Overestimated Trapezoidal Reimann
                    sumTemp = (sumTemp + prevDelta + sumTemp + prevDelta + deltaTemp) / 2.0; // Linear approzimation from trapezoidal reimann
                }

                sumRSquare = 0; // Reset sum of residuals rquared
                short i = 0; // Reset iterations to prepare calculation of sum of r^2
                for (Double dataPoint : data) { // Calcluate sum of r^2
                    sumRSquare += Math.pow(dataPoint - tempVtime.get(i), 2);
                    i += 1;
                }
                // System.out.println("Alpha is " + alpha + "\t + Beta is " + beta + "\t sumTemp is " + sumTemp + "\t sumR is " + sumRSquare);
                // System.out.println(sumRSquare);
                if (sumRSquare < prevSumR2) {
                    Statistics.statistics.add(new Statistics(alpha, beta, sumRSquare, "pseudoTrapezoid"));
                    prevSumR2 = sumRSquare;
                }
                // break;
            } 
            // break;
            System.out.println("Alpha is " + alpha + "\t + Beta is " + beta + "\t method is" + "pseudoTrapezoid");
        }
    }

    public void midEuler() {
        resetTempVTimeList();

        double alpha = this.initAlpha;
        double beta = this.initBeta;
        double sumRSquare = 0;
        double prevSumR2 = Double.MAX_VALUE;
        double deltaTime = 1;
        final double SUBINT = 3660 * deltaTime; // For 3600 seconds
        final double INITTEMP = 294.65; // Initial water temp 294.65 K

        // Optimization of arbitary coefficient for radiation Beta
        for (beta = initBeta; beta <= endBeta + deltaBeta / (byte) 2; beta += deltaBeta /* Should be 10^-11 */) {// (beta = 0.0000000001; beta <= 0.000000001; beta += 0.00000000001 ) { // Increase percision to 16 decimals later

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
                    }
                    
                    // Get lost deltaTemp, make new Delta temp, linear approximate
                    preDelta = (DiffFunc.diffFunc(alpha, beta, sumTemp)) * deltaTime ; // Add lost deltaTemp to pseudotrapezoid
                    deltaTemp = preDelta / 2.0; // Overestimated Trapezoidal Reimann
                    sumTemp = (sumTemp + deltaTemp); // Linear approzimation from trapezoidal reimann
                }

                sumRSquare = 0; // Reset sum of residuals rquared
                short i = 0; // Reset iterations to prepare calculation of sum of r^2
                for (Double dataPoint : data) { // Calcluate sum of r^2
                    sumRSquare += Math.pow(dataPoint - tempVtime.get(i), 2);
                    i += 1;
                }
                // System.out.println("Alpha is " + alpha + "\t + Beta is " + beta + "\t sumTemp is " + sumTemp + "\t sumR is " + sumRSquare);
                // System.out.println(sumRSquare);
                
                if (sumRSquare < prevSumR2) {
                    Statistics.statistics.add(new Statistics(alpha, beta, sumRSquare, "Midpoint Euler"));
                    prevSumR2 = sumRSquare;
                }
                // break;
            } 
            // break;
            System.out.println("Alpha is " + alpha + "\t + Beta is " + beta + "\t method is" + "Midpoint Euler");
        }
    }

    public void rungKuttaOrderTwo() {
        resetTempVTimeList();

        double alpha = this.initAlpha;
        double beta = this.initBeta;
        double sumRSquare = 0;
        double prevSumR2 = Double.MAX_VALUE;
        double deltaTime = 1;
        final double SUBINT = 3660 * deltaTime; // For 3600 seconds
        final double INITTEMP = 294.65; // Initial water temp 294.65 K

        // Optimization of arbitary coefficient for radiation Beta
        for (beta = initBeta; beta <= endBeta + deltaBeta / (byte) 2; beta += deltaBeta /* Should be 10^-11 */) {// (beta = 0.0000000001; beta <= 0.000000001; beta += 0.00000000001 ) { // Increase percision to 16 decimals later

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
                    }
                    // Second-Order Runge-Katta
                    preDelta = deltaTime * (DiffFunc.diffFunc(alpha, beta, sumTemp));
                    deltaTemp = deltaTime * (DiffFunc.diffFunc(alpha, beta, sumTemp + (0.5) * preDelta)); // Overestimated Trapezoidal Reimann
                    sumTemp = (sumTemp + deltaTemp);
                }

                sumRSquare = 0; // Reset sum of residuals rquared
                short i = 0; // Reset iterations to prepare calculation of sum of r^2
                for (Double dataPoint : data) { // Calcluate sum of r^2
                    sumRSquare += Math.pow(dataPoint - tempVtime.get(i), 2);
                    i += 1;
                }
                // System.out.println("Alpha is " + alpha + "\t + Beta is " + beta + "\t sumTemp is " + sumTemp + "\t sumR is " + sumRSquare);
                // System.out.println(sumRSquare);
                
                if (sumRSquare < prevSumR2) {
                    Statistics.statistics.add(new Statistics(alpha, beta, sumRSquare, "2ndOrderRungeKutta"));
                    prevSumR2 = sumRSquare;
                }
                // break;
            } 
            // break;
            System.out.println("Alpha is " + alpha + "\t + Beta is " + beta + "\t method is" + "2nd Order");
        }
    }

    public void rungKuttaOrderFour() {
        resetTempVTimeList();

        double alpha = this.initAlpha;
        double beta = this.initBeta;
        double sumRSquare = 0;
        double prevSumR2 = Double.MAX_VALUE;
        double deltaTime = 1;
        final double SUBINT = 3660 * deltaTime; // For 3600 seconds
        final double INITTEMP = 294.65; // Initial water temp 294.65 K

        // Optimization of arbitary coefficient for radiation Beta
        for (beta = initBeta; beta <= endBeta + deltaBeta / (byte) 2; beta += deltaBeta /* Should be 10^-11 */) {// (beta = 0.0000000001; beta <= 0.000000001; beta += 0.00000000001 ) { // Increase percision to 16 decimals later

            // Optimization of arbitrary coefficient for conduction Alpha
            for (alpha = initAlpha; alpha <= endAlpha + deltaAlpha / 2; alpha += deltaAlpha) {// (alpha = 0.2; alpha <= 1.0/3.0; alpha += 0.001) { // Increase percision to 7 decimals later
                // Initialize variables
                double sumTemp = INITTEMP; // Initialize starting tempterature

                // Perform Second-Order Runge-Kutta
                // k_1 = h * f( x_n, y_n )
                // k_2 = h * f( x_n + 0.5 * h, y_n + 0.5 * k_1)
                // y_(n+1) = y_n + k_2 + O(h^3)
                for (double time = 0; time <= SUBINT + deltaTime / 2; time += deltaTime) {
                    // Add supposed data point at minute timestamps
                    if ( (int) (time % 60) == 0) {
                        tempVtime.set( (int) (time / 60), sumTemp); // Set minute data
                    }
                    // Second-Order Runge-Katta
                    double k_1 = deltaTime * (DiffFunc.diffFunc(alpha, beta, sumTemp));
                    double k_2 = deltaTime * (DiffFunc.diffFunc(alpha, beta, sumTemp + (deltaTime / 2.0) * k_1));
                    double k_3 = deltaTime * (DiffFunc.diffFunc(alpha, beta, sumTemp + (deltaTime / 2.0) * k_2));
                    double k_4 = deltaTime * (DiffFunc.diffFunc(alpha, beta, sumTemp + k_3));
                    double deltaRungeKutta = (k_1 + 2 * k_2 + 2 *  k_3 + k_4) / 6.0;
                    sumTemp = (sumTemp + deltaRungeKutta);
                }

                sumRSquare = 0; // Reset sum of residuals rquared
                short i = 0; // Reset iterations to prepare calculation of sum of r^2
                for (Double dataPoint : data) { // Calcluate sum of r^2
                    sumRSquare += Math.pow(dataPoint - tempVtime.get(i), 2);
                    i += 1;
                }
                // System.out.println("Alpha is " + alpha + "\t + Beta is " + beta + "\t sumTemp is " + sumTemp + "\t sumR is " + sumRSquare);
                // System.out.println(sumRSquare);
                
                if (sumRSquare < prevSumR2) {
                    Statistics.statistics.add(new Statistics(alpha, beta, sumRSquare, "4thOrderRungeKutta"));
                    prevSumR2 = sumRSquare;
                }
                // break;
            } 
            // break;
            System.out.println("Alpha is " + alpha + "\t + Beta is " + beta + "\t method is" + "4th Order");
        }
    }
}
