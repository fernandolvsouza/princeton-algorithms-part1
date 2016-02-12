import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Fernando Luiz Valente de Souza on 03/02/2016.
 */
public class Deque<Item> implements Iterable<Item> {
    private Node first, last = null;
    private int size;

    public Deque() {

    }

    private class Node{
        Item item;
        Node next;
        Node prev;
    }

    private class DequeIterator implements Iterator<Item>
    {
        private Node current;

        DequeIterator(){
            current = first;
        }

        public boolean hasNext() { return current != null; }
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next()
        {
            if(current == null)
                throw new java.util.NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null && last == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if(item == null)
            throw new NullPointerException();

        Node second = first;
        first = new Node();
        first.item = item;
        first.next = second;
        first.prev = null;

        if(second == null)
            last = first;
        else
            second.prev = first;
        size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if(item == null)
            throw new java.lang.NullPointerException();

        Node beforeLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = beforeLast;

        if(beforeLast!= null) {
            beforeLast.next = last;
        }
        else
            first = last;

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if(isEmpty())
            throw  new NoSuchElementException();

        Node oldFirst = first;
        if(first.next != null) {
            first = oldFirst.next;
            first.prev = null;

            oldFirst.prev = null; // ?
            oldFirst.next = null;
        }else
            last = first = null;

        size--;

        return oldFirst.item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if(isEmpty())
            throw  new NoSuchElementException();
        Node oldLast = last;
        if(last.prev != null){
            last = oldLast.prev;
            last.next = null;

            oldLast.prev = null;
            oldLast.next = null; // ?
        }else
            first = last = null;

        size--;
        return oldLast.item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    /*@Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Item item :this) {
            builder.append(item.toString() + " ");
        }
        builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }*/

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();

        for (int i = 0; i < 10; i++) {
            deque.addFirst(i);
        }

        for (int i : deque){
            System.out.print(i);
        }

        System.out.print("\n");

        for (int i = 0; i < 5; i++) {
            deque.removeFirst();
        }

        System.out.println(deque.toString().equals("4 3 2 1 0"));
        System.out.println(deque.size() == 5);

        Deque<Integer> deque2 = new Deque<Integer>();

        for (int i = 0; i < 10; i++) {
            deque2.addLast(i);
        }

        for (int i = 0; i < 5; i++) {
            deque2.removeFirst();
        }

        System.out.println(deque2.toString().equals("5 6 7 8 9"));
        System.out.println(deque2.size() == 5);

    }
}
