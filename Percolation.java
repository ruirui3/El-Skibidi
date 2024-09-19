import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    
    private WeightedQuickUnionUF perc;
    private boolean[][] opened;
    private int numOfOpenSites;
    private int size;
    private final int bottomConnector;
    private static final int topConnector = 0;
    

    public Percolation(int n) {

        if (n <= 0) {
            throw new IllegalArgumentException();
        } 
        size = n;
        perc = new WeightedQuickUnionUF(n * n + 2);
        numOfOpenSites = 0; 
        opened = new boolean[size][size]; // all false
        bottomConnector = size*size+1;
        
    }

    private void validation(int row, int col) {
        if (row <=0 || col <= 0 || row > size || col > size) {
            throw new IllegalArgumentException();
        }
    }

    public void open(int row, int col) {

        validation(row, col);

        opened[row-1][col-1] = true;
        ++numOfOpenSites;

        if (row == 1) {
            perc.union(getNodeNum(row, col), topConnector);
        }
        if (row == size) {
            perc.union(getNodeNum(row, col), bottomConnector);
        }
        if (isOpen(row-1, col) && row>1) {
            perc.union(getNodeNum(row, col), getNodeNum(row-1, col));
        }
        if (isOpen(row+1, col) && row < size) {
            perc.union(getNodeNum(row, col), getNodeNum(row+1, col));
        }
        if (isOpen(row, col-1) && col>1) {
            perc.union(getNodeNum(row, col), getNodeNum(row, col-1));
        }
        if (isOpen(row, col+1) && col<size) {
            perc.union(getNodeNum(row, col), getNodeNum(row, col+1));
        }

    }

    private int getNodeNum (int row, int col) {
        return (row-1) * size + col; 
    }

    public boolean isOpen(int row, int col) {

        validation(row, col);

        return opened[row-1][col-1];

    }

    public boolean isFull(int row, int col) {

        validation(row, col);

        if (perc.find(getNodeNum(row, col)) == perc.find(topConnector)) {
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