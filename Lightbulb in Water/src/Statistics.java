import java.util.ArrayList;
public class Statistics {
    private double alpha;
    private double beta;
    private double sumRSquare;
    private ArrayList<Double> data = new ArrayList<Double>(60);
    
    public Statistics(double alpha, double beta, double sumRSquare, ArrayList<Double> data) {
        this.alpha = alpha;
        this.beta = beta;
        this.sumRSquare = sumRSquare;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public double getSumRSquare() {
        return sumRSquare;
    }

    public void setSumRSquare(double sumRSquare) {
        this.sumRSquare = sumRSquare;
    }

    public ArrayList<Double> getData() {
        return data;
    }

    public void setData(ArrayList<Double> data) {
        this.data = data;
    }
    
}
