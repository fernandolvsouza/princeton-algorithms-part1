import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

/**
 * Created by uq4n on 11/02/2016.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int N = 0;

    public RandomizedQueue() {
        items = (Item[]) new Object[1];
    }


    private class RandomizedQueueIterator implements Iterator<Item> {
        private int[] shuffle;
        private int current;

        RandomizedQueueIterator() {
            current = N;
            shuffle = new int[N];
            for (int i = 0; i < N; i++) {
                shuffle[i] = i;
            }
            StdRandom.shuffle(shuffle);
        }

        public boolean hasNext() {
            return current > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (current <= 0)
                throw new java.util.NoSuchElementException();
            return items[shuffle[--current]];
        }
    }

    public boolean isEmpty() {
        return N == 0;
    }                 // is the queue empty?

    public int size() {
        return N;
    }

    /*public int len() {
        return items.length;
    }*/

    public void enqueue(Item item) {
        if (item == null)
            throw new java.lang.NullPointerException();
        if (N == items.length) resize(2 * items.length);
        items[N++] = item;
    }           // add the item

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++)
            copy[i] = items[i];
        items = copy;
    }

    public Item dequeue() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();

        int random = StdRandom.uniform(N);


        Item item = items[random];
        items[random] = items[--N];
        items[N] = null;

        if (N > 0 && N == items.length / 4) resize(items.length / 2);

        return item;
    }

    public Item sample() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();

        int random = StdRandom.uniform(N);

        return items[random];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    public static void main(String[] args) {

        RandomizedQueue<Integer> deque = new RandomizedQueue<Integer>();
        System.out.println("############### 1000 enqueues ###############");
        int size = 0;
        for (int i = 0; i < 1000; i++) {
            deque.enqueue(i);
            /*if (deque.size() != size) {
                //System.out.print(deque.len() + " ");
                size = deque.len();
            }*/
        }

        System.out.println("\n############## 900 dequeues ################");

        for (int i = 0; i < 900; i++) {
            deque.dequeue();
            /*if (deque.size() != size) {
                System.out.print(deque.len() + " ");
                size = deque.len();
            }*/
        }
        System.out.println("\n############# final size 100 #################");
        System.out.print(deque.size() == 100);

        System.out.println("\n############### random iterator 1 ###############");
        for (int j : deque) {
            System.out.print(j + " ");
        }

        System.out.println("\n############### random iterator 2 ###############");

        for (int j : deque) {
            System.out.print(j + " ");
        }

        System.out.println("\n############## 100 samples ################");

        for (int i = 0; i < 100; i++) {
            System.out.print(deque.sample() + " ");
        }

    }   // unit testing
}
