import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        long n = Long.parseLong(s);

        long sum = 0;
        for (int i = 0; i < s.length(); i++) {
            sum += s.charAt(i) - '0';
        }

        if (n % sum == 0) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }
}
