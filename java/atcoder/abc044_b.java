import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String w = scanner.nextLine();
        if (w.length() % 2 == 1) {
            System.out.println("No");
            return;
        }

        char[] arr = new char[w.length()];
        for (int i = 0; i < w.length(); i++) {
            arr[i] = w.charAt(i);
        }
        Arrays.sort(arr);

        boolean yes = true;
        for (int i = 0; i < w.length(); i += 2) {
            if (arr[i] != arr[i+1]) {
                yes = false;
                break;
            }
        }
        if (yes) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }
}
