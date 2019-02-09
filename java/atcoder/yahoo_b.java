import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[] edgeCount = new int[5];
        for (int i = 0; i < 3; i++) {
            String[] ab = scanner.nextLine().split(" ", 2);
            int a = Integer.parseInt(ab[0]);
            int b = Integer.parseInt(ab[1]);
            edgeCount[a]++;
            edgeCount[b]++;
        }

        boolean all2 = true;
        for (int i = 1; i <= 4; i++) {
            if (edgeCount[i] == 0 || edgeCount[i] >= 3) {
                all2 = false;
                break;
            }
        }
        if (all2) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }
}
