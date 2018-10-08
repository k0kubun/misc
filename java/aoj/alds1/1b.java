import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] inputs = scanner.nextLine().split(" ", 2);

        int a = Integer.parseInt(inputs[0]);
        int b = Integer.parseInt(inputs[1]);
        int gcd = new Main().gcd(a, b);
        System.out.println(gcd);
    }

    public int gcd(int a, int b) {
        int max = 1;
        for (int i = 2; i <= Math.min(a, b); i++) {
            if (a % i == 0 && b % i == 0) {
                max = i;
            }
        }
        return max;
    }
}
