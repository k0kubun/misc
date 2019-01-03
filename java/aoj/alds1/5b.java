import java.util.*;

class Main {
    public static void main(String[] args) {
        new Main().main();
    }

    public void main() {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        String[] line = scanner.nextLine().split(" ", n);

        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = Integer.parseInt(line[i]);
        }

        int result = this.mergeSort(array, 0, n);
        for (int i = 0; i < n; i++) {
            System.out.print(array[i]);
            if (i == n - 1) {
                System.out.print("\n");
            } else {
                System.out.print(" ");
            }
        }
        System.out.println(result);
    }

    private int mergeSort(int[] arr, int left, int right) {
        if (left + 1 >= right) return 0;
        int result = 0;

        int mid = (left + right) / 2;
        result += this.mergeSort(arr, left, mid);
        result += this.mergeSort(arr, mid, right);
        result += this.merge(arr, left, mid, right);
        return result;
    }

    private int merge(int[] arr, int left, int mid, int right) {
        int result = 0;
        int lNum = mid - left;
        int rNum = right - mid;

        int[] lArr = new int[lNum+1];
        for (int i = 0; i < lNum; i++) {
            lArr[i] = arr[left + i];
        }
        lArr[lNum] = Integer.MAX_VALUE;

        int[] rArr = new int[rNum+1];
        for (int i = 0; i < rNum; i++) {
            rArr[i] = arr[mid + i];
        }
        rArr[rNum] = Integer.MAX_VALUE;

        int i = 0;
        int j = 0;
        for (int k = left; k < right; k++) {
            if (lArr[i] <= rArr[j]) {
                arr[k] = lArr[i];
                i++;
            } else {
                arr[k] = rArr[j];
                j++;
            }
            result++;
        }
        return result;
    }
}
