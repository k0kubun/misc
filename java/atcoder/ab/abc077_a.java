import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String c1 = scanner.nextLine();
        String c2 = scanner.nextLine();
        for (int i = 0; i < 3; i++) {
            if (c1.charAt(i) != c2.charAt(2 - i)) {
                System.out.println("NO");
                return;
            }
        }
        System.out.println("YES");
    }
}
