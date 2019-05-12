import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] nq = scanner.nextLine().split(" ", 2);
        int n = Integer.parseInt(nq[0]);
        int q = Integer.parseInt(nq[1]);
        String s = scanner.nextLine();

        long[] count = new long[n];
        Map<Character, List<Integer>> mapIndexes = new HashMap<>();
        for (int i = 0; i < n; i++) {
            count[i] = 1;

            List<Integer> list = mapIndexes.get(s.charAt(i));
            if (list == null) {
                list = new ArrayList<>();
                mapIndexes.put(s.charAt(i), list);
            }
            list.add(i);
        }

        for (int i = 0; i < q; i++) {
            String[] td = scanner.nextLine().split(" ", 2);
            char t = td[0].charAt(0);
            char d = td[1].charAt(0);

            List<Integer> list = mapIndexes.get(t);
            if (list != null && list.size() > 0) {
                for (Integer j : list) {
                    long cnt = count[j];
                    count[j] = 0;
                    if (d == 'R') {
                        if (j+1 < n) count[j+1] += cnt;
                    } else {
                        if (j-1 >= 0) count[j-1] += cnt;
                    }
                }
            }
        }

        long sum = 0;
        for (int i = 0; i < n; i++) {
            sum += count[i];
        }
        System.out.println(sum);
    }
}
