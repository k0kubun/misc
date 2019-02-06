import java.util.*;

class Main {
    enum Color {
        WHITE,
        GRAY,
        BLACK,
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());

        int[][] weight = new int[n][n];

        for (int i = 0; i < n; i++) {
            String[] line = scanner.nextLine().split(" ", n+1);
            for (int j = 0; j < n; j++) {
                int a = Integer.parseInt(line[j+1]);
                weight[i][j] = (a == -1 ? Integer.MAX_VALUE : a);
            }
        }

        System.out.println(prim(weight, n));
    }

    private static int prim(int[][] weight, int n) {
        Color[] color = new Color[n];
        int[] dMin = new int[n];
        int[] parent = new int[n];

        Arrays.fill(dMin, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        Arrays.fill(color, Color.WHITE);

        dMin[0] = 0;

        while (true) {
            // linear search
            int minInd = -1;
            int minCost = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                if (color[i] != Color.BLACK && dMin[i] < minCost) {
                    minInd = i;
                    minCost = dMin[i];
                }
            }

            if (minInd == -1)
                break;

            color[minInd] = Color.BLACK;

            for (int v = 0; v < n; v++) {
                if (color[v] != Color.BLACK && weight[minInd][v] != Integer.MAX_VALUE
                        && weight[minInd][v] < dMin[v]) {
                    dMin[v] = weight[minInd][v];
                    parent[v] = minInd;
                    color[v] = Color.GRAY;
                }
            }
        }

        int result = 0;
        for (int i = 0; i < n; i++) {
            if (parent[i] != -1) {
                result += dMin[i];
                //result += weight[i][parent[i]];
            }
        }
        return result;
    }
}
