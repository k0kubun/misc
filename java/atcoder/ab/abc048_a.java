import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        String[] line = new Scanner(System.in).nextLine().split(" ", 3);
        System.out.printf("A%cC\n", line[1].charAt(0));
    }
}
