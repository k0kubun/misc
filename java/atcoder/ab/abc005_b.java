import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        int min = Integer.parseInt(scanner.nextLine());
        for (int i = 1; i < n; i++) {
            int t = Integer.parseInt(scanner.nextLine());
            if (t < min) {
                min = t;
            }
        }
        System.out.println(min);
    }
}
