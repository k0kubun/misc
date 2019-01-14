import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n = Long.parseLong(scanner.nextLine());
        n %= 30;

        int[] arr = { 1, 2, 3, 4, 5, 6 };
        for (int i = 0; i < n; i++) {
            int left = i % 5;
            int right = left + 1;
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
        }

        for (int i = 0; i < 6; i++) {
            System.out.print(arr[i]);
        }
        System.out.print("\n");
    }
}
