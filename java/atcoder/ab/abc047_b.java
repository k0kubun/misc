import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] whn = scanner.nextLine().split(" ", 3);
        int w = Integer.parseInt(whn[0]);
        int h = Integer.parseInt(whn[1]);
        int n = Integer.parseInt(whn[2]);

        boolean[][] black = new boolean[w][h];
        for (int i = 0; i < n; i++) {
            String[] xya = scanner.nextLine().split(" ", 3);
            int x = Integer.parseInt(xya[0]);
            int y = Integer.parseInt(xya[1]);
            int a = Integer.parseInt(xya[2]);
            switch (a) {
                case 1:
                    for (int j = 0; j < x; j++) {
                        for (int k = 0; k < h; k++) {
                            black[j][k] = true;
                        }
                    }
                    break;
                case 2:
                    for (int j = x; j < w; j++) {
                        for (int k = 0; k < h; k++) {
                            black[j][k] = true;
                        }
                    }
                    break;
                case 3:
                    for (int j = 0; j < w; j++) {
                        for (int k = 0; k < y; k++) {
                            black[j][k] = true;
                        }
                    }
                    break;
                case 4:
                    for (int j = 0; j < w; j++) {
                        for (int k = y; k < h; k++) {
                            black[j][k] = true;
                        }
                    }
                    break;
            }
        }

        int whiteNum = 0;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (!black[i][j]) {
                    whiteNum++;
                }
            }
        }
        System.out.println(whiteNum);
    }
}
