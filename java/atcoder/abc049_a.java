import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        String s = new Scanner(System.in).nextLine();
        char ch = s.charAt(0);
        switch (s.charAt(0)) {
            case 'a':
            case 'i':
            case 'u':
            case 'e':
            case 'o':
                System.out.println("vowel");
                break;
            default:
                System.out.println("consonant");
                break;
        }
    }
}
