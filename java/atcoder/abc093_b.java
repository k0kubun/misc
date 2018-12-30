import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] abk = scanner.nextLine().split(" ", 3);
        long a = Long.parseLong(abk[0]);
        long b = Long.parseLong(abk[1]);
        long k = Long.parseLong(abk[2]);

        long firstEnd = Math.min(b, a + k - 1);
        for (long i = a; i <= firstEnd; i++) {
            System.out.println(i);
        }
        for (long i = Math.max(b - k + 1, firstEnd + 1); i <= b; i++) {
            System.out.println(i);
        }
    }
}
