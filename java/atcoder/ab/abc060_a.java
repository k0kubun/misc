import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] abc = scanner.nextLine().split(" ", 3);
        String a = abc[0];
        String b = abc[1];
        String c = abc[2];

        if (a.charAt(a.length()-1) == b.charAt(0) && b.charAt(b.length()-1) == c.charAt(0)) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }
}
