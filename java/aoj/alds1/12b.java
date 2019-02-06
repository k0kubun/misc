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

        int[][] matrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            String[] line = scanner.nextLine().split(" ", -1);
            int fromV = Integer.parseInt(line[0]);
            int numVectors = Integer.parseInt(line[1]);

            Arrays.fill(matrix[fromV], Integer.MAX_VALUE);
            for (int j = 0; j < numVectors; j++) {
                int toV = Integer.parseInt(line[2 + j * 2]);
                int weight = Integer.parseInt(line[2 + j * 2 + 1]);
                matrix[fromV][toV] = weight;
            }
        }

        dijkstra(matrix, n);
    }

    private static void dijkstra(int[][] matrix, int n) {
        int[] dMin = new int[n];
        Color[] color = new Color[n];

        Arrays.fill(dMin, Integer.MAX_VALUE);
        Arrays.fill(color, Color.WHITE);

        dMin[0] = 0;
        color[0] = Color.GRAY;

        while (true) {
            int minInd = -1;
            for (int i = 0; i < n; i++) { // linear search
                if ((minInd == -1 || dMin[minInd] > dMin[i]) && color[i] != Color.BLACK) {
                    minInd = i;
                }
            }

            if (minInd == -1)
                break;

            color[minInd] = Color.BLACK;
            for (int v = 0; v < n; v++) {
                if (color[v] != Color.BLACK && matrix[minInd][v] != Integer.MAX_VALUE
                        && dMin[minInd] + matrix[minInd][v] < dMin[v]) {
                    dMin[v] = dMin[minInd] + matrix[minInd][v];
                    color[v] = Color.GRAY;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            System.out.printf("%d %d\n", i, dMin[i]);
        }
    }
}
