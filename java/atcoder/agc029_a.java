import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();

        int result = 0;
        int whiteCount = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == 'W') {
                result += i - whiteCount;
                whiteCount++;
            }
        }
        System.out.println(result);
    }
}
