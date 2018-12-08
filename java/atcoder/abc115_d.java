import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] line = scanner.nextLine().split(" ", 2);
        long n = Long.parseLong(line[0]);
        long x = Long.parseLong(line[1]);
        new Main(x, 0).main(n);
    }

    private long toEat;
    private long pati;

    public Main(long toEat, long pati) {
        this.toEat = toEat;
        this.pati = pati;
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
            this.toEat--; // ban

            if (this.toEat > 0) { // level - 1 burger
                this.eat(level - 1);
            }
            if (this.toEat > 0) { // pati
                this.toEat--;
                this.pati++;
            }
            if (this.toEat > 0) { // level - 1 burger
                this.eat(level - 1);
            }
            if (this.toEat > 0) { // ban
                this.toEat--;
            }
        }
    }
}
