import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] ab = scanner.nextLine().split(" ", 2);
        int a = Integer.parseInt(ab[0]);
        int b = Integer.parseInt(ab[1]);
        if (a + b >= 10) {
            System.out.println("error");
        } else {
            System.out.println(a + b);
        }
    }
}
