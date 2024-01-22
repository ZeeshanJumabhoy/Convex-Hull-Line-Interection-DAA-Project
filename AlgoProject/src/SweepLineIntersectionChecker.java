import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class SweepLineIntersectionChecker extends JFrame {
    private List<Point> points;
    private boolean linesDrawn = false;

    public SweepLineIntersectionChecker() {
        setTitle("Sweep Line Intersection Checker");
        setSize(500, 500);
        setLocationRelativeTo(null);
        points = new ArrayList<>();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!linesDrawn) {
                    points.add(e.getPoint());
                    repaint();
                }
            }
        });

        JButton checkIntersectionButton = new JButton("\u2714 Check Intersection"); // Unicode checkmark symbol
        checkIntersectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (points.size() >= 4) {
                    checkIntersection();
                }
            }
        });

        JButton clearButton = new JButton("\u21BB Clear"); // Unicode clockwise arrow symbol
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(checkIntersectionButton);
        buttonPanel.add(clearButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void checkIntersection() {
        long startTime = System.nanoTime();
        List<LineSegment> segments = new ArrayList<>();

        // Create line segments from the points
        for (int i = 0; i < points.size(); i += 2) {
            segments.add(new LineSegment(points.get(i), points.get(i + 1)));
        }

        // Sort the line segments by their y-coordinate
        Collections.sort(segments, Comparator.comparingInt(LineSegment::getMinY));

        TreeSet<LineSegment> activeSegments = new TreeSet<>(Comparator.comparingInt(LineSegment::getMaxY));

        for (LineSegment segment : segments) {
            activeSegments.add(segment);

            LineSegment lowerNeighbor = activeSegments.lower(segment);
            LineSegment higherNeighbor = activeSegments.higher(segment);

            if (lowerNeighbor != null && lowerNeighbor.intersects(segment)) {
                JOptionPane.showMessageDialog(this, "The two line segments intersect.");
                long endTime = System.nanoTime();
                long executionTime = (endTime - startTime) / 1000000;
                JOptionPane.showMessageDialog(this, "The execution time is " + executionTime + " ms");
                return;
            }

            if (higherNeighbor != null && segment.intersects(higherNeighbor)) {
                JOptionPane.showMessageDialog(this, "The two line segments intersect.");
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "The two line segments do not intersect.");
    }

    private void clear() {
        points.clear();
        linesDrawn = false;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        for (Point point : points) {
            g2d.setColor(Color.BLUE);
            g2d.fillOval(point.x - 5, point.y - 5, 10, 10);
        }

        for (int i = 0; i < points.size(); i += 2) {
            g2d.setColor(Color.RED);
            g2d.drawLine(points.get(i).x, points.get(i).y, points.get(i + 1).x, points.get(i + 1).y);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SweepLineIntersectionChecker intersectionChecker = new SweepLineIntersectionChecker();
            intersectionChecker.setVisible(true);
        });
    }

    private static class LineSegment {
        private Point start;
        private Point end;

        public LineSegment(Point start, Point end) {
            this.start = start;
            this.end = end;
        }

        public int getMinY() {
            return Math.min(start.y, end.y);
        }

        public int getMaxY() {
            return Math.max(start.y, end.y);
        }

        public boolean intersects(LineSegment other) {
            return Line2D.linesIntersect(start.x, start.y, end.x, end.y, other.start.x, other.start.y, other.end.x, other.end.y);
        }
    }
}
