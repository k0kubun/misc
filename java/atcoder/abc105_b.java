import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());

        for (int num4 = 0; num4 <= n / 4; num4++) {
            if ((n - num4 * 4 ) % 7 == 0) {
                System.out.println("Yes");
                return;
            }
        }
        System.out.println("No");
    }
}
