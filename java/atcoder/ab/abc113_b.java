import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        String[] ta = scanner.nextLine().split(" ", 2);
        int t = Integer.parseInt(ta[0]);
        int a = Integer.parseInt(ta[1]);

        String[] hLine = scanner.nextLine().split(" ", n);
        double minDiff = 100000.0;
        int minInd = 0;
        for (int i = 0; i < n; i++) {
            int h = Integer.parseInt(hLine[i]);
            double temp = (double)t - (double)h * 0.006;
            double diff = Math.abs((double)a - temp);
            if (diff < minDiff) {
                minDiff = diff;
                minInd = i;
            }
        }
        System.out.println(minInd + 1);
    }
}
