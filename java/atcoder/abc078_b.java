import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] xyz = scanner.nextLine().split(" ", 3);
        int x = Integer.parseInt(xyz[0]);
        int y = Integer.parseInt(xyz[1]);
        int z = Integer.parseInt(xyz[2]);

        System.out.println((x - z) / (y + z));
    }
}
