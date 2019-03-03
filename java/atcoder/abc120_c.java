import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();

        int answer = 0;
        Deque<Boolean> oneStack = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            boolean one = (s.charAt(i) == '1');
            if (oneStack.size() == 0) {
                oneStack.push(one);
            } else {
                boolean top = oneStack.pop();
                if (top != one) {
                    answer += 2;
                } else {
                    oneStack.push(top);
                    oneStack.push(one);
                }
            }
        }
        System.out.println(answer);
    }
}
