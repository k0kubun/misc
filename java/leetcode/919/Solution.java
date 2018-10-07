/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

class CBTInserter {
    private TreeNode root;

    public CBTInserter(TreeNode root) {
        this.root = root;
    }

    public int insert(int v) {
        return cursorInsert(this.root, v);
    }

    private int cursorInsert(TreeNode cursor, int v) {
        if (cursor.val <= v) {
            if (cursor.right == null) {
                cursor.right = new TreeNode(v);
                return cursor.val;
            } else {
                return cursorInsert(cursor.right, v);
            }
        } else {
            if (cursor.left == null) {
                cursor.left = new TreeNode(v);
                return cursor.val;
            } else {
                return cursorInsert(cursor.left, v);
            }
        }
    }

    public TreeNode get_root() {
        return this.root;
    }
}

public class Solution {
    public static void main(String args[]) {
        TreeNode root = new TreeNode(1);
        CBTInserter obj = new CBTInserter(root);
        obj.insert(2);
    }
}

/**
 * Your CBTInserter object will be instantiated and called as such:
 * CBTInserter obj = new CBTInserter(root);
 * int param_1 = obj.insert(v);
 * TreeNode param_2 = obj.get_root();
 */
