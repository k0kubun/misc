import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String firstLine[] = scanner.nextLine().split(" ", 2);

        int n = Integer.parseInt(firstLine[0]);
        long m = Long.parseLong(firstLine[1]);

        long max = new Main().maxNum(n, m);
        System.out.println(max);
    }

    public long maxNum(int n, long m) {
        long max = 1;
        for (long i = 2; i <= m; i++) {
            if (findM(i, n, m)) {
                max = i;
            }
        }
        return max;
    }

    public boolean findM(long base, long n, long m) {
        long sum = n * base;
        if (sum > m) {
            return false;
        }

        if ((m - sum) % base == 0) {
            return true;
        } else {
            return false;
        }
    }
}
