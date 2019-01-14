import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        int n = new Scanner(System.in).nextInt();
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        System.out.println(sum);
    }
}
