import java.util.*;

/**
 * CutVertexFinder is a class that finds cut vertices (articulation points) in a graph.
 */
public class CutVertexFinder {
    private Graph graph;
    private int time;
    private int[] visitedTime;
    private int[] lowTime;
    private boolean[] visited;
    private boolean[] isCutVertex;

    /**
     * Constructor for CutVertexFinder class.
     *
     * @param graph The input graph to find cut vertices.
     */
    public CutVertexFinder(Graph graph) {
        this.graph = graph;
    }

    /**
     * Finds the first cut vertex in the graph.
     *
     * @return The first cut vertex found or -1 if no cut vertex is found.
     */
    public int findCutVertices() {
        int n = graph.getVertices();

        visitedTime = new int[n];
        lowTime = new int[n];
        visited = new boolean[n];
        isCutVertex = new boolean[n];
        time = 0;

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs(i, -1);
            }
        }

        Set<Integer> cutVertices = new HashSet<>();
        for (int i = 0; i < n; i++) {
            if (isCutVertex[i]) {
                cutVertices.add(i);
            }
        }
        if (cutVertices.isEmpty()){
            return -1;
        }
        return (int) cutVertices.toArray()[0];
    }

    /**
     * Depth-First Search to find cut vertices.
     *
     * @param node   The current node being visited.
     * @param parent The parent node of the current node.
     */
    private void dfs(int node, int parent) {
        visited[node] = true;
        visitedTime[node] = lowTime[node] = time++;
        int childrenCount = 0;

        for (int neighbor : graph.getAdjacencyList()[node]) {
            if (neighbor == parent) {
                continue;
            }
            if (!visited[neighbor]) {
                childrenCount++;
                dfs(neighbor, node);
                lowTime[node] = Math.min(lowTime[node], lowTime[neighbor]);

                // If the current node is a cut vertex, mark it as such
                if (visitedTime[node] <= lowTime[neighbor] && parent != -1) {
                    isCutVertex[node] = true;
                }
            } else {
                lowTime[node] = Math.min(lowTime[node], visitedTime[neighbor]);
            }
        }

        // If the root node has more than one child, it is a cut vertex
        if (parent == -1 && childrenCount > 1) {
            isCutVertex[node] = true;
        }
    }
}
