import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] xy = scanner.nextLine().split(" ", 2);
        int x = Integer.parseInt(xy[0]);
        int y = Integer.parseInt(xy[1]);
        System.out.println(x + y / 2);
    }
}
