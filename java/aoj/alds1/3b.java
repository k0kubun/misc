import java.util.Scanner;

class Queue {
    int[] data;
    int maxLen;
    int lastEnqueue;
    int queueSize;

    public Queue(int maxLen) {
        this.data = new int[maxLen];
        this.maxLen = maxLen;
        this.lastEnqueue = maxLen - 1;
        this.queueSize = 0;
    }

    public void enqueue(int num) {
        this.queueSize++;
        this.lastEnqueue++;
        if (this.lastEnqueue >= this.maxLen) {
            this.lastEnqueue = 0;
        }
        this.data[this.lastEnqueue] = num;
    }

    public int dequeue() {
        if (this.queueSize == 0) {
            return -1;
        }

        int dequeueIndex = this.lastEnqueue - this.queueSize + 1;
        if (dequeueIndex < 0) {
            dequeueIndex += this.maxLen;
        }
        int num = this.data[dequeueIndex];
        this.queueSize--;
        return num;
    }
}

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] nq = scanner.nextLine().split(" ", 2);
        int n = Integer.parseInt(nq[0]); // num processes
        int q = Integer.parseInt(nq[1]); // quoantum number

        String[] names = new String[n];
        int[] times = new int[n];
        for (int i = 0; i < n; i++) {
            String[] nameTime = scanner.nextLine().split(" ", 2);
            names[i] = nameTime[0];
            times[i] = Integer.parseInt(nameTime[1]);
        }

        new Main().printProcessFinishes(n, q, names, times);
    }

    private void printProcessFinishes(int n, int q, String[] names, int[] times) {
        int now = 0;
        Queue queue = new Queue(n);
        for (int i = 0; i < n; i++) {
            queue.enqueue(i);
        }

        int dequeued;
        while ((dequeued = queue.dequeue()) >= 0) {
            if (times[dequeued] <= q) {
                now += times[dequeued];
                System.out.print(names[dequeued]);
                System.out.print(" ");
                System.out.println(now);
            } else {
                now += q;
                times[dequeued] -= q;
                queue.enqueue(dequeued);
            }
        }
    }
}
