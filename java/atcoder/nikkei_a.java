import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] nab = scanner.nextLine().split(" ", 3);
        int n = Integer.parseInt(nab[0]);
        int a = Integer.parseInt(nab[1]);
        int b = Integer.parseInt(nab[2]);

        System.out.printf("%d %d\n", Math.min(a, b), Math.max(0, a + b - n));
    }
}
