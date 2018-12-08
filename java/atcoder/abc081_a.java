import java.util.*;

class Main {
    public static void main(String[] args) {
        String line = new Scanner(System.in).nextLine();
        int sum = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '1') {
                sum++;
            }
        }
        System.out.println(sum);
    }
}
