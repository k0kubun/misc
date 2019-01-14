import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        int first = -1;
        int second = -2;

        for (int i = 0; i < n; i++) {
            int a = Integer.parseInt(scanner.nextLine());
            if (a > first) {
                second = first;
                first = a;
            } else if (a != first && a > second) {
                second = a;
            }
        }
        System.out.println(second);
    }
}
