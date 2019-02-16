import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] line = scanner.nextLine().split(" ", 2);
        int a = Integer.parseInt(line[0]);
        int b = Integer.parseInt(line[1]);

        if (b % a == 0) {
            System.out.println(a + b);
        } else {
            System.out.println(b - a);
        }
    }
}
