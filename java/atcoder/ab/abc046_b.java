import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] nk = scanner.nextLine().split(" ", 2);
        int n = Integer.parseInt(nk[0]);
        int k = Integer.parseInt(nk[1]);

        if (n == 1) {
            System.out.println(k);
        } else {
            int answer = k;
            for (int i = 1; i < n; i++) {
                answer *= (k - 1);
            }
            System.out.println(answer);
        }
    }
}
