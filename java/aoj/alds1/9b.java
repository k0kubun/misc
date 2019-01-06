import java.util.*;

class Main {
    class BinaryHeap {
        private long[] heap;
        private int size;

        public BinaryHeap(int capacity) {
            this.heap = new long[capacity];
            this.size = 0;
        }

        public void insert(long key) {
            this.heap[this.size] = key;
            this.size++;
        }

        public void dumpKeys() {
            for (int i = 0; i < this.size; i++) {
                System.out.printf(" %d", this.heap[i]);
            }
            System.out.print("\n");
        }

        public void buildMaxHeap() {
            for (int i = this.size / 2; i >= 0; i--) {
                this.maxHeapify(i);
            }
        }

        private void maxHeapify(int i) {
            int largest = i;

            int l = this.leftIndexOf(i);
            if (l < this.size && this.heap[l] > this.heap[largest]) {
                largest = l;
            }

            int r = this.rightIndexOf(i);
            if (r < this.size && this.heap[r] > this.heap[largest]) {
                largest = r;
            }

            if (largest != i) {
                long tmp = this.heap[i];
                this.heap[i] = this.heap[largest];
                this.heap[largest] = tmp;
                this.maxHeapify(largest);
            }
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
            long key = Long.parseLong(line[i]);
            heap.insert(key);
        }

        heap.buildMaxHeap();
        heap.dumpKeys();
    }
}
