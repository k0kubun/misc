import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = Integer.parseInt(scanner.nextLine());

        Set<String> seen = new HashSet<>();
        for (int i = 0; i < num; i++) {
            String[] op = scanner.nextLine().split(" ", 2);
            if (op[0].equals("insert")) {
                seen.add(op[1]);
            } else if (op[0].equals("find")) {
                if (seen.contains(op[1])) {
                    System.out.println("yes");
                } else {
                    System.out.println("no");
                }
            } else {
                throw new RuntimeException(String.format("unexpected operator: %s", op[0]));
            }
        }
    }
}
