import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str5 = scanner.nextLine();
        int n = Integer.parseInt(scanner.nextLine());

        int firstInd = (n - 1) / 5;
        int secondInd = (n - 1) % 5;
        System.out.printf("%c%c\n", str5.charAt(firstInd), str5.charAt(secondInd));
    }
}
