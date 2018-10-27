import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        if (line.length() == 2) {
            System.out.println(line);
        } else {
            for (int i = 2; i >= 0; i--) {
                System.out.print(line.charAt(i));
            }
            System.out.print("\n");
        }
    }
}
