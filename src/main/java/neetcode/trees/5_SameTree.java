package neetcode.trees;

import java.util.LinkedList;
import java.util.Queue;

/**
 * NeetCode Problem 5 (Trees): Same Tree
 * 
 * Problem Description:
 * Given the roots of two binary trees p and q, write a function to check if they are the same or not.
 * 
 * Two binary trees are considered the same if they are structurally identical, and the nodes have the same value.
 * 
 * Examples:
 * Input: p = [1,2,3], q = [1,2,3]
 * Output: true
 * 
 * Input: p = [1,2], q = [1,null,2]
 * Output: false
 * 
 * Input: p = [1,2,1], q = [1,1,2]
 * Output: false
 * 
 * Constraints:
 * - The number of nodes in both trees is in the range [0, 100].
 * - -10^4 <= Node.val <= 10^4
 * 
 * Approach:
 * We can solve this problem using both recursive and iterative approaches:
 * 
 * 1. Recursive Approach:
 *    - If both trees are null, return true.
 *    - If one tree is null and the other is not, return false.
 *    - If the values of the current nodes are different, return false.
 *    - Recursively check if the left subtrees are the same and if the right subtrees are the same.
 * 
 * 2. Iterative Approach:
 *    - Use a queue to perform a level-order traversal of both trees simultaneously.
 *    - At each step, compare the values of the corresponding nodes.
 *    - If there's a mismatch in structure or values, return false.
 * 
 * Time Complexity: O(n) where n is the number of nodes in the smaller tree
 * Space Complexity: O(h) for the recursive approach where h is the height of the tree
 *                  O(n) for the iterative approach in the worst case
 */
public class SameTree {
    
    /**
     * Definition for a binary tree node.
     */
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        
        TreeNode() {}
        
        TreeNode(int val) {
            this.val = val;
        }
        
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    
    /**
     * Checks if two binary trees are the same using recursion.
     * 
     * @param p The root of the first binary tree
     * @param q The root of the second binary tree
     * @return true if the trees are the same, false otherwise
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        // If both trees are null, they are the same
        if (p == null && q == null) {
            return true;
        }
        
        // If one tree is null and the other is not, they are different
        if (p == null || q == null) {
            return false;
        }
        
        // If the values of the current nodes are different, the trees are different
        if (p.val != q.val) {
            return false;
        }
        
        // Recursively check if the left subtrees are the same and if the right subtrees are the same
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
    
    /**
     * Checks if two binary trees are the same using iteration.
     * 
     * @param p The root of the first binary tree
     * @param q The root of the second binary tree
     * @return true if the trees are the same, false otherwise
     */
    public boolean isSameTreeIterative(TreeNode p, TreeNode q) {
        // If both trees are null, they are the same
        if (p == null && q == null) {
            return true;
        }
        
        // If one tree is null and the other is not, they are different
        if (p == null || q == null) {
            return false;
        }
        
        // Use a queue to perform level-order traversal
        Queue<TreeNode> queueP = new LinkedList<>();
        Queue<TreeNode> queueQ = new LinkedList<>();
        
        queueP.offer(p);
        queueQ.offer(q);
        
        while (!queueP.isEmpty() && !queueQ.isEmpty()) {
            TreeNode nodeP = queueP.poll();
            TreeNode nodeQ = queueQ.poll();
            
            // If the values are different, the trees are different
            if (nodeP.val != nodeQ.val) {
                return false;
            }
            
            // Check the left children
            if (nodeP.left != null && nodeQ.left != null) {
                queueP.offer(nodeP.left);
                queueQ.offer(nodeQ.left);
            } else if (nodeP.left != null || nodeQ.left != null) {
                // If one has a left child and the other doesn't, they are different
                return false;
            }
            
            // Check the right children
            if (nodeP.right != null && nodeQ.right != null) {
                queueP.offer(nodeP.right);
                queueQ.offer(nodeQ.right);
            } else if (nodeP.right != null || nodeQ.right != null) {
                // If one has a right child and the other doesn't, they are different
                return false;
            }
        }
        
        // If we've processed all nodes and found no differences, the trees are the same
        return true;
    }
    
