import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] line = scanner.nextLine().split("/", 3);
        int year = Integer.parseInt(line[0]);
        int month = Integer.parseInt(line[1]);
        int day = Integer.parseInt(line[2]);
        if (year <= 2019 && (month <= 3 || (month == 4 && day <= 30))) {
            System.out.println("Heisei");
        } else {
            System.out.println("TBD");
        }
    }
}
