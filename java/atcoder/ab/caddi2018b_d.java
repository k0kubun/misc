import java.math.*;
import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());

        boolean allEven = true;
        for (int i = 0; i < n; i++) {
            long a = Long.parseLong(scanner.nextLine());
            if (a % 2 != 0) {
                allEven = false;
            }
        }
        if (allEven) {
            System.out.println("second");
        } else {
            System.out.println("first");
        }
    }
}
