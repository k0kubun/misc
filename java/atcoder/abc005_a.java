import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] line = scanner.nextLine().split(" ", 2);
        int x = Integer.parseInt(line[0]);
        int y = Integer.parseInt(line[1]);
        System.out.println(y / x);
    }
}
