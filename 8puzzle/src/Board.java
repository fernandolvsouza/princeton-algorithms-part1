import java.util.ArrayList;
import java.util.List;

/**
 * Created by flvs on 2/21/16.
 */
public class Board {

    private int[][] blocks;
    private int N;

    // construct a board from an N-by-N array of blocks
    public Board(int[][] blocks) {
        this.N = blocks.length;
        this.blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }
    }


    class SearchNode {
        SearchNode previous;
        int moves = 0;
        Board board;
    }

    // (where blocks[i][j] = block in row i, column j)
    // board dimension N
    public int dimension() {
        return N;
    }

    // number of blocks out of place
    public int hamming() {
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] != i * N + j + 1 ) {
                    count++;
                }
            }
        }
        return count;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int g_i = blocks[i][j] / N;
                int g_j = blocks[i][j] % N - 1;

                count = count + Math.abs(g_i - i) + Math.abs(g_j - j);
            }
        }
        return count;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[][] twin_blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                twin_blocks[i][j] = blocks[i][j];
            }
        }

        if(N > 1) {
            int aux = twin_blocks[0][0];
            twin_blocks[0][0] = twin_blocks[0][1];
            twin_blocks[0][1] = aux;
        }


        return new Board(twin_blocks);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null)
            return false;

        if (!y.getClass().equals(getClass()))
            return false;

        Board other = (Board) y;

        if (other.dimension() != this.dimension())
            return false;

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (other.blocks[i][j] != blocks[i][j])
                    return false;
            }
        }
        return true;
    }

    

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> n_boards = new ArrayList<Board>();

        int[][] n_blocks = new int[N][N];
        int i_blank = 0,j_blank = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if(blocks[i][j] == 0){
                    i_blank = i;
                    j_blank = j;
                }

                n_blocks[i][j] = blocks[i][j];
            }
        }

        neighbors_exch(n_boards, n_blocks, i_blank, j_blank, i_blank, j_blank - 1);
        neighbors_exch(n_boards, n_blocks, i_blank, j_blank, i_blank, j_blank + 1);
        neighbors_exch(n_boards, n_blocks, i_blank, j_blank, i_blank - 1, j_blank);
        neighbors_exch(n_boards, n_blocks, i_blank, j_blank, i_blank + 1, j_blank);

        return n_boards;
    }

    private void neighbors_exch(List n_boards, int[][] e_blocks, int i, int j, int i2, int j2) {
        int aux =  e_blocks[i][j];
        e_blocks[i][j] = e_blocks[i2][j2];
        e_blocks[i2][j2] = aux;

        n_boards.add(new Board(e_blocks));

        e_blocks[i2][j2] = e_blocks[i][j];
        e_blocks[i][j] = aux;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(blocks[i][j]);
                if (j < N - 1)
                    System.out.print(" ");
            }
            System.out.print("\n");
        }

        return null;
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        int[][] test_blocks = new int[3][3];
        test_blocks[0][0] = 8;
        test_blocks[0][1] = 1;
        test_blocks[0][2] = 2;

        test_blocks[1][0] = 4;
        test_blocks[1][1] = 0;
        test_blocks[1][2] = 2;

        test_blocks[2][0] = 7;
        test_blocks[2][1] = 6;
        test_blocks[2][2] = 5;

        Board b = new Board(test_blocks);
        System.out.println(b.hamming());
        System.out.println( b.manhattan());
        assert b.hamming() == 5;
        assert b.manhattan() == 10;

    }
}
