import java.util.Arrays;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        String[] line = new Scanner(System.in).nextLine().split(" ", 3);

        int num[] = new int[3];
        num[0] = Integer.parseInt(line[0]);
        num[1] = Integer.parseInt(line[1]);
        num[2] = Integer.parseInt(line[2]);
        Arrays.sort(num);

        if (num[0] == 5 && num[1] == 5 && num[2] == 7) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }
}
