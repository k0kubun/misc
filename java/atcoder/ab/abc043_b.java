import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case '0':
                    builder.append("0");
                    break;
                case '1':
                    builder.append("1");
                    break;
                case 'B':
                    if (builder.length() > 0) {
                        builder.deleteCharAt(builder.length() - 1);
                    }
                    break;
            }
        }
        System.out.println(builder.toString());
    }
}
