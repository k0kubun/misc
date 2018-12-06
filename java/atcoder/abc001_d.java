import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        boolean[][] timeRange = new boolean[25][12];

        for (int i = 0; i < n; i++) {
            String[] line = scanner.nextLine().split("-", 2);
            int startHour = Integer.parseInt(line[0].substring(0, 2));
            int startMin = Integer.parseInt(line[0].substring(2, 4)) / 5;

            int endHour = Integer.parseInt(line[1].substring(0, 2));
            int endMin = Integer.parseInt(line[1].substring(2, 4));
            if (endMin % 5 == 0) {
                endMin = endMin / 5;
            } else {
                endMin = endMin / 5 + 1;
            }

            for (int hour = startHour; hour <= endHour; hour++) {
                for (int min = 0; min <= 11; min++) {
                    if (startHour == endHour) {
                        if (startMin <= min && min < endMin) {
                            timeRange[hour][min] = true;
                        }
                    } else if ((startHour < hour && hour < endHour)
                            || (startHour == hour && startMin <= min)
                            || (endHour == hour && min < endMin)) {
                        timeRange[hour][min] = true;
                    }
                }
            }
            System.gc();
        }

        boolean started = false;
        for (int hour = 0; hour <= 24; hour++) {
            for (int min = 0; min < 12; min++) {
                if (!started && timeRange[hour][min]) {
                    System.out.printf("%02d%02d-", hour, min * 5);
                    started = true;
                } else if (started && !timeRange[hour][min]) {
                    System.out.printf("%02d%02d\n", hour, min * 5);
                    started = false;
                }
            }
        }
    }
}
