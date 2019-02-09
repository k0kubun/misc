import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int l = Integer.parseInt(scanner.nextLine());
        long[] A = new long[l];
        int[] dupCount = new int[l];

        int distCount = 0;
        for (int i = 0; i < l; i++) {
            long a = Long.parseLong(scanner.nextLine());
            if (distCount == 0 || A[distCount-1] != a) {
                A[distCount] = a;
                distCount++;
            }
            dupCount[distCount-1]++;
        }

        boolean notStarted = true;
        long result = 0;
        for (int i = 0; i < distCount; i++) {
            if (A[i] == 0) {
                if (!notStarted && i != distCount - 1) {
                    result += dupCount[i];
                }
                // if not started, just skip
                continue;
            }

            if (notStarted) {
                if (i+1 < distCount-1 && A[i+1] == 0) { // next is non-last 0
                    // check whether we should start or not
                    if (A[i] * dupCount[i] <= dupCount[i+1]) {
                        // do not start. sacrifice A[i]
                        result += A[i] * dupCount[i];
                        System.out.println("B");
                    } else {
                        // start. A[i] can be freely reduced
                        notStarted = false;
                    }
                } else {
                    // any reduction is possible
                    notStarted = false;
                }
                continue;
            }

            if (i == distCount - 1 || (i == distCount - 2 && A[distCount-1] == 0)) {
                // any reduction is possible
                break;
            }

            if (A[i] % 2 == 1) {
                // can reduce properly and go to next.
            } else {
                System.out.printf("+%d at %d\n", dupCount[i], i);
                result += dupCount[i];
            }
        }
        System.out.println(result);
    }
}
