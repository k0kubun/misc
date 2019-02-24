import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());

        double result = 0;
        for (int i = 0; i < n; i++) {
            String[] line = scanner.nextLine().split(" ", 2);
            if (line[1].equals("JPY")) {
                result += Integer.parseInt(line[0]);
            } else {
                result += Double.parseDouble(line[0]) * 380000.0;
            }
        }
        System.out.println(result);
    }
}
