import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        String[] line = scanner.nextLine().split(" ", n);

        int[] twoMult = {
            2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768,
            65536, 131072, 262144, 524288, 1048576, 2097152, 4194304, 8388608, 16777216,
            33554432, 67108864, 134217728, 268435456, 536870912, 1073741824
        };

        int[] oddArr = new int[n];
        int oddSize = 0;
        int[] evenArr = new int[n];
        int evenSize = 0;

        for (int i = 0; i < n; i++) {
            int a = Integer.parseInt(line[i]);
            if (a % 2 == 0) {
                evenArr[evenSize] = a;
                evenSize++;
            } else {
                oddArr[oddSize] = a;
                oddSize++;
            }
        }

        int result = 0;
        int multSize = 0;
        boolean[] used = new boolean[oddSize];
        for (int i = 0; i < oddSize; i++) {
            if (used[i]) {
                continue;
            }

            for (int j = 1; j < oddSize; j++) {
                if (used[j]) {
                    continue;
                }

                while (twoMult[multSize] <= oddArr[j]) {
                    multSize++;
                }

                if (oddArr[i] + oddArr[j] == twoMult[multSize]) {
                    result++;
                    used[j] = true;
                }
            }
        }

        multSize = 0;
        used = new boolean[evenSize];
        for (int i = 0; i < evenSize; i++) {
            if (used[i]) {
                continue;
            }

            for (int j = 1; j < evenSize; j++) {
                if (used[j]) {
                    continue;
                }

                while (twoMult[multSize] <= evenArr[j]) {
                    multSize++;
                }

                if (evenArr[i] + evenArr[j] == twoMult[multSize]) {
                    result++;
                    used[j] = true;
                }
            }
        }
        System.out.println(result);
    }
}
