import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        int n = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '+') {
                n++;
            } else {
                n--;
            }
        }
        System.out.println(n);
    }
}
