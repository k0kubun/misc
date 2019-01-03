import java.util.*;

class Main {
    public static void main(String[] args) {
        new Main().main();
    }

    public void main() {
        Scanner scanner = new Scanner(System.in);

        int aNum = Integer.parseInt(scanner.nextLine());
        String[] aLine = scanner.nextLine().split(" ", aNum);
        int[] a = new int[aNum];
        for (int i = 0; i < aNum; i++) {
            a[i] = Integer.parseInt(aLine[i]);
        }

        int mNum = Integer.parseInt(scanner.nextLine());
        String[] mLine = scanner.nextLine().split(" ", mNum);
        for (int i = 0; i < mNum; i++) {
            int m = Integer.parseInt(mLine[i]);
            if (this.makableBySum(m, 0, a)) {
                System.out.println("yes");
            } else {
                System.out.println("no");
            }
        }
    }

    private boolean makableBySum(int m, int aInd, int[] a) {
        if (m == 0) {
            return true;
        }
        if (aInd == a.length - 1) {
            return m == a[aInd];
        } else {
            return this.makableBySum(m, aInd + 1, a) || this.makableBySum(m - a[aInd], aInd + 1, a);
        }
    }
}
