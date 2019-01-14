import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        int i = Integer.parseInt(scanner.nextLine());
        System.out.println(str.charAt(i - 1));
    }
}
