import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Main {
    class Memo {
        private int startKey;
        private int endKey;

        public Memo(String start, String end) {
            int startHour = Integer.parseInt(start.substring(0, 2));
            int startMin = Integer.parseInt(start.substring(2, 4));
            startMin = startMin / 5 * 5;

            int endHour = Integer.parseInt(end.substring(0, 2));
            int endMin = Integer.parseInt(end.substring(2, 4));
            if (endMin % 5 == 0) {
                endMin = endMin / 5 * 5;
            } else {
                endMin = (endMin / 5 + 1) * 5;
                if (endMin == 60) {
                    endHour += 1;
                    endMin = 0;
                }
            }

            this.startKey = startHour * 60 + startMin;
            this.endKey = endHour * 60 + endMin;
        }

        public String startTime() {
            return String.format("%02d%02d", this.startKey / 60, this.startKey % 60);
        }

        public String endTime() {
            return String.format("%02d%02d", this.endKey / 60, this.endKey % 60);
        }

        public int startKey() {
            return this.startKey;
        }

        public int endKey() {
            return this.endKey;
        }
    }

    public static void main(String[] args) {
        new Main().main();
    }

    public void main() {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());

        List<Memo> memos = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String[] line = scanner.nextLine().split("-");
            Memo memo = new Memo(line[0], line[1]);
            memos.add(memo);
            if (i % 10 == 0) {
                System.gc();
            }
        }
        memos.sort((a, b) -> a.startKey() - b.startKey());

        Memo prev = null;
        for (Memo memo : memos) {
            if (prev == null) {
                System.out.printf("%s-", memo.startTime());
                prev = memo;
            } else if (memo.startKey() <= prev.endKey()) {
                if (prev.endKey() < memo.endKey()) {
                    prev = memo;
                }
            } else {
                System.out.println(prev.endTime());
                System.out.printf("%s-", memo.startTime());
                prev = memo;
            }
        }
        System.out.println(prev.endTime());
    }
}
