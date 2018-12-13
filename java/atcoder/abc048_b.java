import java.math.BigInteger;
import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BigInteger a = scanner.nextBigInteger();
        BigInteger b = scanner.nextBigInteger();
        BigInteger x = scanner.nextBigInteger();

        BigInteger one = new BigInteger("1");
        long answer = 0;
        for (; a.compareTo(b) <= 0; a = a.add(one)) {
            if (a.mod(x).equals(0)) {
                answer++;
            }
        }
        System.out.println(answer);
    }
}
