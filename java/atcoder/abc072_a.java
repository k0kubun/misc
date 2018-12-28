import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] xt = scanner.nextLine().split(" ", 2);
        long x = Long.parseLong(xt[0]);
        long t = Long.parseLong(xt[1]);

        System.out.println(Math.max(x - t, 0));
    }
}
