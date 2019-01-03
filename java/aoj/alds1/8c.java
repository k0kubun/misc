import java.util.*;

class Main {
    class Node {
        int key;
        Node left;
        Node right;
        Node parent;

        public Node(int key) {
            this.key = key;
        }

        public void setLeft(Node left) {
            this.left = left;
            if (left != null) {
                left.parent = this;
            }
        }

        public void setRight(Node right) {
            this.right = right;
            if (right != null) {
                right.parent = this;
            }
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

        public Node find(int val) {
            return this.findInternal(val, this.root);
        }

        private Node findInternal(int val, Node node) {
            if (node.key == val) return node;
            Node n;
            if (node.left != null && (n = this.findInternal(val, node.left)) != null) return n;
            if (node.right != null && (n = this.findInternal(val, node.right)) != null) return n;
            return null;
        }

        public void delete(int val) {
            Node node = this.find(val);
            this.delete(node);
        }

        public void delete(Node node) {
            if (node == null) return;
            if (node.parent == null) {
                this.root = node;
                return;
            }

            if (node.left == null && node.right == null) {
                if (node.parent.left == node) {
                    node.parent.left = null;
                } else {
                    node.parent.setRight(null);
                }
            } else if (node.left == null) {
                if (node.parent.left == node) {
                    node.parent.setLeft(node.right);
                } else {
                    node.parent.setRight(node.right);
                }
            } else if (node.right == null) {
                if (node.parent.left == node) {
                    node.parent.setLeft(node.left);
                } else {
                    node.parent.setRight(node.left);
                }
            } else {
                node.key = node.right.key;
                this.delete(node.right);
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
            } else if (line[0].equals("find")) {
                if (tree.find(Integer.parseInt(line[1])) != null) {
                    System.out.println("yes");
                } else {
                    System.out.println("no");
                }
            } else if (line[0].equals("delete")) {
                tree.delete(Integer.parseInt(line[1]));
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
