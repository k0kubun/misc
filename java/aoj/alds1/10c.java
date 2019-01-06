import java.util.*;

class Main {
    public static void main(String[] args) {
        new Main().main();
    }

    public void main() {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < n; i++) {
            String a = scanner.nextLine();
            String b = scanner.nextLine();
            System.out.println(this.longestCommonSubseqLen(a, b));
        }
    }

    private int longestCommonSubseqLen(String a, String b) {
        if (a.length() == 0 || b.length() == 0) {
            return ;
        }

        b.indexOf(a.charAt(0))
        return 0;
    }
}
