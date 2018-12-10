import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        String[] line = new Scanner(System.in).nextLine().split(" ", 4);
        int a = Integer.parseInt(line[0]);
        int b = Integer.parseInt(line[1]);
        int c = Integer.parseInt(line[2]);
        int d = Integer.parseInt(line[3]);

        int first = a * b;
        int second = c * d;
        if (first > second) {
            System.out.println(first);
        } else {
            System.out.println(second);
        }
    }
}
