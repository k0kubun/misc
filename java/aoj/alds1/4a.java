import java.util.*;

class Main {
    public static void main(String[] args) {
        new Main().main();
    }

    public void main() {
        Scanner scanner = new Scanner(System.in);

        int sNum = Integer.parseInt(scanner.nextLine());
        String[] sLine = scanner.nextLine().split(" ", sNum);
        int[] s = new int[sNum+1];
        for (int i = 0; i < sNum; i++) {
            s[i] = Integer.parseInt(sLine[i]);
        }

        int tNum = Integer.parseInt(scanner.nextLine());
        String[] tLine = scanner.nextLine().split(" ", tNum);

        int result = 0;
        for (int i = 0; i < tNum; i++) {
            int t = Integer.parseInt(tLine[i]);
            if (this.linearSearch(t, s, sNum)) {
                result++;
            }
        }
        System.out.println(result);
    }

    private boolean linearSearch(int key, int[] arr, int arrSize) {
        arr[arrSize] = key;
        int i = 0;
        while (arr[i] != key) {
            i++;
        }
        return i != arrSize;
    }
}
