import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int score = 0;
        for (int i = 0; i < 3; i++) {
            String[] line = scanner.nextLine().split(" ", 2);
            int s = Integer.parseInt(line[0]);
            int e = Integer.parseInt(line[1]);
            score += s / 10 * e;
        }
        System.out.println(score);
    }
}
