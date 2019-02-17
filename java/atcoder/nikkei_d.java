import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] nm = scanner.nextLine().split(" ", 2);
        int n = Integer.parseInt(nm[0]);
        int m = Integer.parseInt(nm[1]);

        Map<Integer, List<Integer>> vParents = new HashMap<>();
        Map<Integer, SortedSet<Integer>> vEdges = new HashMap<>();
        for (int i = 0; i < n - 1 + m; i++) {
            String[] ab = scanner.nextLine().split(" ", 2);
            int a = Integer.parseInt(ab[0]);
            int b = Integer.parseInt(ab[1]);

            SortedSet<Integer> edges = vEdges.get(a);
            if (edges == null) {
                edges = new TreeSet<>();
                vEdges.put(a, edges);
            }
            edges.add(b);

            List<Integer> parents = vParents.get(b);
            if (parents == null) {
                parents = new ArrayList<>();
                vParents.put(b, parents);
            }
            parents.add(a);
        }

        List<Integer> sorted = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        for (Integer v : vEdges.keySet()) {
            topologicalSortVisit(sorted, visited, vEdges, v);
        }

        int[] rank = new int[n+1];
        for (int i = 0; i < sorted.size(); i++) {
            rank[sorted.get(i)] = i;
        }

        for (int i = 1; i <= n; i++) {
            List<Integer> parents = vParents.get(i);
            int maxParent = 0;
            if (parents != null) {
                for (Integer parent : parents) {
                    if (maxParent == 0 || rank[maxParent] < rank[parent]) {
                        maxParent = parent;
                    }
                }
            }
            System.out.println(maxParent);
        }
    }

    private static void topologicalSortVisit(List<Integer> sorted, Set<Integer> visited,
            Map<Integer, SortedSet<Integer>> vEdges, Integer v) {
        if (visited.contains(v)) return;
        visited.add(v);

        if (vEdges.containsKey(v)) {
            for (Integer nextV : vEdges.get(v)) {
                topologicalSortVisit(sorted, visited, vEdges, nextV);
            }
        }
        sorted.add(0, v);
    }
}
