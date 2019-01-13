import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        long[] aArr = new long[n];
        long[] bArr = new long[n];

        String[] aLine = scanner.nextLine().split(" ", n);
        long aSum = 0;
        for (int i = 0; i < n; i++) {
            aArr[i] = Long.parseLong(aLine[i]);
            aSum += aArr[i];
        }

        long loseCount = 0;
        long loseTotal = 0;
        long[] diffArr = new long[n];

        String[] bLine = scanner.nextLine().split(" ", n);
        long bSum = 0;
        for (int i = 0; i < n; i++) {
            bArr[i] = Long.parseLong(bLine[i]);
            bSum += bArr[i];

            if (aArr[i] < bArr[i]) {
                loseCount++;
                loseTotal += bArr[i]- aArr[i];
            } else {
                diffArr[i] = aArr[i] - bArr[i];
            }
        }

        if (aSum < bSum) {
            System.out.println(-1);
            return;
        }

        long magicCount = 0;
        long magicTotal = 0;
        Arrays.sort(diffArr);
        for (int i = n - 1; i >= 0; i--) {
            if (loseTotal <= magicTotal) {
                System.out.println(loseCount + magicCount);
                return;
            }

            magicTotal += diffArr[i];
            magicCount++;
        }

        // should be unreachable, but don't wanna fail
        System.out.println(-1);
    }
}
