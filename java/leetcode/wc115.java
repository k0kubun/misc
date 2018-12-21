/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

class Main {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

class Solution {
    public boolean isCompleteTree(TreeNode root) {
        return this.treeMode(root) > 0;
    }

    // 0: incomplete node detected
    // 1: left node
    // 2: full node
    private int treeMode(TreeNode root) {
        if (root.left == null && root.right == null) {
            return 2;
        } else if (root.left != null && root.right == null) {
            return Math.min(this.treeMode(root.left), 1);
        } else if (root.left == null && root.right != null) {
            return 0;
        } else {
            int left = this.treeMode(root.left);
            int right = this.treeMode(root.right);
            if (left == 1) {
                return 0;
            } else {
                return Math.min(left, right);
            }
        }
    }
}
    public static void main(String[] args) {
        new Main().main();
    }

    public void main() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(4);

        root.right = new TreeNode(3);
        root.right.right = new TreeNode(6);

        System.out.println(new Solution().isCompleteTree(root));
    }
}
