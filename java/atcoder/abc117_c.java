import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] nm = scanner.nextLine().split(" ", 2);
        int n = Integer.parseInt(nm[0]);
        int m = Integer.parseInt(nm[1]);
        if (n >= m) {
            System.out.println(0);
            return;
        }

        String[] line = scanner.nextLine().split(" ", m);
        int[] arr = new int[m];
        for (int i = 0; i < m; i++) {
            arr[i] = Integer.parseInt(line[i]);
        }
        Arrays.sort(arr);

        int[] diff = new int[m-1];
        for (int i = 0; i < m - 1; i++) {
            diff[i] = arr[i+1] - arr[i];
        }
        Arrays.sort(diff);

        long sum = 0;
        for (int i = 0; i < m - n; i++) {
            sum += diff[i];
        }
        System.out.println(sum);
    }
}
