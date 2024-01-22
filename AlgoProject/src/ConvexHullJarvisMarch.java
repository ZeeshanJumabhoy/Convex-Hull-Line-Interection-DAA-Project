import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class ConvexHullJarvisMarch extends JPanel {
    private List<Point2D> points = new ArrayList<>();
    private List<Point2D> convexHull = new ArrayList<>();
    private final int PANEL_WIDTH = 600;
    private final int PANEL_HEIGHT = 600;

    public ConvexHullJarvisMarch() {
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
        if (points.size() < 3) {
            // Convex hull is not possible with less than 3 points
            return;
        }

        convexHull.clear();

        // Find the point with the lowest y-coordinate (and leftmost if tied)
        Point2D pivot = points.get(0);
        for (Point2D point : points) {
            if (point.getY() < pivot.getY() || (point.getY() == pivot.getY() && point.getX() < pivot.getX())) {
                pivot = point;
            }
        }

        convexHull.add(pivot);

        do {
            Point2D nextPoint = points.get(0);
            for (Point2D point : points) {
                if (point.equals(convexHull.get(convexHull.size() - 1))) {
                    continue;
                }

                int orientation = orientation(convexHull.get(convexHull.size() - 1), point, nextPoint);

                if (nextPoint.equals(convexHull.get(convexHull.size() - 1)) || orientation == 1) {
                    nextPoint = point;
                }
            }

            convexHull.add(nextPoint);

        } while (!convexHull.get(convexHull.size() - 1).equals(pivot));

        convexHull.remove(convexHull.size() - 1); // Remove the duplicate pivot point
    }

    private int orientation(Point2D p, Point2D q, Point2D r) {
        double val = (q.getY() - p.getY()) * (r.getX() - q.getX()) - (q.getX() - p.getX()) * (r.getY() - q.getY());
        if (val == 0) return 0;  // Collinear
        return (val > 0) ? 1 : 2; // Clockwise or Counterclockwise
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
            JFrame frame = new JFrame("Convex Hull Jarvis March");
            ConvexHullJarvisMarch convexHullJarvisMarch = new ConvexHullJarvisMarch();
            frame.add(convexHullJarvisMarch);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
