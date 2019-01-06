import java.util.*;

class Main {
    public static void main(String[] args) {
        new Main().main();
    }

    Map<Integer, Integer> fibCache;

    public void main() {
        this.fibCache = new HashMap<>();

        int n = new Scanner(System.in).nextInt();
        System.out.println(this.fib(n));
    }

    private int fib(int n) {
        if (this.fibCache.containsKey(n)) {
            return this.fibCache.get(n);
        }

        int result;
        if (n <= 1) {
            result = 1;
        } else {
            result = this.fib(n - 1) + this.fib(n - 2);
        }
        this.fibCache.put(n, result);
        return result;
    }
}
