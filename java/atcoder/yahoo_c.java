import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] kab = scanner.nextLine().split(" ", 3);
        long k = Long.parseLong(kab[0]);
        long a = Long.parseLong(kab[1]);
        long b = Long.parseLong(kab[2]);

        if (a >= b - 1) {
            System.out.println(1 + k);
            return;
        }

        // now, a + 2 <= b

        long biske = 1;
        while (k > 0) {
            if (biske < a || k == 1) {
                biske++;
                k--;
                continue;
            }

            // now, biske >= a and k > 2

            biske += b - a;
            k -= 2;
        }
        System.out.println(biske);
    }
}
