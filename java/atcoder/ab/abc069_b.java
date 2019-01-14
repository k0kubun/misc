import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        System.out.printf("%c%d%c\n", s.charAt(0), s.length()-2, s.charAt(s.length()-1));
    }
}
