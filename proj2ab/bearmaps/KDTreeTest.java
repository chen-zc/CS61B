package bearmaps;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {

    private static Random r = new Random(500);

    private Point randomPoint() {
        double x = r.nextDouble();
        double y = r.nextDouble();
        return new Point(x, y);
    }

    private List<Point> randomPoints(int n) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < n; i ++) {
            points.add(randomPoint());
        }
        return points;
    }

    @Test
    public void testWithPoints() {
        List<Point> list = randomPoints(1000);
        NaivePointSet set = new NaivePointSet(list);
        KDTree kd = new KDTree(list);

        List<Point> query = randomPoints(2000);
        for (Point p: query) {
            Point expected = set.nearest(p.getX(), p.getY());
            Point actual = kd.nearest(p.getX(), p.getY());
            assertEquals(expected, actual);
        }
    }

}
