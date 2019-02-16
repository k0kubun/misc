import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] nm = scanner.nextLine().split(" ", 2);
        int n = Integer.parseInt(nm[0]);
        int m = Integer.parseInt(nm[1]);

        boolean[] like = new boolean[m+1];
        Arrays.fill(like, true);

        for (int i = 0; i < n; i++) {
            String[] line = scanner.nextLine().split(" ", -1);
            int k = Integer.parseInt(line[0]);

            boolean[] lineLike = new boolean[m+1];
            for (int j = 1; j <= k; j++) {
                int a = Integer.parseInt(line[j]);
                lineLike[a] = true;
            }
            for (int j = 1; j <= m; j++) {
                if (!lineLike[j]) {
                    like[j] = false;
                }
            }
        }

        int num = 0;
        for (int i = 1; i <= m; i++) {
            if (like[i]) {
                num++;
            }
        }
        System.out.println(num);
    }
}
