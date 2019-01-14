import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        if (s.charAt(0) == s.charAt(2)) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }
}
