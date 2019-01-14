import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] abx = scanner.nextLine().split(" ", 3);
        int a = Integer.parseInt(abx[0]);
        int b = Integer.parseInt(abx[1]);
        int x = Integer.parseInt(abx[2]);

        if (a <= x && x <= a + b) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }
}
