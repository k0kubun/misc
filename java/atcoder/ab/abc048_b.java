import java.math.BigInteger;
import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BigInteger a = scanner.nextBigInteger();
        BigInteger b = scanner.nextBigInteger();
        BigInteger x = scanner.nextBigInteger();
        BigInteger zero = new BigInteger("0");

        BigInteger leftEdge;
        if (a.mod(x).equals(zero)) {
            leftEdge = a;
        } else {
            leftEdge = a.add(x.subtract(a.mod(x)));
        }

        BigInteger rightEdge;
        if (b.mod(x).equals(zero)) {
            rightEdge = b;
        } else {
            rightEdge = b.subtract(b.mod(x));
        }

        if (leftEdge.compareTo(rightEdge) > 0) {
            System.out.println(0);
        } else {
            System.out.println(rightEdge.subtract(leftEdge).divide(x).add(new BigInteger("1")));
        }
    }
}
