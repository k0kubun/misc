import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());

        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(scanner.nextLine());
        }

        Arrays.sort(arr);
        int sum = 0;
        for (int i = 0; i < n; i++) {
            if (i == n - 1) {
                sum += arr[i] / 2;
            } else {
                sum += arr[i];
            }
        }

        System.out.println(sum);
    }
}
