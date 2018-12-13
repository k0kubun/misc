import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        String s = scanner.nextLine();

        int x = 0;
        int max = 0;
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case 'I':
                    x++;
                    if (x > max) {
                        max = x;
                    }
                    break;
                case 'D':
                    x--;
                    break;
            }
        }
        System.out.println(max);
    }
}
