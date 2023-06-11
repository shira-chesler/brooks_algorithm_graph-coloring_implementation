import java.util.*;
import java.util.Arrays;

import static java.lang.Thread.sleep;

/**
 * The GraphColoring class is responsible for coloring a given graph using the Brooks algorithm.
 */
public class GraphColoring {
    private Graph graph;

    /**
     * Constructor for GraphColoring class.
     *
     * @param g A Graph object to be colored.
     */
    GraphColoring(Graph g) {
        this.graph = g;
    }

    /**
     * The main method that colors the graph using Brooks algorithm.
     *
     * @param gui A GraphColoringGUI object used to update the GUI as the graph is being colored.
     */
    public void brooksAlgorithm(GraphColoringGUI gui) {
        // Get the maximum degree in the graph
        int maxDegree = graph.getMaxDegree();
        int[] colors = new int[graph.getVertices()];
        Arrays.fill(colors, -1);

        // Check if the graph is connected, as Brooks' algorithm is for connected graphs
        if (!graph.isConnected()) {
            throw new RuntimeException("Brooks algorithm is used to find the chromatic index of a connected graph");
        }

        // Handle clique and odd cycle cases - because Brooks' theorem is not applying to them as well
        if (graph.isClique() || graph.isOddCycle()) {
            if (graph.isClique()) {
                colors = colorVerticesGreedyByOrder(maxDegree, gui, "The graph is a clique, ", null, null, false, 0, 0);
            } else {
                colors = colorVerticesGreedyByOrder(maxDegree, gui, "The graph is an odd cycle, ", null, null, false, 0, 0);
            }
        }
        // Step 1: Handle cases where maxDegree is 0, 1, or 2
        else {
            // If the graph is 2-colorable (path or non-odd cycle)
            if (maxDegree < 3) {
                colors = colorVerticesGreedyByOrder(maxDegree, gui, "Degree is smaller than 3, ", null, null, false, 0, 0);
            } else {
                // Find an ordering of the vertices
                SpanningTreeOrdering sto = new SpanningTreeOrdering(graph);
                int[] ordering = sto.findOrdering();

                // If there is a vertex with max degree smaller than Δ(G)
                if (ordering != null) {
                    TreeGUI anotherGui = new TreeGUI(sto.getTree());
                    colors = colorVerticesGreedyByOrder(maxDegree, gui, "Created a spanning tree rooted in " + sto.getRoot() + " - deg(" + sto.getRoot() + ")<Δ(G): ", ordering, anotherGui, false, 0, 0);
                } else {
                    // Color using cut vertex
                    CutVertexFinder cvf = new CutVertexFinder(graph);
                    int CutNode = cvf.findCutVertices();
                    if (CutNode != -1) {
                        colors = colorVerticesUsingCutNode(CutNode, gui);
                    } else {
                        // Color the tree without two non-adjacent vertices, then color them
                        int[] specVertices = findNonTriangle();
                        Graph withoutYAndZ = graph.copy();
                        withoutYAndZ.removeVertex(specVertices[1]);
                        withoutYAndZ.removeVertex(specVertices[2]);
                        SpanningTreeOrdering stoLast = new SpanningTreeOrdering(withoutYAndZ, specVertices[0]);
                        TreeGUI treeWithoutTwoVerticesGui = new TreeGUI(stoLast.getTree(), specVertices[0]);
                        colors = colorVerticesGreedyByOrder(maxDegree, gui, "Created a spanning tree rooted in " + specVertices[0] + " without vertices " + specVertices[1] + ", and " + specVertices[2], stoLast.findOrdering(), treeWithoutTwoVerticesGui, true, specVertices[1], specVertices[2]);

                        // Sleep for 5 seconds to allow the user to observe the changes
                        try {
                            sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }
        int chromaticIndex = getMaxChromaticIndex(colors);
        gui.displayChromaticIndex(chromaticIndex);

    }


    /**
     * Color the vertices of the graph greedily based on the given ordering.
     *
     * @param maxDegree     The maximum degree in the graph.
     * @param gui           A GraphColoringGUI object used to update the GUI as the graph is being colored.
     * @param added_message A message to display in the GUI during the coloring process.
     * @param ordering      The order in which the vertices should be colored.
     * @param tgui          A TreeGUI object to update as the tree is being colored, or null if not needed.
     * @return An array containing the colors of the vertices.
     */
    private int[] colorVerticesGreedyByOrder(int maxDegree, GraphColoringGUI gui, String added_message, int[] ordering, TreeGUI tgui, boolean two_first, int first, int second) {
        // Check if the ordering array is provided
        if (ordering == null) {
            // If not provided, initialize it with default ordering (0, 1, 2, ..., n-1)
            ordering = new int[graph.getVertices()];
            for (int i = 0; i < ordering.length; i++) {
                ordering[i] = i;
            }
        }

        // Initialize the colors array and set all elements to -1 (unassigned)
        int[] colors = new int[graph.getVertices()];
        Arrays.fill(colors, -1);

        // Need to color the two vertices y,z first
        if (two_first) {
            colors[first] = colors[second] = 0;
            //giving them the same color
            if (tgui != null) {
                tgui.updateColors(colors, "Giving colors to 2 vertices that are not in the tree");
            }
            gui.updateColors(colors, added_message + "Coloring vertex " + first + " with color " + 0);
            gui.updateColors(colors, added_message + "Coloring vertex " + second + " with color " + 0);
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Iterate through the provided ordering of vertices
        for (int j : ordering) {
            if (j == -1) continue; // If there's a removed vertex in the graph, represented by -1 in the ordering

            // Color the vertex according to the greedy coloring approach
            colorVertices(maxDegree, gui, colors, j, added_message + " Coloring Greedy:\n ", tgui);
        }

        // If a TreeGUI instance exists, wait for 1 second and dispose it
        if (tgui != null) {
            try {
                sleep(1000);
                tgui.dispose();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // Return the final colors array
        return colors;
    }

    /**
     * Color a single vertex using the least available color.
     *
     * @param maxDegree     The maximum degree in the graph.
     * @param gui           A GraphColoringGUI object used to update the GUI as the graph is being colored.
     * @param colors        An array representing the colors of the vertices.
     * @param i             The vertex to color.
     * @param added_message A message to display in the GUI during the coloring process.
     * @param tgui          A TreeGUI object to update as the tree is being colored, or null if not needed.
     */
    private void colorVertices(int maxDegree, GraphColoringGUI gui, int[] colors, int i, String added_message, TreeGUI tgui) {
        int j = leastAvailableColor(maxDegree, colors, i);
        if (j == -1) {
            return;
        }
        colors[i] = j;
        gui.updateColors(colors, added_message + "Coloring vertex " + i + " with color " + (j + 1));
        if (tgui != null) {
            tgui.updateColors(colors, "Found available color " + (j + 1) + " to vertex " + i);
        }
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * Find the least available color for vertex i.
     *
     * @param maxDegree The maximum degree in the graph.
     * @param colors    An array representing the colors of the vertices.
     * @param i         The vertex to find the least available color for.
     * @return The least available color for vertex i.
     */
    private int leastAvailableColor(int maxDegree, int[] colors, int i) {
        int leastAvailableColor = -1;
        boolean[] availableColors = new boolean[maxDegree + 1];
        Arrays.fill(availableColors, true);

        if (graph.getAdjacencyList()[i] == null) {
            return leastAvailableColor;
        }

        for (int neighbor : graph.getAdjacencyList()[i]) {
            if (colors[neighbor] != -1) {
                availableColors[colors[neighbor]] = false;
            }
        }
        for (int j = 0; j <= maxDegree; j++) {
            if (availableColors[j]) {
                leastAvailableColor = j;
                break;
            }
        }
        return leastAvailableColor;
    }

    /**
     * Find three vertices x, y, and z, where x is adjacent to y and z, but y and z are not adjacent,
     * and the graph remains connected when removing y and z.
     *
     * @return An array containing the vertices x, y, and z.
     */
    public int[] findNonTriangle() {
        for (int x = 0; x < graph.getVertices(); x++) {
            List<Integer> neighbors = graph.getAdjacencyList()[x];
            for (int i = 0; i < neighbors.size(); i++) {
                int y = neighbors.get(i);
                for (int j = i + 1; j < neighbors.size(); j++) {
                    int z = neighbors.get(j);
                    if (!graph.hasEdge(y, z)) {
                        Graph withoutYandZ = graph.copy();
                        withoutYandZ.removeVertex(y);
                        withoutYandZ.removeVertex(z);
                        if (withoutYandZ.isConnected()) {
                            return new int[]{x, y, z};
                        }
                    }
                }
            }
        }
        return null; //doesn't suppose to happen, we proved that they always exist
    }


    /**
     * Color the vertices of the graph using a cut node.
     *
     * @param cutNode The cut node to use for coloring.
     * @param gui     A GraphColoringGUI object used to update the GUI as the graph is being colored.
     * @return An array containing the colors of the vertices.
     */
    private int[] colorVerticesUsingCutNode(int cutNode, GraphColoringGUI gui) {
        // Initialize the colors array and set all elements to -1 (unassigned)
        int[] colors = new int[graph.getVertices()];
        Arrays.fill(colors, -1);

        // Get the neighbors of the cutNode
        ArrayList<Integer> neighbors = graph.getAdjacencyList()[cutNode];

        // Create two subgraphs, firstSub and secondSub, connected by the cutNode
        Subgraph firstSub = new Subgraph(graph, cutNode, neighbors.get(0));
        Subgraph secondSub = null;
        for (int neighbor : neighbors) {
            if (firstSub.containsNode(neighbor)) {
                secondSub = new Subgraph(graph, cutNode, neighbor);
                break;
            }
        }
        if (secondSub == null) {
            throw new RuntimeException("couldn't create second tree");
        }

        // Create spanning trees for the two subgraphs
        SpanningTreeOrdering stoFirst = new SpanningTreeOrdering(firstSub.getSubgraph(), cutNode);
        SpanningTreeOrdering stoSecond = new SpanningTreeOrdering(secondSub.getSubgraph(), cutNode);

        // Display the two trees in separate TreeGUI instances
        TreeGUI firstTreeGui = new TreeGUI(stoFirst.getTree());
        TreeGUI secondTreeGui = new TreeGUI(stoSecond.getTree());

        // Color the vertices of the two subgraphs using the greedy algorithm
        int[] colors1 = colorVerticesGreedyByOrder(firstSub.getSubgraph().getMaxDegree(), gui, "Created first spanning tree rooted in " + stoFirst.getRoot(), stoFirst.findOrdering(), firstTreeGui, false, 0, 0);
        int[] colors2 = colorVerticesGreedyByOrder(secondSub.getSubgraph().getMaxDegree(), gui, "Created second tree rooted in " + stoSecond.getRoot(), stoSecond.findOrdering(), secondTreeGui, false, 0, 0);

        // If the cutNode has different colors in the two subgraphs, repaint one of the subgraphs
        if (colors2[cutNode] != colors1[cutNode]) {
            int colors1Max = Arrays.stream(colors1).max().getAsInt();
            int color2Max = Arrays.stream(colors2).max().getAsInt();
            if (colors1Max > color2Max) {
                repaint(colors2, cutNode, colors1[cutNode], color2Max, "Repainting subGraph2: ", gui);
            } else {
                repaint(colors1, cutNode, colors2[cutNode], colors1Max, "Repainting subGraph1: ", gui);
            }
        }

        // Combine the colors from both subgraphs into a single colors array
        for (int i = 0; i < colors.length; i++) {
            if (colors1[i] != -1) {
                colors[i] = colors1[i];
            } else {
                colors[i] = colors2[i];
            }
        }

        // Return the final colors array
        return colors;
    }

    /**
     * Repaint the vertices in the colors array.
     *
     * @param colors          An array representing the colors of the vertices.
     * @param cutNode         The cut node to consider when repainting.
     * @param colorCutInOther The color of the cut node in the other subgraph.
     * @param color2Max       The maximum color in the second subgraph.
     * @param added_message   A message to display in the GUI when repainting.
     * @param gui             A GraphColoringGUI object used to update the GUI as the graph is being colored.
     */
    private void repaint(int[] colors, int cutNode, int colorCutInOther, int color2Max, String
            added_message, GraphColoringGUI gui) {
        int moveBy = colorCutInOther - colors[cutNode];
        for (int i = 0; i < colors.length; i++) {
            if (colors[i] == -1) continue;
            int j = (colors[i] + moveBy) % color2Max;
            gui.updateColors(colors, added_message + "Coloring vertex " + i + " with color " + j + 1);
            try {
                sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Get the maximum chromatic index from the colors array.
     *
     * @param colors An array representing the colors of the vertices.
     * @return The maximum chromatic index.
     */
    private int getMaxChromaticIndex(int[] colors) {
        int maxColor = 0;
        for (int color : colors) {
            maxColor = Math.max(maxColor, color);
        }
        return maxColor + 1;
    }


}

