import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        String[] line = new Scanner(System.in).nextLine().split(" ", 3);
        int a = Integer.parseInt(line[0]);
        int b = Integer.parseInt(line[1]);
        int c = Integer.parseInt(line[2]);

        int surface = 0;
        surface += 2 * (a * b);
        surface += 2 * (b * c);
        surface += 2 * (c * a);
        System.out.println(surface);
    }
}
