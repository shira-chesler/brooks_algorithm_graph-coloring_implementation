import java.util.*;

/**
 * The Graph class represents a graph structure.
 */
public class Graph {
    private int vertices;
    private ArrayList<Integer>[] adjacencyList;

    /**
     * Constructs a new Graph object with the specified number of vertices.
     *
     * @param vertices The number of vertices in the graph.
     */
    public Graph(int vertices) {
        this.vertices = vertices;
        adjacencyList = new ArrayList[vertices];
        for (int i = 0; i < vertices; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
    }

    /**
     * Adds an edge between two vertices.
     *
     * @param from The starting vertex.
     * @param to   The ending vertex.
     */
    public void addEdge(int from, int to) {
        adjacencyList[from].add(to);
        adjacencyList[to].add(from);
    }

    /**
     * Calculates the maximum degree of the graph.
     *
     * @return The maximum degree.
     */
    public int getMaxDegree() {
        int maxDegree = 0;
        for (int i = 0; i < vertices; i++) {
            maxDegree = Math.max(maxDegree, adjacencyList[i].size());
        }
        return maxDegree;
    }

    /**
     * Retrieves the adjacency list of the graph.
     *
     * @return An array of ArrayLists representing the adjacency list.
     */
    public ArrayList<Integer>[] getAdjacencyList() {
        return adjacencyList;
    }

    /**
     * Retrieves the number of vertices in the graph.
     *
     * @return The number of vertices.
     */
    public int getVertices() {
        return vertices;
    }

    /**
     * Determines whether the graph is a clique.
     *
     * @return True if the graph is a clique, otherwise false.
     */
    public boolean isClique() {
        for (int i = 0; i < vertices; i++) {
            if (adjacencyList[i].size() != vertices - 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determines whether the graph contains an odd cycle.
     *
     * @return True if the graph contains an odd cycle, otherwise false.
     */
    public boolean isOddCycle() {
        if (getMaxDegree() != 2) return false;
        boolean[] visited = new boolean[vertices];
        int[] levels = new int[vertices];
        for (int i = 0; i < vertices; i++) {
            if (!visited[i]) {
                if (dfsCycleDetection(i, -1, 0, visited, levels)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determines whether the graph is connected.
     *
     * @return True if the graph is connected, otherwise false.
     */
    public boolean isConnected() {
        boolean[] visited = new boolean[vertices];
        dfsConnected(0, visited);

        // Check if all vertices have been visited
        for (boolean visitStatus : visited) {
            if (!visitStatus) {
                return false;
            }
        }
        return true;
    }

    /**
     * Performs a depth-first search to detect cycles in the graph.
     *
     * @param node    The current node being visited.
     * @param parent  The parent node of the current node.
     * @param level   The depth level of the current node.
     * @param visited An array of booleans representing the visitation status of each vertex.
     * @param levels  An array of integers representing the depth level of each vertex.
     * @return True if an odd cycle is detected, otherwise false.
     */
    private boolean dfsCycleDetection(int node, int parent, int level, boolean[] visited, int[] levels) {
        visited[node] = true;
        levels[node] = level;

        for (int neighbor : adjacencyList[node]) {
            if (!visited[neighbor]) {
                if (dfsCycleDetection(neighbor, node, level + 1, visited, levels)) {
                    return true;
                }
            } else if (neighbor != parent) {
                // Cycle detected, now check if it's odd
                if ((levels[node] - levels[neighbor]) % 2 == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Performs a depth-first search to determine the connectedness of the graph.
     *
     * @param node    The current node being visited.
     * @param visited An array of booleans representing the visitation status of each vertex.
     */
    private void dfsConnected(int node, boolean[] visited) {
        visited[node] = true;

        if (adjacencyList[node] == null) return;

        // Traverse all neighbors of the current node
        for (int neighbor : adjacencyList[node]) {
            if (!visited[neighbor]) {
                dfsConnected(neighbor, visited);
            }
        }
    }

    /**
     * Checks if there is an edge between two vertices.
     *
     * @param y The first vertex.
     * @param z The second vertex.
     * @return True if there is an edge between y and z, otherwise false.
     */
    public boolean hasEdge(int y, int z) {
        return this.adjacencyList[y].contains(z);
    }

    /**
     * Creates a deep copy of the graph.
     *
     * @return A new Graph object with the same structure as the original.
     */
    public Graph copy() {
        Graph tmp = new Graph(vertices);
        for (int i = 0; i < this.adjacencyList.length; i++) {
            for (int vertex : this.adjacencyList[i]) {
                if (tmp.adjacencyList[i].contains(vertex)) {
                    continue;
                }
                tmp.addEdge(i, vertex);
            }
        }
        return tmp;
    }

    /**
     * Removes a vertex from the graph by setting its adjacency list entry to null.
     *
     * @param z The vertex to remove.
     */
    public void removeVertex(int z) {
        this.adjacencyList[z] = null;
    }
}
