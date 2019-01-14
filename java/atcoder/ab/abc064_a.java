import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rgb = Integer.parseInt(String.join("", scanner.nextLine().split(" ", 3)));
        if (rgb % 4 == 0) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }
}
