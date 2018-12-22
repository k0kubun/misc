import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] ab = scanner.nextLine().split(" ", 2);
        int a = Integer.parseInt(ab[0]);
        int b = Integer.parseInt(ab[1]);

        if (a == 1) {
            a = 14;
        }
        if (b == 1) {
            b = 14;
        }

        if (a == b) {
            System.out.println("Draw");
        }
        else if (a > b) {
            System.out.println("Alice");
        }
        else {
            System.out.println("Bob");
        }
    }
}
