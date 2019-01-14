import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());

        String previous = null;
        Set<String> seen = new HashSet<>();
        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            if (seen.contains(line)) {
                System.out.println("No");
                return;
            }
            seen.add(line);

            if (previous != null && previous.charAt(previous.length() - 1) != line.charAt(0)) {
                System.out.println("No");
                return;
            }
            previous = line;
        }
        System.out.println("Yes");
    }
}
