import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] ny = scanner.nextLine().split(" ", 2);
        int n = Integer.parseInt(ny[0]);
        int y = Integer.parseInt(ny[1]);

        int y10k = 0;
        int y5k = 0;
        int y1k = 0;

        if (10000 * n < y) {
            System.out.println("-1 -1 -1");
            return;
        }

        for (y10k = y / 10000; y10k >= 0; y10k--) {
            int remY = y - y10k * 10000;
            if (5000 * (n - y10k) < remY) {
                System.out.println("-1 -1 -1");
                return;
            }

            for (y5k = remY / 5000; y5k >= 0; y5k--) {
                int remY2 = remY - y5k * 5000;
                y1k = (n - y10k - y5k);
                if (remY2 == y1k * 1000) {
                    System.out.printf("%d %d %d\n", y10k, y5k, y1k);
                    return;
                } else if (y1k * 1000 < remY2) {
                    break;
                }
            }
        }
        System.out.println("-1 -1 -1");
    }
}
