import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        String s = new Scanner(System.in).nextLine();
        int i = 0;
        while (i < s.length()) {
            String str5 = s.substring(i, Math.min(i + 5, s.length()));
            if (str5.equals("dream")) {
                i += 5;
                if (i + 1 < s.length() && s.charAt(i) == 'e' && s.charAt(i + 1) == 'r'
                    && (i + 2 == s.length() || s.charAt(i + 2) != 'a')) {
                    i += 2;
                }
            }
            else if (str5.equals("erase")) {
                i += 5;
                if (i < s.length() && s.charAt(i) == 'r') {
                    i++;
                }
            }
            else {
                System.out.println("NO");
                return;
            }
        }
        System.out.println("YES");
    }
}
