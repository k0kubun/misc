import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] nx = scanner.nextLine().split(" ", 2);
        int n = Integer.parseInt(nx[0]);
        int x = Integer.parseInt(nx[1]);

        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(scanner.nextLine());
        }
        Arrays.sort(arr);

        int donutsCount = 0;
        for (int i = 0; i < n; i++) {
            if (arr[i] <= x) {
                donutsCount++;
                x -= arr[i];
            } else {
                System.out.println(donutsCount);
                return;
            }
        }

        System.out.println(donutsCount + (x / arr[0]));
    }
}
