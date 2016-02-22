import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by flvs on 22/02/2016.
 */
public class Solver {

    private MinPQ<SearchNode> pq = new MinPQ<>();
    private boolean isSolvable = false;
    private int numberOfMoves = -1;
    private Stack<Board> result = null;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        if(initial == null)
            throw new  java.lang.NullPointerException();

        SearchNode n = new SearchNode(initial);
        SearchNode twin = new SearchNode(initial.twin());
        pq.insert(n);
        pq.insert(twin);

        SearchNode min = pq.delMin();

        while(!min.board.isGoal()) {
            for (Board b : min.board.neighbors()) {
                if(min.previous != null && b.equals(min.previous.board)) {
                    continue;
                }
                pq.insert(new SearchNode(b, min));
            }
            min = pq.delMin();
        }

        if(min.root.equals(n)) {
            numberOfMoves = min.moves;
            isSolvable = true;
            result = new Stack<>();
            while(min != null){
                result.push(min.board);
                min = min.previous;
            }
        }


    }

    private class SearchNode implements Comparable<SearchNode>{

        SearchNode previous;
        int moves;
        Board board;
        SearchNode root;

        SearchNode(Board b){
            board = b;
            moves = 0;
            previous = null;
            root = this;
        }

        SearchNode(Board b,SearchNode previous){
            this.board = b;
            this.moves = previous.moves + 1;
            this.previous = previous;
            this.root = previous.root;
        }

        private Integer manhattanPlusMove(){
          return  Integer.valueOf(board.manhattan() + moves);
        }

        private Integer hammingPlusMove(){
            return  Integer.valueOf(board.manhattan() + moves);
        }

        @Override
        public int compareTo(SearchNode searchNode) {
            int ret = manhattanPlusMove().compareTo(searchNode.manhattanPlusMove());
            if(ret != 0)
                return ret;
            return  hammingPlusMove().compareTo(searchNode.hammingPlusMove());
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return numberOfMoves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return result;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
