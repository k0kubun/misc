import java.util.*;

class Main {
    class BinaryHeap {
        private long[] heap;
        private int size;

        public BinaryHeap(int capacity) {
            this.heap = new long[capacity];
            this.size = 0;
        }

        public void dumpNodes() {
            for (int i = 0; i < this.size; i++) {
                System.out.printf("node %d: key = %d, ", i + 1, this.heap[i]);

                int parent = this.parentIndexOf(i);
                if (i > 0 && parent < this.size) {
                    System.out.printf("parent key = %d, ", this.heap[parent]);
                }

                int left = this.leftIndexOf(i);
                if (left < this.size) {
                    System.out.printf("left key = %d, ", this.heap[left]);
                }

                int right = this.rightIndexOf(i);
                if (right < this.size) {
                    System.out.printf("right key = %d, ", this.heap[right]);
                }

                System.out.print("\n");
            }
        }

        public void insert(long key) {
            this.heap[this.size] = key;
            this.size++;
        }

        private int parentIndexOf(int i) {
            return ((i + 1) / 2) - 1;
        }

        public int leftIndexOf(int i) {
            return ((i + 1) * 2) - 1;
        }

        public int rightIndexOf(int i) {
            return (i + 1) * 2;
        }
    }

    public static void main(String[] args) {
        new Main().main();
    }

    public void main() {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        String[] line = scanner.nextLine().split(" ", n);

        BinaryHeap heap = new BinaryHeap(n);
        for (int i = 0; i < n; i++) {
            long a = Long.parseLong(line[i]);
            heap.insert(a);
        }

        heap.dumpNodes();
    }
}
