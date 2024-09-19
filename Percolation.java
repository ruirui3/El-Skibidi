import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    
    private WeightedQuickUnionUF perc;
    private boolean[][] opened;
    private int numOfOpenSites;
    private int size;
    private int bottomConnector;
    private int topConnector;
    

    public Percolation(int n) {

        if (n <= 0) throw new IllegalArgumentException();
        size = n;
        perc = new WeightedQuickUnionUF(n * n + 2);
        numOfOpenSites = 0; 
        opened = new boolean[size][size]; // all false
        bottomConnector = size*size+1;
        topConnector = 0;

    }

    public void open(int row, int col) {

        if (row < 1 || col < 1 || row > size || col > size) {
            throw new IllegalArgumentException();
        }

        opened[row-1][col-1] = true;
        numOfOpenSites++;

        if (row == 1) {
            perc.union(getNodeNum(row, col), topConnector);
            if (percolates()) {
                return;
            }
        }
        if (row == size) {
            perc.union(getNodeNum(row, col), bottomConnector);
            if (percolates()) {
                return;
            }
        }
        if (isOpen(row-1, col) && row>1) {
            perc.union(getNodeNum(row, col), getNodeNum(row-1, col));
        }
        if (isOpen(row+1, col) && row>1) {
            perc.union(getNodeNum(row, col), getNodeNum(row+1, col));
        }
        if (isOpen(row, col-1) && row>1) {
            perc.union(getNodeNum(row, col), getNodeNum(row, col-1));
        }
        if (isOpen(row, col+1) && row>1) {
            perc.union(getNodeNum(row, col), getNodeNum(row, col+1));
        }

    }

    private int getNodeNum (int row, int col) {
        return (row-1) * size + col; 
    }

    public boolean isOpen(int row, int col) {

        if (row == 0 || col == 0 || row > size || col == size) {
            throw new IllegalArgumentException();
        }

        return opened[row-1][col-1];

    }

    public boolean isFull(int row, int col) {

        if (row == 0 || col == 0 || row > size || col == size) {
            throw new IllegalArgumentException();
        }

        if (perc.find(getNodeNum(row, col)) == topConnector) {
            return true;
        }
        return false;
    }

    public int numberOfOpenSites() {

        return numOfOpenSites;

    }

    public boolean percolates() {

        if (perc.find(topConnector) == perc.find(bottomConnector)) {
            return true;
        }
        
        return false;

    }

    public static void main(String[] args) {

    }
}