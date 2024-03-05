import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Plot {

    private static final double ALPHA = 0.38855;
    private static final double BETA = Math.pow(10, -25);
    final static  double deltaTime = 1;
    final static int SUBINT = 3660; // For 3600 seconds
    final static double INITTEMP = 294.65; // Initial water temp 294.65 K

    public Plot() {
        ;
    }

    static void makeCSV() throws IOException {
        File file = new File("Lightbulb-in-Water\\Lightbulb in Water\\src\\plotData.csv");
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        String method = "";
        bw.write("Time,Temp,Method");
        for (int i = 1; i <= 4; i++) {
            double sumTemp = INITTEMP; // Initialize starting tempterature
            double deltaTemp = 0; // For first instance only at zero
            double prevDelta = 0; // Prepare for adjustments needed sto trapezoidal
            double preDelta; // Prepare for adjustments needed sto trapezoidal


            for (int j = 0; j <= SUBINT; j++) {
                bw.newLine();

                switch(i) {
                    case 1:
                        method = "pseudoTrapezoid";
                        bw.write(j + "," + sumTemp + "," + method);
                        prevDelta = deltaTemp / 2.0 ; // Add lost deltaTemp to pseudotrapezoid
                        deltaTemp = (DiffFunc.diffFunc(ALPHA, BETA, sumTemp + prevDelta)) * deltaTime; // Overestimated Trapezoidal Reimann
                        sumTemp = (sumTemp + prevDelta + sumTemp + prevDelta + deltaTemp) / 2.0; // Linear approzimation from trapezoidal reimann
                        break;

                    case 2:
                        method = "Midpoint Euler";
                        bw.write(j + "," + sumTemp + "," + method);
                        // Get lost deltaTemp, make new Delta temp, linear approximate
                        preDelta = (DiffFunc.diffFunc(ALPHA, BETA, sumTemp)) * deltaTime ; // Add lost deltaTemp to pseudotrapezoid
                        deltaTemp = preDelta / 2.0; // Overestimated Trapezoidal Reimann
                        sumTemp = (sumTemp + deltaTemp); // Linear approzimation from trapezoidal reimann
                        break;

                    case 3:
                        method = "2ndOrderRungeKutta";
                        bw.write(j + "," + sumTemp + "," + method);
                        // Second-Order Runge-Katta
                        preDelta = deltaTime * (DiffFunc.diffFunc(ALPHA, BETA, sumTemp));
                        deltaTemp = deltaTime * (DiffFunc.diffFunc(ALPHA, BETA, sumTemp + (0.5) * preDelta)); // Overestimated Trapezoidal Reimann
                        sumTemp = (sumTemp + deltaTemp);
                        break;
                    
                    case 4:
                        method = "4thOrderRungeKutta";
                        bw.write(j + "," + sumTemp + "," + method);
                        // Second-Order Runge-Katta
                        double k_1 = deltaTime * (DiffFunc.diffFunc(ALPHA, BETA, sumTemp));
                        double k_2 = deltaTime * (DiffFunc.diffFunc(ALPHA, BETA, sumTemp + (deltaTime / 2.0) * k_1));
                        double k_3 = deltaTime * (DiffFunc.diffFunc(ALPHA, BETA, sumTemp + (deltaTime / 2.0) * k_2));
                        double k_4 = deltaTime * (DiffFunc.diffFunc(ALPHA, BETA, sumTemp + k_3));
                        double deltaRungeKutta = (k_1 + 2 * k_2 + 2 *  k_3 + k_4) / 6.0;
                        sumTemp = (sumTemp + deltaRungeKutta);
                }
                
            }
        }
        bw.close();
        fw.close();
    }
}
