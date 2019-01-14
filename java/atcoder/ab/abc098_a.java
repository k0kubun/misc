import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] ab = scanner.nextLine().split(" ", 2);
        int a = Integer.parseInt(ab[0]);
        int b = Integer.parseInt(ab[1]);

        int plus = a + b;
        int minus = a - b;
        int mult = a * b;
        System.out.println(Math.max(plus, Math.max(minus, mult)));
    }
}
