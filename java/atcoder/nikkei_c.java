import java.util.*;

class Main {
    class Dish implements Comparable<Dish> {
        long a;
        long b;
        long priority;

        public Dish(long a, long b) {
            this.a = a;
            this.b = b;
            this.priority = a + b;
        }

        @Override
        public int compareTo(Dish p) {
            if (this.priority > p.priority) {
                return 1;
            } else if (this.priority < p.priority) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    public void main() {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        List<Dish> dishes = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            String[] ab = scanner.nextLine().split(" ", 2);
            long a = Long.parseLong(ab[0]);
            long b = Long.parseLong(ab[1]);
            dishes.add(new Dish(a, b));
        }
        Collections.sort(dishes, Collections.reverseOrder());

        long aCount = 0;
        long bCount = 0;
        for (int i = 0; i < n; i++) {
            Dish dish = dishes.get(i);

            if (i % 2 == 0) { // A turn
                aCount += dish.a;
            } else { // B turn
                bCount += dish.b;
            }
        }
        System.out.println(aCount - bCount);
    }

    public static void main(String[] args) {
        new Main().main();
    }
}
