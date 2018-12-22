import java.math.*;
import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] np = scanner.nextLine().split(" ", 2);
        long n = Long.parseLong(np[0]);
        long p = Long.parseLong(np[1]);
        long remP = p;
        long limit = (long)Math.sqrt(p) + 1;

        if (n == 1) {
            System.out.println(p);
            return;
        }

        long answer = 1;

        for (long i = 2; i <= limit; i++) { // prime
            long cnt = 0;
            while (remP % i == 0) {
                remP /= i;
                cnt++;
            }
            for (long j = 0; j < cnt / n; j++) {
                answer *= i;
            }
            if (remP == 1) {
                break;
            }
        }
        System.out.println(answer);
    }
}
