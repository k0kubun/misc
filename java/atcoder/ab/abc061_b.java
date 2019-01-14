import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] nm = scanner.nextLine().split(" ", 2);
        int n = Integer.parseInt(nm[0]);
        int m = Integer.parseInt(nm[1]);

        int[] roadCount = new int[n];
        for (int i = 0; i < m; i++) {
            String[] ab = scanner.nextLine().split(" ", 2);
            int a = Integer.parseInt(ab[0]) - 1;
            int b = Integer.parseInt(ab[1]) - 1;
            roadCount[a]++;
            roadCount[b]++;
        }

        for (int i = 0; i < n; i++) {
            System.out.println(roadCount[i]);
        }
    }
}
