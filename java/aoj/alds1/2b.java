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

        new Main().selectionSort(numbers, len);
    }

    private void selectionSort(int numbers[], int len) {
        int times = 0;
        boolean sorted = true;

        for (int i = 0; i < len; i++) {
            int minJ = i;
            for (int j = i; j < len; j++) {
                if (numbers[j] < numbers[minJ]) {
                    minJ = j;
                }
            }

            if (i != minJ) {
                int temp = numbers[i];
                numbers[i] = numbers[minJ];
                numbers[minJ] = temp;
                times++;
            }
        }

        for (int i = 0; i < len; i++) {
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
