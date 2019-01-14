import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String o = scanner.nextLine();
        String e = scanner.nextLine();

        for (int i = 0; i < o.length(); i++) {
            System.out.print(o.charAt(i));
            if (i < e.length()) {
                System.out.print(e.charAt(i));
            }
        }
        System.out.print("\n");
    }
}
