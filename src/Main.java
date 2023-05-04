

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph(6);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(0, 3);
        graph.addEdge(0, 4);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 3);
        graph.addEdge(2, 4);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
//        Graph graph = new Graph(17);
//        graph.addEdge(0, 1);
//        graph.addEdge(0, 2);
//        graph.addEdge(0, 3);
//        graph.addEdge(1, 4);
//        graph.addEdge(1, 5);
//        graph.addEdge(2, 6);
//        graph.addEdge(2, 7);
//        graph.addEdge(3, 8);
//        graph.addEdge(3, 9);
//        graph.addEdge(4, 10);
//        graph.addEdge(4, 11);
//        graph.addEdge(5, 11);
//        graph.addEdge(5, 12);
//        graph.addEdge(6, 12);
//        graph.addEdge(6, 13);
//        graph.addEdge(7, 13);
//        graph.addEdge(7, 14);
//        graph.addEdge(8, 14);
//        graph.addEdge(8, 15);
//        graph.addEdge(9, 15);
//        graph.addEdge(9, 10);
//        graph.addEdge(0, 10);
//        graph.addEdge(1, 9);
//        graph.addEdge(10, 16);
//        graph.addEdge(11, 16);
        GraphColoringGUI gui = new GraphColoringGUI(graph);
        GraphColoring grpc = new GraphColoring(graph);
        grpc.brooksAlgorithm(gui);
    }
}


