import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] abc = scanner.nextLine().split(" ", 3);

        int[] arr = new int[3];
        arr[0] = Integer.parseInt(abc[0]);
        arr[1] = Integer.parseInt(abc[1]);
        arr[2] = Integer.parseInt(abc[2]);
        Arrays.sort(arr);

        System.out.println(arr[0] + arr[1]);
    }
}
