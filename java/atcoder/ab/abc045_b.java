import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] arr = new String[3];
        arr[0] = scanner.nextLine();
        arr[1] = scanner.nextLine();
        arr[2] = scanner.nextLine();

        char turn = 'a';
        while (arr[turn - 'a'].length() > 0) {
            String s = arr[turn - 'a'];
            char nextTurn = s.charAt(0);
            arr[turn - 'a'] = s.substring(1, s.length());
            turn = nextTurn;
        }
        System.out.printf("%c\n", 'A' + turn - 'a');
    }
}
