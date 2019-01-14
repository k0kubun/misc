import java.util.*;

class Main {
    class Cache {
        private long size;
        private long pati;

        public Cache(long size, long pati) {
            this.size = size;
            this.pati = pati;
        }

        public long size() {
            return this.size;
        }

        public long pati() {
            return this.pati;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] line = scanner.nextLine().split(" ", 2);
        long n = Long.parseLong(line[0]);
        long x = Long.parseLong(line[1]);
        new Main(x, 0).main(n);
    }

    private long toEat;
    private long pati;
    private HashMap<Long, Cache> cacheMap;

    public Main(long toEat, long pati) {
        this.toEat = toEat;
        this.pati = pati;
        this.cacheMap = new HashMap<>();
    }

    public void main(long level) {
        this.eat(level);
        System.out.println(this.pati);
    }

    public void eat(long level) {
        if (toEat == 0) {
            return;
        }

        if (level == 0) {
            this.toEat--;
            this.pati++;
        } else {
            Cache cache = this.cacheMap.get(level - 1);

            long initToEat = this.toEat;
            long initPati = this.pati;

            this.toEat--; // ban

            // level - 1 burger
            if (cache != null && this.toEat >= cache.size) {
                this.toEat -= cache.size;
                this.pati += cache.pati;
            } else if (this.toEat > 0) {
                this.eat(level - 1);
            }

            if (this.toEat > 0) { // pati
                this.toEat--;
                this.pati++;
            }

            // level - 1 burger
            if (cache != null && this.toEat >= cache.size) {
                this.toEat -= cache.size;
                this.pati += cache.pati;
            } else if (this.toEat > 0) {
                this.eat(level - 1);
            }

            if (this.toEat > 0) { // ban
                this.toEat--;

                this.cacheMap.put(level, new Cache(initToEat - toEat, pati - initPati));
            }
        }
    }
}
