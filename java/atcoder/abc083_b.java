import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] nab = scanner.nextLine().split(" ", 3);
        int n = Integer.parseInt(nab[0]);
        int a = Integer.parseInt(nab[1]);
        int b = Integer.parseInt(nab[2]);

        int result = 0;
        for (int i = 1; i <= n; i++) {
            int total = 0;
            int num = i;
            while (num > 0) {
                total += num % 10;
                num /= 10;
            }
            if (a <= total && total <= b) {
                result += i;
            }
        }
        System.out.println(result);
    }
}
