import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


/**
 * Created by Fernando Luiz Valente de Souza on 29/01/2016.
 */
public class PercolationStats {

    private static final double CONFIDENCE = 1.96d;
    private int t = 0;
    private int n = 0;
    private double[] thresholds;


    /**
     * Perform T independent experiments on an N-by-N grid
     */
    public PercolationStats(int n, int t) {
        if (t < 1 || n < 1)
            throw new IllegalArgumentException();

        this.t = t;
        this.n = n;
        this.thresholds = new double[t];
        execute();
    }

    /**
     * Sample mean of percolation threshold
     */

    public double mean() {
        return StdStats.mean(this.thresholds);
    }

    /**
     * Sample standard deviation of percolation threshold
     */
    public double stddev() {
        return StdStats.stddev(this.thresholds);
    }

    /**
     * Low  endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return mean() - (CONFIDENCE * stddev() / Math.sqrt(t));
    }

    /**
     * High endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return mean() + (CONFIDENCE * stddev() / Math.sqrt(t));
    }

    private void execute() {

        for (int i = 0; i < t; i++) {
            Percolation p = new Percolation(n);
            int threshold = 0;
            while (!p.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                while (p.isOpen(row, col)) {
                    row = StdRandom.uniform(1, n + 1);
                    col = StdRandom.uniform(1, n + 1);
                }

                p.open(row, col);
                threshold++;
            }
            this.thresholds[i] = ((double) threshold) / (n * n);
        }
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats s = new PercolationStats(n, t);
        s.execute();
        System.out.println("mean\t\t\t\t\t = " + s.mean());
        System.out.println("stddev\t\t\t\t\t = " + s.stddev());
        System.out.println("95% confidence interval\t = " + s.confidenceLo() + "," + s.confidenceHi());
    }
}