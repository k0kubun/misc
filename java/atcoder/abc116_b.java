import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int s = Integer.parseInt(scanner.nextLine());

        Set<Integer> seen = new HashSet<>();
        int a = 0;
        for (int i = 1; i <= 1000000; i++) {
            if (i == 1) {
                a = s;
            } else {
                if (a % 2 == 0) {
                    a = a / 2;
                } else {
                    a = 3 * a + 1;
                }
            }
            if (seen.contains(a)) {
                System.out.println(i);
                return;
            } else {
                seen.add(a);
            }
        }
    }
}
