import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] ab = scanner.nextLine().split(" ", 2);
        int a = Integer.parseInt(ab[0]);
        int b = Integer.parseInt(ab[1]);
        for (int i = 1; i <= 3; i++) {
            if ((a * b * i) % 2 == 1) {
                System.out.println("Yes");
                return;
            }
        }
        System.out.println("No");
    }
}
