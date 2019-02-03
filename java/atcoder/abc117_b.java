import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());

        String[] line = scanner.nextLine().split(" ", n);
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(line[i]);
        }
        Arrays.sort(arr);

        int sum = 0;
        for (int i = 0; i < n - 1; i++) {
            sum += arr[i];
        }
        if (arr[n-1] < sum) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }
}
