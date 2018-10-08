import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] inputs = scanner.nextLine().split(" ", 2);

        int w = Integer.parseInt(inputs[0]);
        int h = Integer.parseInt(inputs[1]);

        System.out.print(w * h);
        System.out.print(" ");
        System.out.println(2 * (w + h));
    }
}
