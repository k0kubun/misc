import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();

        int times = ((N - 1) / 111) + 1;
        System.out.print(times);
        System.out.print(times);
        System.out.println(times);
    }
}
