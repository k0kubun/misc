import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int r = Integer.parseInt(scanner.nextLine());
        if (r < 1200) {
            System.out.println("ABC");
        } else if (r < 2800) {
            System.out.println("ARC");
        } else {
            System.out.println("AGC");
        }
    }
}
