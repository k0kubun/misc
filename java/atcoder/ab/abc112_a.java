import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int mode = Integer.parseInt(scanner.nextLine());
        if (mode == 1) {
            new Main().helloWorld();
            return;
        }

        int a = Integer.parseInt(scanner.nextLine());
        int b = Integer.parseInt(scanner.nextLine());

        new Main().plus(a, b);
    }

    public void helloWorld() {
        System.out.println("Hello World");
    }

    public void plus(int a, int b) {
        System.out.println(a + b);
    }
}
