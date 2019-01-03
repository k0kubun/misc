import java.util.*;

class Main {
    class Node {
        int id;
        Node parent;
        Node[] children;

        public Node(int id) {
            this.id = id;
            this.parent = null;
            this.children = null;
        }

        public void setChildren(Node[] children) {
            this.children = children;
            for (Node child : children) {
                child.parent = this;
            }
        }

        public int depth() {
            if (this.parent == null) {
                return 0;
            } else {
                return this.parent.depth() + 1;
            }
        }

        public String type() {
            if (this.parent == null) {
                return "root";
            } else if (this.children.length == 0) {
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
            Node node = nodes[Integer.parseInt(line[0])];
            int numChild = Integer.parseInt(line[1]);

            Node[] children = new Node[numChild];
            for (int j = 2; j < 2 + numChild; j++) {
                children[j - 2] = nodes[Integer.parseInt(line[j])];
            }
            node.setChildren(children);
        }

        for (int i = 0; i < n; i++) {
            Node node = nodes[i];
            int parent = (node.parent != null ? node.parent.id : -1);
            System.out.printf("node %d: parent = %d, depth = %d, %s, [", node.id, parent, node.depth(), node.type());
            for (int j = 0; j < node.children.length; j++) {
                System.out.print(node.children[j].id);
                if (j != node.children.length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
        }
    }
}
