import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] ab = scanner.nextLine().split(" ");
        int a = Integer.parseInt(ab[0]);
        int b = Integer.parseInt(ab[1]);
        if (a > b) {
            System.out.println(a - 1);
        } else {
            System.out.println(a);
        }
    }
}
