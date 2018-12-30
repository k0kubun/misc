import java.math.BigInteger;
import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BigInteger n = scanner.nextBigInteger();

        BigInteger lPrev = new BigInteger("2");
        BigInteger l = new BigInteger("1");
        BigInteger i = new BigInteger("1");
        BigInteger one = new BigInteger("1");

        if (n.equals(one)) {
            System.out.println(1);
            return;
        }

        while (!i.equals(n)) {
            BigInteger lNext = l.add(lPrev);
            lPrev = l;
            l = lNext;
            i = i.add(one);
        }
        System.out.println(l.toString());
    }
}
