import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] nk = scanner.nextLine().split(" ", 2);
        int n = Integer.parseInt(nk[0]);
        int k = Integer.parseInt(nk[1]);

        if ((n + 1) / 2 >= k) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }
}
