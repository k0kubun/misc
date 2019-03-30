import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        String s = scanner.nextLine();

        int red = 0;
        int blue = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'R') {
                red++;
            } else {
                blue++;
            }
        }
        System.out.println(red > blue ? "Yes" : "No");
    }
}
