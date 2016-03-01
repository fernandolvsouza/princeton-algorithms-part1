import edu.princeton.cs.algs4.*;

import java.util.*;

/**
 * Created by uq4n on 26/02/2016.
 */
public class PointSET {

    private RedBlackBST<Point2D, Point2D> points;

    public PointSET() {
        points = new RedBlackBST<>();
    }                               // construct an empty set of points

    public boolean isEmpty() {
        return points.isEmpty();
    }                      // is the set empty?

    public int size() {
        return points.size();
    }                         // number of points in the set

    public void insert(Point2D p) {
        if (p == null)
            throw new java.lang.NullPointerException();
        points.put(p, p);
    }             // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        if (p == null)
            throw new java.lang.NullPointerException();
        return points.contains(p);
    }           // does the set contain point p?

    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        for (Point2D p : points.keys()) {
            p.draw();
        }
    }                         // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new java.lang.NullPointerException();
        List<Point2D> r = new ArrayList<>();
        for (Point2D p : points.keys()) {
            if (rect.contains(p))
                r.add(p);
        }
        return r;

    }             // all points that are inside the rectangle

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new java.lang.NullPointerException();

        Point2D nearest = null;
        double nearest_distance = Double.MAX_VALUE;
        for (Point2D other : points.keys()) {
            double d = other.distanceSquaredTo(p);

            if (other.distanceSquaredTo(p) < nearest_distance) {
                nearest_distance = d;
                nearest = other;
            }
        }
        return nearest;
    }             // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);

        StdDraw.show(0);

        // initialize the two data structures with point from standard input
        PointSET brute = new PointSET();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            brute.insert(p);
        }
        brute.draw();
        Point2D p = new Point2D(0.4, 0);
        Point2D n = brute.nearest(p);

        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(.01);

        p.draw();
        n.draw();
        System.out.println("near [ " + p + "] = " + n);

        RectHV rect = new RectHV(0.0, 0.01, 0.5, 0.5);
        Iterable<Point2D> matches = brute.range(rect);

        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.setPenRadius(.01);
        rect.draw();
        for (Point2D m : matches) {
            m.draw();
        }

        StdDraw.show(50);
    }
}
