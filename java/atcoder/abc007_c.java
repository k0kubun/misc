import java.util.*;

class Main {
    class Point {
        int r;
        int c;

        public Point(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    public void main() {
        Scanner scanner = new Scanner(System.in);
        String[] rc = scanner.nextLine().split(" ", 2);
        int r = Integer.parseInt(rc[0]);
        int c = Integer.parseInt(rc[1]);

        String[] sysx = scanner.nextLine().split(" ", 2);
        int sy = Integer.parseInt(sysx[0]) - 1;
        int sx = Integer.parseInt(sysx[1]) - 1;

        String[] gygx = scanner.nextLine().split(" ", 2);
        int gy = Integer.parseInt(gygx[0]) - 1;
        int gx = Integer.parseInt(gygx[1]) - 1;

        int[][] count = new int[r][c];
        boolean[][] movable = new boolean[r][c];
        for (int i = 0; i < r; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < c; j++) {
                movable[i][j] = (line.charAt(j) == '.');
                count[i][j] = -1;
            }
        }

        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(sy, sx));
        count[sy][sx] = 0;

        Point point;
        while ((point = queue.poll()) != null) {
            if (point.r == gy && point.c == gx) {
                System.out.println(count[point.r][point.c]);
                return;
            }

            if (movable[point.r-1][point.c] && count[point.r-1][point.c] == -1) {
                count[point.r-1][point.c] = count[point.r][point.c] + 1;
                queue.add(new Point(point.r-1, point.c));
            }
            if (movable[point.r][point.c+1] && count[point.r][point.c+1] == -1) {
                count[point.r][point.c+1] = count[point.r][point.c] + 1;
                queue.add(new Point(point.r, point.c+1));
            }
            if (movable[point.r][point.c-1] && count[point.r][point.c-1] == -1) {
                count[point.r][point.c-1] = count[point.r][point.c] + 1;
                queue.add(new Point(point.r, point.c-1));
            }
            if (movable[point.r+1][point.c] && count[point.r+1][point.c] == -1) {
                count[point.r+1][point.c] = count[point.r][point.c] + 1;
                queue.add(new Point(point.r+1, point.c));
            }
        }
    }

    public static void main(String[] args) {
        new Main().main();
    }
}
