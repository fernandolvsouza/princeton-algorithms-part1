import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;

/**
 * Created by uq4n on 11/02/2016.
 */
public class Subset {
    public static void main(String[] args) {
        if(args.length != 1)
            throw new IllegalArgumentException();

        RandomizedQueue<String> rQueue = new RandomizedQueue<String>();

        int k = Integer.parseInt(args[0]);
        String[] s = StdIn.readAllStrings();
        for (int i = 0; i < s.length; i++) {
            rQueue.enqueue(s[i]);
        }

        Iterator<String> it = rQueue.iterator();
        for (int i = 0; i < k; i++) {
            System.out.println(it.next());
        }
    }
}
