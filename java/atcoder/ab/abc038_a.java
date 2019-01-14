import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        String str = new Scanner(System.in).nextLine();
        if (str.charAt(str.length() - 1) == 'T') {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }
}
