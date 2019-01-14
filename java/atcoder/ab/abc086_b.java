import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] ab = scanner.nextLine().split(" ", 2);

        int n = Integer.parseInt(String.format("%s%s", ab[0], ab[1]));
        for (int i = 1; i <= n / 2; i++) {
            if (i * i == n) {
                System.out.println("Yes");
                return;
            } else if (i * i > n) {
                break;
            }
        }
        System.out.println("No");
    }
}
