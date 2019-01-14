import java.math.*;
import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] nhw = scanner.nextLine().split(" ", 3);
        long n = Long.parseLong(nhw[0]);
        long h = Long.parseLong(nhw[1]);
        long w = Long.parseLong(nhw[2]);

        long cnt = 0;
        for (long i = 0; i < n; i++) {
            String[] ab = scanner.nextLine().split(" ", 2);
            long a = Long.parseLong(ab[0]);
            long b = Long.parseLong(ab[1]);

            if (a >= h && b >= w) {
                cnt++;
            }
        }
        System.out.println(cnt);
    }
}
