import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        String[] line = new Scanner(System.in).nextLine().split(" ", 2);
        System.out.printf("%s %s\n", line[1], line[0]);
    }
}
