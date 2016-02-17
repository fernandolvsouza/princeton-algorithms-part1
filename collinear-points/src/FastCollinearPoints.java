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

        for (Point p : points) {
            if (p == null)
                throw new java.lang.NullPointerException();
        }

        //TODO java.lang.IllegalArgumentException

        for (int p = 0; p < points.length; p++) {

            Arrays.sort(points,points[p].slopeOrder());

            int lo = 0;
            int hi = 1;

            while(hi < points.length){
                double slope = points[p].slopeTo(points[hi]);
                double slopeBefore = points[p].slopeTo(points[hi-1]);
                if(slope != slopeBefore){
                    if(hi - lo >= 4){
                        segments.add(new LineSegment(points[lo],points[hi]));
                        numberOfSegments++;

                    }
                    lo = hi;
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
}
