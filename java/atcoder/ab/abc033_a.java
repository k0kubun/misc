import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        String s = new Scanner(System.in).nextLine();
        char ch = s.charAt(0);
        if (ch == s.charAt(1) && ch == s.charAt(2) && ch == s.charAt(3)) {
            System.out.println("SAME");
        } else {
            System.out.println("DIFFERENT");
        }
    }
}
