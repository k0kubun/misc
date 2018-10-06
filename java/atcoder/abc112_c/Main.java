import java.util.Scanner;

public class Main {
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

        new Main().printCenterHour(n, xs, ys, hs);
    }

    public void printCenterHour(int n, int xs[], int ys[], int hs[]) {
        System.out.print(1);
        System.out.print(" ");
        System.out.print(1);
        System.out.print(" ");
        System.out.println(1);
    }
}
