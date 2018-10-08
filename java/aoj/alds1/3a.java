import java.util.Scanner;

class Stack {
    int stack[];
    int valNum;

    public Stack() {
        this.stack = new int[1000];
        this.valNum = 0;
    }

    public void push(int num) {
        this.stack[this.valNum++] = num;
    }

    public Integer pop() {
        return this.stack[--this.valNum];
    }
}

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] tokens = scanner.nextLine().split(" ", -1);

        Stack stack = new Stack();
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
