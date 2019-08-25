package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int[] grid;
    private int openSites;
    private WeightedQuickUnionUF uf;
    private int top, bottom;
    private int sizeOfGrid;
    private WeightedQuickUnionUF antiBackwash;

    public Percolation(int N) {
        grid = new int[N * N + N];
        sizeOfGrid = N;
        openSites = 0;
        top = N * N;
        bottom = N * N + 1;
        //connect top and bottom row to the virtual tip and bottom points.
        uf = new WeightedQuickUnionUF(N * N + 2);
        antiBackwash = new WeightedQuickUnionUF(N * N + 2);
        for (int i = 0; i < sizeOfGrid; i ++) {
            uf.union(top, i);
            antiBackwash.union(top, i);
        }

        for (int j = N * N - 1 ; j > N * N - N - 1; j --) {
            uf.union(bottom, j);
        }
    }                // create N-by-N grid, with all sites initially blocked

    private int xyTo1D(int row, int col) {
        return row * sizeOfGrid + col;
    }

    private boolean checkRange(int row, int col) {
        if (row < 0 || col < 0) {
            throw new java.lang.IllegalArgumentException(" ");
        }
        if (row > sizeOfGrid || col > sizeOfGrid) {
            throw new java.lang.IndexOutOfBoundsException(" ");
        }
        return true;
    }

    private void connectSites(int row, int col) {
        int index = xyTo1D(row, col);
        if (row > 0 && isOpen(row - 1, col)) {
            uf.union(index, xyTo1D(row - 1, col));
            antiBackwash.union(index, xyTo1D(row - 1, col));
        }
        if (row < sizeOfGrid && isOpen(row + 1, col)) {
            uf.union(index, xyTo1D(row + 1, col));
            antiBackwash.union(index, xyTo1D(row + 1, col));
        }
        if (col > 0 && isOpen(row, col - 1)) {
            uf.union(index, xyTo1D(row, col - 1));
            antiBackwash.union(index, xyTo1D(row, col - 1));
        }
        if (col < sizeOfGrid - 1 && isOpen(row, col + 1)) {
            uf.union(index, xyTo1D(row, col + 1));
            antiBackwash.union(index, xyTo1D(row, col + 1));
        }
    }

    public void open(int row, int col) {
        if (checkRange(row, col)) {
            int index = xyTo1D(row, col);
            if (grid[index] == 1) {
                return;
            } else {
                grid[index] = 1;
                openSites += 1;
                connectSites(row, col);
            }
        }
    }       // open the site (row, col) if it is not open already

    public boolean isOpen(int row, int col) {
        checkRange(row, col);
        int index = xyTo1D(row, col);
        return grid[index] > 0;
    }  // is the site (row, col) open?

    public boolean isFull(int row, int col) {
        checkRange(row, col);
        int index = xyTo1D(row, col);
        return (antiBackwash.connected(top, index) && isOpen(row, col));
    } // is the site (row, col) full?

    public int numberOfOpenSites() {
        return openSites;
    }           // number of open sites

    public boolean percolates() {
        return uf.connected(top, bottom);
    }             // does the system percolate?

    public static void main(String[] args) {

    }   // use for unit testing (not required, but keep this here for the autograder)

}
