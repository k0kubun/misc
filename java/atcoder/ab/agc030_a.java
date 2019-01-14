import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] abc = scanner.nextLine().split(" ", 3);
        long a = Long.parseLong(abc[0]);
        long b = Long.parseLong(abc[1]);
        long c = Long.parseLong(abc[2]);

        if (a + b >= c) {
            System.out.println(b + c);
        } else {
            System.out.println(a + b + 1 + b);
        }
    }
}
