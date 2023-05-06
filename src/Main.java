import static java.lang.Thread.sleep;

public class Main {

    public static void main(String[] args) throws InterruptedException {
//        Graph graph = new Graph(6);
//        graph.addEdge(0, 1);
//        graph.addEdge(0, 2);
//        graph.addEdge(0, 3);
//        graph.addEdge(0, 4);
//        graph.addEdge(1, 2);
//        graph.addEdge(1, 3);
//        graph.addEdge(1, 4);
//        graph.addEdge(2, 3);
//        graph.addEdge(2, 4);
//        graph.addEdge(3, 4);
//        graph.addEdge(4, 5);
//
//
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
//
//
//        Graph graph = new Graph(8);
//        graph.addEdge(0,1);
//        graph.addEdge(1,2);
//        graph.addEdge(2, 3);
//        graph.addEdge(3, 0);
//        graph.addEdge(2, 4);
//        graph.addEdge(4, 5);
//        graph.addEdge(5, 6);
//        graph.addEdge(6, 7);
//        graph.addEdge(7, 4);
//
//
//        Graph graph = new Graph(10);
//        graph.addEdge(0, 1);
//        graph.addEdge(0, 2);
//        graph.addEdge(0, 3);
//        graph.addEdge(0, 4);
//        graph.addEdge(1, 2);
//        graph.addEdge(1, 3);
//        graph.addEdge(1, 4);
//        graph.addEdge(2, 3);
//        graph.addEdge(2, 4);
//        graph.addEdge(3, 4);
//        graph.addEdge(5, 6);
//        graph.addEdge(5, 7);
//        graph.addEdge(5, 8);
//        graph.addEdge(5, 9);
//        graph.addEdge(6, 7);
//        graph.addEdge(6, 8);
//        graph.addEdge(6, 9);
//        graph.addEdge(7, 8);
//        graph.addEdge(7, 9);
//        graph.addEdge(8, 9);
//        graph.addEdge(4, 5);
//
//
//        Graph graph = new Graph(12);
//        graph.addEdge(0, 1);
//        graph.addEdge(0, 2);
//        graph.addEdge(0, 3);
//        graph.addEdge(0, 4);
//        graph.addEdge(1, 2);
//        graph.addEdge(1, 3);
//        graph.addEdge(1, 4);
//        graph.addEdge(2, 3);
//        graph.addEdge(2, 4);
//        graph.addEdge(3, 4);
//
//        graph.addEdge(5, 6);
//        graph.addEdge(5, 7);
//        graph.addEdge(5, 8);
//        graph.addEdge(5, 9);
//        graph.addEdge(6, 7);
//        graph.addEdge(6, 8);
//        graph.addEdge(6, 9);
//        graph.addEdge(7, 8);
//        graph.addEdge(7, 9);
//        graph.addEdge(8, 9);
//
//        graph.addEdge(10, 11);
//        graph.addEdge(10, 0);
//        graph.addEdge(10, 1);
//        graph.addEdge(10, 2);
//        graph.addEdge(11, 5);
//        graph.addEdge(11, 6);
//        graph.addEdge(11, 7);
//
//
//        Graph graph = new Graph(5);
//        graph.addEdge(1,2);
//        graph.addEdge(2,3);
//        graph.addEdge(1,3);
//        graph.addEdge(3,4);
//        graph.addEdge(0,3);
//        graph.addEdge(0,4);


//        Graph graph = new Graph(7);
//        graph.addEdge(0, 1);
//        graph.addEdge(0, 2);
//        graph.addEdge(0, 3);
//        graph.addEdge(0, 4);
//        graph.addEdge(0, 5);
//        graph.addEdge(0, 6);
//        graph.addEdge(1, 2);
//        graph.addEdge(1, 3);
//        graph.addEdge(1, 4);
//        graph.addEdge(1, 5);
//        graph.addEdge(1, 6);
//        graph.addEdge(2, 3);
//        graph.addEdge(2, 4);
//        graph.addEdge(2, 5);
//        graph.addEdge(2, 6);
//        graph.addEdge(3, 4);
//        graph.addEdge(3, 5);
//        graph.addEdge(3, 6);
//        graph.addEdge(4, 5);
//        graph.addEdge(4, 6);
//        graph.addEdge(5, 6);
//

//        Graph graph = new Graph(6);
//        graph.addEdge(0, 1);
//        graph.addEdge(0, 2);
//        graph.addEdge(0, 3);
//        graph.addEdge(1, 2);
//        graph.addEdge(1, 4);
//        graph.addEdge(2, 5);
//        graph.addEdge(3, 4);
//        graph.addEdge(3, 5);
//        graph.addEdge(4, 5);
//
//        GraphColoringGUI gui = new GraphColoringGUI(graph);
//        GraphColoring grpc = new GraphColoring(graph);
//        grpc.brooksAlgorithm(gui);
        GraphInputGUI guix = new GraphInputGUI();
        while (!guix.Getfinished()){
            try{
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Graph graph = guix.getInputGraph();
        GraphColoringGUI gui = new GraphColoringGUI(graph);
        GraphColoring grpc = new GraphColoring(graph);
        grpc.brooksAlgorithm(gui);
//        0 1
//        1 2
//        1 3
//        1 4
//        0 2
//        0 3
//        0 4
//        2 3
//        2 4
//        3 4
    }
}


