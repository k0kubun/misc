import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        String[] line = scanner.nextLine().split(" ", n);

        int total = 0;
        int cur = 0;
        for (int i = 0; i < n; i++) {
            int h = Integer.parseInt(line[i]);
            if (cur < h) {
                total += h - cur;
            }
            cur = h;
        }
        System.out.println(total);
    }
}
