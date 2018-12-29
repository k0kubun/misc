import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] ab = scanner.nextLine().split(" ", 2);
        int a = Integer.parseInt(ab[0]);
        int b = Integer.parseInt(ab[1]);

        if (Math.max(a, b) <= 8) {
            System.out.println("Yay!");
        } else {
            System.out.println(":(");
        }
    }
}
