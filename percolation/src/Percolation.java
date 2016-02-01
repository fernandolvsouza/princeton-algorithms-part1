/**
 * Created by Fernando Luiz Valente de Souza on 29/01/2016.
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF uf,uf2;
    private int n = 0;
    private int[][] grid;
    private int virtualTopSite = 0;
    private int virtualBottomSite = 0;

    public Percolation(int N) {
        if (N < 1)
            throw new IllegalArgumentException();

        virtualTopSite = N * N;
        virtualBottomSite = N * N + 1;
        this.grid = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = 0;
            }
        }

        this.uf = new WeightedQuickUnionUF(N * N + 2);
        this.uf2 = new WeightedQuickUnionUF(N * N + 2);
        this.n = N;

    }

    public boolean isFull(int row, int col) {
        validateBounds(row, col);
        return uf2.connected(virtualTopSite, xyTo1D(row, col));
    }

    public boolean isOpen(int row, int col) {
        validateBounds(row, col);
        return this.grid[row - 1][col - 1] == 1;
    }

    public void open(int row, int col) {
        validateBounds(row, col);
        int a = xyTo1D(row, col);

        if (row > 1 && isOpen(row - 1, col)) {
            int m = xyTo1D(row - 1, col);
            uf.union(a, m);
            uf2.union(a, m);
        }
        if (row < this.n && isOpen(row + 1, col)) {
            int m = xyTo1D(row + 1, col);
            uf.union(a, m);
            uf2.union(a, m);
        }
        if (col > 1 && isOpen(row, col - 1)) {
            int m = xyTo1D(row, col - 1);
            uf.union(a, m);
            uf2.union(a, m);
        }
        if (col < this.n && isOpen(row, col + 1)) {
            int m = xyTo1D(row, col + 1);
            uf.union(a, m);
            uf2.union(a, m);
        }

        if (row == this.n) {
            uf.union(a, virtualBottomSite);
        }

        if (row == 1) {
            uf.union(a, virtualTopSite);
            uf2.union(a, virtualTopSite);
        }

        this.grid[row - 1][col - 1] = 1;
    }

    public boolean percolates() {
        return uf.connected(virtualTopSite, virtualBottomSite);
    }

    private void validateBounds(int row, int col) {
        if (row < 1 || row > n)
            throw new IndexOutOfBoundsException();
        if (col < 1 || col > n)
            throw new IndexOutOfBoundsException();
    }

    private int xyTo1D(int row, int col) {
        return col - 1 + (row - 1) * n;
    }
}
