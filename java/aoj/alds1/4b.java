import java.util.*;

class Main {
    public static void main(String[] args) {
        new Main().main();
    }

    public void main() {
        Scanner scanner = new Scanner(System.in);

        int sNum = Integer.parseInt(scanner.nextLine());
        String[] sLine = scanner.nextLine().split(" ", sNum);
        int[] s = new int[sNum];
        for (int i = 0; i < sNum; i++) {
            s[i] = Integer.parseInt(sLine[i]);
        }

        int tNum = Integer.parseInt(scanner.nextLine());
        String[] tLine = scanner.nextLine().split(" ", tNum);

        int result = 0;
        for (int i = 0; i < tNum; i++) {
            int t = Integer.parseInt(tLine[i]);
            if (this.binarySearch(t, s)) {
                result++;
            }
        }
        System.out.println(result);
    }

    private boolean binarySearch(int key, int[] arr) {
        int left = 0;
        int right = arr.length - 1;

        while (true) {
            int middle = (left + right) / 2;
            if (middle == left || middle == right) {
                return key == arr[left] || key == arr[right];
            }

            if (key == arr[middle]) {
                return true;
            } else if (key < arr[middle]) {
                right = middle;
            } else {
                left = middle;
            }
        }
    }
}
