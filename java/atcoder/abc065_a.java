import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] xab = scanner.nextLine().split(" ", 3);
        long x = Long.parseLong(xab[0]);
        long a = Long.parseLong(xab[1]);
        long b = Long.parseLong(xab[2]);

        if (b - a <= 0) {
            System.out.println("delicious");
        } else if (b - a > x) {
            System.out.println("dangerous");
        } else {
            System.out.println("safe");
        }
    }
}
