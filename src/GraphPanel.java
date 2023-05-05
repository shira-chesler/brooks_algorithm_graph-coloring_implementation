import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * The GraphPanel class is responsible for visualizing a graph, its vertices, edges,
 * and vertex colors. It extends JPanel and provides custom drawing for graph representation.
 */
class GraphPanel extends JPanel {
    private Graph graph;
    private int[] colors;
    private Map<Integer, Point> vertexPositions;
    boolean first_run = true;
    JLabel statusLabel;
    JLabel chromaticIndexLabel;

    /**
     * Constructs a new GraphPanel object with the given graph.
     *
     * @param graph The graph to be visualized.
     */
    public GraphPanel(Graph graph) {
        this.graph = graph;
        vertexPositions = new HashMap<>();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                calculateVertexPositions();
            }
        });
        chromaticIndexLabel = new JLabel();
        chromaticIndexLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        chromaticIndexLabel.setVisible(false);
        add(chromaticIndexLabel);
    }

    /**
     * Calculates and assigns random positions to the vertices within the panel.
     */
    private void calculateVertexPositions() {
        int radius = 20;
        int padding = 50;
        for (int i = 0; i < graph.getVertices(); i++) {
            int x = padding + (int) (Math.random() * (getWidth() - 2 * padding));
            int y = padding + (int) (Math.random() * (getHeight() - 2 * padding));
            vertexPositions.put(i, new Point(x, y));
        }
    }

    /**
     * Sets the colors of the vertices and repaints the panel.
     *
     * @param colors An array of integers representing the colors assigned to each vertex.
     */
    public void setColors(int[] colors) {
        this.colors = colors;
        repaint();
    }

    /**
     * Custom paintComponent method responsible for drawing the vertices, edges, and vertex colors.
     *
     * @param g Graphics object for rendering operations.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int radius = 20;
        int padding = 50;

        // Define a color palette for vertex coloring
        Color[] colorPalette = {Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.MAGENTA, Color.CYAN, Color.ORANGE, Color.PINK, Color.BLACK};
        if (first_run) {
            first_run = false;
            calculateVertexPositions();
        }

        // Draw edges between vertices
        for (int i = 0; i < graph.getVertices(); i++) {
            for (int neighbor : graph.getAdjacencyList()[i]) {
                if (i < neighbor) {
                    Point p1 = vertexPositions.get(i);
                    Point p2 = vertexPositions.get(neighbor);
                    g.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            }
        }

        // Draw and color vertices
        for (int i = 0; i < graph.getVertices(); i++) {
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
     * Sets the status message to be displayed in the GUI.
     *
     * @param statusMessage A string representing the status message to be displayed.
     */
    public void setStatus(String statusMessage) {
        statusLabel.setText(statusMessage);
    }

    /**
     * Displays the chromatic index of the graph in the panel.
     *
     * @param chromaticIndex An integer representing the chromatic index of the graph.
     */
    public void displayChromaticIndex(int chromaticIndex) {
        String message = "Chromatic index: " + chromaticIndex;
        chromaticIndexLabel.setText(message);
        chromaticIndexLabel.setLocation((getWidth() - chromaticIndexLabel.getPreferredSize().width) / 2, (getHeight() - chromaticIndexLabel.getPreferredSize().height) / 2);
        chromaticIndexLabel.setVisible(true);
        statusLabel.setText("Computation ended");
        repaint();
    }
}