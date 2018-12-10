import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        String[] line = new Scanner(System.in).nextLine().split(" ", 2);
        int a = Integer.parseInt(line[0]);
        int b = Integer.parseInt(line[1]);
        System.out.println((b - 1) / a + 1);
    }
}
