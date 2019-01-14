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

        int alice = 0;
        int bob = 0;
        for (int i = 0; i < n; i++) {
            int num = arr[n - 1 - i];
            if (i % 2 == 0) {
                alice += num;
            } else {
                bob += num;
            }
        }
        System.out.println(alice - bob);
    }
}
