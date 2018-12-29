import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] abcd = scanner.nextLine().split(" ", 4);
        int a = Integer.parseInt(abcd[0]);
        int b = Integer.parseInt(abcd[1]);
        int c = Integer.parseInt(abcd[2]);
        int d = Integer.parseInt(abcd[3]);

        if ((Math.abs(a - b) <= d && Math.abs(b - c) <= d) || Math.abs(a - c) <= d) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }
}
