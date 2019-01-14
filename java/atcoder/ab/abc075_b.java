import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] hw = scanner.nextLine().split(" ", 2);
        int h = Integer.parseInt(hw[0]);
        int w = Integer.parseInt(hw[1]);

        char[][] board = new char[h][w];
        for (int i = 0; i < h; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < w; j++) {
                board[i][j] = line.charAt(j);
            }
        }

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (board[i][j] == '#') continue;

                int count = 0;
                for (int k = Math.max(0, i - 1); k <= Math.min(h - 1, i + 1); k++) {
                    for (int l = Math.max(0, j - 1); l <= Math.min(w - 1, j + 1); l++) {
                        if ((k != i || j != l) && board[k][l] == '#') {
                            count++;
                        }
                    }
                }
                board[i][j] = (char)('0' + count);
            }
        }

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                System.out.print(board[i][j]);
            }
            System.out.print("\n");
        }
    }
}
