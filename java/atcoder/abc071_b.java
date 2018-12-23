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

        int i = 0;
        for (char ch = 'a'; ch <= 'z'; ch++) {
            if (i >= s.length() || arr[i] != ch) {
                System.out.println(ch);
                return;
            }
            while (i < s.length() && arr[i] == ch) {
                i++;
            }
        }
        System.out.println("None");
    }
}
