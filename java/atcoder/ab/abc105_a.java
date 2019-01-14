import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] nk = scanner.nextLine().split(" ", 2);
        int n = Integer.parseInt(nk[0]);
        int k = Integer.parseInt(nk[1]);
        if (n % k == 0) {
            System.out.println(0);
        } else {
            System.out.println(1);
        }
    }
}
