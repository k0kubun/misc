import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] abc = scanner.nextLine().split(" ", 3);

        int[] nums = new int[3];
        nums[0] = Integer.parseInt(abc[0]);
        nums[1] = Integer.parseInt(abc[1]);
        nums[2] = Integer.parseInt(abc[2]);
        Arrays.sort(nums);

        int k = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < k; i++) {
            nums[2] *= 2;
        }

        System.out.println(nums[0] + nums[1] + nums[2]);
    }
}
