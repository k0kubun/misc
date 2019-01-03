import java.util.*;

class Main {
    class Node {
        int id;
        Node left;
        Node right;
        Node parent;

        public Node(int id) {
            this.id = id;
        }

        public int parent() {
            if (this.parent == null) {
                return -1;
            } else {
                return this.parent.id;
            }
        }

        public void setLeft(Node left) {
            this.left = left;
            left.parent = this;
        }

        public void setRight(Node right) {
            this.right = right;
            right.parent = this;
        }

        public int depth() {
            if (this.parent == null) {
                return 0;
            } else {
                return this.parent.depth() + 1;
            }
        }

        public int height() {
            int leftHeight = 0;
            if (this.left != null) {
                leftHeight = this.left.height() + 1;
            }
            int rightHeight = 0;
            if (this.right != null) {
                rightHeight = this.right.height() + 1;
            }
            return Math.max(leftHeight, rightHeight);
        }

        public int degree() {
            int degree = 0;
            if (this.left != null) degree++;
            if (this.right != null) degree++;
            return degree;
        }

        public int sibling() {
            if (this.parent == null) {
                return -1;
            }

            Node parent = this.parent;
            if (parent.left == this) {
                if (parent.right == null) {
                    return -1;
                }
                return parent.right.id;
            } else {
                if (parent.left == null) {
                    return -1;
                }
                return parent.left.id;
            }
        }

        public String type() {
            if (this.parent == null) {
                return "root";
            } else if (this.left == null && this.right == null) {
                return "leaf";
            } else {
                return "internal node";
            }
        }
    }

    public static void main(String[] args) {
        new Main().main();
    }

    public void main() {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(i);
        }

        for (int i = 0; i < n; i++) {
            String[] line = scanner.nextLine().split(" ", -1);
            int id = Integer.parseInt(line[0]);
            int left = Integer.parseInt(line[1]);
            int right = Integer.parseInt(line[2]);

            Node node = nodes[id];
            if (left != -1) {
                node.setLeft(nodes[left]);
            }
            if (right != -1) {
                node.setRight(nodes[right]);
            }
        }

        for (Node node : nodes) {
            System.out.printf("node %d: parent = %d, sibling = %d, degree = %d, depth = %d, height = %d, %s\n",
                    node.id, node.parent(), node.sibling(), node.degree(), node.depth(), node.height(), node.type());
        }
    }
}
