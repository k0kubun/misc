import java.util.*;

class Main {
    class Sushi implements Comparable<Sushi> {
        long type;
        long d;

        public Sushi(long type, long d) {
            this.type = type;
            this.d = d;
        }

        @Override
        public int compareTo(Sushi p) {
            if (this.d > p.d) {
                return 1;
            } else if (this.d < p.d) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    public void main() {
        Scanner scanner = new Scanner(System.in);
        String[] nk = scanner.nextLine().split(" ", 2);
        int n = Integer.parseInt(nk[0]);
        int k = Integer.parseInt(nk[1]);

        List<Sushi> sushis = new ArrayList<>();
        Map<Long, Sushi> topByType = new HashMap<>();

        for (int i = 0; i < n; i++) {
            String[] td = scanner.nextLine().split(" ", 2);
            long t = Long.parseLong(td[0]);
            long d = Long.parseLong(td[1]);

            Sushi sushi = new Sushi(t, d);
            sushis.add(sushi);

            if (topByType.containsKey(t)) {
                Sushi top = topByType.get(t);
                if (top.d < sushi.d) {
                    topByType.put(t, sushi);
                }
            } else {
                topByType.put(t, sushi);
            }
        }

        List<Sushi> tops = new ArrayList<Sushi>(topByType.keySet().size());
        for (Sushi top : topByType.values()) {
            tops.add(top);
        }

        Collections.sort(sushis, Collections.reverseOrder());
        Collections.sort(tops, Collections.reverseOrder());

        long basePoint = 0;
        Map<Long, List<Sushi>> usedByType = new HashMap<>();

        for (int i = 0; i < k; i++) {
            Sushi sushi = sushis.get(i);
            basePoint += sushi.d;

            if (usedByType.containsKey(sushi.type)) {
                List<Sushi> list = usedByType.get(sushi.type);
                list.add(sushi);
            } else {
                List<Sushi> list = new ArrayList<>();
                list.add(sushi);
                usedByType.put(sushi.type, list);
            }
        }

        List<Sushi> usedWorst = new ArrayList<>();
        for (Long usedType : usedByType.keySet()) {
            List<Sushi> list = usedByType.get(usedType);
            if (list.size() >= 2) {
                usedWorst.add(list.get(list.size() - 1));
            }
        }
        Collections.sort(usedWorst);

        Set<Long> usedTypes = usedByType.keySet();
        long typesSize = usedTypes.size();
        long maxManzoku = basePoint + typesSize * typesSize;

        for (Sushi top : tops) {
            if (usedTypes.contains(top.type)) continue;

            Sushi minSushi = null;
            if (usedWorst.size() > 0) {
                minSushi = usedWorst.get(0);
            }
            if (minSushi == null) break;

            basePoint = basePoint - minSushi.d + top.d;
            typesSize++;
            long manzoku = basePoint + typesSize * typesSize;
            if (manzoku > maxManzoku) {
                maxManzoku = manzoku;
            }

            usedWorst.remove(0);
            List<Sushi> list = usedByType.get(minSushi.type);
            list.remove(list.size() - 1);
            if (list.size() >= 2) {
                usedWorst.add(list.get(list.size() - 1));
                Collections.sort(usedWorst);
            }
        }

        System.out.println(maxManzoku);
    }

    public static void main(String[] args) {
        new Main().main();
    }
}
