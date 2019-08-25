package hw2;

import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {

    int mean, std, size, time;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        int[] result = new int[T];
        size = N;
        time = T;
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException("");
        }
        for (int i = 0; i < T; i ++) {
            Percolation p = new Percolation(N);
            while (! p.percolates()) {
                int x = StdRandom.uniform(N);
                int y = StdRandom.uniform(N);
                p.open(x, y);

            }
            result[i] = p.numberOfOpenSites() / (N * N);
        }
        int sum = 0;
        for (int j = 0; j < T; j ++) {
            sum += result[j];
        }
        mean = sum / T;
        int sum2 = 0;
        for (int m = 0; m < T; m ++) {
            sum2 += (result[m] - mean) * (result[m] - mean);
        }
        std = sum2 / (T - 1);
    }   // perform T independent experiments on an N-by-N grid

    public double mean() {
        return mean;
    }                                          // sample mean of percolation threshold

    public double stddev() {
        return std;
    }                                        // sample standard deviation of percolation threshold

    public double confidenceLow() {
        double low;
        low = this.mean - 1.96 * this.std / Math.sqrt(time);
        return low;
    }                                 // low endpoint of 95% confidence interval

    public double confidenceHigh() {
        double high;
        high = this.mean + 1.96 * this.std / Math.sqrt(time);
        return high;
    }                                // high endpoint of 95% confidence interval

}
