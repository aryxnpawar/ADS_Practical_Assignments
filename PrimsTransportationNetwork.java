import java.util.*;
import java.util.HashMap;

public class PrimsTransportationNetwork {
    static class Edge{
        final String from;
        final String to;
        final double weight;

        public Edge(String from,String to,double weight){
            this.from=from;
            this.to=to;
            this.weight=weight;
        }

        @Override
        public String toString(){
            return String.format("Edge : %s -> %s, cost : %.2f",from,to,weight);
        }
    }

    public List<Edge> findMST(String startVertex,List<Edge> edges){
        List<Edge> res = new ArrayList<>();
        HashSet<String> visited = new HashSet<>();
        HashMap<String,List<Edge>> adjList = new HashMap<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingDouble(edge -> edge.weight));

        for (Edge e :
                edges) {
            adjList.computeIfAbsent(e.from,k->new ArrayList<>()).add(e);
            adjList.computeIfAbsent(e.to,k->new ArrayList<>()).add(new Edge(e.to,e.from,e.weight));
        }

        visited.add(startVertex);
        pq.addAll(adjList.getOrDefault(startVertex,Collections.emptyList()));

        while(!pq.isEmpty()){
            Edge edge = pq.poll();
            String  from = edge.from;
            String  to = edge.to;

            if(visited.contains(to))
                continue;

            res.add(edge);
            visited.add(to);

            for (Edge adjacentEdge :
                    adjList.getOrDefault(edge.to, Collections.emptyList())) {
                if (!visited.contains(adjacentEdge.to))
                    pq.add(adjacentEdge);
            }
        }

        return res;
    }

    public static void main(String[] args) {
        PrimsTransportationNetwork pMST = new PrimsTransportationNetwork();
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
                        System.out.println("Enter starting vertex : ");
                        String startVertex = sc.nextLine();
                        for (Edge e :
                                pMST.findMST(startVertex,inputEdges)) {
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
}
