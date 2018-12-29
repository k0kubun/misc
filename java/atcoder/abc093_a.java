import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();

        char[] chars = new char[3];
        for (int i = 0; i < s.length(); i++) {
            chars[i] = s.charAt(i);
        }
        Arrays.sort(chars);

        if (chars[0] == 'a' && chars[1] == 'b' && chars[2] == 'c') {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }
}
