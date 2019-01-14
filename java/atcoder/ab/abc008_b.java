import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());

        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            map.put(line, map.getOrDefault(line, 0) + 1);
        }

        int maxN = 0;
        String str = "";
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (Integer.valueOf(maxN).compareTo(entry.getValue()) < 0) {
                str = entry.getKey();
                maxN = entry.getValue();
            }
        }
        System.out.println(str);
    }
}
