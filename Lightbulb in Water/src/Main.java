import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    // private ArrayList<Statistics> statistics = new ArrayList<Statistics>();
    static ArrayList<Double> tempVtime = new ArrayList<Double>();
    public static void main(String[] args) throws Exception {
        final double STARTBETA = 0.000000000001; // Start at 10^-11
        final double ENDBETA = 0.00000001; // End at 10^-9
        
        // Create optimizer classes
        Optimizer optimizerOne = new Optimizer(0.0, 0.02, STARTBETA, ENDBETA);
        Optimizer optimizerTwo = new Optimizer(0.02, 0.04, STARTBETA, ENDBETA);
        Optimizer optimizerThree = new Optimizer(0.04, 0.06, STARTBETA, ENDBETA);
        Optimizer optimizerFour = new Optimizer(0.06, 0.08, STARTBETA, ENDBETA);
        Optimizer optimizerFive = new Optimizer(0.8, 0.10, STARTBETA, ENDBETA);
        Optimizer optimizerSix = new Optimizer(0.10, 0.12, STARTBETA, ENDBETA);
        Optimizer optimizerSeven = new Optimizer(0.12, 0.14, STARTBETA, ENDBETA);
        Optimizer optimizerEight = new Optimizer(0.14, 0.16, STARTBETA, ENDBETA);
        Optimizer optimizerNine = new Optimizer(0.16, 0.18, STARTBETA, ENDBETA);
        Optimizer optimizerTen = new Optimizer(0.18, 0.20, STARTBETA, ENDBETA);
        Optimizer optimizerEleven = new Optimizer(0.34, 0.36, STARTBETA, ENDBETA);
        Optimizer optimizerTwelve = new Optimizer(0.36, 0.38, STARTBETA, ENDBETA);
        Optimizer optimizerThirteen = new Optimizer(0.38, 0.40, STARTBETA, ENDBETA);
        Optimizer optimizerFourteen = new Optimizer(0.40, 0.42, STARTBETA, ENDBETA);
        Optimizer optimizerFifteen = new Optimizer(0.42, 0.44, STARTBETA, ENDBETA);
        Optimizer optimizerSixteen = new Optimizer(0.44, 0.46, STARTBETA, ENDBETA);
        // Optimizer optimizerSeventeen = new Optimizer(0.296, 0.302, STARTBETA, ENDBETA);
        // Optimizer optimizerEighteen = new Optimizer(0.302, 0.308, STARTBETA, ENDBETA);
        // Optimizer optimizerNineteen = new Optimizer(0.308, 0.314, STARTBETA, ENDBETA);
        // Optimizer optimizerTwenty = new Optimizer(0.314, 0.32, STARTBETA, ENDBETA);
        // Optimizer optimizerTwentyOne = new Optimizer(0.32, 0.326, STARTBETA, ENDBETA);
        // Optimizer optimizerTwentyTwo = new Optimizer(0.326, 0.332, STARTBETA, ENDBETA);
        // Optimizer optimizerTwentyThree = new Optimizer(0.332, 0.338, STARTBETA, ENDBETA);
        // Optimizer optimizerTwentyFour = new Optimizer(0.338, 0.344, STARTBETA, ENDBETA);
        
        // Create threads
        Thread threadOne = new Thread(optimizerOne);
        Thread threadTwo = new Thread(optimizerTwo);
        Thread threadThree = new Thread(optimizerThree);
        Thread threadFour = new Thread(optimizerFour);
        Thread threadFive = new Thread(optimizerFive);
        Thread threadSix = new Thread(optimizerSix);
        Thread threadSeven = new Thread(optimizerSeven);
        Thread threadEight = new Thread(optimizerEight);
        Thread threadNine = new Thread(optimizerNine);
        Thread threadTen = new Thread(optimizerTen);
        Thread threadEleven = new Thread(optimizerEleven);
        Thread threadTwelve = new Thread(optimizerTwelve);
        Thread threadThirteen = new Thread(optimizerThirteen);
        Thread threadFourteen = new Thread(optimizerFourteen);
        Thread threadFifteen = new Thread(optimizerFifteen);
        Thread threadSixteen = new Thread(optimizerSixteen);
        // Thread threadSeventeen = new Thread(optimizerSeventeen);
        // Thread threadEighteen = new Thread(optimizerEighteen);
        // Thread threadNineteen = new Thread(optimizerNineteen);
        // Thread threadTwenty = new Thread(optimizerTwenty);
        // Thread threadTwentyOne = new Thread(optimizerTwentyOne);
        // Thread threadTwentyTwo = new Thread(optimizerTwentyTwo);
        // Thread threadTwentyThree = new Thread(optimizerTwentyThree);
        // Thread threadTwentyFour = new Thread(optimizerTwentyFour);

        // Run the threads!!!
        threadOne.start();
        threadTwo.start();
        threadThree.start();
        threadFour.start();
        threadFive.start();
        threadSix.start();
        threadSeven.start();
        threadEight.start();
        threadNine.start();
        threadTen.start();
        threadEleven.start();
        threadTwelve.start();
        threadThirteen.start();
        threadFourteen.start();
        threadFifteen.start();
        threadSixteen.start();
        // threadSeventeen.start();
        // threadEighteen.start();
        // threadNineteen.start();
        // threadTwenty.start();
        // threadTwentyOne.start();
        // threadTwentyTwo.start();
        // threadTwentyThree.start();
        // threadTwentyFour.start();

        // Wait for threads to die!!!
        threadOne.join();
        threadTwo.join();
        threadThree.join();
        threadFour.join();
        threadFive.join();
        threadSix.join();
        threadSeven.join();
        threadEight.join();
        threadNine.join();
        threadTen.join();
        threadEleven.join();
        threadTwelve.join();
        threadThirteen.join();
        threadFourteen.join();
        threadFifteen.join();
        threadSixteen.join();
        // threadSeventeen.join();
        // threadEighteen.join();
        // threadNineteen.join();
        // threadTwenty.join();
        // threadTwentyOne.join();
        // threadTwentyTwo.join();
        // threadTwentyThree.join();
        // threadTwentyFour.join();

        System.out.println("There are " + Statistics.statistics.size() + " in this goddamn arraylist");

        makeCSV();
    }

    static void makeCSV() throws IOException {
        File file = new File("Lightbulb in Water\\src\\Statistics.csv");
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("Alpha,Beta,SumResidualSquare,Method");
        for (int i = 0; i < Statistics.statistics.size(); i++) {
            bw.newLine();
            bw.write(Statistics.statistics.get(i).getAlpha() + "," + Statistics.statistics.get(i).getBeta() + "," + Statistics.statistics.get(i).getSumRSquare() + "," + Statistics.statistics.get(i).getMethod());
        }
        bw.close();
        fw.close();
    }
}
