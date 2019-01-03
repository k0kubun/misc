import java.util.*;

class Main {
    class Dot {
        double x;
        double y;

        public Dot(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        new Main().main();
    }

    public void main() {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());

        List<Dot> list = new ArrayList<>();
        list.add(new Dot(0.0, 0.0));
        list.add(new Dot(100.0, 0.0));
        this.growKochCurve(list, n);

        for (Dot val : list) {
            System.out.printf("%f %f\n", val.x, val.y);
        }
    }

    private void growKochCurve(List<Dot> list, int level) {
        if (level == 0) {
            return;
        }

        Dot[] array = list.toArray(new Dot[list.size()]);
        for (int i = array.length - 2; i >= 0; i--) {
            Dot left = array[i];
            Dot right = array[i+1];

            Dot firstVector = new Dot((right.x - left.x) / 3.0, (right.y - left.y) / 3.0);

            Dot firstDot  = new Dot(left.x + firstVector.x, left.y + firstVector.y);
            Dot secondDot = this.makeSecondDot(left, right);
            Dot thirdDot  = new Dot(left.x + 2.0 * firstVector.x, left.y + 2.0 * firstVector.y);
            list.add(i + 1, thirdDot);
            list.add(i + 1, secondDot);
            list.add(i + 1, firstDot);
        }

        this.growKochCurve(list, level - 1);
    }

    private Dot makeSecondDot(Dot left, Dot right) {
        Dot firstVector = new Dot((right.x - left.x) / 3.0, (right.y - left.y) / 3.0);
        Dot secondVector = new Dot(
                firstVector.x * Math.cos(Math.toRadians(60)) - firstVector.y * Math.sin(Math.toRadians(60)),
                firstVector.x * Math.sin(Math.toRadians(60)) + firstVector.y * Math.cos(Math.toRadians(60)));
        return new Dot(
                left.x + firstVector.x + secondVector.x,
                left.y + firstVector.y + secondVector.y);
    }
}
