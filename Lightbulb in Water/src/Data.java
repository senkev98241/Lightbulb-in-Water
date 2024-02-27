import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;



public class Data {
    static ArrayList<Double> data = Main.data;
    // public static void main(String[] args) {
    //     String csvFile = "TempvWater.csv"; // Replace with the path to your CSV file
    //     ArrayList<String> stringList = readCSVToStringList(csvFile);
    //     ArrayList<Double> doubleList = convertStringListToDoubleList(stringList);

    //     // Print the ArrayList of Doubles
    //     for (Double value : doubleList) {
    //         System.out.println(value);
    //     }
    // }

    private static ArrayList<String> readCSVToStringList(String csvFile) {
        ArrayList<String> stringList = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                // Assuming single column CSV, add the entire line to the list
                stringList.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringList;
    }

    private static ArrayList<Double> convertStringListToDoubleList(ArrayList<String> stringList) {
        ArrayList<Double> doubleList = new ArrayList<>();
        for (String str : stringList) {
            try {
                // Convert each string to double and add to the list
                double value = Double.parseDouble(str);
                doubleList.add(value);
            } catch (NumberFormatException e) {
                // Handle if the conversion fails for any reason
                e.printStackTrace();
            }
        }

        return doubleList;
    }

    public static ArrayList<Double> extractData() {
        String csvFile = "Lightbulb in Water\\src\\TempvWaterData.csv"; // Replace with the path to your CSV file
        ArrayList<String> stringList = readCSVToStringList(csvFile);
        ArrayList<Double> doubleList = convertStringListToDoubleList(stringList);

        // Print the ArrayList of Doubles
        // for (Double value : doubleList) {
        //     System.out.println(value);
        // }
        return doubleList;
    }
}
