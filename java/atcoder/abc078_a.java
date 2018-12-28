import java.util.*;

class Main {
    public static void main(String[] args) {
        new Main().main();
    }

    public void main() {
        Scanner scanner = new Scanner(System.in);
        String[] xy = scanner.nextLine().split(" ", 2);
        int x = this.parseHex(xy[0].charAt(0));
        int y = this.parseHex(xy[1].charAt(0));
        if (x == y) {
            System.out.println("=");
        } else if (x < y) {
            System.out.println("<");
        } else {
            System.out.println(">");
        }
    }

    private int parseHex(char ch) {
        if ('A' <= ch && ch <= 'F') {
            return 10 + (ch - 'A');
        } else {
            return ch - '0';
        }
    }
}
