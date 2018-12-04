import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            switch (ch) {
                case 'a':
                case 'i':
                case 'u':
                case 'e':
                case 'o':
                    break;
                default:
                    System.out.print(ch);
                    break;
            }
        }
        System.out.print("\n");
    }
}
