import java.util.*;

public class CourierRouteOptimizer {
    static class Edge {
        final String from;
        final String to;
        final double weight;

        public Edge(String from, String to, double weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    public Map<String, Double> findShortestPath(String startVertex, List<Edge> edges, Map<String, String> prevNodes) {
        // Adjacency list representation
        HashMap<String, List<Edge>> adjList = new HashMap<>();
        for (Edge edge : edges) {
            adjList.computeIfAbsent(edge.from, k -> new ArrayList<>()).add(edge);
        }

        // Min-heap to prioritize nodes with smaller distances
        PriorityQueue<Map.Entry<String, Double>> pq = new PriorityQueue<>(Comparator.comparingDouble(Map.Entry::getValue));
        HashMap<String, Double> distances = new HashMap<>();
        HashSet<String> visited = new HashSet<>();

        // Initialize distances
        for (Edge edge : edges) {
            distances.put(edge.from, Double.MAX_VALUE);
            distances.put(edge.to, Double.MAX_VALUE);
        }
        distances.put(startVertex, 0.0);

        pq.offer(new AbstractMap.SimpleEntry<>(startVertex, 0.0));

        while (!pq.isEmpty()) {
            Map.Entry<String, Double> current = pq.poll();
            String currentVertex = current.getKey();
            double currentDistance = current.getValue();

            if (visited.contains(currentVertex)) continue;
            visited.add(currentVertex);

            for (Edge edge : adjList.getOrDefault(currentVertex, Collections.emptyList())) {
                String neighbor = edge.to;
                double newDistance = currentDistance + edge.weight;

                if (newDistance < distances.getOrDefault(neighbor, Double.MAX_VALUE)) {
                    distances.put(neighbor, newDistance);
                    prevNodes.put(neighbor, currentVertex); // Track the previous node
                    pq.offer(new AbstractMap.SimpleEntry<>(neighbor, newDistance));
                }
            }
        }

        return distances;
    }

    public List<String> reconstructPath(String start, String end, Map<String, String> prevNodes) {
        List<String> path = new LinkedList<>();
        String current = end;
        while (current != null && !current.equals(start)) {
            path.add(0, current); // Add to the beginning of the list
            current = prevNodes.get(current);
        }
        if (current != null) {
            path.add(0, start);
        }
        return path;
    }

    public static void main(String[] args) {
        CourierRouteOptimizer routeOptimizer = new CourierRouteOptimizer();
        Scanner sc = new Scanner(System.in);
        List<Edge> inputEdges = new ArrayList<>();
        int choice;

        System.out.println("Welcome to the Route Planner!");
        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Add Routes");
            System.out.println("2. Find Shortest Path");
            System.out.println("3. Exit");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    while (true) {
                        System.out.println("Enter City A:");
                        String cityA = sc.nextLine();
                        System.out.println("Enter City B:");
                        String cityB = sc.nextLine();
                        System.out.printf("Enter the distance between %s and %s:\n", cityA, cityB);
                        double distance = sc.nextDouble();
                        sc.nextLine();

                        inputEdges.add(new Edge(cityA, cityB, distance));
                        System.out.println("Enter 1 to add more routes, 0 to stop:");
                        int continueAdding = sc.nextInt();
                        sc.nextLine();
                        if (continueAdding == 0) break;
                    }
                    break;

                case 2:
                    if (inputEdges.isEmpty()) {
                        System.out.println("No routes available! Add some routes first.");
                        break;
                    }
                    System.out.println("Enter the starting city:");
                    String startCity = sc.nextLine();
                    Map<String, String> prevNodes = new HashMap<>();
                    Map<String, Double> shortestPaths = routeOptimizer.findShortestPath(startCity, inputEdges, prevNodes);

                    System.out.println("Shortest paths from " + startCity + ":");
                    for (Map.Entry<String, Double> entry : shortestPaths.entrySet()) {
                        String destination = entry.getKey();
                        double distance = entry.getValue();
                        List<String> path = routeOptimizer.reconstructPath(startCity, destination, prevNodes);
                        System.out.printf("To %s: Distance = %.2f, Path = %s\n", destination, distance, path);
                    }
                    break;

                case 3:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
