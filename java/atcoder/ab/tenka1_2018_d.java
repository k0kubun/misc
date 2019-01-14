import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = Integer.parseInt(scanner.nextLine());

        int i;
        for (i = 1; i * (i + 1) < 2 * N; i++) {
            // none
        }
        if (i * (i + 1) == 2 * N) {
            System.out.println("Yes");
            new Main().printAnswer(N, i);
        } else {
            System.out.println("No");
        }
    }

    public void printAnswer(int N, int arrayLen) {
        System.out.println(arrayLen + 1);

        int[] usedCount = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            usedCount[i] = 0;
        }

        int[][] elems = new int[arrayLen + 1][arrayLen];
        boolean[] indexUsable = new boolean[arrayLen];

        for (int i = 0; i <= arrayLen; i++) { // arrayLen + 1 times
            System.out.print(arrayLen);
            System.out.print(" ");
            int size = 0;

            // reset indexUsable
            for (int j = 1; j < arrayLen; j++) {
                indexUsable[j] = true;
            }

            for (int j = 1; j <= N; j++) {
                if (usedCount[j] == 2) {
                    continue;
                }

                for (int k = 0; k < i; k++) {
                    if (indexUsable[k] == false) {
                        for (int g = 0; g < arrayLen; g++) {
                            if (elems[k][g] == j) {
                                continue;
                            }
                        }
                    }
                }

                // choose j as num

                System.out.print(j);
                elems[i][size] = j;
                usedCount[j]++;
                size++;

                // invalidate indexUsable
                for (int k = 0; k < i; k++) {
                    for (int l = 0; l < arrayLen; l++) {
                        if (elems[k][l] == j) {
                            indexUsable[k] = false;
                        }
                    }
                }

                if (size == arrayLen) {
                    System.out.println("");
                    break;
                } else {
                    System.out.print(" ");
                }
            }
        }
    }
}
