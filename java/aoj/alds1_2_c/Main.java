import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int len = Integer.parseInt(scanner.nextLine());
        String cards[] = scanner.nextLine().split(" ", len);

        Main main = new Main();

        String sorted[] = main.bubbleSort(cards);
        main.printStrings(sorted);
        System.out.println(main.stablySorted(cards, sorted) ? "Stable" : "Not stable");

        sorted = main.selectionSort(cards);
        main.printStrings(sorted);
        System.out.println(main.stablySorted(cards, sorted) ? "Stable" : "Not stable");
    }

    private String[] bubbleSort(String cards[]) {
        String sorted[] = cards.clone();
        boolean found = true;

        int i = 0; // count of sorted cards
        while (found) {
            found = false;
            for (int j = sorted.length - 1; j > i; j--) {
                if (sorted[j - 1].substring(1).compareTo(sorted[j].substring(1)) > 0) {
                    String temp = sorted[j - 1];
                    sorted[j - 1] = sorted[j];
                    sorted[j] = temp;

                    found = true;
                }
            }
            i++;
        }
        return sorted;
    }

    private String[] selectionSort(String cards[]) {
        String sorted[] = cards.clone();
        for (int i = 0; i < sorted.length; i++) {
            int minJ = i;
            for (int j = i; j < sorted.length; j++) {
                if (sorted[j].substring(1).compareTo(sorted[minJ].substring(1)) < 0) {
                    minJ = j;
                }
            }

            if (i != minJ) {
                String temp = sorted[i];
                sorted[i] = sorted[minJ];
                sorted[minJ] = temp;
            }
        }
        return sorted;
    }

    private void printStrings(String strs[]) {
        for (int i = 0; i < strs.length; i++) {
            System.out.print(strs[i]);
            if (i == strs.length - 1) {
                System.out.print("\n");
            } else {
                System.out.print(" ");
            }
        }
    }

    private boolean stablySorted(String original[], String sorted[]) {
        List<String> sortedList = Arrays.asList(sorted);
        for (int i = 0; i < original.length; i++) {
            String card = original[i];
            int iIndex = sortedList.indexOf(original[i]);
            for (int j = i + 1; j < original.length; j++) {
                if (original[i].substring(1).compareTo(original[j].substring(1)) <= 0
                        && iIndex > sortedList.indexOf(original[j])) {
                    return false;
                }
            }
        }
        return true;
    }
}
