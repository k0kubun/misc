import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 3; i++) {
            String line = scanner.nextLine();
            System.out.print(line.charAt(i));
        }
        System.out.print("\n");
    }
}
