import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        int n100 = n / 100;
        int n10 = (n / 10) % 10;
        int n1 = n % 10;
        System.out.print(10 - n100);
        System.out.print(10 - n10);
        System.out.println(10 - n1);
    }
}
