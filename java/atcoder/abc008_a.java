import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        String[] line = new Scanner(System.in).nextLine().split(" ", 2);
        int s = Integer.parseInt(line[0]);
        int t = Integer.parseInt(line[1]);
        System.out.println(t - s + 1);
    }
}
