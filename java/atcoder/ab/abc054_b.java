import java.util.*;

class Main {
    public static void main(String[] args) {
        new Main().main();
    }

    public void main() {
        Scanner scanner = new Scanner(System.in);
        String[] nw = scanner.nextLine().split(" ", 2);
        int n = Integer.parseInt(nw[0]);
        int m = Integer.parseInt(nw[1]);

        char[][] a = new char[n][n];
        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < n; j++) {
                a[i][j] = line.charAt(j);
            }
        }

        char[][] b = new char[m][m];
        for (int i = 0; i < m; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < m; j++) {
                b[i][j] = line.charAt(j);
            }
        }

        boolean found = false;
        for (int startI = 0; startI <= n - m; startI++) {
            for (int startJ = 0; startJ <= n - m; startJ++) {
                if (this.matches(a, b, startI, startJ, n, m)) {
                    found = true;
                    break;
                }
            }
            if (found) {
                break;
            }
        }
        if (found) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }

    private boolean matches(char[][] a, char[][] b, int startI, int startJ, int n, int m) {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                if (a[i + startI][j + startJ] != b[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
