import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] line = scanner.nextLine().split(" ", 3);
        int a = Integer.parseInt(line[0]);
        int b = Integer.parseInt(line[1]);
        int k = Integer.parseInt(line[2]);

        int count = 0;
        for (int i = 100; i >= 1; i--) {
            if (a % i == 0 && b % i == 0) {
                count++;
                if (count == k) {
                    System.out.println(i);
                    break;
                }
            }
        }
    }
}
