import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();

        int minDiff = 10000;
        for (int i = 0; i <= s.length() - 3; i++) {
            int diff = Math.abs(Integer.parseInt(s.substring(i, i + 3)) - 753);
            if (diff < minDiff) {
                minDiff = diff;
            }
        }
        System.out.println(minDiff);
    }
}
