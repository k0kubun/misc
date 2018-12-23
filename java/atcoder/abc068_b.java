import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());

        int power = 1;
        while ((power * 2) <= n) {
            power *= 2;
        }
        System.out.println(power);
    }
}
