import javax.swing.*;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * TreePanel is a custom JPanel that displays a graphical representation of a spanning tree.
 */
public class TreePanel extends JPanel {
    private Graph tree;
    private int root = -1;
    private int[] colors;
    private Map<Integer, Point> vertexPositions;
    boolean first_run = true;
    JLabel statusLabel;
    JLabel order;

    /**
     * Constructs a TreePanel with a given Graph.
     *
     * @param graph The spanning tree as a Graph object.
     */
    public TreePanel(Graph graph) {
        this.tree = graph;
        this.colors = new int[graph.getVertices()];
        vertexPositions = new HashMap<>();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                calculateVertexPositions();
            }
        });
        order = new JLabel();
        order.setFont(new Font("Arial", Font.PLAIN, 30));
        order.setVisible(false);
        add(order);
        displayOrder();
    }

    /**
     * Constructs a TreePanel with a given Graph and root.
     *
     * @param graph The spanning tree as a Graph object.
     * @param root  The root vertex of the spanning tree.
     */
    public TreePanel(Graph graph, int root) {
        this.tree = graph;
        this.root = root;
        this.colors = new int[graph.getVertices()];
        vertexPositions = new HashMap<>();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                calculateVertexPositions();
            }
        });
        order = new JLabel();
        order.setFont(new Font("Arial", Font.PLAIN, 30));
        order.setVisible(false);
        add(order);
        displayOrder();
    }

    /**
     * Calculates and assigns random positions for the vertices of the tree.
     */
    private void calculateVertexPositions() {
        int radius = 20;
        int padding = 50;
        for (int i = 0; i < tree.getVertices(); i++) {
            if (tree.getAdjacencyList()[i].size() != 0) {
                int x = padding + (int) (Math.random() * (getWidth() - 2 * padding));
                int y = padding + (int) (Math.random() * (getHeight() - 2 * padding));
                vertexPositions.put(i, new Point(x, y));
            }
        }
    }

    /**
     * Sets the colors of the vertices and repaints the panel.
     *
     * @param colors An array of color values for each vertex.
     */
    public void setColors(int[] colors) {
        this.colors = colors;
        repaint();
    }

    /**
     * Sets the status message in the status label.
     *
     * @param statusMessage The status message to be displayed.
     */
    public void setStatus(String statusMessage) {
        statusLabel.setText(statusMessage);
    }


    /**
     * Custom paint component method to draw the tree graph on the panel.
     *
     * @param g The Graphics object to draw on.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Calculate vertex positions on the first run
        if (first_run) {
            first_run = false;
            calculateVertexPositions();
        }

        // Initialize radius, padding, and color palette
        int radius = 20;
        int padding = 50;
        Color[] colorPalette = {Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.MAGENTA, Color.CYAN, Color.ORANGE, Color.PINK, Color.BLACK};

        // Draw edges of the tree graph
        for (int i = 0; i < tree.getVertices(); i++) {
            for (int neighbor : tree.getAdjacencyList()[i]) {
                if (i < neighbor) {
                    Point p1 = vertexPositions.get(i);
                    Point p2 = vertexPositions.get(neighbor);
                    g.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            }
        }

        // Draw vertices of the tree graph with their corresponding colors
        for (int i = 0; i < tree.getVertices(); i++) {
            if (tree.getAdjacencyList()[i].size() == 0) continue;
            Point p = vertexPositions.get(i);
            if (colors != null && colors[i] != -1) {
                g.setColor(colorPalette[colors[i] % colorPalette.length]);
            } else {
                g.setColor(Color.GRAY);
            }
            g.fillOval(p.x - radius / 2, p.y - radius / 2, radius, radius);
            g.setColor(Color.BLACK);
            g.drawOval(p.x - radius / 2, p.y - radius / 2, radius, radius);
            g.drawString(Integer.toString(i), p.x - 4, p.y + 4);
        }
    }

    /**
     * Displays the ordering of vertices in the spanning tree.
     */
    private void displayOrder() {
        // Create a SpanningTreeOrdering object based on the root vertex, if specified
        SpanningTreeOrdering spoTree;
        int[] ordering;
        int border;
        if (root != -1) {
            spoTree = new SpanningTreeOrdering(tree, root);
            ordering = spoTree.findOrdering();
            border = ordering.length - 2;
        } else {
            spoTree = new SpanningTreeOrdering(tree);
            ordering = spoTree.findOrdering();
            border = ordering.length;
        }

        // Build the string representing the tree painting order
        StringBuilder order = new StringBuilder("Tree painting Order: " + ordering[0]);
        for (int i = 1; i < border; i++) {
            if (ordering[i] == -1) continue;
            order.append("->").append(ordering[i]);
        }

        // Set the order text, position, and visibility, and then repaint the panel
        this.order.setText(order.toString());
        this.order.setLocation((getWidth() - this.order.getPreferredSize().width) / 2, (getHeight() - this.order.getPreferredSize().height) / 2);
        this.order.setVisible(true);
        repaint();
    }

}
