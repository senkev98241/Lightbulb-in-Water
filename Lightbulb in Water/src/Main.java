import java.util.ArrayList;

public class Main {
    // private ArrayList<Statistics> statistics = new ArrayList<Statistics>();
    static ArrayList<Double> tempVtime = new ArrayList<Double>();
    public static void main(String[] args) throws Exception {
        final double STARTBETA = 0.00000000001;
        final double ENDBETA = 0.000000001;
        
        // Create optimizer classes
        Optimizer optimizerOne = new Optimizer(0.20, 0.215, STARTBETA, ENDBETA);
        Optimizer optimizerTwo = new Optimizer(0.215, 0.23, STARTBETA, ENDBETA);
        Optimizer optimizerThree = new Optimizer(0.23, 0.245, STARTBETA, ENDBETA);
        Optimizer optimizerFour = new Optimizer(0.245, 0.26, STARTBETA, ENDBETA);
        Optimizer optimizerFive = new Optimizer(0.26, 0.275, STARTBETA, ENDBETA);
        Optimizer optimizerSix = new Optimizer(0.275, 0.29, STARTBETA, ENDBETA);
        Optimizer optimizerSeven = new Optimizer(0.29, 0.315, STARTBETA, ENDBETA);
        Optimizer optimizerEight = new Optimizer(0.315, 0.33, STARTBETA, ENDBETA);
        
        // Create threads
        Thread threadOne = new Thread(optimizerOne);
        Thread threadTwo = new Thread(optimizerTwo);
        Thread threadThree = new Thread(optimizerThree);
        Thread threadFour = new Thread(optimizerFour);
        Thread threadFive = new Thread(optimizerFive);
        Thread threadSix = new Thread(optimizerSix);
        Thread threadSeven = new Thread(optimizerSeven);
        Thread threadEight = new Thread(optimizerEight);

        // Run the threads!!!
        threadOne.start();
        threadTwo.start();
        threadThree.start();
        threadFour.start();
        threadFive.start();
        threadSix.start();
        threadSeven.start();
        threadEight.start();

        // Wait for threads to die!!!
        threadOne.join();
        threadTwo.join();
        threadThree.join();
        threadFour.join();
        threadFive.join();
        threadSix.join();
        threadSeven.join();
        threadEight.join();

        System.out.println("There are " + Statistics.statistics.size() + "In this goddamn arraylist");
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
