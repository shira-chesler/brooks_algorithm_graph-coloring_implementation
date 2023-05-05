import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * TreeGUI is a JFrame that displays a graphical representation of a spanning tree.
 */
public class TreeGUI extends JFrame {
    public static int num = 0;
    private TreePanel treePanel;

    /**
     * Constructs a TreeGUI with a given Graph.
     *
     * @param tree The spanning tree as a Graph object.
     */
    public TreeGUI(Graph tree) {
        setTitle("Spanning tree");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);

        treePanel = new TreePanel(tree);
        add(treePanel, BorderLayout.CENTER);

        // Set window location based on 'num'
        if (num == 0) {
            this.setLocation(800, 0);
            num += 1;
        } else if (num == 1) {
            this.setLocation(800, 500);
            num += 1;
        }

        // Create and add the status label
        treePanel.statusLabel = new JLabel("Creating a spanning tree");
        treePanel.statusLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
        treePanel.statusLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        add(treePanel.statusLabel, BorderLayout.SOUTH);
        setVisible(true);
    }

    /**
     * Constructs a TreeGUI with a given Graph and root.
     *
     * @param tree The spanning tree as a Graph object.
     * @param root The root vertex of the spanning tree.
     */
    public TreeGUI(Graph tree, int root) {
        setTitle("Spanning tree");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);

        treePanel = new TreePanel(tree, root);
        add(treePanel, BorderLayout.CENTER);

        // Set window location based on 'num'
        if (num == 0) {
            this.setLocation(800, 0);
            num += 1;
        } else if (num == 1) {
            this.setLocation(800, 500);
            num += 1;
        }

        // Create and add the status label
        treePanel.statusLabel = new JLabel("Creating a spanning tree");
        treePanel.statusLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
        treePanel.statusLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        add(treePanel.statusLabel, BorderLayout.SOUTH);
        setVisible(true);
    }

    /**
     * Updates the colors of the vertices and the status message.
     *
     * @param colors        An array of color values for each vertex.
     * @param statusMessage The status message to be displayed.
     */
    public void updateColors(int[] colors, String statusMessage) {
        treePanel.setColors(colors);
        treePanel.setStatus((statusMessage));
    }
}
