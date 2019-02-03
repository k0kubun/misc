import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] nk = scanner.nextLine().split(" ", 2);
        int n = Integer.parseInt(nk[0]);
        long k = Long.parseLong(nk[1]);

        long kCopy = k;
        int kBits = 0;
        while (kCopy > 0) {
            kCopy /= 2;
            kBits++;
        }

        int[] numBit0 = new int[kBits];
        int[] numBit1 = new int[kBits];

        String[] line = scanner.nextLine().split(" ", n);
        long[] A = new long[n];
        for (int i = 0; i < n; i++) {
            A[i] = Long.parseLong(line[i]);

            long a = A[i];
            for (int j = 0; j < kBits; j++) {
                if (a % 2 == 0) {
                    numBit0[j]++;
                } else {
                    numBit1[j]++;
                }
                a /= 2;
            }
        }

        if (kBits == 0) {
            long sum = 0;
            for (int i = 0; i < n; i++) {
                sum += A[i];
            }
            System.out.println(sum);
            return;
        }

        long x = 0;

        long placeholder = 1;
        for (int i = 0; i < kBits - 1; i++) {
            placeholder *= 2;
        }

        for (int i = kBits - 1; i >= 0; i--) {
            if (numBit1[i] < numBit0[i] && x + placeholder <= k) {
                x += placeholder;
            }
            placeholder /= 2;
        }

        long result = 0;
        for (int i = 0; i < n; i++) {
            result += x ^ A[i];
        }
        System.out.println(result);
    }
}
