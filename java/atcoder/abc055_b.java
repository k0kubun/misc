import java.math.BigInteger;
import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n = Long.parseLong(scanner.nextLine());

        long result = 1;
        for (long i = 2; i <= n; i++) {
            result *= i;
            if (result > 1000000007) {
                result %= 1000000007;
            }
        }
        System.out.println(result);
    }
}
