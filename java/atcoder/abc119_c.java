import java.util.*;

class Main {
    private static class Answer {
        int mp;
        List<Integer> used;

        public Answer(int mp, List<Integer> used) {
            this.mp = mp;
            this.used = used;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] line = scanner.nextLine().split(" ", 4);
        int n = Integer.parseInt(line[0]);

        List<Integer> targets = new ArrayList<>();
        targets.add(Integer.parseInt(line[1]));
        targets.add(Integer.parseInt(line[2]));
        targets.add(Integer.parseInt(line[3]));

        List<Integer> bamboos = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int bamboo = Integer.parseInt(scanner.nextLine());
            bamboos.add(bamboo);
        }

        long mp = 0;
        while (targets.size() > 0) {
            Answer[] answers = new Answer[targets.size()];
            for (int i = 0; i < targets.size(); i++) {
                answers[i] = solve(targets.get(i), targets.size(), bamboos);
            }

            int minInd = 0;
            for (int i = 1; i < targets.size(); i++) {
                if (answers[minInd].mp > answers[i].mp) {
                    minInd = i;
                }
            }

            System.out.printf("mp:%d, used:%s, target:%d\n", answers[minInd].mp, answers[minInd].used.toString(), targets.get(minInd));
            mp += answers[minInd].mp;
            for (Integer b : answers[minInd].used) {
                bamboos.remove(b);
            }
            targets.remove(minInd);
        }
        System.out.println(mp);
    }

    private static Answer solve(int target, int targetsNum, List<Integer> bamboos) {
        List<Integer> copies = new ArrayList<>();

        // over answer
        Integer overBamboo = null;
        for (Integer bamboo : bamboos) {
            if (target == bamboo) {
                List<Integer> u = new ArrayList<>();
                u.add(target);
                return new Answer(0, u);
            }

            if (target < bamboo && (overBamboo == null || bamboo < overBamboo)) {
                overBamboo = bamboo;
            }
            if (bamboo < target) {
                copies.add(bamboo);
            }
        }

        int underMP = 0;
        List<Integer> used = new ArrayList<>();
        int base = 0;
        for (int i = 0; i <= bamboos.size() - targetsNum; i++) {
            Integer maxInd = null;
            for (int j = 0; j < copies.size(); j++) {
                int copy = copies.get(j);
                if ((target - base) >= copy) {
                    if (maxInd == null || copies.get(maxInd) < copy) {
                        maxInd = j;
                    }
                }
            }

            if (maxInd == null) {
                break;
            }

            if (base == 0) {
                base += copies.get(maxInd);
                used.add(copies.get(maxInd));
                int ind = maxInd;
                copies.remove(ind);
            } else {
                if (copies.get(maxInd) > 10) {
                    base += copies.get(maxInd);
                    used.add(copies.get(maxInd));
                    int ind = maxInd;
                    copies.remove(ind);
                    underMP += 10;
                } else {
                    break;
                }
            }
        }
        if (base < target) {
            System.out.printf(" ++%d (from:%d)\n", target - base, underMP);
            underMP += (target - base);
        }

        if (overBamboo == null) {
            return new Answer(underMP, used);
        } else {
            int overMP = overBamboo - target;
            if (overMP <= underMP) {
                List<Integer> u = new ArrayList<>();
                u.add(overBamboo);
                return new Answer(overMP, u);
            } else {
                return new Answer(underMP, used);
            }
        }
    }
}
