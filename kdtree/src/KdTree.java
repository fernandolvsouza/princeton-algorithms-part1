import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uq4n on 29/02/2016.
 */
public class KdTree {
    Node root;

    private static class Node {
        Node(Point2D p, RectHV rect, int level) {
            this.p = p;
            this.rect = rect;
            this.level = level;
        }

        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node left;        // the left/bottom subtree
        private Node right;        // the right/top subtree
        private int level;

        public void draw() {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.01);
            p.draw();

            StdDraw.setPenColor(level % 2 == 1 ? StdDraw.RED :  StdDraw.BLUE);
            StdDraw.setPenRadius(.001);

            if(level % 2 == 1)
                StdDraw.line(p.x(),rect.ymin(),p.x(),rect.ymax());
            else
                StdDraw.line(rect.xmin(),p.y(),rect.xmax(),p.y());
        }
    }

    public KdTree() {
        root = null;
    }                               // construct an empty set of points

    public boolean isEmpty() {
        return root == null;
    }                      // is the set empty?

    public int size() {
        return 0;
    }                         // number of points in the set

    public void insert(Point2D p) {
        if (p == null)
            throw new java.lang.NullPointerException();
        root = put(root,null, p, 1);
    }


    private Node put(Node x, Node parent, Point2D p, int level) {

        if (x == null){
            return new Node(p, createRect(parent,p) , level);
        }

        int cmp = compare(p, x.p, level);

        if (cmp < 0) x.left = put(x.left,x, p, ++level);
        else if (cmp > 0) x.right = put(x.right,x, p, ++level);
        else if (p.equals(x.p))
                x.p = p;
            else
                x.right = put(x.right, x, p, ++level);
        return x;
    }

    private static RectHV createRect(Node parent,Point2D p){
        if(parent == null)
            return new RectHV(0,0,1,1);
        if(compare(p,parent.p,parent.level) < 0) { // p left
            if(parent.level % 2 == 1)
                return new RectHV(parent.rect.xmin(),parent.rect.ymin(),parent.p.x(),parent.rect.ymax());
            else
                return new RectHV(parent.rect.xmin(),parent.rect.ymin(),parent.rect.xmax(),parent.p.y());
        }else{
            if(parent.level % 2 == 1)
                return new RectHV(parent.p.x(),parent.rect.ymin(),parent.rect.xmax(),parent.rect.ymax());
            else
                return new RectHV(parent.rect.xmin(),parent.p.y(),parent.rect.xmax(),parent.rect.ymax());
        }
    }

    private static int compare(Point2D p1, Point2D p2, int level) {
        if (level % 2 == 1)
            return Double.compare(p1.x(), p2.x());

        return Double.compare(p1.y(), p2.y());
    }

    public boolean contains(Point2D p) {
        if (p == null)
            throw new java.lang.NullPointerException();
        return get(root, p) != null;
    }

    private Point2D get(Node x, Point2D p) {
        if (x == null) return null;
        int cmp = compare(p, x.p, x.level);

        if (cmp < 0)        return get(x.left, p);
        else if (cmp > 0)   return get(x.right, p);
        else                return p.equals(x.p) ? x.p : get(x.right, p);
    }

    public void draw() {
        draw(root);
    }

    private void draw(Node x) {
        if (x == null) return;
        x.draw();
        draw(x.left);
        draw(x.right);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new java.lang.NullPointerException();
        List<Point2D> points = new ArrayList<>();

        range(root,rect,points);
        return points;
    }

    private void range(Node x, RectHV rect, List points){
        if( x == null || !rect.intersects(x.rect))
            return ;
        if(rect.contains(x.p))
            points.add(x.p);
        range(x.left,rect,points);
        range(x.right,rect,points);
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new java.lang.NullPointerException();

        return nearest(root, p, root.p);
    }

    private Point2D nearest(Node x , Point2D qp, Point2D closestPoint){
        if(x == null)
            return closestPoint;

        Double closestDistanceSoFar = closestPoint.distanceSquaredTo(qp);

        if(closestDistanceSoFar < x.rect.distanceSquaredTo(qp))
            return closestPoint;

        if(closestDistanceSoFar > x.p.distanceSquaredTo(qp))
            closestPoint = x.p;

        int cmp = compare(qp, x.p, x.level);

        if (cmp < 0) {
            closestPoint = nearest(x.left, qp, closestPoint);
            closestPoint = nearest(x.right, qp, closestPoint);
        } else{
            closestPoint = nearest(x.right, qp, closestPoint);
            closestPoint = nearest(x.left, qp, closestPoint);
        }

        return closestPoint;
    }

    public static void main(String[] args) {
        Point2D p = new Point2D(0.7, 0.2);
        Point2D p1 = new Point2D(0.5, 0.4);
        Point2D p2 = new Point2D(0.2, 0.3);
        Point2D p3 = new Point2D(0.4, 0.7);
        Point2D p4 = new Point2D(0.9, 0.6);

        KdTree kdtree = new KdTree();
        kdtree.insert(p);
        kdtree.insert(p1);
        kdtree.insert(p2);
        kdtree.insert(p3);
        kdtree.insert(p4);

        System.out.println(kdtree.contains(new Point2D(0.5, 0.61)));
        kdtree.draw();
    }
}
