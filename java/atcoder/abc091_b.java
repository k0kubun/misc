import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Integer> strMoney = new HashMap<>();

        int n = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < n; i++) {
            String s = scanner.nextLine();
            if (strMoney.containsKey(s)) {
                strMoney.put(s, strMoney.get(s) + 1);
            } else {
                strMoney.put(s, 1);
            }
        }

        int m = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < m; i++) {
            String s = scanner.nextLine();
            if (strMoney.containsKey(s)) {
                strMoney.put(s, strMoney.get(s) - 1);
            } else {
                strMoney.put(s, -1);
            }
        }

        int max = 0;
        for (String key : strMoney.keySet()) {
            int money = strMoney.get(key);
            if (money > max) {
                max = money;
            }
        }
        System.out.println(max);
    }
}
