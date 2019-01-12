import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        String[] ab = scanner.nextLine().split(" ", 2);
        int a = Integer.parseInt(ab[0]);
        int b = Integer.parseInt(ab[1]);
        String[] line = scanner.nextLine().split(" ", n);

        int firstCnt = 0;
        int secondCnt = 0;
        int thirdCnt = 0;
        for (int i = 0; i < n; i++) {
            int p = Integer.parseInt(line[i]);
            if (p <= a) {
                firstCnt++;
            } else if (a + 1 <= p && p <= b) {
                secondCnt++;
            } else if (b + 1 <= p) {
                thirdCnt++;
            }
        }
        System.out.println(Math.min(firstCnt, Math.min(secondCnt, thirdCnt)));
    }
}
