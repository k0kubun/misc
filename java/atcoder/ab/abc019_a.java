import java.util.Arrays;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        String[] line = new Scanner(System.in).nextLine().split(" ", 3);
        int[] arr = new int[3];
        arr[0] = Integer.parseInt(line[0]);
        arr[1] = Integer.parseInt(line[1]);
        arr[2] = Integer.parseInt(line[2]);

        Arrays.sort(arr);
        System.out.println(arr[1]);
    }
}
