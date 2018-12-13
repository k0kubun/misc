import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        int i = 0;
        int j = s.length() - 1;

        for (; i < s.length(); i++) {
            if (s.charAt(i) == 'A') {
                break;
            }
        }
        for (; j >= i; j--) {
            if (s.charAt(j) == 'Z') {
                break;
            }
        }
        System.out.println(j - i + 1);
    }
}
