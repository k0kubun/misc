import java.util.*;

class Main {
    public static void main(String[] args) {
        new Main().main();
    }

    public void main() {
        Scanner scanner = new Scanner(System.in);

        int n = Integer.parseInt(scanner.nextLine());
        String[] line = scanner.nextLine().split(" ", n);
        int[] nums = new int[n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(line[i]);
            if (k < nums[i]) {
                k = nums[i];
            }
        }

        int[] sorted = new int[n];
        this.countingSort(nums, sorted, k);

        for (int i = 0; i < n; i++) {
            System.out.printf((i == n - 1 ? "%d\n" : "%d "), sorted[i]);
        }
    }

    private void countingSort(int[] a, int[] b, int k) {
        int[] c = new int[k+1];

        for (int i = 0; i < a.length; i++) {
            c[a[i]]++;
        }

        for (int i = 1; i <= k; i++) {
            c[i] = c[i] + c[i-1];
        }

        for (int i = a.length-1; i >= 0; i--) {
            b[c[a[i]]-1] = a[i];
            c[a[i]]--;
        }
    }
}
