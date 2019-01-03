import java.util.*;

class Main {
    class Node {
        int key;
        Node left;
        Node right;

        public Node(int key) {
            this.key = key;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public void setRight(Node right) {
            this.right = right;
        }
    }

    class BinaryTree {
        Node root;

        public BinaryTree() {
            this.root = null;
        }

        public void insert(Node z) {
            Node y = null; // x's parent
            Node x = this.root;
            while (x != null) {
                y = x;
                if (z.key < x.key) {
                    x = x.left;
                } else {
                    x = x.right;
                }
            }

            if (y == null) {
                this.root = z;
            } else if (z.key < y.key) {
                y.setLeft(z);
            } else {
                y.setRight(z);
            }
        }
    }

    public static void main(String[] args) {
        new Main().main();
    }

    public void main() {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());

        BinaryTree tree = new BinaryTree();
        for (int i = 0; i < n; i++) {
            String[] line = scanner.nextLine().split(" ", 2);
            if (line[0].equals("insert")) {
                Node node = new Node(Integer.parseInt(line[1]));
                tree.insert(node);
            } else if (line[0].equals("print")) {
                this.inorder(tree.root);
                System.out.print("\n");
                this.preorder(tree.root);
                System.out.print("\n");
            } else {
                throw new RuntimeException("Unexpected operation: " + line[0]);
            }
        }
    }

    private void inorder(Node node) {
        if (node.left != null) {
            this.inorder(node.left);
        }
        this.printNode(node);
        if (node.right != null) {
            this.inorder(node.right);
        }
    }

    private void preorder(Node node) {
        this.printNode(node);
        if (node.left != null) {
            this.preorder(node.left);
        }
        if (node.right != null) {
            this.preorder(node.right);
        }
    }

    private void printNode(Node node) {
        System.out.printf(" %d", node.key);
    }
}
