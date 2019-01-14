import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        if (n < 105) {
            System.out.println(0);
            return;
        }

        int count = 0;
        for (int i = 105; i <= n; i += 2) {
            int modCount = 0;
            for (int j = 1; j <= i; j++) {
                if (i % j == 0) {
                    modCount++;
                }
            }
            if (modCount == 8) {
                count++;
            }
        }
        System.out.println(count);
    }
}
