import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] line = scanner.nextLine().split(" ", 4);
        int[] arr = new int[4];
        for (int i = 0; i < 4; i++) {
            arr[i] = Integer.parseInt(line[i]);
        }
        Arrays.sort(arr);
        if (arr[0] == 1 && arr[1] == 4 && arr[2] == 7 && arr[3] == 9) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }
}
