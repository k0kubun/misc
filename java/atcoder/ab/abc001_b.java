import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = Integer.parseInt(scanner.nextLine());

        System.out.printf("%02d\n", new Main().vv(m));
    }

    private int vv(int m) {
        if (m < 100) {
            return 0;
        } else if (100 <= m && m <= 5000) {
            return m / 100;
        } else if (6000 <= m && m <= 30000) {
            return m / 1000 + 50;
        } else if (35000 <= m && m <= 70000) {
            return (m / 1000 - 30) / 5 + 80;
        } else if (70000 < m) {
            return 89;
        } else {
            return 0; // unreachable
        }
    }
}
