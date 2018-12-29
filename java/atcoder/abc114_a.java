import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int x = Integer.parseInt(scanner.nextLine());
        switch (x) {
            case 3:
            case 5:
            case 7:
                System.out.println("YES");
                break;
            default:
                System.out.println("NO");
                break;
        }
    }
}
