import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int k = Integer.parseInt(scanner.nextLine());
        if (k % 2 == 0) {
            System.out.println((k / 2) * (k / 2));
        } else {
            System.out.println((k / 2) * ((k / 2) + 1));
        }
    }
}
