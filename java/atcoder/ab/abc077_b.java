import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n = Long.parseLong(scanner.nextLine());
        long sqrt = (long)Math.sqrt(n);
        long result = 1;
        for (long i = sqrt - 1; i <= sqrt + 1; i++) {
            long candidate = i * i;
            if (candidate > result && candidate <= n) {
                result = candidate;
            }
        }
        System.out.println(result);
    }
}
