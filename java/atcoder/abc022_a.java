import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] line = scanner.nextLine().split(" ", 3);
        long n = Integer.parseInt(line[0]);
        long s = Integer.parseInt(line[1]);
        long t = Integer.parseInt(line[2]);

        long days = 0;
        long w = Integer.parseInt(scanner.nextLine());
        if (s <= w && w <= t) {
            days++;
        }
        for (long i = 1; i < n; i++) {
            w += Integer.parseInt(scanner.nextLine());
            if (s <= w && w <= t) {
                days++;
            }
        }
        System.out.println(days);
    }
}
