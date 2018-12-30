import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String s = scanner.nextLine();
        char[] sArr = s.toCharArray();
        Arrays.sort(sArr);
        s = new String(sArr);

        String t = scanner.nextLine();
        char[] tArr = t.toCharArray();
        Arrays.sort(tArr);
        t = new StringBuilder(new String(tArr)).reverse().toString();

        if (s.compareTo(t) < 0) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }
}
