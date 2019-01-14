import java.util.*;

class Main {
    public static void main(String[] args) {
        new Main().main();
    }

    public void main() {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        String[] line = scanner.nextLine().split(" ", n);

        int[] arr = new int[1];
        int arrSize = 0;
        for (int i = 0; i < n; i++) {
            int a = Integer.parseInt(line[i]);
            if (i == 0) {
                arr = new int[a];
                arrSize = a;
            } else if (arrSize < a) {
                int[] newArr = new int[a];
                System.arraycopy(arr, 0, newArr, 0, arrSize);
                arrSize = a;
                arr = newArr;
            } else {
                arrSize = a;
                this.increment(arr, a);
            }
        }

        int max = 0;
        for (int i = 0; i < arrSize; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        System.out.println(max + 1);
    }

    private void increment(int[] arr, int size) {
        int last = arr[size - 1];
        for (int i = size - 2; i >= 0; i--) {
            if (arr[i] < last) {
                arr[i]++;
                return;
            }
        }
        arr[size - 1]++;
    }
}
