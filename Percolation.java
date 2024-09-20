import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    
    private final WeightedQuickUnionUF perc;
    private final boolean[][] opened;
    private int numOfOpenSites;
    private final int size;
    private final int bottomConnector;
    private static final int topConnector = 0;
    
    //https://stackoverflow.com/questions/61396690/how-to-handle-the-backwash-problem-in-percolation-without-creating-an-extra-wuf 
    //thread on how to remove backwash?
    //note to self- discover how to remove backwash friday-saturday
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

        if (isOpen(row, col)) {
            return; //terminates open
        }

        opened[row-1][col-1] = true;
        numOfOpenSites++;
        if (percolates()) {
            return;
        }
        if (row == 1) {
            perc.union(getNodeNum(row, col), topConnector);
        }
        if (row == size) {
            perc.union(getNodeNum(row, col), bottomConnector);
            
        }
        if (row>1 && isOpen(row-1, col) ) { //THIS ENTIRE TIME THE WHOLE REASON I WAS WRONG WAS I PUT ISOPEN FIRST INSTEAD OF CHECKING IF ROW > 1
            perc.union(getNodeNum(row, col), getNodeNum(row-1, col));
        }
        if (row < size && isOpen(row+1, col)) {
            perc.union(getNodeNum(row, col), getNodeNum(row+1, col));
        }
        if (col>1 && isOpen(row, col-1)) {
            perc.union(getNodeNum(row, col), getNodeNum(row, col-1));
        }
        if (col<size && isOpen(row, col+1)) {
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
        return perc.find(topConnector) == perc.find(getNodeNum(row, col));
    }

    public int numberOfOpenSites() {

        return numOfOpenSites;

    }

    public boolean percolates() {

        
        return perc.find(topConnector) == perc.find(bottomConnector);

    }

    public static void main(String[] args) {

    }
}