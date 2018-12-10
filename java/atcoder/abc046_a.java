import java.util.Arrays;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        String[] line = new Scanner(System.in).nextLine().split(" ", 3);
        int count = 1;
        int a = Integer.parseInt(line[0]);
        int b = Integer.parseInt(line[1]);
        int c = Integer.parseInt(line[2]);
        if (a != b) {
            count += 1;
            if (a != c && b != c) {
                count += 1;
            }
        } else if (a != c || b != c) {
            count += 1;
        }
        System.out.println(count);
    }
}
