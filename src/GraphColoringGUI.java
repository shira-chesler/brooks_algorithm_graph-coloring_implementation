import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * The GraphColoringGUI class is responsible for displaying the graph coloring process and results.
 * It extends JFrame and provides a visual interface for graph coloring operations.
 */
public class GraphColoringGUI extends JFrame {
    private GraphPanel graphPanel;

    /**
     * Constructs a new GraphColoringGUI object.
     *
     * @param graph The graph to be visualized and colored.
     */
    public GraphColoringGUI(Graph graph) {
        setTitle("Graph Coloring");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);

        // Initialize graph panel with the given graph and add it to the frame
        graphPanel = new GraphPanel(graph);
        add(graphPanel, BorderLayout.CENTER);

        this.setLocation(0, 0);

        // Create and add the status label
        graphPanel.statusLabel = new JLabel("Starting...");
        graphPanel.statusLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
        graphPanel.statusLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        add(graphPanel.statusLabel, BorderLayout.SOUTH);
        setVisible(true);
    }

    /**
     * Updates the colors of the vertices in the GUI and sets the status message.
     *
     * @param colors        An array of integers representing the colors assigned to each vertex.
     * @param statusMessage A string representing the status message to be displayed.
     */
    public void updateColors(int[] colors, String statusMessage) {
        graphPanel.setColors(colors);
        graphPanel.setStatus(statusMessage); // Update the status message
    }

    /**
     * Displays the chromatic index of the graph in the GUI.
     *
     * @param chromaticIndex An integer representing the chromatic index of the graph.
     */
    public void displayChromaticIndex(int chromaticIndex) {
        graphPanel.displayChromaticIndex(chromaticIndex);
    }
}
