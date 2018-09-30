import java.util.Scanner;

public class Alds1_1_a {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int len = Integer.parseInt(scanner.nextLine());

        int inputs[] = new int[len];
        for (int i = 0; i < len; i++) {
            inputs[i] = Integer.parseInt(scanner.nextLine());
        }

        new Alds1_1_a().printMax(inputs);
    }

    private static void printMax(int numbers[]) {
        int max = 0;
        for (int n : numbers) {
            if (n > max) {
                max = n;
            }
        }
        System.out.println(max);
    }
}
