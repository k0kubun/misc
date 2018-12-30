import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] ab = scanner.nextLine().split(" ", 2);

        int a = Integer.parseInt(ab[0]);
        int b = Integer.parseInt(ab[1]);
        String s = scanner.nextLine();

        if (a + b + 1 != s.length()) {
            System.out.println("No");
            return;
        }

        for (int i = 0; i <= a + b; i++) {
            char ch = s.charAt(i);
            if (i == a) {
                if (ch != '-') {
                    System.out.println("No");
                    return;
                }
            } else {
                if (!('0' <= ch && ch <= '9')) {
                    System.out.println("No");
                    return;
                }
            }
        }
        System.out.println("Yes");
    }
}
