import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        String line = new Scanner(System.in).nextLine();
        int sum = 0;
        for (int i = 0; i < line.length(); i++) {
            sum += line.charAt(i) - '0';
        }
        System.out.println(sum);
    }
}
