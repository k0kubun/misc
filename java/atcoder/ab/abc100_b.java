import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] dn = scanner.nextLine().split(" ", 2);
        int d = Integer.parseInt(dn[0]);
        int n = Integer.parseInt(dn[1]);

        int result = 1;
        for (int i = 0; i < d; i++) {
            result *= 100;
        }
        if (n == 100) {
            n++;
        }
        result *= n;
        System.out.println(result);
    }
}
