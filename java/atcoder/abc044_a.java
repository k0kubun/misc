import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        int k = Integer.parseInt(scanner.nextLine());
        int x = Integer.parseInt(scanner.nextLine());
        int y = Integer.parseInt(scanner.nextLine());

        int sum = 0;
        for (int i = 1; i <= n; i++) {
            if (i >= k + 1) {
                sum += y;
            } else {
                sum += x;
            }
        }
        System.out.println(sum);
    }
}
