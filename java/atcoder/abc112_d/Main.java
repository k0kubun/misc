import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String firstLine[] = scanner.nextLine().split(" ", 2);

        int n = Integer.parseInt(firstLine[0]);
        long m = Long.parseLong(firstLine[1]);

        long max;
        if (m % n == 0) {
            max = m / n;
        } else {
            max = new Main().maxNum(n, m);
        }
        System.out.println(max);
    }

    public long maxNum(int n, long m) {
        long max = 1;
        for (long base = 2; base <= m; base++) {
            long sum = n * base;
            if (sum <= m && (m - sum) % base == 0) {
                max = base;
            }
        }
        return max;
    }
}
