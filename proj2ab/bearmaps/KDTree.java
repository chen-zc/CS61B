package bearmaps;

import java.util.List;

public class KDTree implements PointSet {

    private Node root;

    private class Node {
        private Point val;
        private Node left, right;
        private boolean horizontal;

        public Node(Point p, boolean b) {
            val = p;
            horizontal = b;
        }
    }

    public Node add(Node n, Point p, boolean b) {
        if (n == null) {
            return new Node(p, b);
        }
        if (p.equals(n.val)) {
            return n;
        }
        double i, j;
        if (n.horizontal) {
            i = n.val.getX();
            j = p.getX();
        } else {
            i = n.val.getY();
            j = p.getY();
        }
        if (Double.compare(i, j) > 0) {
            n.left = add(n.left, p, !b);
        } else {
            n.right = add(n.right, p, !b);
        }
        return n;
    }

    public KDTree(List<Point> points) {
        for (Point p : points) {
            root = add(root, p, true);
        }
    } //You can assume points has at least size 1.

    @Override
    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        Point best = root.val;
        return nearest(root, goal, best);
    } //Returns the closest point to the inputted coordinates.

    private Point nearest(Node n, Point p, Point best) {
        if (n == null) {
            return best;
        }
        if (Point.distance(n.val, p) < Point.distance(best, p)) {
            best = n.val;
        }
        double i, j;
        Node good, bad;
        if (n.horizontal) {
            i = n.val.getX();
            j = p.getX();
        } else {
            i = n.val.getY();
            j = p.getY();
        }
        if (Double.compare(i, j) > 0) {
            good = n.left;
            bad = n.right;
        } else {
            good = n.right;
            bad = n.left;
        }
        best = nearest(good, p, best);
        if (worthLooking(n, p, best)) {
            best = nearest(bad, p, best);
        }
        return best;
    }

    private boolean worthLooking(Node n, Point p, Point best) {
        double diff;
        if (n.horizontal) {
            diff = n.val.getX() - p.getX();
        } else {
            diff = n.val.getY() - p.getY();
        }
        diff = diff * diff;
        if (diff < Point.distance(p, best)) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        Point p1 = new Point(2, 3); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 5);
        Point p4 = new Point(3, 3);
        Point p5 = new Point(1, 5);
        Point p6 = new Point(4, 4);
        KDTree kd = new KDTree(List.of(p1, p2, p3, p4, p5, p6));
        Point best = kd.nearest(0,7);
    }
}
