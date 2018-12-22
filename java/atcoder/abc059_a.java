import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] s = scanner.nextLine().split(" ", 3);
        char c1 = s[0].charAt(0);
        char c2 = s[1].charAt(0);
        char c3 = s[2].charAt(0);
        int diff = 'A' - 'a';
        System.out.printf("%c%c%c\n", c1 + diff, c2 + diff, c3 + diff);
    }
}
