/**
 * SpanningTreeOrdering class constructs a depth-first search (DFS) spanning tree
 * and provides an ordering of the vertices in the graph.
 */
public class SpanningTreeOrdering {
    private Graph graph;
    private int root;
    private Graph tree;

    /**
     * Constructs a new SpanningTreeOrdering object with the given graph and
     * finds a vertex with a smaller degree to set as the root.
     *
     * @param graph The graph to perform spanning tree ordering on.
     */
    public SpanningTreeOrdering(Graph graph) {
        this.graph = graph;
        this.root = findVertexWithSmallerDegree();
        this.tree = new Graph(graph.getVertices());
    }

    /**
     * Constructs a new SpanningTreeOrdering object with the given graph and
     * specified root vertex.
     *
     * @param graph The graph to perform spanning tree ordering on.
     * @param root  The root vertex for the spanning tree.
     */
    public SpanningTreeOrdering(Graph graph, int root) {
        this.graph = graph;
        this.root = root;
        this.tree = new Graph(graph.getVertices());
        reverseDepthFirstSearchOrder(root);
    }

    /**
     * Finds an ordering of vertices using depth-first search (DFS) starting from the root.
     *
     * @return An array of integers representing the order of vertices in the graph.
     */
    public int[] findOrdering() {
        if (root == -1) {
            return null;
        }
        int[] dfsOrder = reverseDepthFirstSearchOrder(root);
        return dfsOrder;
    }

    /**
     * Finds a vertex with a degree smaller than the maximum degree in the graph.
     *
     * @return The index of the vertex with a smaller degree, or -1 if not found.
     */
    private int findVertexWithSmallerDegree() {
        int maxDegree = graph.getMaxDegree();

        for (int i = 0; i < graph.getVertices(); i++) {
            if (graph.getAdjacencyList()[i].size() < maxDegree) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the root vertex of the spanning tree.
     *
     * @return The index of the root vertex.
     */
    public int getRoot() {
        return root;
    }

    /**
     * Returns the spanning tree generated during the ordering process.
     *
     * @return A Graph object representing the spanning tree.
     */
    public Graph getTree() {
        return tree;
    }

    /**
     * Performs reverse depth-first search starting from the given vertex.
     *
     * @param start The starting vertex for the depth-first search.
     * @return An array of integers representing the reverse order of the depth-first search.
     */
    private int[] reverseDepthFirstSearchOrder(int start) {
        int[] dfsOrder = new int[graph.getVertices()];
        boolean[] visited = new boolean[graph.getVertices()];
        int index = 0;

        index = depthFirstSearch(start, visited, dfsOrder, index);

        return dfsOrder;
    }

    /**
     * Recursive depth-first search function.
     *
     * @param current  The current vertex being visited.
     * @param visited  An array of booleans representing the visited status of each vertex.
     * @param dfsOrder An array of integers to store the order of visited vertices.
     * @param index    The current index in the dfsOrder array.
     * @return The updated index in the dfsOrder array.
     */
    private int depthFirstSearch(int current, boolean[] visited, int[] dfsOrder, int index) {
        visited[current] = true;

        // If the adjacency list of the current vertex is null, set the dfsOrder at the current index to -1 and increment the index.
        if (graph.getAdjacencyList()[current] == null) {
            dfsOrder[index] = -1;
            return index + 1;
        }

        // Iterate through the neighbors of the current vertex.
        for (int neighbor : graph.getAdjacencyList()[current]) {
            // If the neighbor has not been visited, perform a depth-first search on it.
            if (!visited[neighbor]) {
                index = depthFirstSearch(neighbor, visited, dfsOrder, index);
                // If the adjacency list of the neighbor is not null, means the neighbor was nod deleted -
                // add an edge between the current vertex and the neighbor in the tree.
                if (graph.getAdjacencyList()[neighbor] != null) {
                    tree.addEdge(current, neighbor);
                }
            }
        }

        // Set the dfsOrder at the current index to the current vertex and increment the index.
        dfsOrder[index] = current;
        return index + 1;
    }
}

