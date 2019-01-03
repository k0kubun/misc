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

        int index = this.partition(arr, 0, n-1);
        for (int i = 0; i < n; i++) {
            if (i == index) {
                System.out.printf("[%d]", arr[i]);
            } else {
                System.out.print(arr[i]);
            }

            if (i == n - 1) {
                System.out.print("\n");
            } else {
                System.out.print(" ");
            }
        }
    }

    private int partition(int[] arr, int p, int r) {
        int x = arr[r];
        int i = p - 1;

        for (int j = p; j < r; j++) {
            if (arr[j] <= x) {
                i++;
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }
        }

        int tmp = arr[i+1];
        arr[i+1] = arr[r];
        arr[r] = tmp;
        return i + 1;
    }
}
