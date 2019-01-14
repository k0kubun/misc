import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        int[] vs = new int[n];

        String[] line = scanner.nextLine().split(" ", -1);
        for (int i = 0; i < n; i++) {
            vs[i] = Integer.parseInt(line[i]);
        }

        int v1max = 0;
        int[] v1count = new int[100001];
        int v2max = 0;
        int[] v2count = new int[100001];

        for (int i = 0; i < n / 2; i++) {
            int v = vs[i * 2];
            if (v > v1max) {
                v1max = v;
            }
            v1count[v]++;
        }
        for (int i = 0; i < (n / 2); i++) {
            int v = vs[i * 2 + 1];
            if (v > v2max) {
                v2max = v;
            }
            v2count[v]++;
        }

        int v1top1num = 0;
        int v1top1count = 0;
        int v1top2num = 0;
        int v1top2count = 0;
        for (int i = 0; i <= v1max; i++) {
            if (v1count[i] > v1top1count) {
                v1top1num = i;
                v1top1count = v1count[i];
            }
        }
        for (int i = 0; i <= v1max; i++) {
            if (i != v1top1num && v1count[i] > v1top2count) {
                v1top2num = i;
                v1top2count = v1count[i];
            }
        }

        int v2top1num = 0;
        int v2top1count = 0;
        int v2top2num = 0;
        int v2top2count = 0;
        for (int i = 0; i <= v2max; i++) {
            if (v2count[i] > v2top1count) {
                v2top1num = i;
                v2top1count = v2count[i];
            }
        }
        for (int i = 0; i <= v2max; i++) {
            if (i != v2top1num && v2count[i] > v2top2count) {
                v2top2num = i;
                v2top2count = v2count[i];
            }
        }

        int n2 = n / 2;
        if (v1top1num == v2top1num) {
            int a = (n2 - v1top1count) + (n2 - v2top2count);
            int b = (n2 - v1top2count) + (n2 - v2top1count);
            System.out.println(Math.min(a, b));
        }
        else {
            System.out.println((n2 - v1top1count) + (n2 - v2top1count));
        }
    }
}
