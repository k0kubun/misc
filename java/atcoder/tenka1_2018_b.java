import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line[] = scanner.nextLine().split(" ", -1);
        int A = Integer.parseInt(line[0]);
        int B = Integer.parseInt(line[1]);
        int K = Integer.parseInt(line[2]);

        for (int i = 0; i < K; i++) {
            if (i % 2 == 0) {
                // turn A
                if (A % 2 != 0) {
                    A--;
                }
                int giveA = A / 2;
                A -= giveA;
                B += giveA;
            } else {
                // turn B
                if (B % 2 != 0) {
                    B--;
                }
                int giveB = B / 2;
                A += giveB;
                B -= giveB;
            }
        }

        System.out.print(A);
        System.out.print(" ");
        System.out.println(B);
    }
}
