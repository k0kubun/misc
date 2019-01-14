import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        String[] line = new Scanner(System.in).nextLine().split(" ", 3);
        int l1 = Integer.parseInt(line[0]);
        int l2 = Integer.parseInt(line[1]);
        int l3 = Integer.parseInt(line[2]);
        if (l1 == l2) {
            System.out.println(l3);
        } else if (l1 == l3) {
            System.out.println(l2);
        } else {
            System.out.println(l1);
        }
    }
}
