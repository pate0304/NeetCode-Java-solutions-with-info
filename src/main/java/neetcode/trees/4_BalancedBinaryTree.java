package neetcode.trees;

import java.util.LinkedList;
import java.util.Queue;

/**
 * NeetCode Problem 4 (Trees): Balanced Binary Tree
 * 
 * Problem Description:
 * Given a binary tree, determine if it is height-balanced.
 * 
 * A height-balanced binary tree is defined as a binary tree in which the depth of the two subtrees of every node
 * never differs by more than one.
 * 
 * Examples:
 * Input: root = [3,9,20,null,null,15,7]
 * Output: true
 * 
 * Input: root = [1,2,2,3,3,null,null,4,4]
 * Output: false
 * 
 * Input: root = []
 * Output: true
 * 
 * Constraints:
 * - The number of nodes in the tree is in the range [0, 5000].
 * - -10^4 <= Node.val <= 10^4
 * 
 * Approach:
 * We can solve this problem using a recursive approach:
 * 
 * 1. Top-down approach (less efficient):
 *    - For each node, calculate the height of its left and right subtrees.
 *    - Check if the difference in heights is at most 1.
 *    - Recursively check if both subtrees are balanced.
 *    - Time Complexity: O(n^2) in the worst case.
 * 
 * 2. Bottom-up approach (more efficient):
 *    - Use a modified height function that returns -1 if the tree is unbalanced.
 *    - For each node, calculate the height of its left and right subtrees.
 *    - If either subtree is unbalanced or the height difference is more than 1, return -1.
 *    - Otherwise, return the height of the current subtree.
 *    - Time Complexity: O(n) as we visit each node once.
 * 
 * We'll implement both approaches for comparison.
 */
public class BalancedBinaryTree {
    
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
     * Determines if a binary tree is height-balanced using the top-down approach.
     * 
     * @param root The root of the binary tree
     * @return true if the tree is height-balanced, false otherwise
     */
    public boolean isBalanced(TreeNode root) {
        // Base case: empty tree is balanced
        if (root == null) {
            return true;
        }
        
        // Check if the current node is balanced
        int leftHeight = height(root.left);
        int rightHeight = height(root.right);
        
        if (Math.abs(leftHeight - rightHeight) > 1) {
            return false;
        }
        
        // Recursively check if both subtrees are balanced
        return isBalanced(root.left) && isBalanced(root.right);
    }
    
    /**
     * Helper method to calculate the height of a binary tree.
     * 
     * @param node The root of the binary tree
     * @return The height of the binary tree
     */
    private int height(TreeNode node) {
        // Base case: empty tree has height 0
        if (node == null) {
            return 0;
        }
        
        // Calculate the height of left and right subtrees
        int leftHeight = height(node.left);
        int rightHeight = height(node.right);
        
        // Return the height of the current subtree
        return 1 + Math.max(leftHeight, rightHeight);
    }
    
    /**
     * Determines if a binary tree is height-balanced using the bottom-up approach.
     * 
     * @param root The root of the binary tree
     * @return true if the tree is height-balanced, false otherwise
     */
    public boolean isBalancedBottomUp(TreeNode root) {
        // If the height is not -1, the tree is balanced
        return heightBottomUp(root) != -1;
    }
    
