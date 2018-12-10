import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = Integer.parseInt(scanner.nextLine());
        int b = Integer.parseInt(scanner.nextLine());
        int h = Integer.parseInt(scanner.nextLine());
        System.out.println((a + b) * h / 2);
    }
}
