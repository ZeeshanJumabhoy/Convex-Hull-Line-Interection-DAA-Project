import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

public class ConvexHullGrahamScan extends JPanel {
    private List<Point2D> points = new ArrayList<>();
    private List<Point2D> convexHull = new ArrayList<>();
    private final int PANEL_WIDTH = 600;
    private final int PANEL_HEIGHT = 600;

    public ConvexHullGrahamScan() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Color.white);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                points.add(new Point2D.Double(e.getX(), e.getY()));
                repaint();
            }
        });

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "calculateConvexHull");
        getActionMap().put("calculateConvexHull", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long startTime = System.nanoTime();
                calculateConvexHull();
                repaint();
                long endTime = System.nanoTime();

                long executionTime
                        = (endTime - startTime)/100000;

                JOptionPane.showMessageDialog(null, "the execution time is "+ executionTime +" ms",
                        "time", JOptionPane.PLAIN_MESSAGE);
            }
        });
    }

    private void calculateConvexHull() {
        // Sort the points based on polar angle
        Point2D pivot = findPivot(points);
        Collections.sort(points, new PolarAngleComparator(pivot));

        // Initialize the convex hull with the first three points
        Stack<Point2D> hull = new Stack<>();
        hull.push(points.get(0));
        hull.push(points.get(1));
        hull.push(points.get(2));

        // Iterate through the remaining points
        for (int i = 3; i < points.size(); i++) {
            while (orientation(nextToTop(hull), hull.peek(), points.get(i)) != 2) {
                hull.pop();
            }
            hull.push(points.get(i));
        }

        convexHull = new ArrayList<>(hull);
    }

    private Point2D findPivot(List<Point2D> points) {
        Point2D pivot = points.get(0);
        for (Point2D point : points) {
            if (point.getY() < pivot.getY() || (point.getY() == pivot.getY() && point.getX() < pivot.getX())) {
                pivot = point;
            }
        }
        return pivot;
    }

    private int orientation(Point2D p, Point2D q, Point2D r) {
        double val = (q.getY() - p.getY()) * (r.getX() - q.getX()) - (q.getX() - p.getX()) * (r.getY() - q.getY());
        if (val == 0) return 0;  // Collinear
        return (val > 0) ? 1 : 2; // Clockwise or Counterclockwise
    }

    private Point2D nextToTop(Stack<Point2D> stack) {
        Point2D top = stack.pop();
        Point2D nextToTop = stack.peek();
        stack.push(top);
        return nextToTop;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw points
        g2d.setColor(Color.blue);
        for (Point2D point : points) {
            int x = (int) point.getX();
            int y = (int) point.getY();
            g2d.fillOval(x - 5, y - 5, 10, 10);
        }

        // Draw convex hull with a black outline
        g2d.setColor(Color.red);
        if (convexHull.size() > 1) {
            g2d.setColor(Color.black);
            Point2D firstPoint = convexHull.get(0);
            Point2D prevPoint = convexHull.get(convexHull.size() - 1);
            for (Point2D point : convexHull) {
                int x1 = (int) prevPoint.getX();
                int y1 = (int) prevPoint.getY();
                int x2 = (int) point.getX();
                int y2 = (int) point.getY();
                g2d.drawLine(x1, y1, x2, y2);
                prevPoint = point;
            }
            // Connect the last point to the first point
            int x1 = (int) prevPoint.getX();
            int y1 = (int) prevPoint.getY();
            int x2 = (int) firstPoint.getX();
            int y2 = (int) firstPoint.getY();
            g2d.drawLine(x1, y1, x2, y2);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Convex Hull Graham Scan");
            ConvexHullGrahamScan convexHullGrahamScan = new ConvexHullGrahamScan();
            frame.add(convexHullGrahamScan);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static class PolarAngleComparator implements Comparator<Point2D> {
        private Point2D pivot;

        public PolarAngleComparator(Point2D pivot) {
            this.pivot = pivot;
        }

        @Override
        public int compare(Point2D p1, Point2D p2) {
            double angle1 = Math.atan2(p1.getY() - pivot.getY(), p1.getX() - pivot.getX());
            double angle2 = Math.atan2(p2.getY() - pivot.getY(), p2.getX() - pivot.getX());

            if (angle1 < angle2) return -1;
            if (angle1 > angle2) return 1;

            // If angles are the same, compare distances to pivot
            double dist1 = pivot.distance(p1);
            double dist2 = pivot.distance(p2);

            return Double.compare(dist1, dist2);
        }
    }
}
