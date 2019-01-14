import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());

        int result = 0;
        String[] line = scanner.nextLine().split(" ", n);
        for (int i = 0; i < n; i++) {
            int a = Integer.parseInt(line[i]);
            if (a % 2 == 0) {
                if (a % 3 == 2 || a % 3 == 1) {
                    result++;
                } else {
                    result += 3;
                }
            } else if (a % 3 == 2) {
                result += 2;
            }
        }
        System.out.println(result);
    }
}
