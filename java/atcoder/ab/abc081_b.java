import java.util.*;

class Main {
    public static void main(String[] args) {
        new Main().main();
    }

    public void main() {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        String[] line = scanner.nextLine().split(" ", n);

        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(line[i]);
        }

        System.out.println(this.count(n, arr));
    }

    private int count(int n, int[] arr) {
        int result = 0;
        while (true) {
            for (int i = 0; i < n; i++) {
                if (arr[i] % 2 != 0) {
                    return result;
                }
                arr[i] /= 2;
            }
            result++;
        }
    }
}
