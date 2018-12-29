import java.util.*;

class Main {
    public static void main(String[] args) {
        new Main().main();
    }

    public void main() {
        Scanner scanner = new Scanner(System.in);
        String[] ln = scanner.nextLine().split(" ", 2);
        long l = Long.parseLong(ln[0]);
        int n = Integer.parseInt(ln[1]);

        long[] arr = new long[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Long.parseLong(scanner.nextLine());
        }

        long answer = this.solve(l, 0, 0, n - 1, 0, true, arr);
        System.out.println(answer);
    }

    private long solve(long l, long pos, int minInd, int maxInd, long total, boolean towardsMin, long[] arr) {
        while (minInd != maxInd) {
            long maxDist = 0;
            long minDist = 0;
            if (towardsMin) {
                maxDist = pos + (l - arr[maxInd]);
                minDist = arr[minInd] - pos;
            } else {
                maxDist = pos - arr[maxInd];
                minDist = l - pos + arr[minInd];
            }

            if (minDist == maxDist) {
                long answer1 = this.solve(l, arr[minInd], minInd + 1, maxInd, total + minDist, true, arr);
                long answer2 = this.solve(l, arr[maxInd], minInd, maxInd - 1, total + maxDist, false, arr);
                return Math.max(answer1, answer2);
            } else if (minDist > maxDist) {
                total += minDist;
                pos = arr[minInd];
                minInd++;
                towardsMin = true;
            } else {
                total += maxDist;
                pos = arr[maxInd];
                maxInd--;
                towardsMin = false;
            }
        }
        long lastDist = Math.abs(pos - arr[minInd]);
        total += Math.max(lastDist, l - lastDist);
        return total;
    }
}
