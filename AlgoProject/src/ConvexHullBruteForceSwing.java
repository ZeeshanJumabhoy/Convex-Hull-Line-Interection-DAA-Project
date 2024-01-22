import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class ConvexHullBruteForceSwing extends JFrame {
    private List<Point> points = new ArrayList<>();
    private List<Point> convexHull = new ArrayList<>();

    private static class Point {
        double x, y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public ConvexHullBruteForceSwing() {
        setSize(600, 600);
        setTitle("Convex Hull - Brute Force");
        setLocationRelativeTo(null);
        JPanel root = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawPoints(g);
                drawConvexHull(g);
            }
        };
        root.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addPoint(e.getX(), e.getY(), root);
                repaint();
            }
        });

        JButton computeButton = new JButton("Compute Convex Hull");
        computeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                computeConvexHull();
                repaint();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(computeButton);

        add(root, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addPoint(int x, int y, JPanel root) {
        points.add(new Point(x, y));
        Graphics g = root.getGraphics();
        g.setColor(Color.BLUE);
        g.fillOval(x - 3, y - 3, 6, 6);
    }

    private void computeConvexHull() {
        if (points.size() < 3) {
            System.out.println("Convex hull not possible with less than 3 points.");
            return;
        }
        long startTime = System.nanoTime();
        convexHull = bruteForce();
        long endTime = System.nanoTime();

        long executionTime
                = (endTime - startTime)/100000;

        JOptionPane.showMessageDialog(this, "the execution time is "+ executionTime +" ms",
                "time", JOptionPane.PLAIN_MESSAGE);
    }

    private List<Point> bruteForce() {
        List<Point> hull = new ArrayList<>();
        int n = points.size();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    boolean valid = true;
                    for (int k = 0; k < n; k++) {
                        if (k != i && k != j) {
                            double crossProduct = crossProduct(points.get(i), points.get(j), points.get(k));
                            if (crossProduct > 0) {
                                valid = false;
                                break;
                            }
                        }
                    }
                    if (valid) {
                        hull.add(points.get(i));
                        hull.add(points.get(j));
                    }
                }
            }
        }

        return hull;
    }

    private double crossProduct(Point a, Point b, Point c) {
        return (b.x - a.x) * (c.y - a.y) - (c.x - a.x) * (b.y - a.y);
    }

    private void drawConvexHull(Graphics g) {
        g.setColor(Color.RED);
        for (int i = 0; i < convexHull.size(); i += 2) {
            int x1 = (int) convexHull.get(i).x;
            int y1 = (int) convexHull.get(i).y;
            int x2 = (int) convexHull.get(i + 1).x;
            int y2 = (int) convexHull.get(i + 1).y;
            g.drawLine(x1, y1, x2, y2);
        }
    }

    private void drawPoints(Graphics g) {
        g.setColor(Color.BLUE);
        for (Point point : points) {
            int x = (int) point.x;
            int y = (int) point.y;
            g.fillOval(x - 3, y - 3, 6, 6);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ConvexHullBruteForceSwing().setVisible(true);
            }
        });
    }
}
