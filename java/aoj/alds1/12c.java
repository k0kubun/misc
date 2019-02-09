import java.util.*;

class Main {
    enum Color {
        WHITE,
        GRAY,
        BLACK,
    }

    private static class Edge {
        int toV;
        int weight;

        public Edge(int toV, int weight) {
            this.toV = toV;
            this.weight = weight;
        }
    }

    private static class MinCost implements Comparable<MinCost> {
        int v;
        int cost;

        public MinCost(int v, int cost) {
            this.v = v;
            this.cost = cost;
        }

        @Override
        public int compareTo(MinCost p) {
            return this.cost - p.cost;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());

        int[][] matrix = new int[n][n];
        List<List<Edge>> adjLists = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            String[] line = scanner.nextLine().split(" ", -1);
            int fromV = Integer.parseInt(line[0]);
            int numVectors = Integer.parseInt(line[1]);

            List<Edge> adjList = new ArrayList<>(numVectors);
            for (int j = 0; j < numVectors; j++) {
                int toV = Integer.parseInt(line[2 + j * 2]);
                int weight = Integer.parseInt(line[2 + j * 2 + 1]);
                adjList.add(new Edge(toV, weight));
            }
            adjLists.add(adjList);
        }

        dijkstra(adjLists, n);
    }

    private static void dijkstra(List<List<Edge>> adjLists, int n) {
        PriorityQueue<MinCost> pq = new PriorityQueue<>();
        Color[] color = new Color[n];
        int[] dMin = new int[n];

        Arrays.fill(color, Color.WHITE);
        Arrays.fill(dMin, Integer.MAX_VALUE);

        pq.add(new MinCost(0, 0));
        dMin[0] = 0;
        color[0] = Color.GRAY;

        while (pq.size() > 0) {
            MinCost minCost = pq.poll();
            color[minCost.v] = Color.BLACK;

            if (dMin[minCost.v] < minCost.cost)
                continue; // extract min, but if it's not the shortest, ignore.

            for (Edge adj : adjLists.get(minCost.v)) {
                if (color[adj.toV] != Color.BLACK && dMin[minCost.v] + adj.weight < dMin[adj.toV]) {
                    dMin[adj.toV] = dMin[minCost.v] + adj.weight;
                    pq.add(new MinCost(adj.toV, dMin[adj.toV]));
                    color[adj.toV] = Color.GRAY;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            System.out.printf("%d %d\n", i, dMin[i]);
        }
    }
}
