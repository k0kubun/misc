import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] abc = scanner.nextLine().split(" ", 3);
        int a = Integer.parseInt(abc[0]);
        int b = Integer.parseInt(abc[1]);
        int c = Integer.parseInt(abc[2]);

        for (int i = 1; i <= b; i++) {
            if ((a * i) % b == c) {
                System.out.println("YES");
                return;
            }
        }
        System.out.println("NO");
    }
}
