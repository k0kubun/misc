import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] nmLine = scanner.nextLine().split(" ", 2);
        int n = Integer.parseInt(nmLine[0]);
        int m = Integer.parseInt(nmLine[1]);
        int nm = n * m;

        String[] aLine = scanner.nextLine().split(" ", n);
        long[] aArr = new long[n];
        for (int i = 0; i < n; i++) {
            aArr[i] = Long.parseLong(aLine[i]);
        }

        String[] bLine = scanner.nextLine().split(" ", m);
        long[] bArr = new long[m];
        for (int i = 0; i < m; i++) {
            bArr[i] = Long.parseLong(bLine[i]);
        }

        Arrays.sort(aArr);
        Arrays.sort(bArr);

		long mod = 1000000007;
        long answer = 1;
        int aInd = n - 1;
        int bInd = m - 1;
        for (int i = nm; i >= 1; i--) {
            if (aInd >= 0 && aArr[aInd] == i && bInd >= 0 && bArr[bInd] == i) {
                aInd--;
                bInd--;
            } else if (aInd >= 0 && aArr[aInd] == i) {
                answer *= m - 1 - bInd;
                aInd--;
            } else if (bInd >= 0 && bArr[bInd] == i) {
                answer *= n - 1 - aInd;
                bInd--;
            } else if ((n - 1 - aInd) * (m - 1 - bInd) > (nm - i)) {
                answer *= (n - 1 - aInd) * (m - 1 - bInd) - (nm - i);
            } else {
                answer = 0;
                break;
            }
            answer %= mod;
        }
        System.out.println(answer);
    }
}
