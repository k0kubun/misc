import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] nm = scanner.nextLine().split(" ", 2);
        int n = Integer.parseInt(nm[0]);
        int m = Integer.parseInt(nm[1]);

        Map<Integer, Integer> inputMap = new HashMap<>();
        Map<Integer, Set<Integer>> parentsMap = new HashMap<>();
        for (int i = 0; i < n - 1 + m; i++) {
            String[] ab = scanner.nextLine().split(" ", 2);
            int a = Integer.parseInt(ab[0]);
            int b = Integer.parseInt(ab[1]);
            inputMap.put(a, b);

            Set<Integer> parents = parentsMap.get(b);
            if (parents == null) {
                parents = new HashSet<>();
                parentsMap.put(b, parents);
            }
            parents.add(a);
        }

        for (int i = 1; i <= n; i++) {
            if (!parentsMap.containsKey(i)) {
                removeChildrensParent(inputMap, parentsMap, i, inputMap.get(i));
                break;
            }
        }

        for (int i = 1; i <= n; i++) {
            if (parentsMap.containsKey(i)) {
                for (Integer parent : parentsMap.get(i)) {
                    System.out.println(parent);
                    break;
                }
            } else {
                System.out.println(0);
            }
        }
    }

    private static void removeChildrensParent(Map<Integer, Integer> inputMap, Map<Integer, Set<Integer>> parentsMap, Integer removedParent, Integer parent) {
        Integer child = inputMap.get(parent);
        if (child == null) return;

        Set<Integer> parentsSet = parentsMap.get(child);
        if (parentsSet == null || parentsSet.size() == 1) return;

        parentsSet.remove(removedParent);
        removeChildrensParent(inputMap, parentsMap, removedParent, child);
        removeChildrensParent(inputMap, parentsMap, parent, child);
    }
}
