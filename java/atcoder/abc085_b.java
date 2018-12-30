import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());

        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            int a = Integer.parseInt(scanner.nextLine());
            set.add(a);
        }
        System.out.println(set.size());
    }
}
