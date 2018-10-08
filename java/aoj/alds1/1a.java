import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int len = Integer.parseInt(scanner.nextLine());
        String inputs[] = scanner.nextLine().split(" ", -1);

        int numbers[] = new int[len];
        for (int i = 0; i < len; i++) {
            numbers[i] = Integer.parseInt(inputs[i]);
        }

        new Main().insertionSort(numbers, len);
    }

    private void insertionSort(int numbers[], int len) {
        for (int i = 0; i < len; i++) {
            int v = numbers[i];
            int j = i - 1;
            while (j >= 0 && numbers[j] > v) {
                numbers[j + 1] = numbers[j];
                j--;
            }
            numbers[j + 1] = v;

            for (j = 0; j < len; j++) {
                System.out.print(numbers[j]);
                if (j == len - 1) {
                    System.out.print("\n");
                } else {
                    System.out.print(" ");
                }
            }
        }
    }
}
