import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int x = Integer.parseInt(scanner.nextLine());
        int lastPow = 1;
        for (int i = 2; i <= x; i++) {
            int pow = i;
            while (pow * i <= x) {
                pow *= i;
            }
            if (i < pow && pow <= x && lastPow < pow) {
                lastPow = pow;
            }
        }
        System.out.println(lastPow);
    }
}
