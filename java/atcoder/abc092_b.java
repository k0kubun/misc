import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        String[] dx = scanner.nextLine().split(" ", 2);
        int d = Integer.parseInt(dx[0]);
        int x = Integer.parseInt(dx[1]);

        int[] people = new int[n];
        for (int i = 0; i < n; i++) {
            people[i] = Integer.parseInt(scanner.nextLine());
        }

        int eaten = 0;
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < n; j++) {
                if (i % people[j] == 0) {
                    eaten++;
                }
            }
        }
        System.out.println(x + eaten);
    }
}
