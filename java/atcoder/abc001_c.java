import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] line = scanner.nextLine().split(" ", 2);
        int degree = Integer.parseInt(line[0]); // 風向 Deg
        int distance = Integer.parseInt(line[1]); // 1分間の風程 Dis

        Main main = new Main();
        int w = main.windSpeed(distance);
        System.out.printf("%s %d\n", main.direction(degree, w), w);
    }

    private int windSpeed(int distance) {
        int speed = (distance * 100) / 60;
        if (speed % 10 >= 5) {
            speed += 10;
        }
        speed /= 10;

        if (speed <= 2) {
            return 0;
        } else if (speed <= 15) {
            return 1;
        } else if (speed <= 33) {
            return 2;
        } else if (speed <= 54) {
            return 3;
        } else if (speed <= 79) {
            return 4;
        } else if (speed <= 107) {
            return 5;
        } else if (speed <= 138) {
            return 6;
        } else if (speed <= 171) {
            return 7;
        } else if (speed <= 207) {
            return 8;
        } else if (speed <= 244) {
            return 9;
        } else if (speed <= 284) {
            return 10;
        } else if (speed <= 326) {
            return 11;
        } else {
            return 12;
        }
    }

    private String direction(int degree, int w) {
        if (w == 0) {
            return "C";
        } else if (113 <= degree && degree <= 337) {
            return "NNE";
        } else if (338 <= degree && degree <= 562) {
            return "NE";
        } else if (563 <= degree && degree <= 787) {
            return "ENE";
        } else if (758 <= degree && degree <= 1012) {
            return "E";
        } else if (1013 <= degree && degree <= 1237) {
            return "ESE";
        } else if (1238 <= degree && degree <= 1462) {
            return "SE";
        } else if (1463 <= degree && degree <= 1687) {
            return "SSE";
        } else if (1688 <= degree && degree <= 1912) {
            return "S";
        } else if (1913 <= degree && degree <= 2137) {
            return "SSW";
        } else if (2138 <= degree && degree <= 2362) {
            return "SW";
        } else if (2363 <= degree && degree <= 2587) {
            return "WSW";
        } else if (2588 <= degree && degree <= 2812) {
            return "W";
        } else if (2813 <= degree && degree <= 3037) {
            return "WNW";
        } else if (3038 <= degree && degree <= 3262) {
            return "NW";
        } else if (3263 <= degree && degree <= 3487) {
            return "NNW";
        } else {
            return "N";
        }
    }
}
