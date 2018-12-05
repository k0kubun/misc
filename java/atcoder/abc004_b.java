import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[][] matrix = new String[4][4];
        for (int i = 0; i < 4; i++) {
            String[] line = scanner.nextLine().split(" ", 4);
            for (int j = 0; j < 4; j++) {
                matrix[3 - i][3 - j] = line[j];
            }
        }

        for (int i = 0; i < 4; i++) {
            System.out.printf("%s %s %s %s\n", matrix[i][0], matrix[i][1], matrix[i][2], matrix[i][3]);
        }
    }
}
