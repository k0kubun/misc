import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        String[] line = new Scanner(System.in).nextLine().split(",", 3);
        System.out.printf("%s %s %s\n", line[0], line[1], line[2]);
    }
}
