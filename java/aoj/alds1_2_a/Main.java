import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int len = Integer.parseInt(scanner.nextLine());
        String inputs[] = scanner.nextLine().split(" ", -1);

        int numbers[] = new int[len];
        for (int i = 0; i < len; i++) {
            numbers[i] = Integer.parseInt(inputs[i]);
        }

        new Main().bubbleSort(numbers, len);
    }

    private void bubbleSort(int numbers[], int len) {
        int times = 0;
        boolean sorted = true;

        int i = 0; // count of sorted numbers
        while (sorted) {
            sorted = false;
            for (int j = len - 1; j > i; j--) {
                if (numbers[j - 1] > numbers[j]) {
                    int temp = numbers[j - 1];
                    numbers[j - 1] = numbers[j];
                    numbers[j] = temp;

                    times++;
                    sorted = true;
                }
            }
            i++;
        }

        for (i = 0; i < len; i++) {
            System.out.print(numbers[i]);
            if (i == len - 1) {
                System.out.print("\n");
            } else {
                System.out.print(" ");
            }
        }
        System.out.println(times);
    }
}
