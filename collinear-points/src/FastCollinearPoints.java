import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by uq4n on 17/02/2016.
 */
public class FastCollinearPoints {

    private List<LineSegment> segments = new ArrayList<>();

    private int numberOfSegments = 0;

    public FastCollinearPoints(Point[] points) {

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

        for (int p = 0; p < naturalSorted.length; p++) {
            Point pointP = naturalSorted[p];

            Point[] slopeSorted = new Point[naturalSorted.length];

            for (int i = 0; i < naturalSorted.length; i++) {
                slopeSorted[i] = naturalSorted[i];
            }

            Arrays.sort(slopeSorted, pointP.slopeOrder());

            int lo = 0, hi = 0;

            while (hi < slopeSorted.length) {
                if (hi + 1 == slopeSorted.length || pointP.slopeTo(slopeSorted[hi + 1]) != pointP.slopeTo(slopeSorted[hi])) {
                    if (hi - lo >= 2) {
                        if (pointP.compareTo(slopeSorted[lo]) < 0) {
                            segments.add(new LineSegment(pointP, slopeSorted[hi]));
                            numberOfSegments++;
                        }
                    }
                    lo = hi + 1;
                }
                hi++;
            }
        }
    }

    public int numberOfSegments() {
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        return (LineSegment[]) segments.toArray(new LineSegment[segments.size()]);
    }

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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
}
