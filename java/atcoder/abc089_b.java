import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        String[] line = scanner.nextLine().split(" ", n);

        Set<Character> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            set.add(line[i].charAt(0));
        }

        if (set.size() == 4) {
            System.out.println("Four");
        } else {
            System.out.println("Three");
        }
    }
}
