import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();

        String rest = "keyence";
        int restI = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == rest.charAt(restI)) {
                restI++;
                if (restI == rest.length()) {
                    System.out.println("YES");
                    return;
                }
                continue;
            }

            if (s.substring(s.length() - (rest.length() - restI), s.length()).equals(rest.substring(restI, rest.length()))) {
                System.out.println("YES");
                return;
            } else {
                break;
            }
        }
        System.out.println("NO");
    }
}
