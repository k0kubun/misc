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
        int cost;

        private static int getAllManDist(int[] f) {
            int sum = 0;
            for (int i = 0; i < N2; i++) {
                if (f[i] == N2) continue;
                sum += manDistTable[i][f[i] - 1];
            }
            return sum;
        }

        public Puzzle(int[] f, int spaceIndex) {
            this(f, spaceIndex, getAllManDist(f), 0);
        }

        public Puzzle(int[] f, int spaceIndex, int manDist, int cost) {
            this.f = f;
            this.spaceIndex = spaceIndex;
            this.manDist = manDist;
            this.cost = cost;
        }

        public Puzzle copy() {
            int[] newF = new int[N2];
            System.arraycopy(this.f, 0, newF, 0, N2);
            return new Puzzle(newF, spaceIndex, manDist, cost);
        }

        public boolean equals(Puzzle a) {
            for (int i = 0; i < N2; i++) {
                if (this.f[i] != a.f[i]) return false;
            }
            return true;
        }

        public int hashCode() {
            return Arrays.hashCode(f);
        }
    }

    private static class State implements Comparable<State> {
        Puzzle puzzle;
        int estimated;

        public State(Puzzle puzzle, int estimated) {
            this.puzzle = puzzle;
            this.estimated = estimated;
        }

        @Override
        public int compareTo(State s) {
            return this.estimated - s.estimated;
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

        int answer = astar(in, manDistTable);
        System.out.println(answer);
    }

    private static int astar(Puzzle s, int[][] manDistTable) {
        PriorityQueue<State> pq = new PriorityQueue<>();
        Map<Puzzle, Boolean> visited = new HashMap<>();

        State initial = new State(s, s.manDist);
        pq.add(initial);

        while (pq.size() > 0) {
            State st = pq.poll();

            if (st.puzzle.manDist == 0) return st.puzzle.cost;
            visited.put(st.puzzle, true);

            int spaceX = st.puzzle.spaceIndex / N;
            int spaceY = st.puzzle.spaceIndex % N;

            for (int dirInd = 0; dirInd < 4; dirInd++) {
                int nextSpaceX = spaceX + directionX[dirInd];
                int nextSpaceY = spaceY + directionY[dirInd];

                if (nextSpaceX < 0 || nextSpaceY < 0 || nextSpaceX >= N || nextSpaceY >= N) continue;

                Puzzle v = st.puzzle.copy();

                // calculate next manhattan distance
                v.manDist -= manDistTable[nextSpaceX * N + nextSpaceY][v.f[nextSpaceX * N + nextSpaceY] - 1];
                v.manDist += manDistTable[spaceX * N + spaceY][v.f[nextSpaceX * N + nextSpaceY] - 1];


                // swap pieces
                int tmpF = v.f[nextSpaceX * N + nextSpaceY];
                v.f[nextSpaceX * N + nextSpaceY] = v.f[spaceX * N + spaceY];
                v.f[spaceX * N + spaceY] = tmpF;
                v.spaceIndex = nextSpaceX * N + nextSpaceY;

                if (!visited.containsKey(v)) {
                    v.cost++;
                    State news = new State(v, v.cost + v.manDist);
                    pq.add(news);
                }
            }
        }
        return -1;
    }
}
