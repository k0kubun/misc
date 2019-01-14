import java.util.ArrayList;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        int n = Integer.parseInt(new Scanner(System.in).nextLine());
        ArrayList<Integer> arr = new ArrayList<>();
        while (n >= 8) {
            n -= 8;
            arr.add(8);
        }
        while (n >= 4) {
            n -= 4;
            arr.add(4);
        }
        while (n >= 2) {
            n -= 2;
            arr.add(2);
        }
        if (n % 2 == 1) {
            arr.add(1);
        }
        System.out.println(arr.size());
        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr.get(i));
        }
    }
}
