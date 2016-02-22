import java.util.ArrayList;
import java.util.List;

/**
 * Created by flvs on 2/21/16.
 */
public class Board {

    private int[][] blocks;
    private int N, manhattan = -1, hamming = -1;

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

    // (where blocks[i][j] = block in row i, column j)
    // board dimension N
    public int dimension() {
        return N;
    }

    // number of blocks out of place
    public int hamming() {
        if(hamming > -1)
            return hamming;
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if(blocks[i][j] == 0)
                    continue;

                if (blocks[i][j] != i * N + j + 1) {
                    count++;
                }
            }
        }
        hamming = count;
        return count;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        if(manhattan > -1)
            return manhattan;
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {

                if(blocks[i][j] == 0)
                    continue;

                if (blocks[i][j] != i * N + j + 1) {
                    int g_i = (blocks[i][j] - 1) / N;
                    int g_j = (blocks[i][j] - 1) % N ;
                    count = count + Math.abs(g_i - i) + Math.abs(g_j - j);
                }
            }
        }
        manhattan = count;
        return count;

    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[] i_exch = new int[2];
        int[] j_exch = new int[2];
        int aux_index = 0;

        int[][] twin_blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if(blocks[i][j] != 0 && aux_index < 2){
                    i_exch[aux_index] = i;
                    j_exch[aux_index] = j;
                    aux_index++;
                }

                twin_blocks[i][j] = blocks[i][j];
            }
        }

        int aux = twin_blocks[i_exch[0]][j_exch[0]];
        twin_blocks[i_exch[0]][j_exch[0]] = twin_blocks[i_exch[1]][j_exch[1]];
        twin_blocks[i_exch[1]][j_exch[1]] = aux;

        return new Board(twin_blocks);
    }

    // does this board equal y?
    @Override
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

    private void neighbors_exch(List<Board> n_boards, int[][] e_blocks, int i, int j, int i2, int j2) {
        if(i < 0 || i >= N || j < 0 || j >= N )
            return;
        if(i2 < 0 || i2 >= N || j2 < 0 || j2 >= N )
            return;

        int aux =  e_blocks[i][j];
        e_blocks[i][j] = e_blocks[i2][j2];
        e_blocks[i2][j2] = aux;

        n_boards.add(new Board(e_blocks));

        e_blocks[i2][j2] = e_blocks[i][j];
        e_blocks[i][j] = aux;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder b = new StringBuilder( N + "\n");
        for (int i = 0; i < N; i++) {
            b.append(" ");
            for (int j = 0; j < N; j++) {
                b.append(blocks[i][j]);
                if (j < N - 1)
                    b.append(" ");
            }
            b.append("\n");
        }

        return b.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        int[][] test_blocks = {{8,1,3},{4,0,2},{7,6,5}};//new int[3][3];

        Board b = new Board(test_blocks);
        System.out.println(b.hamming());
        System.out.println( b.manhattan());
        System.out.println("neighbors of \n" + b + "are: ");
        for (Board n : b.neighbors()) {
            System.out.println( n);
        }

        System.out.println("twin : \n" + b.twin());
        int[][] test_blocks2 = {{0,1,3},{4,8,2},{7,6,5}};//new int[3][3];

        Board b2 = new Board(test_blocks2);

        System.out.println("neighbors of \n" + b2 + "are: ");
        for (Board n : b2.neighbors()) {
            System.out.println(n);
        }

        System.out.println("twin : \n" + b2.twin());

        int[][] test = { {1,2}, {3,0}};
        int[][] test2 = { {1,2}, {3,0}};

        System.out.println( new Board(test).equals(new Board(test2)));

        int[][] test3 = {{1, 2,3,4},{5,6,12,7},{9,10, 8, 0},{13, 14, 11, 15}};
        System.out.println( new Board(test3).manhattan());
    }
}
