package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Arrays;

public class Percolation {
    private int N;
    private int[] status;
    //private int[][] grid;
    private WeightedQuickUnionUF weightedQuickUnionUF;
    private int[] bottom;
    private int globalFullBottom;
    private int openSitesNum = 0;
    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0)
            throw new IllegalArgumentException();
        this.N = N;
        /*grid = new int[N][N];
        for (int[] ints : grid) {
            Arrays.fill(ints, 0);
        }*/
        status = new int[N * N];
        Arrays.fill(status, 0);
        bottom = new int[N * N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                bottom[indexMap(i, j)] = i;
            }
        }
        globalFullBottom  = -1;
        weightedQuickUnionUF = new WeightedQuickUnionUF(N * N);
    }
    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if(!checkRolAndCol(row, col)) {
            throw new IllegalArgumentException();
        }
        if (isOpen(row, col)) {
            return;
        }
        int siteThis = indexMap(row, col);
        if (row == 0) {
            //grid[row][col] = 2;
            status[siteThis] = 2;
            if (globalFullBottom < 0) {
                globalFullBottom = 0;
            }
        }
        else {
            // grid[row][col] = 1;
            status[siteThis] = 1;
        }
        openSitesNum++;
        if (row > 0) {
            if (isOpen(row - 1, col)) {
                if (!weightedQuickUnionUF.connected(indexMap(row - 1, col), indexMap(row, col))) {
                    int rootOther = weightedQuickUnionUF.find(indexMap(row - 1, col));
                    handleUnion(siteThis, rootOther);
                }
            }
        }
        if (row < N - 1) {
            if (isOpen(row + 1, col)) {
                if (!weightedQuickUnionUF.connected(indexMap(row + 1, col), indexMap(row, col))) {
                    int rootOther = weightedQuickUnionUF.find(indexMap(row + 1, col));
                    handleUnion(siteThis, rootOther);
                }
            }
        }
        if (col > 0) {
            if (isOpen(row, col - 1)) {
                if (!weightedQuickUnionUF.connected(indexMap(row, col - 1), indexMap(row, col))) {
                    int rootOther = weightedQuickUnionUF.find(indexMap(row, col - 1));
                    handleUnion(siteThis, rootOther);
                }
            }
        }
        if (col < N - 1) {
            if (isOpen(row, col + 1)) {
                if (!weightedQuickUnionUF.connected(indexMap(row, col + 1), indexMap(row, col))) {
                    int rootOther = weightedQuickUnionUF.find(indexMap(row, col + 1));
                    handleUnion(siteThis, rootOther);
                }
            }
        }
    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if(!checkRolAndCol(row, col)) {
            throw new IllegalArgumentException();
        }
        return status[weightedQuickUnionUF.find(indexMap(row, col))] > 0;
    }
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if(!checkRolAndCol(row, col)) {
            throw new IllegalArgumentException();
        }
        return status[weightedQuickUnionUF.find(indexMap(row, col))] == 2;
    }
    // number of open sites
    public int numberOfOpenSites() {
        return openSitesNum;
    }
    // does the system percolate?
    public boolean percolates() {
        return globalFullBottom == N - 1;
    }
    private int indexMap(int row, int col) {
        return row * N + col;
    }
    private boolean checkRolAndCol(int row, int col) {
        return row >= 0 && col >= 0 && row <= N - 1 && col <= N - 1;
    }
    private void handleUnion(int siteThis, int rootOther) {
        int root = weightedQuickUnionUF.find(siteThis);
        if (status[root] < status[rootOther]) {
            status[root] = status[rootOther];
        }
        else {
            status[rootOther] = status[root];
        }
        if (bottom[root] < bottom[rootOther]) {
            bottom[root] = bottom[rootOther];
        }
        else {
            bottom[rootOther] = bottom[root];
        }
        weightedQuickUnionUF.union(root, rootOther);
        if (bottom[root] > globalFullBottom && status[root] == 2) {
            globalFullBottom = bottom[root];
        }
    }
    public static void main(String[] args) {

    }
}
