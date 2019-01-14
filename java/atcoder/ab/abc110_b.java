import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] nmxy = scanner.nextLine().split(" ", 4);
        int n = Integer.parseInt(nmxy[0]);
        int m = Integer.parseInt(nmxy[1]);
        int x = Integer.parseInt(nmxy[2]);
        int y = Integer.parseInt(nmxy[3]);

        String[] xLine = scanner.nextLine().split(" ", n);
        int[] xNums = new int[n];
        for (int i = 0; i < n; i++) {
            xNums[i] = Integer.parseInt(xLine[i]);
        }
        Arrays.sort(xNums);

        String[] yLine = scanner.nextLine().split(" ", m);
        int[] yNums = new int[m];
        for (int i = 0; i < m; i++) {
            yNums[i] = Integer.parseInt(yLine[i]);
        }
        Arrays.sort(yNums);

        if (Math.max(x, xNums[n-1]) < Math.min(y, yNums[0])) {
            System.out.println("No War");
        } else {
            System.out.println("War");
        }
    }
}
