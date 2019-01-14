import java.util.*;

class Main {
    public static void main(String[] args) {
        new Main().main();
    }

    public void main() {
        Scanner scanner = new Scanner(System.in);
        String[] line = scanner.nextLine().split(" ", 2);
        int n = Integer.parseInt(line[0]);
        int k = Integer.parseInt(line[1]);

        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(scanner.nextLine());
        }
        Arrays.sort(arr);

        int memorySize = 0;
        Integer min = null;
        for (int i = 0; i < n; i++) {
            if (i >= k - 1) {
                int x = arr[i] - arr[i - k + 1];
                if (min == null || x < min) {
                    min = x;
                }
            }
        }
        System.out.println(min);
    }
}
