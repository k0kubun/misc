import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());

        int[] A = new int[n];
        for (int i = 0; i < n; i++) {
            A[i] = Integer.parseInt(scanner.nextLine());
        }

        Main main = new Main();
        main.shellSort(A, n);
    }

    private int cnt;

    private void insertionSort(int A[], int n, int g) {
        for (int i = g; i < n; i++) {
            int v = A[i];
            int j = i - g;
            while (j >= 0 && A[j] > v) {
                A[j + g] = A[j];
                j -= g;
                this.cnt++;
            }
            A[j + g] = v;
        }
    }

    private void shellSort(int[] A, int n) {
        this.cnt = 0;

        List<Integer> G = genG(n);
        int m = G.size();

        for (int i = 0; i < m; i++) {
            insertionSort(A, n, G.get(i));
        }

        System.out.println(m);
        for (int i = 0; i < m; i++) {
            System.out.print(G.get(i));
            if (i == m - 1) {
                System.out.print("\n");
            } else {
                System.out.print(" ");
            }
        }
        System.out.println(cnt);
        for (int i = 0; i < n; i++) {
            System.out.println(A[i]);
        }
    }

    private List<Integer> genG(int n) {
        List<Integer> list = new ArrayList<Integer>();
        int cur = 1;
        while (cur <= n) {
            list.add(cur);
            cur = 3 * cur + 1;
        }
        Collections.reverse(list);
        return list;
    }
}
