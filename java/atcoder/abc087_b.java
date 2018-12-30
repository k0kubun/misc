import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = Integer.parseInt(scanner.nextLine());
        int b = Integer.parseInt(scanner.nextLine());
        int c = Integer.parseInt(scanner.nextLine());
        int x = Integer.parseInt(scanner.nextLine());

        int result = 0;
        for (int i500 = 0; i500 <= a; i500++) {
            for (int i100 = 0; i100 <= b; i100++) {
                int rest = (x - 500 * i500 - 100 * i100);
                if (rest >= 0 && rest == Math.min(rest / 50, c) * 50) {
                    result++;
                }
            }
        }
        System.out.println(result);
    }
}
