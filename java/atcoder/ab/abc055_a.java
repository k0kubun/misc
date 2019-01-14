import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());

        int x = 800 * n;
        int y = (n / 15) * 200;
        System.out.println(x - y);
    }
}
