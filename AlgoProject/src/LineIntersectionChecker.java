import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;

public class LineIntersectionChecker extends JFrame {
    private Point point1, point2, point3, point4;
    private boolean linesDrawn = false;

    public LineIntersectionChecker() {
        setTitle("Line Intersection Checker");
        setSize(500, 500);
        setLocationRelativeTo(null);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
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

    private void checkIntersection() {
        long startTime = System.nanoTime();
        // Use the CCW algorithm to check if the points are in counterclockwise order
        int orientation1 = orientation(point1, point2, point3);
        int orientation2 = orientation(point1, point2, point4);
        int orientation3 = orientation(point3, point4, point1);
        int orientation4 = orientation(point3, point4, point2);

        if ((orientation1 != orientation2) && (orientation3 != orientation4)) {
            JOptionPane.showMessageDialog(this, "The two line segments intersect.");
            long endTime = System.nanoTime();
            long executionTime = (endTime - startTime) / 1000000;
            JOptionPane.showMessageDialog(this, "The execution time is " + executionTime +" ms");
            return;
        } else {
            JOptionPane.showMessageDialog(this, "The two line segments do not intersect.");
        }
    }

    private int orientation(Point p, Point q, Point r) {
        int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
        if (val == 0) return 0; // Collinear
        return (val > 0) ? 1 : 2; // Clockwise or Counterclockwise
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
            LineIntersectionChecker lineIntersectionChecker = new LineIntersectionChecker();
            lineIntersectionChecker.setVisible(true);
        });
    }
}
