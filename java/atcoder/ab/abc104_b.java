import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();

        if (s.charAt(0) != 'A') {
            System.out.println("WA");
            return;
        }

        boolean cFound = false;
        for (int i = 1; i < s.length(); i++) {
            if (!cFound && 2 <= i && i <= s.length() - 2 && s.charAt(i) == 'C') {
                cFound = true;
            } else if (s.charAt(i) < 'a' || 'z' < s.charAt(i)) {
                System.out.println("WA");
                return;
            }
        }

        if (cFound) {
            System.out.println("AC");
        } else {
            System.out.println("WA");
        }
    }
}
