import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        String[] exp = new Scanner(System.in).nextLine().split(" ", 3);
        int a = Integer.parseInt(exp[0]);
        int b = Integer.parseInt(exp[2]);
        char op = exp[1].charAt(0);

        if (op == '+') {
            System.out.println(a + b);
        } else {
            System.out.println(a - b);
        }
    }
}
