import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        String[] line = new Scanner(System.in).nextLine().split(" ", 2);
        int n = Integer.parseInt(line[0]);
        int x = Integer.parseInt(line[1]);

        int left = x - 1;
        int right = n - x;
        if (left < right) {
            System.out.println(left);
        } else {
            System.out.println(right);
        }
    }
}
