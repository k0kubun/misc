import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String firstLine[] = scanner.nextLine().split(" ", 2);

        int n = Integer.parseInt(firstLine[0]);
        int m = Integer.parseInt(firstLine[1]);

        int max = new Main().maxNum(n, m);
        System.out.println(max);
    }

    public int maxNum(int n, int m) {
        return 1;
    }
}
