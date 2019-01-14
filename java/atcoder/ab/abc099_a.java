import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        if (n >= 1000) {
            System.out.println("ABD");
        } else {
            System.out.println("ABC");
        }
    }
}
