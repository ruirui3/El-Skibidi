import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    //initialize int n and int trials as private;
    private static final double CONST = 1.96;
    private int trials;
    private double[] sumOfAverage;
    

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        //set this.n and this.trials

        if (n<1 || trials < 1) {
            throw new IllegalArgumentException();
        }

        this.trials = trials;
        sumOfAverage = new double[this.trials];
        
        for (int i = 0; i<this.trials; i++) {

            Percolation perc = new Percolation(n);
            int openSites = 0;
            while (!perc.percolates()) {
            
                
                int randomOne = StdRandom.uniformInt(1, n+1);
                int randomTwo = StdRandom.uniformInt(1, n+1);
    
                if (!perc.isOpen(randomOne, randomTwo)) {
                    perc.open(randomOne, randomTwo);
                    openSites += 1;
                }
    
                // perc.open(StdRandom.uniformInt(n) + 1, StdRandom.uniformInt(n) + 1);
    
            }
            double progressOfSites = (double) openSites / (n * n);
            sumOfAverage[i] = progressOfSites;

        }

    }

    // sample mean of percolation threshold
    public double mean() {
        
        //for each experiment, get number of open sites, add all of them then divide them by # of trials for average
        return StdStats.mean(sumOfAverage);

    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(sumOfAverage);

    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {

        return mean() - ((CONST * stddev()) / Math.sqrt(this.trials));

    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        
        return mean() + ((CONST * stddev()) / Math.sqrt(this.trials));
        
    }

   // test client (see below)
   public static void main(String[] args) {
        
   }

}