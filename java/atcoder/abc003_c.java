import java.util.Arrays;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] nk = scanner.nextLine().split(" ", 2);
        int n = Integer.parseInt(nk[0]);
        int k = Integer.parseInt(nk[1]);

        String[] line = scanner.nextLine().split(" ", n);
        int[] rates = new int[n];
        for (int i = 0; i < n; i++) {
            rates[i] = Integer.parseInt(line[i]);
        }

        Arrays.sort(rates);

        double rate = 0.0;
        for (int i = n - k; i < n; i++) {
            rate = (rate + (double)rates[i]) / 2.0;
        }
        System.out.println(rate);
    }
}
