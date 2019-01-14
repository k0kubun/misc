import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());

        int tPrev = 0;
        int xPrev = 0;
        int yPrev = 0;
        for (int i = 0; i < n; i++) {
            String[] txy = scanner.nextLine().split(" ", 3);
            int t = Integer.parseInt(txy[0]);
            int x = Integer.parseInt(txy[1]);
            int y = Integer.parseInt(txy[2]);

            int tDiff = t - tPrev;
            int xDiff = Math.abs(x - xPrev);
            int yDiff = Math.abs(y - yPrev);

            int remainingTime = tDiff - xDiff - yDiff;
            if (remainingTime < 0 || remainingTime % 2 != 0) {
                System.out.println("No");
                return;
            }

            tPrev = t;
            xPrev = x;
            yPrev = y;
        }
        System.out.println("Yes");
    }
}
