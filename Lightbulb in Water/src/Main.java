import java.util.ArrayList;
import java.util.Collections;

public class Main {
    // private ArrayList<Statistics> statistics = new ArrayList<Statistics>();
    static ArrayList<Double> tempVtime = new ArrayList<Double>();
    public static void main(String[] args) throws Exception {
        final double STARTBETA = 0.00000000001;
        final double ENDBETA = 0.000000001; // Should be 10^-9
        
        // Create optimizer classes
        Optimizer optimizerOne = new Optimizer(0.20, 0.201, STARTBETA, ENDBETA);
        Optimizer optimizerTwo = new Optimizer(0.201, 0.202, STARTBETA, ENDBETA);
        Optimizer optimizerThree = new Optimizer(0.202, 0.203, STARTBETA, ENDBETA);
        Optimizer optimizerFour = new Optimizer(0.203, 0.204, STARTBETA, ENDBETA);
        Optimizer optimizerFive = new Optimizer(0.204, 0.205, STARTBETA, ENDBETA);
        Optimizer optimizerSix = new Optimizer(0.205, 0.206, STARTBETA, ENDBETA);
        Optimizer optimizerSeven = new Optimizer(0.206, 0.207, STARTBETA, ENDBETA);
        Optimizer optimizerEight = new Optimizer(0.207, 0.208, STARTBETA, ENDBETA);
        
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

        // Find the minimum sumRSquare value
        double minSumRSquare = findMinSumRSquare(Statistics.statistics);

        // Find the index of the minimum sumRSquare value
        // int minIndex = Statistics.statistics.indexOf(0, 0, minSumRSquare);
        // System.out.println("In index " + minIndex);
    }

    public static double findMinSumRSquare(ArrayList<Statistics> statsList) {
        if (statsList.isEmpty()) {
            throw new IllegalArgumentException("The list is empty.");
        }

        // Initialize with the first value
        double minSumRSquare = statsList.get(0).getSumRSquare();

        // Compare with the rest of the values
        for (Statistics stat : statsList) {
            minSumRSquare = Math.min(minSumRSquare, stat.getSumRSquare());
        }
        // int minIndex = statsList.indexOf(new Statistics(0, 0, minSumRSquare));
        return minSumRSquare;
    }
}
