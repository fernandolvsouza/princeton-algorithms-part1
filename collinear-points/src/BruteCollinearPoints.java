import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by uq4n on 17/02/2016.
 */
public class BruteCollinearPoints {

    private List<LineSegment> segments = new ArrayList<>();
    private int numberOfSegments = 0;

    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new java.lang.NullPointerException();

        Point[] naturalSorted = new Point[points.length];

        for (int i = 0; i < points.length; i++) {
            naturalSorted[i] = points[i];
        }

        Arrays.sort(naturalSorted);

        for (int i = 0; i < naturalSorted.length; i++) {
            if (naturalSorted[i] == null)
                throw new java.lang.NullPointerException();

            if (i + 1 == naturalSorted.length)
                continue;

            if (naturalSorted[i].compareTo(naturalSorted[i + 1]) == 0)
                throw new java.lang.IllegalArgumentException();
        }

        for (int p = 0; p < naturalSorted.length - 3; p++) {
            for (int q = p + 1; q < naturalSorted.length - 2; q++) {
                for (int r = q + 1; r < naturalSorted.length - 1; r++) {
                    for (int s = r + 1; s < naturalSorted.length; s++) {
                        double slopePQ = naturalSorted[p].slopeTo(naturalSorted[q]);
                        double slopePR = naturalSorted[p].slopeTo(naturalSorted[r]);
                        double slopePS = naturalSorted[p].slopeTo(naturalSorted[s]);


                        if (slopePQ == slopePR && slopePQ == slopePS) {
                            Point[] line = new Point[4];
                            line[0] = naturalSorted[p];
                            line[1] = naturalSorted[q];
                            line[2] = naturalSorted[r];
                            line[3] = naturalSorted[s];

                            Arrays.sort(line);
                            LineSegment segment = new LineSegment(line[0], line[3]);
                            segments.add(segment);
                            numberOfSegments++;
                        }

                    }
                }
            }
        }

    }    // finds all line segments containing 4 points

    public int numberOfSegments() {
        return numberOfSegments;
    }        // the number of line segments

    public LineSegment[] segments() {
        return (LineSegment[]) segments.toArray(new LineSegment[segments.size()]);
    }                // the line segments

    public static void main(String[] args) {

        // read the N points from a file
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
}