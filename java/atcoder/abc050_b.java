import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());

        String[] line = scanner.nextLine().split(" ", n);
        int[] ts = new int[n];
        for (int i = 0; i < n; i++) {
            ts[i] = Integer.parseInt(line[i]);
        }

        int m = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < m; i++) {
            line = scanner.nextLine().split(" ", 2);
            int p = Integer.parseInt(line[0]);
            int x = Integer.parseInt(line[1]);

            int result = 0;
            for (int j = 0; j < n; j++) {
                if (j == p - 1) {
                    result += x;
                } else {
                    result += ts[j];
                }
            }
            System.out.println(result);
        }
    }
}
