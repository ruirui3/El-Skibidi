import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    //initialize int n and int trials as private;
    private final static double CONST = 1.96;
    private int n;
    private int trials;
    private Percolation perc;
    private int[] sumOfAverage;
    

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        //set this.n and this.trials

        if (n<1 || trials < 1) {
            throw new IllegalArgumentException();
        }

        this.n = n;
        this.trials = trials;
        sumOfAverage = new int[this.trials];
        
        for (int i = 0; i<this.trials; i++) {

            perc = new Percolation(n);
            while (!perc.percolates()) {
            
                
                int randomOne = StdRandom.uniformInt(n) + 1;
                int randomTwo = StdRandom.uniformInt(n) + 1;
    
                if (perc.isOpen(randomOne, randomTwo)) {
                    perc.open(randomOne, randomTwo);
                    
                }
    
                // perc.open(StdRandom.uniformInt(n) + 1, StdRandom.uniformInt(n) + 1);
    
            }
            sumOfAverage[i] = perc.numberOfOpenSites();

        }

    }

    // sample mean of percolation threshold
    public double mean() {
        
        //for each experiment, get number of open sites, add all of them then divide them by # of trials for average
        return StdStats.mean(sumOfAverage);

    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double std = 0;
        for (int i = 0; i<this.trials; i++) {
            std +=  Math.pow(sumOfAverage[i]-CONST, 2);
        }
        return std/(this.trials-1);

    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {

        return CONST - (1.96 * stddev())/Math.sqrt(this.trials);

    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        
        return CONST + (1.96 * stddev())/Math.sqrt(this.trials);
        
    }

   // test client (see below)
   public static void main(String[] args) {
    
   }

}