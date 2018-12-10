import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        int n = new Scanner(System.in).nextInt();
        if (n == 100) {
            System.out.println("Perfect");
        } else if (n >= 90) {
            System.out.println("Great");
        } else if (n >= 60) {
            System.out.println("Good");
        } else {
            System.out.println("Bad");
        }
    }
}
