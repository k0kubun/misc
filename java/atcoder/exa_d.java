import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] nx = scanner.nextLine().split(" ", 2);
        int n = Integer.parseInt(nx[0]);
        int x = Integer.parseInt(nx[1]);
        String[] line = scanner.nextLine().split(" ", n);

        for (int i = 0; i < n; i++) {
            String s = Integer.parseInt(line[i]);
        }

    }
}
