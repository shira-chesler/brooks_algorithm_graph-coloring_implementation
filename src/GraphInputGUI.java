import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphInputGUI extends JFrame {
    private JTextField verticesField;
    private JTextArea edgesInputArea;
    private JTextField fromVertexField;
    private JTextField toVertexField;
    private JButton addEdgeButton;
    private JButton removeEdgeButton;
    private JButton submitButton;

    private Graph inputGraph;
    private boolean finished;

    public GraphInputGUI() {
        setTitle("Graph Input");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(600, 550);

        // Initialize components
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 10, 10, 10);

        JLabel verticesLabel = new JLabel("Number of vertices:");
        verticesField = new JTextField(10);
        c.gridx = 0;
        c.gridy = 0;
        inputPanel.add(verticesLabel, c);
        c.gridx = 1;
        c.gridy = 0;
        inputPanel.add(verticesField, c);

        JLabel addEdgeLabel = new JLabel("Add edges (add between 0 and #vertices-1):");
        c.gridx = 0;
        c.gridy = 1;
        inputPanel.add(addEdgeLabel, c);

        JLabel fromVertexLabel = new JLabel("From:");
        fromVertexField = new JTextField(5);
        c.gridx = 0;
        c.gridy = 2;
        inputPanel.add(fromVertexLabel, c);
        c.gridx = 1;
        c.gridy = 2;
        inputPanel.add(fromVertexField, c);

        JLabel toVertexLabel = new JLabel("To:");
        toVertexField = new JTextField(5);
        c.gridx = 2;
        c.gridy = 2;
        inputPanel.add(toVertexLabel, c);
        c.gridx = 3;
        c.gridy = 2;
        inputPanel.add(toVertexField, c);

        edgesInputArea = new JTextArea(10, 30);
        edgesInputArea.setEditable(false);
        JScrollPane edgesScrollPane = new JScrollPane(edgesInputArea);
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 4;
        inputPanel.add(edgesScrollPane, c);

        addEdgeButton = new JButton("Add Edge");
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        inputPanel.add(addEdgeButton, c);

        JLabel removeEdgeLabel = new JLabel("Remove edges:");
        c.gridx = 0;
        c.gridy = 4;
        inputPanel.add(removeEdgeLabel, c);

        JLabel removeFromVertexLabel = new JLabel("From:");
        JTextField removeFromVertexField = new JTextField(5);
        c.gridx = 0;
        c.gridy = 5;
        inputPanel.add(removeFromVertexLabel, c);
        c.gridx = 1;
        c.gridy = 5;
        inputPanel.add(removeFromVertexField, c);

        JLabel removeToVertexLabel = new JLabel("To:");
        JTextField removeToVertexField = new JTextField(5);
        c.gridx = 2;
        c.gridy = 5;
        inputPanel.add(removeToVertexLabel, c);
        c.gridx = 3;
        c.gridy = 5;
        inputPanel.add(removeToVertexField, c);

        JButton removeEdgeButton = new JButton("Remove Edge");
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 1;
        inputPanel.add(removeEdgeButton, c);


        add(inputPanel, BorderLayout.CENTER);

        submitButton = new JButton("Submit");
        c.gridx = 1;
        c.gridy = 8;
        c.gridwidth = 1;
        inputPanel.add(submitButton, c);

        // Add action listener to add edge button
        addEdgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get input from fromVertexField and toVertexField
                String fromVertexText = fromVertexField.getText().trim();
                String toVertexText = toVertexField.getText().trim();

                // Check if input is valid
                try {
                    int fromVertex = Integer.parseInt(fromVertexText);
                    int toVertex = Integer.parseInt(toVertexText);

                    if (fromVertex < 0 || fromVertex >= Integer.parseInt(verticesField.getText().trim())) {
                        throw new NumberFormatException();
                    }

                    if (toVertex < 0 || toVertex >= Integer.parseInt(verticesField.getText().trim())) {
                        throw new NumberFormatException();
                    }

                    // Add edge to edgesInputArea
                    edgesInputArea.append(fromVertexText + " " + toVertexText + "\n");
                } catch (NumberFormatException ex) {
                    // Display error message
                    JOptionPane.showMessageDialog(GraphInputGUI.this, "Invalid input. Please enter valid vertex numbers.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                    // Clear input fields
                    fromVertexField.setText("");
                    toVertexField.setText("");
                }
        });

        // Add action listener to remove edge button
        removeEdgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get input from removeFromVertexField and removeToVertexField
                String removeFromVertexText = removeFromVertexField.getText().trim();
                String removeToVertexText = removeToVertexField.getText().trim();

                // Check if input is valid
                try {
                    int removeFromVertex = Integer.parseInt(removeFromVertexText);
                    int removeToVertex = Integer.parseInt(removeToVertexText);

                    if (removeFromVertex < 0 || removeFromVertex >= Integer.parseInt(verticesField.getText().trim())) {
                        throw new NumberFormatException();
                    }

                    if (removeToVertex < 0 || removeToVertex >= Integer.parseInt(verticesField.getText().trim())) {
                        throw new NumberFormatException();
                    }

                    // Remove the selected edge from edgesInputArea
                    String[] inputEdges = edgesInputArea.getText().split("\n");
                    StringBuilder updatedEdges = new StringBuilder();
                    boolean exists = false;
                    for (String inputEdge : inputEdges) {
                        String[] verticesPair = inputEdge.split(" ");
                        int v1 = Integer.parseInt(verticesPair[0].trim());
                        int v2 = Integer.parseInt(verticesPair[1].trim());
                        if ((v1 != removeFromVertex || v2 != removeToVertex) && (v2 != removeFromVertex || v1 != removeToVertex)) {
                            updatedEdges.append(inputEdge).append("\n");
                        }
                        else{
                            exists=true;
                        }
                    }
                    if (!exists){
                        throw new RuntimeException();
                    }
                    edgesInputArea.setText(updatedEdges.toString());
                } catch (NumberFormatException ex) {
                    // Display error message
                    JOptionPane.showMessageDialog(GraphInputGUI.this, "Invalid input. Please enter valid vertex numbers.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (RuntimeException ex) {
                    // Display error message
                    JOptionPane.showMessageDialog(GraphInputGUI.this, "Invalid input. Edge tried to remove doesn't exists.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                // Clear input fields
                removeFromVertexField.setText("");
                removeToVertexField.setText("");
            }
        });


        // Add action listener to submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get input from verticesField
                String verticesText = verticesField.getText().trim();

                // Check if input is valid
                try {
                    int vertices = Integer.parseInt(verticesText);

                    if (vertices < 1) {
                        throw new NumberFormatException();
                    }

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

                    // Set input_graph and finished flag
                    setInputGraph(graph);

                    finished = true;
                    dispose(); // Dispose of the GraphInputGUI instance

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(GraphInputGUI.this, "Please enter a valid integer for the number of vertices.", "Invalid input", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(GraphInputGUI.this, ex.getMessage(), "Invalid input", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }

    public Graph getInputGraph() {
        return inputGraph;
    }

    public void setInputGraph(Graph inputGraph) {
        this.inputGraph = inputGraph;
    }

    public boolean Getfinished() {
        return finished;
    }
}
