import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] nl = scanner.nextLine().split(" ", 2);
        int n = Integer.parseInt(nl[0]);
        int l = Integer.parseInt(nl[1]);

        String[] arr = new String[n];
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextLine();
        }
        Arrays.sort(arr);
        for (int i = 0; i < n; i++) {
            System.out.print(arr[i]);
        }
        System.out.print("\n");
    }
}
