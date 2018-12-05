import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long N = scanner.nextInt();

        if (N <= 2) {
            System.out.println(0);
            return;
        } else if (N == 3) {
            System.out.println(1);
            return;
        }

        long a_n2 = 0;
        long a_n1 = 0;
        long a_n = 1;
        for (long i = 4; i <= N; i++) {
            long next_a_n = a_n + a_n1 + a_n2;
            if (next_a_n > 10007) {
                next_a_n %= 10007;
            }
            a_n2 = a_n1;
            a_n1 = a_n;
            a_n = next_a_n;
        }

        System.out.println(a_n % 10007);
    }
}
