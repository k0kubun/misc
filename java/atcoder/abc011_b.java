import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        new Main().main();
    }

    public void main() {
        String line = new Scanner(System.in).nextLine();
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (i == 0) {
                System.out.print(this.upcase(ch));
            } else {
                System.out.print(this.downcase(ch));
            }
        }
        System.out.print("\n");
    }

    private char upcase(char ch) {
        if ('a' <= ch && ch <= 'z') {
            return (char)('A' - 'a' + ch);
        } else {
            return ch;
        }
    }

    private char downcase(char ch) {
        if ('A' <= ch && ch <= 'Z') {
            return (char)('a' - 'A' + ch);
        } else {
            return ch;
        }
    }
}
