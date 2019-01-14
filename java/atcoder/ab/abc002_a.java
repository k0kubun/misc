import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] line = scanner.nextLine().split(" ", 2);
        int x = Integer.parseInt(line[0]);
        int y = Integer.parseInt(line[1]);
        if (x > y) {
            System.out.println(x);
        } else {
            System.out.println(y);
        }
    }
}
