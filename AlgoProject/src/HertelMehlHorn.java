import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HertelMehlHorn extends JFrame {
    private Point point1, point2, point3, point4;
    private boolean linesDrawn = false;

    public HertelMehlHorn() {
        setTitle("HertelMehlHorn Intersection Checker");
        setSize(500, 500);
        setLocationRelativeTo(null);
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                handleMouseClick(e);
            }
        });

        JButton checkIntersectionButton = new JButton("\u2714 Check Intersection"); // Unicode checkmark symbol
        checkIntersectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (linesDrawn) {
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

    private void handleMouseClick(java.awt.event.MouseEvent e) {
        if (!linesDrawn) {
            if (point1 == null) {
                point1 = e.getPoint();
            } else {
                point2 = e.getPoint();
                linesDrawn = true;
                repaint();
            }
        } else {
            if (point3 == null) {
                point3 = e.getPoint();
            } else {
                point4 = e.getPoint();
                repaint();
            }
        }
    }

    private void checkIntersection() {
        long startTime = System.nanoTime();
        List<LineSegment> segments = new ArrayList<>();
        segments.add(new LineSegment(point1, point2));
        segments.add(new LineSegment(point3, point4));

        Collections.sort(segments);

        List<LineSegment> activeSegments = new ArrayList<>();

        for (LineSegment segment : segments) {
            Graphics2D g2d = (Graphics2D) getGraphics();
            if (segment.getStart().equals(point1) && segment.getEnd().equals(point2)) {
                g2d.setColor(Color.RED);
            } else {
                g2d.setColor(Color.BLUE);
            }
            g2d.drawLine(segment.getStart().x, segment.getStart().y, segment.getEnd().x, segment.getEnd().y);
            g2d.fillOval(segment.getStart().x - 5, segment.getStart().y - 5, 10, 10);
            g2d.fillOval(segment.getEnd().x - 5, segment.getEnd().y - 5, 10, 10);

            activeSegments.add(segment);
            Collections.sort(activeSegments, Comparator.comparingInt(s -> s.getEnd().y));

            int currentIndex = activeSegments.indexOf(segment);
            if (currentIndex > 0) {
                LineSegment prevSegment = activeSegments.get(currentIndex - 1);
                if (segment.getStart().y < prevSegment.getEnd().y) {
                    JOptionPane.showMessageDialog(this, "The two line segments intersect.");
                    long endTime = System.nanoTime();
                    long executionTime = (endTime - startTime) / 1000000;
                    JOptionPane.showMessageDialog(this, "The execution time is " + executionTime + " ms");
                    return;
                }
            }
        }

        JOptionPane.showMessageDialog(this, "The two line segments do not intersect.");
    }


    private void clear() {
        point1 = point2 = point3 = point4 = null;
        linesDrawn = false;
        repaint();
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        if (point1 != null && point2 != null) {
            g2d.setColor(Color.RED);
            g2d.drawLine(point1.x, point1.y, point2.x, point2.y);
            g2d.fillOval(point1.x - 5, point1.y - 5, 10, 10);
            g2d.fillOval(point2.x - 5, point2.y - 5, 10, 10);
        }

        if (point3 != null && point4 != null) {
            g2d.setColor(Color.BLUE);
            g2d.drawLine(point3.x, point3.y, point4.x, point4.y);
            g2d.fillOval(point3.x - 5, point3.y - 5, 10, 10);
            g2d.fillOval(point4.x - 5, point4.y - 5, 10, 10);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HertelMehlHorn sec = new HertelMehlHorn();
            sec.setVisible(true);
        });
    }

    private static class LineSegment implements Comparable<LineSegment> {
        private final Point start, end;

        public LineSegment(Point start, Point end) {
            this.start = start;
            this.end = end;
        }

        public Point getStart() {
            return start;
        }

        public Point getEnd() {
            return end;
        }

        @Override
        public int compareTo(LineSegment other) {
            return Integer.compare(this.start.x, other.start.x);
        }
    }
}
