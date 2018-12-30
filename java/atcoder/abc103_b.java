import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        String t = scanner.nextLine();

        for (int i = 0; i < s.length(); i++) {
            String rotated = String.format("%s%s", s.substring(i, s.length()), s.substring(0, i));
            if (t.equals(rotated)) {
                System.out.println("Yes");
                return;
            }
        }
        System.out.println("No");
    }
}
