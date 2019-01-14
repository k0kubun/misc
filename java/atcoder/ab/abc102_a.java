import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        if (n % 2 == 0) {
            System.out.println(n);
        } else {
            System.out.println(n * 2);
        }
    }
}
