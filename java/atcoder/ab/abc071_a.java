import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] xab = scanner.nextLine().split(" ", 3);
        int x = Integer.parseInt(xab[0]);
        int a = Integer.parseInt(xab[1]);
        int b = Integer.parseInt(xab[2]);

        int aDist = Math.abs(x - a);
        int bDist = Math.abs(x - b);
        if (aDist < bDist) {
            System.out.println("A");
        } else {
            System.out.println("B");
        }
    }
}
