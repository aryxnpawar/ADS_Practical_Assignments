package Assignments;

import java.util.*;
import java.util.HashMap;

public class PrimsTransportationNetwork {

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

    public List<Edge> findOptimalRoadSystem(String startCity, List<Edge> roads) {
        List<Edge> result = new ArrayList<>();
        HashSet<String> visited = new HashSet<>();
        HashMap<String, List<Edge>> adjacencyList = new HashMap<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingDouble(edge -> edge.weight));

        // Build adjacency list
        for (Edge road : roads) {
            adjacencyList.computeIfAbsent(road.from, k -> new ArrayList<>()).add(road);
            adjacencyList.computeIfAbsent(road.to, k -> new ArrayList<>())
                    .add(new Edge(road.to, road.from, road.weight)); // Add reverse edge
        }

        // Start from the initial city
        visited.add(startCity);
        pq.addAll(adjacencyList.getOrDefault(startCity, Collections.emptyList()));

        // Prim's algorithm
        while (!pq.isEmpty()) {
            Edge currentEdge = pq.poll();
            String from = currentEdge.from;
            String to = currentEdge.to;

            if (visited.contains(to)) {
                continue; // Skip if the city is already visited
            }

            result.add(currentEdge);
            visited.add(to);

            // Add adjacent edges of the newly visited city
            for (Edge adjacentEdge : adjacencyList.getOrDefault(to, Collections.emptyList())) {
                if (!visited.contains(adjacentEdge.to)) {
                    pq.add(adjacentEdge);
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        // Define the graph (cities and road costs)
        List<Edge> roads = Arrays.asList(
                new Edge("A", "B", 5),
                new Edge("B", "C", 4),
                new Edge("B", "D", 2),
                new Edge("A", "D", 4),
                new Edge("A", "E", 1),
                new Edge("E", "D", 2),
                new Edge("E", "F", 1),
                new Edge("F", "D", 5),
                new Edge("F", "G", 7),
                new Edge("D", "G", 11),
                new Edge("D", "H", 2),
                new Edge("C", "H", 4),
                new Edge("G", "H", 1),
                new Edge("I", "G", 4),
                new Edge("I", "C", 1),
                new Edge("I", "H", 6),
                new Edge("C", "J", 2),
                new Edge("I", "J", 0)
        );

        PrimsTransportationNetwork network = new PrimsTransportationNetwork();
        List<Edge> optimalRoadSystem = network.findOptimalRoadSystem("A", roads);

        System.out.println("Optimal Road System (Minimum Spanning Tree):");
        double totalCost = 0;
        for (Edge road : optimalRoadSystem) {
            System.out.println(road);
            totalCost += road.weight;
        }
        System.out.printf("Total Construction Cost: %.2f\n", totalCost);
    }
}
