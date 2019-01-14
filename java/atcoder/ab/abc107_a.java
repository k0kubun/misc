import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] ni = scanner.nextLine().split(" ", 2);
        int n = Integer.parseInt(ni[0]);
        int i = Integer.parseInt(ni[1]);
        System.out.println(n - i + 1);
    }
}
