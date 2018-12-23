import java.util.*;

class Main {
    public static void main(String[] args) {
        new Main().main();
    }

    public void main() {
        Scanner scanner = new Scanner(System.in);
        String[] xy = scanner.nextLine().split(" ", 2);
        int x = Integer.parseInt(xy[0]);
        int y = Integer.parseInt(xy[1]);
        if (this.group(x) == this.group(y)) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }

    public int group(int n) {
        switch (n) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 1;
            case 4:
            case 6:
            case 9:
            case 11:
                return 2;
            case 2:
                return 3;
            default:
                return 0;
        }
    }
}
