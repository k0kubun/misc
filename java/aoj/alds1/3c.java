import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class LinkedList {
    int val;
    private LinkedList prev;
    LinkedList next;

    public LinkedList(int val) {
        this.prev = null;
        this.val = val;
        this.next = null;
    }

    public LinkedList insert(int val) {
        LinkedList next = new LinkedList(val);
        next.next = this;
        this.prev = next;
        return next;
    }

    public LinkedList delete(int val) {
        if (this.val == val) {
            this.next.prev = null;
            return this.next;
        }

        LinkedList node = this;
        while (node != null && node.val != val) {
            node = node.next;
        }
        if (node != null) {
            node.prev.next = node.next;
            if (node.next != null) {
                node.next.prev = node.prev;
            }
        }
        return this;
    }

    public LinkedList deleteFirst() {
        LinkedList next = this.next;
        if (next != null) {
            next.prev = null;
        }
        return next;
    }

    public LinkedList deleteLast() {
        if (this.next == null && this.prev == null) {
            return null;
        }

        LinkedList last = this;
        while (last.next != null) {
            last = last.next;
        }
        if (last.prev != null) {
            last.prev.next = null;
        }
        return this;
    }
}

class Main {
    public static void main(String[] args) throws IOException {
        InputStreamReader streamReader = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(streamReader);

        int n = Integer.parseInt(reader.readLine());
        String[] commands = new String[n];
        int[] numbers = new int[n];

        for (int i = 0; i < n; i++) {
            String[] line = reader.readLine().split(" ", 2);
            commands[i] = line[0];
            if (line.length > 1) {
                numbers[i] = Integer.parseInt(line[1]);
            }
        }

        LinkedList head = null;
        for (int i = 0; i < n; i++) {
            switch (commands[i]) {
                case "insert":
                    if (head == null) {
                        head = new LinkedList(numbers[i]);
                        continue;
                    }
                    head = head.insert(numbers[i]);
                    break;
                case "delete":
                    head = head.delete(numbers[i]);
                    break;
                case "deleteFirst":
                    head = head.deleteFirst();
                    break;
                case "deleteLast":
                    head = head.deleteLast();
                    break;
                default:
                    break;
            }
        }

        if (head != null) {
            System.out.print(head.val);
            head = head.next;
            while (head != null) {
                System.out.print(" ");
                System.out.print(head.val);
                head = head.next;
            }
            System.out.print("\n");
        }
    }
}
