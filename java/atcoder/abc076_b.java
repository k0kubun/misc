import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        int k = Integer.parseInt(scanner.nextLine());

        int result = 1;
        for (int i = 0; i < n; i++) {
            result = Math.min(result * 2, result + k);
        }
        System.out.println(result);
    }
}
