import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();

        char[] arr = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            arr[i] = s.charAt(i);
        }

        Arrays.sort(arr);

        char ch = arr[0];
        for (int i = 1; i < s.length(); i++) {
            if (ch == arr[i]) {
                System.out.println("no");
                return;
            }
            ch = arr[i];
        }
        System.out.println("yes");
    }
}
