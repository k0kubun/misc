import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        String t = scanner.nextLine();

        if (new Main().canWin(s, t)) {
            System.out.println("You can win");
        } else {
            System.out.println("You will lose");
        }
    }

    private boolean canWin(String s, String t) {
        for (int i = 0; i < s.length(); i++) {
            char sch = s.charAt(i);
            char tch = t.charAt(i);
            if (sch != tch) {
                if (sch == '@') {
                    if (!this.isAtcoder(tch)) return false;
                } else if (tch == '@') {
                    if (!this.isAtcoder(sch)) return false;
                } else {
                    return false;
                }
            }
            if (sch != '@' && tch != '@' && sch != tch) {
                return false;
            }
        }
        return true;
    }

    private boolean isAtcoder(char c) {
        switch (c) {
            case 'a':
            case 't':
            case 'c':
            case 'o':
            case 'd':
            case 'e':
            case 'r':
                return true;
            default:
                return false;
        }
    }
}
