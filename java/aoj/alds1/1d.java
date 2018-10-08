import java.util.Scanner;

class ALDS1_1d {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int len = Integer.parseInt(scanner.nextLine());

        int inputs[] = new int[len];
        for (int i = 0; i < len; i++) {
            inputs[i] = Integer.parseInt(scanner.nextLine());
        }

        int maxProfit = new Main().maxProfit(inputs);
        System.out.println(maxProfit);
    }

    private static int maxProfit(int numbers[]) {
        Integer maxProfit = null;
        Integer minStart = null;
        for (int n : numbers) {
            if (minStart == null) {
                minStart = n;
                continue;
            }
            int profit = n - minStart.intValue();
            if (maxProfit == null || maxProfit < profit) {
                maxProfit = profit;
            }

            if (n < minStart.intValue()) {
                minStart = n;
            }
        }
        return maxProfit.intValue();
    }
}
