import java.util.*;
class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] abc = scanner.nextLine().split(" ", 3);
        System.out.println(Integer.parseInt(abc[0]) * Integer.parseInt(abc[1]) / 2);
    }
}
