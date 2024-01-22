import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class QuickHullSwing extends JFrame {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;

    private List<Point> points = new ArrayList<>();
    private List<Point> convexHull = new ArrayList<>();
    private boolean isAnimationStarted = false;
    private JPanel mainPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new QuickHullSwing().setVisible(true);
        });
    }

    public QuickHullSwing() {
        setTitle("Quickhull Convex Hull");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);

        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (!isAnimationStarted) {
                    drawOriginalPoints(g);
                } else {
                    drawConvexHull(g);
                }
            }
        };

        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isAnimationStarted) {
                    double x = e.getX();
                    double y = e.getY();
                    Point newPoint = new Point(x, y);
                    points.add(newPoint);
                    mainPanel.repaint();
                }
            }
        });

        mainPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && !isAnimationStarted && points.size() >= 3) {
                    startAnimation();
                }
            }
        });

        mainPanel.setFocusable(true);

        add(mainPanel);
    }

    private void drawOriginalPoints(Graphics g) {
        g.setColor(Color.BLUE);
        for (Point p : points) {
            g.fillOval((int) p.getX() - 3, (int) p.getY() - 3, 6, 6);
        }
    }

    private void drawConvexHull(Graphics g) {
        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.BLACK);

        if (convexHull.size() < 2) {
            return;
        }

        Point first = convexHull.get(0);
        g.drawLine((int) first.getX(), (int) first.getY(), (int) convexHull.get(convexHull.size() - 1).getX(),
                (int) convexHull.get(convexHull.size() - 1).getY());

        for (int i = 1; i < convexHull.size(); i++) {
            Point current = convexHull.get(i);
            g.drawLine((int) first.getX(), (int) first.getY(), (int) current.getX(), (int) current.getY());
            first = current;
        }

        g.setColor(Color.BLUE);
        for (Point p : points) {
            g.fillOval((int) p.getX() - 3, (int) p.getY() - 3, 6, 6);
        }
    }

    private void stopAnimation() {
        isAnimationStarted = false;

    }

    private void startAnimation() {
        isAnimationStarted = true;
        convexHull.clear();
        mainPanel.repaint();
        long startTime = System.nanoTime();

        calculateConvexHull();
        long endTime = System.nanoTime();

        long executionTime
                = (endTime - startTime)/10000;

        JOptionPane.showMessageDialog(this, "the execution time is "+ executionTime +" ms",
                "time", JOptionPane.PLAIN_MESSAGE);

    }

    private void calculateConvexHull() {
        // Clear previous convex hull
        convexHull.clear();

        // Find the leftmost and rightmost points
        Point leftmost = points.get(0);
        Point rightmost = points.get(0);

        for (Point point : points) {
            if (point.getX() < leftmost.getX()) {
                leftmost = point;
            }
            if (point.getX() > rightmost.getX()) {
                rightmost = point;
            }
        }

        convexHull.add(leftmost);
        convexHull.add(rightmost);

        // Separate points to the left and right of the line formed by leftmost and rightmost
        List<Point> leftSet = new ArrayList<>();
        List<Point> rightSet = new ArrayList<>();

        for (Point point : points) {
            if (isLeft(leftmost, rightmost, point)) {
                leftSet.add(point);
            } else if (isLeft(rightmost, leftmost, point)) {
                rightSet.add(point);
            }
        }

        // Recursively find the convex hull for each set
        findHull(leftmost, rightmost, leftSet);
        findHull(rightmost, leftmost, rightSet);

        mainPanel.repaint();
    }

    private void findHull(Point p1, Point p2, List<Point> pointSet) {
        if (pointSet.isEmpty()) {
            return;
        }

        double maxDistance = -1;
        Point farthestPoint = null;

        for (Point point : pointSet) {
            double distance = distanceToLine(p1, p2, point);
            if (distance > maxDistance) {
                maxDistance = distance;
                farthestPoint = point;
            }
        }

        if (farthestPoint != null) {
            convexHull.add(convexHull.indexOf(p2), farthestPoint);

            List<Point> leftSet = new ArrayList<>();
            List<Point> rightSet = new ArrayList<>();

            for (Point point : pointSet) {
                if (isLeft(p1, farthestPoint, point)) {
                    leftSet.add(point);
                }
            }

            for (Point point : pointSet) {
                if (isLeft(farthestPoint, p2, point)) {
                    rightSet.add(point);
                }
            }

            findHull(p1, farthestPoint, leftSet);
            findHull(farthestPoint, p2, rightSet);
        }
    }

    private double distanceToLine(Point p1, Point p2, Point point) {
        return Math.abs((p2.getY() - p1.getY()) * point.getX() - (p2.getX() - p1.getX()) * point.getY()
                + p2.getX() * p1.getY() - p2.getY() * p1.getX())
                / Math.sqrt((p2.getY() - p1.getY()) * (p2.getY() - p1.getY()) +
                (p2.getX() - p1.getX()) * (p2.getX() - p1.getX()));
    }

    private boolean isLeft(Point p1, Point p2, Point p) {
        return ((p2.getX() - p1.getX()) * (p.getY() - p1.getY()) -
                (p2.getY() - p1.getY()) * (p.getX() - p1.getX())) > 0;
    }

    public static class Point {
        private double x;
        private double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }
}
