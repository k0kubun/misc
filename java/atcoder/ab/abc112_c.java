import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());

        int xs[] = new int[n];
        int ys[] = new int[n];
        int hs[] = new int[n];
        for (int i = 0; i < n; i++) {
            String line[] = scanner.nextLine().split(" ", 3);
            xs[i] = Integer.parseInt(line[0]);
            ys[i] = Integer.parseInt(line[1]);
            hs[i] = Integer.parseInt(line[2]);
        }

        new Main().printCenterHeight(n, xs, ys, hs);
    }

    public void printCenterHeight(int n, int xs[], int ys[], int hs[]) {
        int centerX = 0;
        int centerY = 0;
        int centerHeight = 0;

        for (int x = 0; x <= 100; x++) {
            for (int y = 0; y <= 100; y++) {
                for (int i = 0; i < n; i++) {
                    int height = hs[i] + abs(xs[i] - x) + abs(ys[i] - y);
                    if (height > centerHeight) {
                        centerX = x;
                        centerY = y;
                        centerHeight = height;
                    }
                }
            }
        }

        System.out.print(centerX);
        System.out.print(" ");
        System.out.print(centerY);
        System.out.print(" ");
        System.out.println(centerHeight);
    }

    public int abs(int n) {
        if (n < 0) {
            return -n;
        } else {
            return n;
        }
    }
}
