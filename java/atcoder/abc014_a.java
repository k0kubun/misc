import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = Integer.parseInt(scanner.nextLine());
        int b = Integer.parseInt(scanner.nextLine());

        if (a % b == 0) {
            System.out.println(0);
        } else {
            System.out.println(b - (a % b));
        }
    }
}
