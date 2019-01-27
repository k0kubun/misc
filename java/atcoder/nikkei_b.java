import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        String a = scanner.nextLine();
        String b = scanner.nextLine();
        String c = scanner.nextLine();

        int count = 0;
        for (int i = 0; i < n; i++) {
            char chA = a.charAt(i);
            char chB = b.charAt(i);
            char chC = c.charAt(i);

            if (chA == chB && chB == chC) {
                // pass
            } else if (chA == chB || chB == chC || chA == chC) {
                count++;
            } else {
                count += 2;
            }
        }
        System.out.println(count);
    }
}
