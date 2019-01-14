import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        for (int length = s.length() - 2; length >= 2; length -= 2) {
            if (s.substring(0, length/2).equals(s.substring(length/2, length))) {
                System.out.println(length);
                return;
            }
        }
    }
}
