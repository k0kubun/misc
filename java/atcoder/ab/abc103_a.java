import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] aaa = scanner.nextLine().split(" ", 3);

        int[] arr = new int[3];
        for (int i = 0; i < 3; i++) {
            arr[i] = Integer.parseInt(aaa[i]);
        }
        Arrays.sort(arr);

        System.out.println(arr[2] - arr[0]);
    }
}
