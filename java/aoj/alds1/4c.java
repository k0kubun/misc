import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int sNum = Integer.parseInt(scanner.nextLine());
        String[] sLine = scanner.nextLine().split(" ", sNum);

        Map<String, Boolean> sSeen = new HashMap<>();
        for (int i = 0; i < sNum; i++) {
            sSeen.put(sLine[i], true);
        }

        int tNum = Integer.parseInt(scanner.nextLine());
        String[] tLine = scanner.nextLine().split(" ", tNum);

        int result = 0;
        for (int i = 0; i < tNum; i++) {
            if (sSeen.containsKey(tLine[i])) {
                result++;
            }
        }
        System.out.println(result);
    }
}
