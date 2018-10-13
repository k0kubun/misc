import java.util.Scanner;

class Queue {
}

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] nq = scanner.nextLine().split(" ", 2);
        int n = Integer.parseInt(nq[0]); // num processes
        int q = Integer.parseInt(nq[1]); // quoantum number

        String[] names = new String[n];
        int[] times = new int[n];
        for (int i = 0; i < n; i++) {
            String[] nameTime = scanner.nextLine().split(" ", 2);
            names[i] = nameTime[0];
            times[i] = Integer.parseInt(nameTime[1]);
        }

        new Main().printProcessFinishes(n, q, names, times);
    }

    private void printProcessFinishes(int n, int q, String[] names, int[] times) {
    }
}
