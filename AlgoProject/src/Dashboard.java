import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;

public class Dashboard extends JFrame {


    public Dashboard() {
        setTitle("Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);
        setUndecorated(true);

        // Create a custom border with a glow effect
        Border glowBorder = new GlowBorder(Color.CYAN, 10, 20);

        // Apply the custom border to the frame
        getRootPane().setBorder(glowBorder);

        // Create the left panel with a darker color
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.DARK_GRAY);
        leftPanel.setPreferredSize(new Dimension(200, 300));

        // Create the right panel with a lighter color
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.LIGHT_GRAY);
        rightPanel.setPreferredSize(new Dimension(200, 300));


        ImageIcon imageIcon = new ImageIcon("QuickHull-removebg-preview.png");
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(20,0,150,200);
        leftPanel.add(imageLabel);

        // Create a button to close the project
        JButton closeButton = new JButton("X");
        closeButton.setForeground(Color.BLACK);
        closeButton.setBackground(Color.RED);
        closeButton.setOpaque(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setBorderPainted(false);
        closeButton.setFont(new Font("Arial", Font.PLAIN, 20));

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });



        JLabel algorithmsLabel = new JLabel("ALGORITHMS", SwingConstants.CENTER);
        algorithmsLabel.setFont(new Font("Algerian", Font.BOLD, 16));
        algorithmsLabel.setForeground(Color.BLACK);
        algorithmsLabel.setBounds(200,0,200,100);
        rightPanel.add(algorithmsLabel);

        JButton bruteForceButton = new JButton("Brute Force");
        bruteForceButton.setFocusPainted(false);
        bruteForceButton.setBounds(200,150,200,50);
        bruteForceButton.setBackground(new Color(192, 192, 192));
        bruteForceButton.setForeground(Color.BLACK);
        bruteForceButton.setBorderPainted(false);
        bruteForceButton.setOpaque(true);
        bruteForceButton.setFont(new Font("Arial", Font.BOLD, 12));
        rightPanel.add(bruteForceButton, BorderLayout.CENTER);
        bruteForceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConvexHullBruteForceSwing.main(null);
            }
        });
        bruteForceButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bruteForceButton.setBackground(new Color(169, 169, 169)); // Darker gray on hover
                bruteForceButton.setBorderPainted(true);
                bruteForceButton.setBorder(new Dashboard.DottedBorder(20, true)); // Add shadow effect
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                bruteForceButton.setBackground(new Color(192, 192, 192)); // Restore original color
                bruteForceButton.setBorderPainted(false);
            }
        });

        JButton divideAndConquerButton = new JButton("Divide And Conquer");
        divideAndConquerButton.setBounds(200,220,200,50);
        divideAndConquerButton.setFocusPainted(false);
        divideAndConquerButton.setBackground(new Color(192, 192, 192));
        divideAndConquerButton.setForeground(Color.BLACK);
        divideAndConquerButton.setFont(new Font("Arial", Font.BOLD, 12));
        divideAndConquerButton.setBorderPainted(false);
        divideAndConquerButton.setOpaque(true);
        rightPanel.add(divideAndConquerButton ,BorderLayout.CENTER);

        divideAndConquerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConvexHullDivideAndConquer.main(null);
            }
        });
        divideAndConquerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                divideAndConquerButton.setBackground(new Color(169, 169, 169)); // Darker gray on hover
                divideAndConquerButton.setBorderPainted(true);
                divideAndConquerButton.setBorder(new Dashboard.DottedBorder(20, true)); // Add shadow effect
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                divideAndConquerButton.setBackground(new Color(192, 192, 192)); // Restore original color
                divideAndConquerButton.setBorderPainted(false);
            }
        });

        JButton grahamScanButton = new JButton("Graham Scan");
        grahamScanButton.setFocusPainted(false);
        grahamScanButton.setBounds(200,290,200,50);
        grahamScanButton.setBackground(new Color(192, 192, 192));
        grahamScanButton.setForeground(Color.BLACK);
        grahamScanButton.setFont(new Font("Arial", Font.BOLD, 12));
        grahamScanButton.setBorderPainted(false);
        grahamScanButton.setOpaque(true);
        rightPanel.add(grahamScanButton, BorderLayout.CENTER);
        grahamScanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConvexHullGrahamScan.main(null);
            }
        });
        grahamScanButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                grahamScanButton.setBackground(new Color(169, 169, 169)); // Darker gray on hover
                grahamScanButton.setBorderPainted(true);
                grahamScanButton.setBorder(new Dashboard.DottedBorder(20, true)); // Add shadow effect
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                grahamScanButton.setBackground(new Color(192, 192, 192)); // Restore original color
                grahamScanButton.setBorderPainted(false);
            }
        });

        JButton jarvisMarchButton = new JButton("Jarvis March");
        jarvisMarchButton.setFocusPainted(false);
        jarvisMarchButton.setBounds(200,360,200,50);
        jarvisMarchButton.setBackground(new Color(192, 192, 192));
        jarvisMarchButton.setForeground(Color.BLACK);
        jarvisMarchButton.setFont(new Font("Arial", Font.BOLD, 12));
        jarvisMarchButton.setBorderPainted(false);
        jarvisMarchButton.setOpaque(true);
        rightPanel.add(jarvisMarchButton , BorderLayout.CENTER);
        jarvisMarchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConvexHullJarvisMarch.main(null);
            }
        });
        jarvisMarchButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jarvisMarchButton.setBackground(new Color(169, 169, 169)); // Darker gray on hover
                jarvisMarchButton.setBorderPainted(true);
                jarvisMarchButton.setBorder(new Dashboard.DottedBorder(20, true)); // Add shadow effect
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                jarvisMarchButton.setBackground(new Color(192, 192, 192)); // Restore original color
                jarvisMarchButton.setBorderPainted(false);
            }
        });

        JButton quickHullButton = new JButton("Quick Hull");
        quickHullButton.setFocusPainted(false);
        quickHullButton.setBounds(200,430,200,50);
        quickHullButton.setBackground(new Color(192, 192, 192));
        quickHullButton.setForeground(Color.BLACK);
        quickHullButton.setFont(new Font("Arial", Font.BOLD, 12));
        quickHullButton.setBorderPainted(false);
        quickHullButton.setOpaque(true);
        rightPanel.add(quickHullButton, BorderLayout.CENTER);
        quickHullButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QuickHullSwing.main(null);
            }
        });
        quickHullButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                quickHullButton.setBackground(new Color(169, 169, 169)); // Darker gray on hover
                quickHullButton.setBorderPainted(true);
                quickHullButton.setBorder(new Dashboard.DottedBorder(20, true)); // Add shadow effect
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                quickHullButton.setBackground(new Color(192, 192, 192)); // Restore original color
                quickHullButton.setBorderPainted(false);
            }
        });

        JButton hertelMehlHornButton = new JButton("Hertel MehlHorn");
        hertelMehlHornButton.setFocusPainted(false);
        hertelMehlHornButton.setBounds(200,500,200,50);
        hertelMehlHornButton.setBackground(new Color(192, 192, 192));
        hertelMehlHornButton.setForeground(Color.BLACK);
        hertelMehlHornButton.setFont(new Font("Arial", Font.BOLD, 12));
        hertelMehlHornButton.setBorderPainted(false);
        hertelMehlHornButton.setOpaque(true);
        rightPanel.add(hertelMehlHornButton, BorderLayout.CENTER);
        hertelMehlHornButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HertelMehlHorn.main(null);
            }
        });
        hertelMehlHornButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                hertelMehlHornButton.setBackground(new Color(169, 169, 169)); // Darker gray on hover
                hertelMehlHornButton.setBorderPainted(true);
                hertelMehlHornButton.setBorder(new Dashboard.DottedBorder(20, true)); // Add shadow effect
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                hertelMehlHornButton.setBackground(new Color(192, 192, 192)); // Restore original color
                hertelMehlHornButton.setBorderPainted(false);
            }
        });

        JButton lineIntersectionButton = new JButton("line Intersection");
        lineIntersectionButton.setFocusPainted(false);
        lineIntersectionButton.setBounds(200,570,200,50);
        lineIntersectionButton.setBackground(new Color(192, 192, 192));
        lineIntersectionButton.setForeground(Color.BLACK);
        lineIntersectionButton.setFont(new Font("Arial", Font.BOLD, 12));
        lineIntersectionButton.setBorderPainted(false);
        lineIntersectionButton.setOpaque(true);
        rightPanel.add(lineIntersectionButton, BorderLayout.CENTER);
        lineIntersectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LineIntersectionChecker.main(null);
            }
        });
        lineIntersectionButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lineIntersectionButton.setBackground(new Color(169, 169, 169)); // Darker gray on hover
                lineIntersectionButton.setBorderPainted(true);
                lineIntersectionButton.setBorder(new Dashboard.DottedBorder(20, true)); // Add shadow effect
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                lineIntersectionButton.setBackground(new Color(192, 192, 192)); // Restore original color
                lineIntersectionButton.setBorderPainted(false);
            }
        });

        JButton sweepLineIntersectionButton = new JButton("Sweep-Line Intersection");
        sweepLineIntersectionButton.setFocusPainted(false);
        sweepLineIntersectionButton.setBounds(200,650,200,50);
        sweepLineIntersectionButton.setBackground(new Color(192, 192, 192));
        sweepLineIntersectionButton.setForeground(Color.BLACK);
        sweepLineIntersectionButton.setFont(new Font("Arial", Font.BOLD, 12));
        sweepLineIntersectionButton.setBorderPainted(false);
        sweepLineIntersectionButton.setOpaque(true);
        rightPanel.add(sweepLineIntersectionButton, BorderLayout.CENTER);
        sweepLineIntersectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SweepLineIntersectionChecker.main(null);
            }
        });
        sweepLineIntersectionButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                sweepLineIntersectionButton.setBackground(new Color(169, 169, 169)); // Darker gray on hover
                sweepLineIntersectionButton.setBorderPainted(true);
                sweepLineIntersectionButton.setBorder(new Dashboard.DottedBorder(20, true)); // Add shadow effect
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                sweepLineIntersectionButton.setBackground(new Color(192, 192, 192)); // Restore original color
                sweepLineIntersectionButton.setBorderPainted(false);
            }
        });


        JButton openButton = new JButton("Visualize Algorithms");
        openButton.setFocusPainted(false);
        openButton.setBounds(15,400,170,30);
        openButton.setBackground(new Color(192, 192, 192));
        openButton.setForeground(Color.BLACK);
        openButton.setFont(new Font("Arial", Font.BOLD, 12));
        leftPanel.add(openButton, BorderLayout.CENTER);

        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openHTMLFile();
            }
        });
        openButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                openButton.setBackground(new Color(169, 169, 169)); // Darker gray on hover
                openButton.setBorderPainted(true);
                openButton.setBorder(new Dashboard.DottedBorder(20, true)); // Add shadow effect
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                openButton.setBackground(new Color(192, 192, 192)); // Restore original color
                openButton.setBorderPainted(false);
            }
        });

        JButton credits = new JButton("Credits");
        credits.setFocusPainted(false);
        credits.setBounds(15,450,170,30);
        credits.setBackground(new Color(192, 192, 192));
        credits.setForeground(Color.BLACK);
        credits.setFont(new Font("Arial", Font.BOLD, 12));
        leftPanel.add(credits, BorderLayout.CENTER);

        credits.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Suleman 21K-3887\nZeeshan 21K-3919", "Group Members", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        credits.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                credits.setBackground(new Color(169, 169, 169)); // Darker gray on hover
                credits.setBorderPainted(true);
                credits.setBorder(new Dashboard.DottedBorder(20, true)); // Add shadow effect
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                credits.setBackground(new Color(192, 192, 192)); // Restore original color
                credits.setBorderPainted(false);
            }
        });

    leftPanel.setLayout(new BorderLayout());
        rightPanel.setLayout(new BorderLayout());

        // Create a panel for the button and set its layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(closeButton);

        // Add the button panel to the top right corner of the right panel
        rightPanel.add(buttonPanel, BorderLayout.NORTH);

        // Add the panels to the content pane
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(leftPanel, BorderLayout.WEST);
        contentPane.add(rightPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Dashboard dashboard = new Dashboard();
            dashboard.setVisible(true);
        });
    }

    // Custom border class for the glow effect
    private static class GlowBorder extends AbstractBorder {
        private final Color glowColor;
        private final int size;
        private final int glowRadius;

        public GlowBorder(Color glowColor, int size, int glowRadius) {
            this.glowColor = glowColor;
            this.size = size;
            this.glowRadius = glowRadius;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(size, size, size, size);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = insets.bottom = insets.top = size;
            return insets;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            RoundRectangle2D rect = new RoundRectangle2D.Float(x, y, width - 1, height - 1, glowRadius, glowRadius);
            Area area = new Area(rect);

            g2d.setColor(glowColor);

            for (int i = 0; i < size; i++) {
                area.subtract(new Area(new RoundRectangle2D.Float(x - i, y - i, width + 2 * i - 1, height + 2 * i - 1, glowRadius, glowRadius)));
            }

            g2d.fill(area);
            g2d.dispose();
        }

    }

    private void openHTMLFile() {
        // Specify the path to your local HTML file
        String filePath = "index.html";

        // Create a File object
        File htmlFile = new File(filePath);

        // Check if the file exists
        if (htmlFile.exists()) {
            try {
                // Open the file using the default system web browser
                Desktop.getDesktop().browse(htmlFile.toURI());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "File not found: " + filePath, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public class DottedBorder implements Border {
        private int radius;
        private boolean hasShadow;

        public DottedBorder(int radius) {
            this.radius = radius;
            this.hasShadow = false;
        }

        public DottedBorder(int radius, boolean hasShadow) {
            this.radius = radius;
            this.hasShadow = hasShadow;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + (hasShadow ? 5 : 0), this.radius + (hasShadow ? 5 : 0), this.radius + (hasShadow ? 5 : 0), this.radius + (hasShadow ? 5 : 0));
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(new Color(52, 73, 94)); // Border color
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (hasShadow) {
                int shadowGap = 5;
                int shadowOffset = 4;
                int shadowAlpha = 150;

                RoundRectangle2D rect = new RoundRectangle2D.Double(x + shadowOffset, y + shadowOffset, width - shadowGap, height - shadowGap, radius, radius);
                Color shadowColor = new Color(0, 0, 0, shadowAlpha);
                ((Graphics2D) g).setColor(shadowColor);
                ((Graphics2D) g).fill(rect);
            }

            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{4}, 0));
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2d.dispose();
        }
    }
}
