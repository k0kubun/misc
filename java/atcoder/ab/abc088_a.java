import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        int a = Integer.parseInt(scanner.nextLine());

        if (n % 500 <= a) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }
}
