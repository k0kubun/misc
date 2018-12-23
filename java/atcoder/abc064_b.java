import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        String[] line = scanner.nextLine().split(" ", n);
        int min = 1001;
        int max = -1;
        for (int i = 0; i < n; i++) {
            int a = Integer.parseInt(line[i]);
            if (a > max) {
                max = a;
            }
            if (a < min) {
                min = a;
            }
        }
        System.out.println(max - min);
    }
}
