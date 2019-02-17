import java.util.*;

class UnionFind {
    static class RootInfo {
        int i;
        int j;
        int depth;

        public RootInfo(int i, int j, int depth) {
            this.i = i;
            this.j = j;
            this.depth = depth;
        }
    }

    int[] body; // This keeps parent number. If it has the index itself, it's a root.
    int h;
    int w;

    public UnionFind(int h, int w) {
        this.body = new int[h * w];
        this.h = h;
        this.w = w;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                this.body[indexOf(i, j)] = indexOf(i, j);
            }
        }
    }

    public void union(int i1, int j1, int i2, int j2) {
        RootInfo root1 = findRoot(i1, j1);
        RootInfo root2 = findRoot(i2, j2);
        if (root1.depth >= root2.depth) { // optimize
            this.body[indexOf(root2.i, root2.j)] = indexOf(root1.i, root1.j);
        } else {
            this.body[indexOf(root1.i, root1.j)] = indexOf(root2.i, root2.j);
        }
    }

    public int findGroup(int i, int j) {
        RootInfo info = findRoot(i, j);
        return indexOf(info.i, info.j);
    }

    private RootInfo findRoot(int i, int j) {
        RootInfo info = findRootInternal(i, j);
        if (info.depth >= 2) { // optimize
            this.body[indexOf(i, j)] = indexOf(info.i, info.j);
        }
        return info;
    }

    private RootInfo findRootInternal(int i, int j) {
        int index = this.body[indexOf(i, j)];
        int nextI = index % h;
        int nextJ = index / h;
        if (i == nextI && j == nextJ) {
            return new RootInfo(i, j, 0);
        } else {
            RootInfo info = findRootInternal(nextI, nextJ);
            info.depth += 1;
            return info;
        }
    }

    private int indexOf(int i, int j) {
        return i + h * j;
    }
}

class Main {
    public static void main(String[] args) {
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

        UnionFind groups = new UnionFind(h, w);
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                // left
                /*
                if (i - 1 >= 0 && black[i][j] != black[i-1][j]) {
                    groups.union(i, j, i-1, j);
                }
                */

                // right
                if (i + 1 < h && black[i][j] != black[i+1][j]) {
                    groups.union(i, j, i+1, j);
                }

                // up
                /*
                if (j - 1 >= 0 && black[i][j] != black[i][j-1]) {
                    groups.union(i, j, i, j-1);
                }
                */

                // down
                if (j + 1 < w && black[i][j] != black[i][j+1]) {
                    groups.union(i, j, i, j+1);
                }
            }
        }

        Map<Integer, Integer> groupBlacks = new HashMap<>();
        Map<Integer, Integer> groupWhites = new HashMap<>();
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int group = groups.findGroup(i, j);
                if (black[i][j]) {
                    int count = groupBlacks.getOrDefault(group, 0);
                    groupBlacks.put(group, count + 1);
                } else {
                    int count = groupWhites.getOrDefault(group, 0);
                    groupWhites.put(group, count + 1);
                }
            }
        }

        long answer = 0;
        for (Integer group : groupBlacks.keySet()) {
            long blacks = groupBlacks.get(group);
            long whites = groupWhites.getOrDefault(group, 0);
            answer += blacks * whites;
        }
        System.out.println(answer);
    }
}
