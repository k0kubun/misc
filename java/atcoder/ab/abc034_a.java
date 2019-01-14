import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        String[] line = new Scanner(System.in).nextLine().split(" ", 2);
        int x = Integer.parseInt(line[0]);
        int y = Integer.parseInt(line[1]);
        if (x < y) {
            System.out.println("Better");
        } else {
            System.out.println("Worse");
        }
    }
}
