import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        String[] line = new Scanner(System.in).nextLine().split(" ", 2);
        int a = Integer.parseInt(line[0]);
        int d = Integer.parseInt(line[1]);

        int attack = (a + 1) * d;
        int diffense = a * (d + 1);
        if (attack >= diffense) {
            System.out.println(attack);
        } else {
            System.out.println(diffense);
        }
    }
}
