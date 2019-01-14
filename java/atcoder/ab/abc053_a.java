import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        int x = new Scanner(System.in).nextInt();
        if (x < 1200) {
            System.out.println("ABC");
        } else {
            System.out.println("ARC");
        }
    }
}
