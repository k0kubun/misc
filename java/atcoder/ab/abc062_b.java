import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] hw = scanner.nextLine().split(" ", 2);
        int h = Integer.parseInt(hw[0]);
        int w = Integer.parseInt(hw[1]);

        for (int i = 0; i < w + 2; i++) {
            System.out.print("#");
        }
        System.out.print("\n");
        for (int i = 0; i < h; i++) {
            String line = scanner.nextLine();
            System.out.printf("#%s#\n", line);
        }
        for (int i = 0; i < w + 2; i++) {
            System.out.print("#");
        }
        System.out.print("\n");
    }
}
