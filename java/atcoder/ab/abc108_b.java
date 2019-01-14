import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] xyxy = scanner.nextLine().split(" ", 4);
        int x1 = Integer.parseInt(xyxy[0]);
        int y1 = Integer.parseInt(xyxy[1]);
        int x2 = Integer.parseInt(xyxy[2]);
        int y2 = Integer.parseInt(xyxy[3]);

        int xDiff = y1 - y2;
        int yDiff = x2 - x1;
        int x3 = x2 + xDiff;
        int y3 = y2 + yDiff;

        xDiff = x1 - x2;
        yDiff = y1 - y2;
        int x4 = x3 + xDiff;
        int y4 = y3 + yDiff;
        System.out.printf("%d %d %d %d\n", x3, y3, x4, y4);
    }
}
