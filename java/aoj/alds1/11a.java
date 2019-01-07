import java.util.*;

class Main {
    public static void main(String[] args) {
        new Main().main();
    }

    public void main() {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < n; i++) {
            int[] row = new int[n];

            String[] line = scanner.nextLine().split(" ", -1);
            for (int j = 0; j < Integer.parseInt(line[1]); j++) {
                row[Integer.parseInt(line[2 + j]) - 1] = 1;
            }

            for (int j = 0; j < n; j++) {
                System.out.print(row[j]);
                System.out.print((j == n - 1) ? "\n" : " ");
            }
        }
    }
}
