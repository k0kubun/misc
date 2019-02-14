import java.util.*;

class Main {
    private static final int N = 4;
    private static final int N2 = 16;
    private static final int LIMIT = 100;
    private static final char[] directions = { 'r', 'u', 'l', 'd' };
    private static final int[] directionX = { 0, -1,  0, 1 };
    private static final int[] directionY = { 1,  0, -1, 0 };
    private static int[][] manDistTable;

    private static class Puzzle {
        int[] f;
        int spaceIndex;
        int manDist;

        private static int getAllManDist(int[] f) {
            int sum = 0;
            for (int i = 0; i < N2; i++) {
                if (f[i] == N2) continue;
                sum += manDistTable[i][f[i] - 1];
            }
            return sum;
        }

        public Puzzle(int[] f, int spaceIndex) {
            this(f, spaceIndex, getAllManDist(f));
        }

        public Puzzle(int[] f, int spaceIndex, int manDist) {
            this.f = f;
            this.spaceIndex = spaceIndex;
            this.manDist = manDist;
        }

        public Puzzle copy() {
            int[] newF = new int[N2];
            System.arraycopy(this.f, 0, newF, 0, N2);
            return new Puzzle(newF, spaceIndex, manDist);
        }
    }

    public static void main(String[] args) {
        manDistTable = new int[N2][N2];
        for (int i = 0; i < N2; i++) {
            for (int j = 0; j < N2; j++) {
                manDistTable[i][j] = Math.abs(i / N - j / N) + Math.abs(i % N - j % N);
            }
        }

        Scanner scanner = new Scanner(System.in);
        int[] f = new int[N2];
        int spaceIndex = 0;
        for (int i = 0; i < N2; i++) {
            f[i] = scanner.nextInt();
            if (f[i] == 0) {
                spaceIndex = i;
                f[i] = N2;
            }
        }
        Puzzle in = new Puzzle(f, spaceIndex);

        String answer = iterativeDeepening(in, manDistTable);
        System.out.println(answer.length());
    }

    private static String iterativeDeepening(Puzzle in, int[][] manDistTable) {
        int[] path = new int[LIMIT];

        for (int limit = in.manDist; limit <= LIMIT; limit++) {
            if (dfs(in, path, limit, 0, -100)) {
                StringBuilder answer = new StringBuilder();
                for (int i = 0; i < limit; i++)
                    answer.append(directions[path[i]]);
                return answer.toString();
            }
        }

        throw new RuntimeException("unsolvable");
    }

    private static boolean dfs(Puzzle state, int[] path, int limit, int depth, int prevDirInd) {
        if (state.manDist == 0) return true;
        // prune branch with heuristics
        if (depth + state.manDist > limit) return false;

        int spaceX = state.spaceIndex / N;
        int spaceY = state.spaceIndex % N;

        for (int dirInd = 0; dirInd < 4; dirInd++) {
            int nextSpaceX = spaceX + directionX[dirInd];
            int nextSpaceY = spaceY + directionY[dirInd];
            if (nextSpaceX < 0 || nextSpaceY < 0 || nextSpaceX >= N || nextSpaceY >= N) continue;
            if (Math.max(prevDirInd, dirInd) - Math.min(prevDirInd, dirInd) == 2) continue; // prohibit rollback

            Puzzle tmp = state.copy();
            // calculate next manhattan distance
            state.manDist -= manDistTable[nextSpaceX * N + nextSpaceY][state.f[nextSpaceX * N + nextSpaceY] - 1];
            state.manDist += manDistTable[spaceX * N + spaceY][state.f[nextSpaceX * N + nextSpaceY] - 1];

            // swap pieces
            int tmpF = state.f[nextSpaceX * N + nextSpaceY];
            state.f[nextSpaceX * N + nextSpaceY] = state.f[spaceX * N + spaceY];
            state.f[spaceX * N + spaceY] = tmpF;
            state.spaceIndex = nextSpaceX * N + nextSpaceY;

            if (dfs(state, path, limit, depth + 1, dirInd)) {
                path[depth] = dirInd;
                return true;
            }
            state = tmp;
        }

        return false;
    }
}
