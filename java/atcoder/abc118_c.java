import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        String[] line = scanner.nextLine().split(" ", n);

        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(line[i]);
        }
        Arrays.sort(arr);

        int min = arr[0];
        int result = arr[0];
        while (true) {
            int nextMin = Integer.MAX_VALUE;
            boolean withNon0 = false;
            for (int i = 0; i < n; i++) {
                if (arr[i] == min) continue;
                arr[i] %= min;
                if (arr[i] > 0) {
                    withNon0 = true;
                    result = Math.min(result, arr[i]);
                    nextMin = Math.min(nextMin, arr[i]);
                }
            }

            if (withNon0) {
                min = nextMin;
            } else {
                break;
            }
        }
        System.out.println(result);
    }
}
