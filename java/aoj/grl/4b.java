import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] ve = scanner.nextLine().split(" ", 2);
        int v = Integer.parseInt(ve[0]);
        int e = Integer.parseInt(ve[1]);

        Map<Integer, List<Integer>> vEdges = new HashMap<>();

        for (int i = 0; i < e; i++) {
            String[] st = scanner.nextLine().split(" ", 2);
            int s = Integer.parseInt(st[0]);
            int t = Integer.parseInt(st[1]);

            List<Integer> edges = vEdges.get(s);
            if (edges == null) {
                edges = new ArrayList<>();
                vEdges.put(s, edges);
            }
            edges.add(t);
        }

        Set<Integer> visited = new HashSet<>();
        List<Integer> sorted = new ArrayList<>();
        for (int i = 0; i < v; i++) {
            visit(visited, sorted, vEdges, i, 0);
        }

        for (Integer vertex : sorted) {
            System.out.println(vertex);
        }
    }

    private static void visit(Set<Integer> visited, List<Integer> sorted, Map<Integer, List<Integer>> vEdges, int vertex, int depth) {
        if (visited.contains(vertex)) return;
        visited.add(vertex);

        if (vEdges.containsKey(vertex)) {
            for (Integer edge : vEdges.get(vertex)) {
                visit(visited, sorted, vEdges, edge, depth + 1);
            }
        }
        sorted.add(0, vertex);
    }
}
