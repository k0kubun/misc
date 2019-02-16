import java.util.*;

class Item {
    int value;
    int weight;

    public Item(int value, int weight) {
        this.value = value;
        this.weight = weight;
    }
}

class Main {
    private static final int DIAGONAL = 1; // i is selected
    private static final int TOP = 0; // i is NOT selected

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] nw = scanner.nextLine().split(" ", 2);
        int n = Integer.parseInt(nw[0]);
        int w = Integer.parseInt(nw[1]);

        Item[] items = new Item[n+1];
        for (int i = 1; i <= n; i++) {
            String[] vw = scanner.nextLine().split(" ", 2);
            int value = Integer.parseInt(vw[0]);
            int weight = Integer.parseInt(vw[1]);
            items[i] = new Item(value, weight);
        }

        int maxValue = knapsackMaxValue(n, w, items);
        System.out.println(maxValue);
    }

    private static int knapsackMaxValue(int n, int weight, Item[] items) {
        int[][] maxValueC = new int[n+1][weight+1];
        int[][] selectedG = new int[n+1][weight+1];

        for (int w = 0; w <= weight; w++) {
            maxValueC[0][w] = 0;
            selectedG[0][w] = DIAGONAL;
        }
        for (int i = 1; i <= n; i++) maxValueC[i][0] = 0;

        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= weight; w++) {
                maxValueC[i][w] = maxValueC[i - 1][w];
                selectedG[i][w] = TOP;
                if (items[i].weight > w) continue;

                if (items[i].value + maxValueC[i - 1][w - items[i].weight] > maxValueC[i - 1][w]) {
                    maxValueC[i][w] = items[i].value + maxValueC[i - 1][w - items[i].weight];
                    selectedG[i][w] = DIAGONAL;
                }
            }
        }

        return maxValueC[n][weight];
    }
}
