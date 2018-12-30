import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        String[] line = scanner.nextLine().split(" ", n);

        long[] nums = new long[n];
        for (int i = 0; i < n; i++) {
            nums[i] = Long.parseLong(line[i]);
        }
        Arrays.sort(nums);

        System.out.println(nums[n - 1] - nums[0]);
    }
}
