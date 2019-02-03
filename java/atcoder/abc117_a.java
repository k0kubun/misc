import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] tx = scanner.nextLine().split(" ", 2);
        int t = Integer.parseInt(tx[0]);
        int x = Integer.parseInt(tx[1]);
        System.out.println((double)t / (double)x);
    }
}
