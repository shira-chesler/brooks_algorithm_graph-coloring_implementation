import java.util.*;
import java.util.Arrays;

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
        int maxDegree = graph.getMaxDegree();
        int[] colors = new int[graph.getVertices()];
        Arrays.fill(colors, -1);
        if (!graph.isConnected()) {
            throw new RuntimeException("Brooks algorithm is used to find the chromatic index of a connected graph");
        }
        if (graph.isClique() || graph.isOddCycle()) {
            if (graph.isClique()) {
                colors = colorVerticesGreedy(maxDegree, gui, "The graph is a clique, ");
            } else {
                colors = colorVerticesGreedy(maxDegree, gui, "The graph is an odd cycle, ");
            }
        }
        // Step 1: Handle cases where maxDegree is 0, 1, or 2
        else {
            if (maxDegree < 3) {
                colors = colorVerticesGreedy(maxDegree, gui, "");
            } else {
                // Step 2: Handle graphs with one-node and two-node cut-sets
//            If a graph has a one-node cut-set (i.e., an articulation point), then its maximum degree (Δ) should be used as the number of colors in the optimal coloring.
//            If a graph has a two-node cut-set and is not a complete graph, then the optimal coloring can be found using Δ colors.
//            If a graph has a two-node cut-set and is a complete graph, then the optimal coloring requires Δ+1 colors.
                int[] oneNodeCutSet = findOneNodeCutSet();
                if (oneNodeCutSet != null) {
                    colors = colorVerticesUsingCutSet(oneNodeCutSet, gui, 1);
                } else {
                    int[] twoNodeCutSet = findTwoNodeCutSet();
                    if (twoNodeCutSet != null) {
                        colors = colorVerticesUsingCutSet(twoNodeCutSet, gui, 2);
                    } else {
                        // Step 3: Apply Brooks' theorem
                        int[] nonAdjacentNeighbors = findNonAdjacentNeighborsOfMaxDegreeNode();
                        if (nonAdjacentNeighbors != null) {
                            int[] bfsOrder = breadthFirstSearchOrder(nonAdjacentNeighbors[0]);
                            colors = colorVerticesInReverseOrder(bfsOrder, maxDegree, gui, "Coloring vertices by non adjacent neighbor of max degree:\n ");
                        }
                    }
                }
            }
        }
        int chromaticIndex = getMaxChromaticIndex(colors);
        gui.displayChromaticIndex(chromaticIndex);

    }

    // Helper methods for the algorithm

    /**
     * A helper method that colors the graph vertices greedily.
     *
     * @param maxDegree     The maximum degree of the graph.
     * @param gui           A GraphColoringGUI object used to update the GUI as the graph is being colored.
     * @param added_message A string message to be displayed in the GUI.
     * @return An array of colors assigned to vertices.
     */
    private int[] colorVerticesGreedy(int maxDegree, GraphColoringGUI gui, String added_message) {
        int[] colors = new int[graph.getVertices()];
        Arrays.fill(colors, -1);

        for (int i = 0; i < graph.getVertices(); i++) {
            colorVertices(maxDegree, gui, colors, i, added_message + " Coloring Greedy:\n ");
        }

        return colors;
    }

    /**
     * A helper method that colors a single vertex with the smallest available color while updating the GUI.
     *
     * @param maxDegree     The maximum degree of the graph.
     * @param gui           A GraphColoringGUI object used to update the GUI as the graph is being colored.
     * @param colors        An array representing the current colors of vertices.
     * @param i             The index of the vertex to be colored.
     * @param added_message A string message to be displayed in the GUI.
     */
    private void colorVertices(int maxDegree, GraphColoringGUI gui, int[] colors, int i, String added_message) {
        boolean[] availableColors = new boolean[maxDegree + 1];
        Arrays.fill(availableColors, true);

        for (int neighbor : graph.getAdjacencyList()[i]) {
            if (colors[neighbor] != -1) {
                availableColors[colors[neighbor]] = false;
            }
        }

        for (int j = 0; j <= maxDegree; j++) {
            if (availableColors[j]) {
                colors[i] = j;
                gui.updateColors(colors, added_message + "Coloring vertex " + i + " with color " + j + 1);
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    /**
     * A helper method that finds a one-node cut-set in the graph, if it exists.
     *
     * @return An array containing the one-node cut-set or null if not found.
     */
    private int[] findOneNodeCutSet() {
        for (int i = 0; i < graph.getVertices(); i++) {
            if (graph.getAdjacencyList()[i].size() < graph.getMaxDegree()) {
                return new int[]{i};
            }
        }
        return null;
    }

    /**
     * A helper method that finds a two-node cut-set in the graph, if it exists.
     *
     * @return An array containing the two-node cut-set or null if not found.
     */
    int[] findTwoNodeCutSet() {
        for (int i = 0; i < graph.getVertices(); i++) {
            for (int j = i + 1; j < graph.getVertices(); j++) {
                if (!graph.getAdjacencyList()[i].contains(j) && (graph.getAdjacencyList()[i].size() + graph.getAdjacencyList()[j].size() == graph.getMaxDegree())) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    /**
     * A helper method that colors the graph vertices using a cut-set (one-node or two-node).
     *
     * @param cutSet        An array containing the cut-set vertices.
     * @param gui           A GraphColoringGUI object used to update the GUI as the graph is being colored.
     * @param which_cut_set An integer specifying which cut-set to use (1 for one-node, 2 for two-node).
     * @return An array of colors assigned to vertices.
     */
    private int[] colorVerticesUsingCutSet(int[] cutSet, GraphColoringGUI gui, int which_cut_set) {
        int[] colors = new int[graph.getVertices()];
        Arrays.fill(colors, -1);

        Set<Integer> cutSetVertices = new HashSet<>();
        for (int vertex : cutSet) {
            cutSetVertices.add(vertex);
        }

        for (int i = 0; i < graph.getVertices(); i++) {
            if (!cutSetVertices.contains(i)) {
                colors[i] = 0;
            }
        }

        for (int vertex : cutSet) {
            int[] bfsOrder = breadthFirstSearchOrder(vertex);
            if (which_cut_set == 1) {
                colors = colorVerticesInReverseOrder(bfsOrder, graph.getMaxDegree(), gui, "Coloring vertices using a 1-vx cut set:\n ");
            } else {
                colors = colorVerticesInReverseOrder(bfsOrder, graph.getMaxDegree(), gui, "Coloring vertices using a 2-vx cut set:\n ");
            }
        }

        return colors;
    }

    /**
     * A helper method that finds a pair of non-adjacent neighbors of a node with maximum degree in the graph.
     *
     * @return An array containing the pair of non-adjacent neighbors or null if not found.
     */
    private int[] findNonAdjacentNeighborsOfMaxDegreeNode() {
        int maxDegreeNode = -1;
        int maxDegree = graph.getMaxDegree();

        for (int i = 0; i < graph.getVertices(); i++) {
            if (graph.getAdjacencyList()[i].size() == maxDegree) {
                maxDegreeNode = i;
                break;
            }
        }

        for (int i = 0; i < graph.getAdjacencyList()[maxDegreeNode].size(); i++) {
            for (int j = i + 1; j < graph.getAdjacencyList()[maxDegreeNode].size(); j++) {
                int u = graph.getAdjacencyList()[maxDegreeNode].get(i);
                int v = graph.getAdjacencyList()[maxDegreeNode].get(j);
                if (!graph.getAdjacencyList()[u].contains(v)) {
                    return new int[]{u, v};
                }
            }
        }

        return null;
    }

    /**
     * A helper method that returns the BFS order of the graph vertices, starting from a given vertex.
     *
     * @param start The starting vertex for BFS traversal.
     * @return An array containing the BFS order of vertices.
     */
    private int[] breadthFirstSearchOrder(int start) {
        int[] bfsOrder = new int[graph.getVertices()];
        boolean[] visited = new boolean[graph.getVertices()];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited[start] = true;
        int index = 0;

        while (!queue.isEmpty()) {
            int current = queue.poll();
            bfsOrder[index++] = current;

            for (int neighbor : graph.getAdjacencyList()[current]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }

        return bfsOrder;
    }

    /**
     * A helper method that colors the graph vertices in reverse order of the given order array.
     *
     * @param order         An array containing the order of vertices to be colored in reverse.
     * @param maxDegree     The maximum degree of the graph.
     * @param gui           A GraphColoringGUI object used to update the GUI as the graph is being colored.
     * @param added_message A string message to be displayed in the GUI.
     * @return An array of colors assigned to vertices.
     */
    private int[] colorVerticesInReverseOrder(int[] order, int maxDegree, GraphColoringGUI gui, String added_message) {
        int[] colors = new int[graph.getVertices()];
        Arrays.fill(colors, -1);

        for (int i = order.length - 1; i >= 0; i--) {
            int current = order[i];
            colorVertices(maxDegree, gui, colors, current, added_message);
        }

        return colors;
    }


    /**
     * A helper method that calculates the maximum chromatic index from the given colors array.
     *
     * @param colors An array representing the current colors of vertices.
     * @return The maximum chromatic index of the graph.
     */
    private int getMaxChromaticIndex(int[] colors) {
        int maxColor = 0;
        for (int color : colors) {
            maxColor = Math.max(maxColor, color);
        }
        return maxColor + 1;
    }

}

