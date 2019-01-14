import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());

        int count = 0;
        boolean[] arrived = new boolean[100001];
        for (int i = 0; i < n; i++) {
            int type = Integer.parseInt(scanner.nextLine());
            if (arrived[type]) {
                count++;
            }
            arrived[type] = true;
        }
        System.out.println(count);
    }
}
