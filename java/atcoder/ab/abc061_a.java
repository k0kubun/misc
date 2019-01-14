import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] abc = scanner.nextLine().split(" ", 3);
        int a = Integer.parseInt(abc[0]);
        int b = Integer.parseInt(abc[1]);
        int c = Integer.parseInt(abc[2]);
        if (c >= a && c <= b) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }
}
