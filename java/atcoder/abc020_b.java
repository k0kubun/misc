import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] line = scanner.nextLine().split(" ", 2);
        int ab = Integer.parseInt(String.format("%s%s", line[0], line[1]));
        System.out.println(ab * 2);
    }
}
