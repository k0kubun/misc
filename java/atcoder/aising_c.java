import java.util.*;

class Main {
    public static void main(String[] args) {
        new Main().main();
    }

    public void main() {
        Scanner scanner = new Scanner(System.in);
        String[] hw = scanner.nextLine().split(" ", 2);
        int h = Integer.parseInt(hw[0]);
        int w = Integer.parseInt(hw[1]);

        boolean[][] black = new boolean[h][w];
        for (int i = 0; i < h; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < w; j++) {
                black[i][j] = (line.charAt(j) == '#');
            }
        }

        int[][] ids = new int[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                ids[i][j] = i + h * (j + 1);
            }
        }

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                // right
                if (j < w - 1) {
                    if (black[i][j] != black[i][j+1]) {
                        int root = this.rootOf(i, j+1, h, ids);
                        int I = root % h;
                        int J = root / h - 1;
                        ids[I][J] = ids[i][j];
                    }
                }

                // down
                if (i < h - 1) {
                    if (black[i][j] != black[i+1][j]) {
                        int root = this.rootOf(i+1, j, h, ids);
                        int I = root % h;
                        int J = root / h - 1;
                        ids[I][J] = ids[i][j];
                    }
                }
            }
        }

        int[] blackCount = new int[(h + 1) * (w + 2) + 1];
        int[] whiteCount = new int[(h + 1) * (w + 2) + 1];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int root = this.rootOf(i, j, h, ids);
                if (black[i][j]) {
                    blackCount[root]++;
                } else {
                    whiteCount[root]++;
                }
            }
        }

        int result = 0;
        int last = (h + 1) * (w + 2) + 1;
        for (int i = 0; i < last; i++) {
            result += (blackCount[i] * whiteCount[i]);
        }
        System.out.println(result);
    }

    private int rootOf(int i, int j, int h, int[][] ids) {
        int root = ids[i][j];
        int I = i;
        int J = j;
        while (true) {
            int nextI = root % h;
            int nextJ = root / h - 1;
            if (I != nextI || J != nextJ) {
                root = ids[nextI][nextJ];
                I = nextI;
                J = nextJ;
            } else {
                break;
            }
        }
        return root;
    }
}