    /**
     * Helper method for the bottom-up approach.
     * Returns -1 if the tree is unbalanced, otherwise returns the height.
     * 
     * @param node The root of the binary tree
     * @return The height of the binary tree, or -1 if it's unbalanced
     */
    private int heightBottomUp(TreeNode node) {
        // Base case: empty tree has height 0
        if (node == null) {
            return 0;
        }
        
        // Calculate the height of left and right subtrees
        int leftHeight = heightBottomUp(node.left);
        int rightHeight = heightBottomUp(node.right);
        
        // If either subtree is unbalanced or the current node is unbalanced, return -1
        if (leftHeight == -1 || rightHeight == -1 || Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }
        
        // Return the height of the current subtree
        return 1 + Math.max(leftHeight, rightHeight);
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
        BalancedBinaryTree solution = new BalancedBinaryTree();
        
        // Example 1: [3,9,20,null,null,15,7]
        Integer[] values1 = {3, 9, 20, null, null, 15, 7};
        TreeNode root1 = createTree(values1);
        System.out.println("Example 1:");
        System.out.print("Tree: ");
        printTree(root1);
        
        boolean isBalanced1 = solution.isBalanced(root1);
        System.out.println("Is balanced (top-down): " + isBalanced1);
        
        boolean isBalanced1BottomUp = solution.isBalancedBottomUp(root1);
        System.out.println("Is balanced (bottom-up): " + isBalanced1BottomUp);
        
        // Example 2: [1,2,2,3,3,null,null,4,4]
        Integer[] values2 = {1, 2, 2, 3, 3, null, null, 4, 4};
        TreeNode root2 = createTree(values2);
        System.out.println("\nExample 2:");
        System.out.print("Tree: ");
        printTree(root2);
        
        boolean isBalanced2 = solution.isBalanced(root2);
        System.out.println("Is balanced (top-down): " + isBalanced2);
        
        boolean isBalanced2BottomUp = solution.isBalancedBottomUp(root2);
        System.out.println("Is balanced (bottom-up): " + isBalanced2BottomUp);
        
        // Example 3: []
        TreeNode root3 = null;
        System.out.println("\nExample 3:");
        System.out.print("Tree: ");
        printTree(root3);
        
        boolean isBalanced3 = solution.isBalanced(root3);
        System.out.println("Is balanced (top-down): " + isBalanced3);
        
        // Let's trace through the execution of Example 1 using the bottom-up approach:
        System.out.println("\nTracing bottom-up approach for Example 1:");
        System.out.println("Tree: [3, 9, 20, null, null, 15, 7]");
        
        System.out.println("1. Call heightBottomUp(3):");
        System.out.println("   - Call heightBottomUp(9) for left subtree");
        System.out.println("     - Call heightBottomUp(null) for left subtree of 9, returns 0");
        System.out.println("     - Call heightBottomUp(null) for right subtree of 9, returns 0");
        System.out.println("     - |0 - 0| <= 1, so return 1 + max(0, 0) = 1");
        System.out.println("   - Call heightBottomUp(20) for right subtree");
        System.out.println("     - Call heightBottomUp(15) for left subtree of 20");
        System.out.println("       - Call heightBottomUp(null) for left subtree of 15, returns 0");
        System.out.println("       - Call heightBottomUp(null) for right subtree of 15, returns 0");
        System.out.println("       - |0 - 0| <= 1, so return 1 + max(0, 0) = 1");
        System.out.println("     - Call heightBottomUp(7) for right subtree of 20");
        System.out.println("       - Call heightBottomUp(null) for left subtree of 7, returns 0");
        System.out.println("       - Call heightBottomUp(null) for right subtree of 7, returns 0");
        System.out.println("       - |0 - 0| <= 1, so return 1 + max(0, 0) = 1");
        System.out.println("     - |1 - 1| <= 1, so return 1 + max(1, 1) = 2");
        System.out.println("   - |1 - 2| <= 1, so return 1 + max(1, 2) = 3");
        
        System.out.println("2. heightBottomUp(3) returns 3, which is not -1, so the tree is balanced.");
        
        // Let's trace through the execution of Example 2 using the bottom-up approach:
        System.out.println("\nTracing bottom-up approach for Example 2:");
        System.out.println("Tree: [1, 2, 2, 3, 3, null, null, 4, 4]");
        
        System.out.println("1. Call heightBottomUp(1):");
        System.out.println("   - Call heightBottomUp(2) for left subtree");
        System.out.println("     - Call heightBottomUp(3) for left subtree of 2");
        System.out.println("       - Call heightBottomUp(4) for left subtree of 3");
        System.out.println("         - Call heightBottomUp(null) for left subtree of 4, returns 0");
        System.out.println("         - Call heightBottomUp(null) for right subtree of 4, returns 0");
        System.out.println("         - |0 - 0| <= 1, so return 1 + max(0, 0) = 1");
        System.out.println("       - Call heightBottomUp(4) for right subtree of 3");
        System.out.println("         - Call heightBottomUp(null) for left subtree of 4, returns 0");
        System.out.println("         - Call heightBottomUp(null) for right subtree of 4, returns 0");
        System.out.println("         - |0 - 0| <= 1, so return 1 + max(0, 0) = 1");
        System.out.println("       - |1 - 1| <= 1, so return 1 + max(1, 1) = 2");
        System.out.println("     - Call heightBottomUp(3) for right subtree of 2");
        System.out.println("       - Call heightBottomUp(null) for left subtree of 3, returns 0");
        System.out.println("       - Call heightBottomUp(null) for right subtree of 3, returns 0");
        System.out.println("       - |0 - 0| <= 1, so return 1 + max(0, 0) = 1");
        System.out.println("     - |2 - 1| <= 1, so return 1 + max(2, 1) = 3");
        System.out.println("   - Call heightBottomUp(2) for right subtree");
        System.out.println("     - Call heightBottomUp(null) for left subtree of 2, returns 0");
        System.out.println("     - Call heightBottomUp(null) for right subtree of 2, returns 0");
        System.out.println("     - |0 - 0| <= 1, so return 1 + max(0, 0) = 1");
        System.out.println("   - |3 - 1| > 1, so return -1 (unbalanced)");
        
        System.out.println("2. heightBottomUp(1) returns -1, so the tree is not balanced.");
    }
}
