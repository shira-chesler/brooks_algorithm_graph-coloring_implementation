import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;

public class GraphInputGUI extends JFrame {
    private JTextField verticesField;
    private JTextArea edgesInputArea;
    private JButton submitButton;
    private Graph input_graph;
    private AtomicBoolean finished = new AtomicBoolean(false);
    public GraphInputGUI() {
        setTitle("Graph Input");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setSize(400, 300);

        // Initialize components
        verticesField = new JTextField(10);
        edgesInputArea = new JTextArea(10, 30);
        submitButton = new JButton("Submit");

        // Add components
        add(new JLabel("Number of vertices:"));
        add(verticesField);
        add(new JLabel("Enter edges (each edge on a new line, vertices separated by a space):"));
        add(new JScrollPane(edgesInputArea));
        add(submitButton);

        // Add action listener to submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int vertices = Integer.parseInt(verticesField.getText().trim());

                // Create the graph
                Graph graph = new Graph(vertices);

                // Read the edges input and add edges to the graph
                String[] inputEdges = edgesInputArea.getText().split("\n");
                for (String inputEdge : inputEdges) {
                    String[] verticesPair = inputEdge.split(" ");
                    int v1 = Integer.parseInt(verticesPair[0].trim());
                    int v2 = Integer.parseInt(verticesPair[1].trim());
                    graph.addEdge(v1, v2);
                }

                // Dispose the GraphInputGUI instance
                dispose();

                setInput_graph(graph);
                finished.set(true);
            }
        });
        setVisible(true);
    }

    public Graph getInput_graph() {
        return input_graph;
    }

    public void setInput_graph(Graph input_graph) {
        this.input_graph = input_graph;
    }

    public boolean Getfinished(){
        return finished.get();
    }
}
