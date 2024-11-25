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
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 5),
                new Edge("B", "C", 1),
                new Edge("A", "C", 10),
                new Edge("C", "D", 2),
                new Edge("B", "D", 9),
                new Edge("D", "E", 3)
        );

        CourierRouteOptimizer optimizer = new CourierRouteOptimizer();
        Map<String, String> prevNodes = new HashMap<>();
        Map<String, Double> shortestPaths = optimizer.findShortestPath("A", edges, prevNodes);

        System.out.println("Shortest paths from A:");
        for (Map.Entry<String, Double> entry : shortestPaths.entrySet()) {
            System.out.printf("To %s: %.2f, Path: %s\n",
                    entry.getKey(),
                    entry.getValue(),
                    optimizer.reconstructPath("A", entry.getKey(), prevNodes));
        }
    }
}
