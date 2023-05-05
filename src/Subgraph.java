import java.util.HashSet;
import java.util.Set;

/**
 * Subgraph class creates a subgraph from an original graph by excluding
 * a specific neighbor of a given cut vertex.
 */
public class Subgraph {
    private Graph originalGraph;
    private Graph subgraph;
    private Set<Integer> subgraphNodes;

    /**
     * Constructs a new Subgraph object with the given original graph,
     * cut vertex, and excluded neighbor.
     *
     * @param originalGraph The original graph to create the subgraph from.
     * @param cutVertex     The cut vertex to start creating the subgraph.
     * @param excludedNeighbor The neighbor of the cut vertex to be excluded from the subgraph.
     */
    public Subgraph(Graph originalGraph, int cutVertex, int excludedNeighbor) {
        this.originalGraph = originalGraph;
        this.subgraph = new Graph(originalGraph.getVertices());
        this.subgraphNodes = new HashSet<>();

        createSubgraphWithoutNeighbor(cutVertex, excludedNeighbor);
    }

    /**
     * Creates a subgraph without the excluded neighbor of the cut vertex.
     *
     * @param cutVertex        The cut vertex to start creating the subgraph.
     * @param excludedNeighbor The neighbor of the cut vertex to be excluded from the subgraph.
     */
    private void createSubgraphWithoutNeighbor(int cutVertex, int excludedNeighbor) {
        Set<Integer> visited = new HashSet<>();
        visited.add(excludedNeighbor);

        for (int neighbor : originalGraph.getAdjacencyList()[cutVertex]) {
            if (neighbor != excludedNeighbor && isntNeighborOfExcluded(neighbor, excludedNeighbor)) {
                addEdgesToSubgraph(cutVertex, neighbor, visited);
            }
        }
    }

    /**
     * Checks if a vertex is not a neighbor of the excluded neighbor.
     *
     * @param neighbor         The vertex to check.
     * @param excludedNeighbor The excluded neighbor.
     * @return true if the vertex is not a neighbor of the excluded neighbor, false otherwise.
     */
    private boolean isntNeighborOfExcluded(int neighbor, int excludedNeighbor) {
        return originalGraph.getAdjacencyList()[excludedNeighbor].contains(neighbor);
    }

    /**
     * Adds edges to the subgraph recursively.
     *
     * @param vertex   The current vertex.
     * @param neighbor The neighbor of the current vertex.
     * @param visited  A set of visited vertices.
     */
    private void addEdgesToSubgraph(int vertex, int neighbor, Set<Integer> visited) {
        addEdge(vertex, neighbor);
        visited.add(neighbor);

        for (int nextNeighbor : originalGraph.getAdjacencyList()[neighbor]) {
            if (!visited.contains(nextNeighbor)) {
                addEdgesToSubgraph(neighbor, nextNeighbor, visited);
            }
        }
    }

    /**
     * Adds an edge between two vertices in the subgraph and updates the set
     * of subgraph nodes.
     *
     * @param v The first vertex of the edge.
     * @param w The second vertex of the edge.
     */
    public void addEdge(int v, int w) {
        subgraph.addEdge(v, w);
        subgraphNodes.add(v);
        subgraphNodes.add(w);
    }

    /**
     * Checks if the subgraph contains a given node.
     *
     * @param node The node to check for.
     * @return true if the subgraph contains the node, false otherwise.
     */
    public boolean containsNode(int node) {
        return subgraphNodes.contains(node);
    }

    /**
     * Returns the subgraph generated from the original graph.
     *
     * @return A Graph object representing the subgraph.
     */
    public Graph getSubgraph() {
        return subgraph;
    }
}
