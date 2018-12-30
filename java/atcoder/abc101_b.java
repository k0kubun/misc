import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        long n = Long.parseLong(s);

        long fact = 0;
        for (int i = 0; i < s.length(); i++) {
            fact += s.charAt(i) - '0';
        }
        if (n % fact == 0) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }
}
