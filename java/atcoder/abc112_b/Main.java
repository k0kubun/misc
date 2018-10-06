import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String firstLine[] = scanner.nextLine().split(" ", 2);
        int n = Integer.parseInt(firstLine[0]);
        int t = Integer.parseInt(firstLine[1]);

        int cs[] = new int[n];
        int ts[] = new int[n];
        for (int i = 0; i < n; i++) {
            String line[] = scanner.nextLine().split(" ", 2);
            cs[i] = Integer.parseInt(line[0]);
            ts[i] = Integer.parseInt(line[1]);
        }

        int cost = new Main().minCost(n, t, cs, ts);
        if (cost == 1001) {
            System.out.println("TLE");
        } else {
            System.out.println(cost);
        }
    }

    public int minCost(int n, int t, int costs[], int times[]) {
        int min = 1001;
        for (int i = 0; i < n; i++) {
            if (times[i] <= t && costs[i] < min) {
                min = costs[i];
            }
        }
        return min;
    }
}
