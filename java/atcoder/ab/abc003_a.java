import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());

        double avg = 0.0;
        for (int i = 1; i <= n; i++) {
            avg += ((double)i * 10000.0) / (double)n;
        }
        System.out.println((long)avg);
    }
}
