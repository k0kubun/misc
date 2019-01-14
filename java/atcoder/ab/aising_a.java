import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        int h = Integer.parseInt(scanner.nextLine());
        int w = Integer.parseInt(scanner.nextLine());

        System.out.println((n - h + 1) * (n - w + 1));
    }
}
