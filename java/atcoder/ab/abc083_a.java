import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] abcd = scanner.nextLine().split(" ", 4);
        int a = Integer.parseInt(abcd[0]);
        int b = Integer.parseInt(abcd[1]);
        int c = Integer.parseInt(abcd[2]);
        int d = Integer.parseInt(abcd[3]);

        int left = a + b;
        int right = c + d;
        if (left == right) {
            System.out.println("Balanced");
        } else if (left > right) {
            System.out.println("Left");
        } else {
            System.out.println("Right");
        }
    }
}
