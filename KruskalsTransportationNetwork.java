import java.util.*;
import java.util.HashMap;

public class KruskalsTransportationNetwork {

    static class Edge {
        final String from;
        final String to;
        final double weight;

        public Edge(String from, String to, double weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return String.format("Road: %s -> %s, Cost: %.2f", from, to, weight);
        }
    }

    public List<Edge> findOptimalRoadSystem(List<Edge> edges) {
        List<Edge> result = new ArrayList<>();

        // Union-Find helper class
        class UnionFind {
            private final HashMap<String, String> parent = new HashMap<>();

            public UnionFind() {
                for (Edge edge : edges) {
                    parent.putIfAbsent(edge.from, edge.from);
                    parent.putIfAbsent(edge.to, edge.to);
                }
            }

            public String find(String vertex) {
                if (!parent.get(vertex).equals(vertex)) {
                    parent.put(vertex, find(parent.get(vertex))); // Path compression
                }
                return parent.get(vertex);
            }

            public void union(String vertexA, String vertexB) {
                String rootA = find(vertexA);
                String rootB = find(vertexB);
                if (!rootA.equals(rootB)) {
                    parent.put(rootA, rootB);
                }
            }

            public boolean isConnected(String vertexA, String vertexB) {
                return find(vertexA).equals(find(vertexB));
            }
        }

        // Initialize Union-Find and priority queue
        UnionFind uf = new UnionFind();
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingDouble(e -> e.weight));
        pq.addAll(edges);

        // Kruskal's algorithm
        while (!pq.isEmpty()) {
            Edge currentEdge = pq.poll();
            String from = currentEdge.from;
            String to = currentEdge.to;

            if (!uf.isConnected(from, to)) {
                result.add(currentEdge);
                uf.union(from, to);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        KruskalsTransportationNetwork kMST = new KruskalsTransportationNetwork();
        Scanner sc = new Scanner(System.in);
        int choice ;
        List<Edge> inputEdges = new ArrayList<>();
        System.out.println("Welcome to our Transportation service!");
        while (true){
            System.out.println("Enter 1 to add roads : ");
            System.out.println("Enter 2 to find MST : ");
            System.out.println("Enter 3 to exit : ");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice){
                case 1:
                    while (true){
                        System.out.println("Enter City A : ");
                        String cityA = sc.nextLine();
                        System.out.println("Enter City B : ");
                        String cityB = sc.nextLine();
                        System.out.printf("Enter the road length between %s and %s\n",cityA,cityB);
                        double distance = sc.nextDouble();
                        sc.nextLine();
                        inputEdges.add(new Edge(cityA,cityB,distance));
                        System.out.println("Enter 1 to continue adding new Edges!");
                        System.out.println("Enter 0 to stop adding new Edges!");
                        int continueOrStop = sc.nextInt();
                        sc.nextLine();
                        if (continueOrStop==0)
                            break;
                    }
                    break;
                case 2:
                    if(inputEdges.isEmpty())
                        System.out.println("No edges added!");
                    else {
                        for (Edge e :
                                kMST.findOptimalRoadSystem(inputEdges)) {
                            System.out.println(e.toString());
                        }
                    }
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Enter valid option!");
            }
        }
    }

    // public static void main(String[] args) {
    //     // Define the graph (cities and road costs)
    //     List<Edge> edges = Arrays.asList(
    //             new Edge("A", "B", 5),
    //             new Edge("B", "C", 4),
    //             new Edge("B", "D", 2),
    //             new Edge("A", "D", 4),
    //             new Edge("A", "E", 1),
    //             new Edge("E", "D", 2),
    //             new Edge("E", "F", 1),
    //             new Edge("F", "D", 5),
    //             new Edge("F", "G", 7),
    //             new Edge("D", "G", 11),
    //             new Edge("D", "H", 2),
    //             new Edge("C", "H", 4),
    //             new Edge("G", "H", 1),
    //             new Edge("I", "G", 4),
    //             new Edge("I", "C", 1),
    //             new Edge("I", "H", 6),
    //             new Edge("C", "J", 2),
    //             new Edge("I", "J", 0)
    //     );

    //     KruskalsTransportationNetwork network = new KruskalsTransportationNetwork();
    //     List<Edge> optimalRoadSystem = network.findOptimalRoadSystem(edges);

    //     System.out.println("Optimal Road System (Minimum Spanning Tree):");
    //     double totalCost = 0;
    //     for (Edge road : optimalRoadSystem) {
    //         System.out.println(road);
    //         totalCost += road.weight;
    //     }
    //     System.out.printf("Total Construction Cost: %.2f\n", totalCost);
    // }
}
