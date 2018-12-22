import java.math.BigInteger;
import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BigInteger a = scanner.nextBigInteger();
        BigInteger b = scanner.nextBigInteger();
        if (a.equals(b)) {
            System.out.println("EQUAL");
        } else if (a.compareTo(b) > 0) {
            System.out.println("GREATER");
        } else {
            System.out.println("LESS");
        }
    }
}
