import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] nm = scanner.nextLine().split(" ", 2);
        int n = Integer.parseInt(nm[0]);
        int m = Integer.parseInt(nm[1]);
        System.out.println((n - 1) * (m - 1));
    }
}