    /**
     * Helper method to create a binary tree from a level-order traversal array.
     * Null values in the array represent null nodes.
     */
    private static TreeNode createTree(Integer[] values) {
        if (values == null || values.length == 0 || values[0] == null) {
            return null;
        }
        
        TreeNode root = new TreeNode(values[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        for (int i = 1; i < values.length; i += 2) {
            TreeNode current = queue.poll();
            
            if (i < values.length && values[i] != null) {
                current.left = new TreeNode(values[i]);
                queue.offer(current.left);
            }
            
            if (i + 1 < values.length && values[i + 1] != null) {
                current.right = new TreeNode(values[i + 1]);
                queue.offer(current.right);
            }
        }
        
        return root;
    }
    
    /**
     * Helper method to print a binary tree in a readable format.
     */
    private static void printTree(TreeNode root) {
        if (root == null) {
            System.out.println("[]");
            return;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        java.util.List<Integer> values = new java.util.ArrayList<>();
        
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            
            if (node == null) {
                values.add(null);
            } else {
                values.add(node.val);
                queue.offer(node.left);
                queue.offer(node.right);
            }
        }
        
        // Remove trailing nulls
        while (values.size() > 0 && values.get(values.size() - 1) == null) {
            values.remove(values.size() - 1);
        }
        
        // Print the tree
        System.out.print("[");
        for (int i = 0; i < values.size(); i++) {
            if (values.get(i) == null) {
                System.out.print("null");
            } else {
                System.out.print(values.get(i));
            }
            
            if (i < values.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        SameTree solution = new SameTree();
        
        // Example 1: p = [1,2,3], q = [1,2,3]
        Integer[] values1P = {1, 2, 3};
        Integer[] values1Q = {1, 2, 3};
        TreeNode p1 = createTree(values1P);
        TreeNode q1 = createTree(values1Q);
        
        System.out.println("Example 1:");
        System.out.print("Tree p: ");
        printTree(p1);
        System.out.print("Tree q: ");
        printTree(q1);
        
        boolean isSame1 = solution.isSameTree(p1, q1);
        System.out.println("Are the trees the same (recursive)? " + isSame1);
        
        boolean isSame1Iterative = solution.isSameTreeIterative(p1, q1);
        System.out.println("Are the trees the same (iterative)? " + isSame1Iterative);
        
        // Example 2: p = [1,2], q = [1,null,2]
        Integer[] values2P = {1, 2};
        Integer[] values2Q = {1, null, 2};
        TreeNode p2 = createTree(values2P);
        TreeNode q2 = createTree(values2Q);
        
        System.out.println("\nExample 2:");
        System.out.print("Tree p: ");
        printTree(p2);
        System.out.print("Tree q: ");
        printTree(q2);
        
        boolean isSame2 = solution.isSameTree(p2, q2);
        System.out.println("Are the trees the same (recursive)? " + isSame2);
        
        // Example 3: p = [1,2,1], q = [1,1,2]
        Integer[] values3P = {1, 2, 1};
        Integer[] values3Q = {1, 1, 2};
        TreeNode p3 = createTree(values3P);
        TreeNode q3 = createTree(values3Q);
        
        System.out.println("\nExample 3:");
        System.out.print("Tree p: ");
        printTree(p3);
        System.out.print("Tree q: ");
        printTree(q3);
        
        boolean isSame3 = solution.isSameTree(p3, q3);
        System.out.println("Are the trees the same (recursive)? " + isSame3);
        
        // Let's trace through the execution of Example 1 using the recursive approach:
        System.out.println("\nTracing recursive approach for Example 1:");
        System.out.println("p = [1,2,3], q = [1,2,3]");
        
        System.out.println("1. Call isSameTree(p=1, q=1):");
        System.out.println("   - p and q are not null, and p.val = q.val = 1");
        System.out.println("   - Call isSameTree(p.left=2, q.left=2):");
        System.out.println("     - p.left and q.left are not null, and p.left.val = q.left.val = 2");
        System.out.println("     - Call isSameTree(p.left.left=null, q.left.left=null):");
        System.out.println("       - Both are null, return true");
        System.out.println("     - Call isSameTree(p.left.right=null, q.left.right=null):");
        System.out.println("       - Both are null, return true");
        System.out.println("     - Return true && true = true");
        System.out.println("   - Call isSameTree(p.right=3, q.right=3):");
        System.out.println("     - p.right and q.right are not null, and p.right.val = q.right.val = 3");
        System.out.println("     - Call isSameTree(p.right.left=null, q.right.left=null):");
        System.out.println("       - Both are null, return true");
        System.out.println("     - Call isSameTree(p.right.right=null, q.right.right=null):");
        System.out.println("       - Both are null, return true");
        System.out.println("     - Return true && true = true");
        System.out.println("   - Return true && true = true");
        
        System.out.println("2. Final result: true");
        
        // Let's trace through the execution of Example 2 using the recursive approach:
        System.out.println("\nTracing recursive approach for Example 2:");
        System.out.println("p = [1,2], q = [1,null,2]");
        
        System.out.println("1. Call isSameTree(p=1, q=1):");
        System.out.println("   - p and q are not null, and p.val = q.val = 1");
        System.out.println("   - Call isSameTree(p.left=2, q.left=null):");
        System.out.println("     - One is null and the other is not, return false");
        System.out.println("   - Return false && (not evaluated) = false");
        
        System.out.println("2. Final result: false");
    }
}
