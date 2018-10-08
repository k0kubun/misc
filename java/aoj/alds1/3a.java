import java.util.Scanner;
import java.util.Stack;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] tokens = scanner.nextLine().split(" ", -1);

        Stack<Integer> stack = new Stack<>();
        int lhs, rhs;
        for (String token : tokens) {
            switch (token) {
                case "+":
                    rhs = stack.pop();
                    lhs = stack.pop();
                    stack.push(lhs + rhs);
                    break;
                case "-":
                    rhs = stack.pop();
                    lhs = stack.pop();
                    stack.push(lhs - rhs);
                    break;
                case "*":
                    rhs = stack.pop();
                    lhs = stack.pop();
                    stack.push(lhs * rhs);
                    break;
                case "/":
                    rhs = stack.pop();
                    lhs = stack.pop();
                    stack.push(lhs / rhs);
                    break;
                default:
                    stack.push(Integer.parseInt(token));
                    break;
            }
        }
        System.out.println(stack.pop());
    }
}
