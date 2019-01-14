import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = Integer.parseInt(scanner.nextLine());
        int b = Integer.parseInt(scanner.nextLine());
        int n = Integer.parseInt(scanner.nextLine());

        while (true) {
            if (n % a == 0 && n % b == 0) {
                break;
            }
            n++;
        }
        System.out.println(n);
    }
}
