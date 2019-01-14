import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());

        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(scanner.nextLine());
        }

        boolean[] used = new boolean[n];
        int i = 0;
        int cnt = 0;
        while (true) {
            cnt++;
            if (arr[i] == 2) {
                System.out.println(cnt);
                return;
            }
            if (used[arr[i] - 1]) {
                System.out.println(-1);
                return;
            }
            i = arr[i] - 1;
            used[i] = true;
        }
    }
}
