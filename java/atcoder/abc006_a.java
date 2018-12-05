import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String strN = scanner.nextLine();
        int n = Integer.parseInt(strN);

        if (strN.indexOf("3") >= 0 || n % 3 == 0) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }
}
