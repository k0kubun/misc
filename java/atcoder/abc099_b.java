import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] ab = scanner.nextLine().split(" ", 2);
        int a = Integer.parseInt(ab[0]);
        int b = Integer.parseInt(ab[1]);

        int leftHeight = 1;
        int rightHeight = 1;
        for (int i = 2; i <= 999; i++) {
            rightHeight += i;

            if (leftHeight > a && rightHeight > b && leftHeight - a == rightHeight - b) {
                System.out.println(leftHeight - a);
                return;
            }

            leftHeight += i;
        }
    }
}
