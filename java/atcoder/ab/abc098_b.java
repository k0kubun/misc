import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        String s = scanner.nextLine();

        int max = 0;
        for (int rightStart = 1; rightStart <= n - 1; rightStart++) {
            int[] leftCount = new int['z' - 'a' + 1];
            int[] rightCount = new int['z' - 'a' + 1];
            for (int i = 0; i < n; i++) {
                int charInd = s.charAt(i) - 'a';
                if (i < rightStart) {
                    leftCount[charInd]++;
                } else {
                    rightCount[charInd]++;
                }
            }

            int total = 0;
            for (char ch = 'a'; ch <= 'z'; ch++) {
                if (leftCount[ch - 'a'] > 0 && rightCount[ch - 'a'] > 0) {
                    total++;
                }
            }
            if (total > max) {
                max = total;
            }
        }
        System.out.println(max);
    }
}
