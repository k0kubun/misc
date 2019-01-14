import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String n = scanner.nextLine();
        for (int i = 0; i < n.length(); i++) {
            if (n.charAt(i) == '9') {
                System.out.println("Yes");
                return;
            }
        }
        System.out.println("No");
    }
}
