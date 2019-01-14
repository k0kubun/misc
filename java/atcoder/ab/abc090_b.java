import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] ab = scanner.nextLine().split(" ", 2);
        int a = Integer.parseInt(ab[0]);
        int b = Integer.parseInt(ab[1]);

        int count = 0;
        for (int i = a; i <= b; i++) {
            if (i / 10000 == i % 10 && (i % 10000) / 1000 == (i % 100) / 10) {
                count++;
            }
        }
        System.out.println(count);
    }
}
