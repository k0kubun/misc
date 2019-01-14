import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] line = scanner.nextLine().split(" ", 2);
        int m = Integer.parseInt(line[0]);
        int d = Integer.parseInt(line[1]);
        if (m % d == 0) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }
}
