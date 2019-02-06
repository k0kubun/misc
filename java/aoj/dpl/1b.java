import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] nw = scanner.nextLine().split(" ", 2);
        int N = Integer.parseInt(nw[0]);
        int W = Integer.parseInt(nw[1]);

        int[] v = new int[N];
        int[] w = new int[N];
        for (int i = 0; i < N; i++) {
            String[] vw = scanner.nextLine().split(" ", 2);
            v[i] = Integer.parseInt(vw[0]);
            w[i] = Integer.parseInt(vw[1]);
        }
    }
}
