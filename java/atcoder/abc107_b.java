import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] hw = scanner.nextLine().split(" ", 2);
        int h = Integer.parseInt(hw[0]);
        int w = Integer.parseInt(hw[1]);

        String[] lines = new String[h];
        for (int i = 0; i < h; i++) {
            lines[i] = scanner.nextLine();
        }

        boolean[] deletedColumns = new boolean[w];
        for (int j = 0; j < w; j++) {
            boolean allDotColumn = true;
            for (int i = 0; i < h; i++) {
                if (lines[i].charAt(j) != '.') {
                    allDotColumn = false;
                    break;
                }
            }
            deletedColumns[j] = allDotColumn;
        }

        for (int i = 0; i < h; i++) {
            String line = lines[i];
            boolean allDotRow = true;
            for (int j = 0; j < w; j++) {
                if (line.charAt(j) != '.') {
                    allDotRow = false;
                    break;
                }
            }
            if (allDotRow) continue;

            for (int j = 0; j < w; j++) {
                if (!deletedColumns[j]) {
                    System.out.print(line.substring(j, j+1));
                }
            }
            System.out.print("\n");
        }
    }
}
