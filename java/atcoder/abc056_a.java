import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] ab = scanner.nextLine().split(" ", 2);
        String a = ab[0];
        String b = ab[1];

        boolean truthTeller;
        if (a.equals("H")) { // b is truth
            truthTeller = b.equals("H");
        }
        else { // b is inverse
            truthTeller = !b.equals("H");
        }
        if (truthTeller) {
            System.out.println("H");
        }
        else {
            System.out.println("D");
        }
    }
}
