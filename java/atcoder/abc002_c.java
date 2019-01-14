import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] xyxyxy = scanner.nextLine().split(" ", 6);
        int x1 = Integer.parseInt(xyxyxy[0]);
        int y1 = Integer.parseInt(xyxyxy[1]);
        int x2 = Integer.parseInt(xyxyxy[2]);
        int y2 = Integer.parseInt(xyxyxy[3]);
        int x3 = Integer.parseInt(xyxyxy[4]);
        int y3 = Integer.parseInt(xyxyxy[5]);

        int a = x2 - x1;
        int b = y2 - y1;
        int c = x3 - x1;
        int d = y3 - y1;
        System.out.println(Math.abs(a * d - b * c) / 2.0);
    }
}
