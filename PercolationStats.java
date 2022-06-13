import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final int trails;  // number of trails for experiment
    private final double[] threshold; // the percolation threshold
    private final double confidence95 = 1.96; // 95% confidence interval


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {


        this.trails = trials;
        if (n <= 0 || trails <= 0)
            throw new IllegalArgumentException();

        int sitesNumber = n * n;
        threshold = new double[trails];

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);

            while (true) {
                if (percolation.percolates()) {
                    // calculate threshold and start the next trail
                    threshold[i] =
                            (double) percolation.numberOfOpenSites() / sitesNumber;

                    break;
                }

                int randomRow = StdRandom.uniform(1, n + 1);
                int randomCol = StdRandom.uniform(1, n + 1);
                percolation.open(randomRow, randomCol);

            }

        }


    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(threshold);

    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(threshold);

    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((confidence95 * stddev()) / Math.sqrt(trails));

    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((confidence95 * stddev()) / Math.sqrt(trails));

    }

    // test client (see below)
    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        int trails = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, trails);

        StdOut.println("mean                    = " + percolationStats.mean());
        StdOut.println("stddev                  = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = ["
                               + percolationStats.confidenceHi() + ", "
                               + percolationStats.confidenceLo() + "]");


    }
}







