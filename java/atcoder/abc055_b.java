import java.math.BigInteger;
import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BigInteger n = scanner.nextBigInteger();
        BigInteger x = new BigInteger("1");
        BigInteger one = new BigInteger("1");

        for (BigInteger i = new BigInteger("1"); i.compareTo(n) <= 0; i = i.add(one)) {
            x = x.multiply(i);
        }
        BigInteger mod = new BigInteger("100000007");
        System.out.println(x.mod(mod).toString());
    }
}
