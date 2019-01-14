import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] wab = scanner.nextLine().split(" ", 3);
        int w = Integer.parseInt(wab[0]);
        int a = Integer.parseInt(wab[1]);
        int b = Integer.parseInt(wab[2]);

        int left = Math.min(a, b);
        int right = Math.max(a, b);
        if (right <= left + w) {
            System.out.println(0);
            return;
        }
        System.out.println(right - (left + w));
    }
}
