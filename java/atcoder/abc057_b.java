import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] nm = scanner.nextLine().split(" ", 2);
        int n = Integer.parseInt(nm[0]);
        int m = Integer.parseInt(nm[1]);

        long[][] ab = new long[n][2];
        for (int i = 0; i < n; i++) {
            String[] line = scanner.nextLine().split(" ", 2);
            ab[i][0] = Long.parseLong(line[0]);
            ab[i][1] = Long.parseLong(line[1]);
        }

        long[][] cd = new long[m][2];
        for (int i = 0; i < m; i++) {
            String[] line = scanner.nextLine().split(" ", 2);
            cd[i][0] = Long.parseLong(line[0]);
            cd[i][1] = Long.parseLong(line[1]);
        }

        for (int i = 0; i < n; i++) {
            long a = ab[i][0];
            long b = ab[i][1];

            long minDist = 0;
            long minJ = 0;
            for (int j = 0; j < m; j++) {
                long c = cd[j][0];
                long d = cd[j][1];
                long dist = Math.abs(a - c) + Math.abs(b - d);

                if (j == 0 || dist < minDist) {
                    minDist = dist;
                    minJ = j;
                }
            }
            System.out.printf("%d\n", minJ + 1);
        }
    }
}
