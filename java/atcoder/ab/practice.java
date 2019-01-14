import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = Integer.parseInt(scanner.nextLine());
        String[] bc = scanner.nextLine().split(" ", 2);
        int b = Integer.parseInt(bc[0]);
        int c = Integer.parseInt(bc[1]);
        String s = scanner.nextLine();

        System.out.print(a + b + c);
        System.out.print(" ");
        System.out.println(s);
    }
}
