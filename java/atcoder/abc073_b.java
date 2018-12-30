import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        int result = 0;
        for (int i = 0; i < n; i++) {
            String[] lr = scanner.nextLine().split(" ", 2);
            int l = Integer.parseInt(lr[0]);
            int r = Integer.parseInt(lr[1]);
            result += r - l + 1;
        }
        System.out.println(result);
    }
}
