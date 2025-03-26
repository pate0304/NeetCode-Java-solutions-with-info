package neetcode.trees;

import java.util.LinkedList;
import java.util.Queue;

/**
 * NeetCode Problem 1 (Trees): Invert Binary Tree
 * 
 * Problem Description:
 * Given the root of a binary tree, invert the tree, and return its root.
 * To invert a binary tree, we swap the left and right children of every node in the tree.
 * 
 * Examples:
 * Input: root = [4,2,7,1,3,6,9]
 * Output: [4,7,2,9,6,3,1]
 * 
 * Input: root = [2,1,3]
 * Output: [2,3,1]
 * 
 * Input: root = []
 * Output: []
 * 
 * Constraints:
 * - The number of nodes in the tree is in the range [0, 100].
 * - -100 <= Node.val <= 100
 * 
 * Approach:
 * We can solve this problem using both recursive and iterative approaches:
 * 
 * 1. Recursive Approach:
 *    - Base case: If the root is null, return null.
 *    - Recursively invert the left and right subtrees.
 *    - Swap the left and right children of the current node.
 *    - Return the root.
 * 
 * 2. Iterative Approach (using a queue):
 *    - If the root is null, return null.
 *    - Initialize a queue and add the root to it.
 *    - While the queue is not empty:
 *      - Dequeue a node.
 *      - Swap its left and right children.
 *      - Enqueue the left and right children if they are not null.
 *    - Return the root.
 * 
 * Time Complexity: O(n) where n is the number of nodes in the tree
 * Space Complexity: O(h) for the recursive approach where h is the height of the tree
 *                  O(n) for the iterative approach in the worst case
 */
public class InvertBinaryTree {
    
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
     * Inverts a binary tree recursively.
     * 
     * @param root The root of the binary tree
     * @return The root of the inverted binary tree
     */
    public TreeNode invertTree(TreeNode root) {
        // Base case: empty tree or leaf node
        if (root == null) {
            return null;
        }
        
        // Recursively invert the left and right subtrees
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        
        // Swap the left and right children
        root.left = right;
        root.right = left;
        
        // Return the root
        return root;
    }
    
    /**
     * Inverts a binary tree iteratively using a queue.
     * 
     * @param root The root of the binary tree
     * @return The root of the inverted binary tree
     */
    public TreeNode invertTreeIterative(TreeNode root) {
        // Edge case: empty tree
        if (root == null) {
            return null;
        }
        
        // Initialize a queue for level-order traversal
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        // Process nodes level by level
        while (!queue.isEmpty()) {
            // Dequeue a node
            TreeNode node = queue.poll();
            
            // Swap its left and right children
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;
            
            // Enqueue the left and right children if they are not null
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        
        // Return the root
        return root;
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
            
            if (values[i] != null) {
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
     * Helper method to convert a binary tree to a level-order traversal array.
     */
    private static Integer[] treeToArray(TreeNode root) {
        if (root == null) {
            return new Integer[0];
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        java.util.List<Integer> result = new java.util.ArrayList<>();
        
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            
            if (node == null) {
                result.add(null);
            } else {
                result.add(node.val);
                queue.offer(node.left);
                queue.offer(node.right);
            }
        }
        
        // Remove trailing nulls
        while (result.size() > 0 && result.get(result.size() - 1) == null) {
            result.remove(result.size() - 1);
        }
        
        return result.toArray(new Integer[0]);
    }
    
    /**
     * Helper method to print a binary tree in a readable format.
     */
    private static void printTree(TreeNode root) {
        if (root == null) {
            System.out.println("[]");
            return;
        }
        
        Integer[] array = treeToArray(root);
        System.out.print("[");
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                System.out.print("null");
            } else {
                System.out.print(array[i]);
            }
            
            if (i < array.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        InvertBinaryTree solution = new InvertBinaryTree();
        
        // Example 1: [4,2,7,1,3,6,9]
        Integer[] values1 = {4, 2, 7, 1, 3, 6, 9};
        TreeNode root1 = createTree(values1);
        System.out.println("Example 1:");
        System.out.print("Original tree: ");
        printTree(root1);
        
        TreeNode inverted1 = solution.invertTree(root1);
        System.out.print("Inverted tree (recursive): ");
        printTree(inverted1);
        
        // Reset the tree for the iterative approach
        root1 = createTree(values1);
        TreeNode inverted1Iterative = solution.invertTreeIterative(root1);
        System.out.print("Inverted tree (iterative): ");
        printTree(inverted1Iterative);
        
        // Example 2: [2,1,3]
        Integer[] values2 = {2, 1, 3};
        TreeNode root2 = createTree(values2);
        System.out.println("\nExample 2:");
        System.out.print("Original tree: ");
        printTree(root2);
        
        TreeNode inverted2 = solution.invertTree(root2);
        System.out.print("Inverted tree (recursive): ");
        printTree(inverted2);
        
        // Example 3: []
        TreeNode root3 = null;
        System.out.println("\nExample 3:");
        System.out.print("Original tree: ");
        printTree(root3);
        
        TreeNode inverted3 = solution.invertTree(root3);
        System.out.print("Inverted tree (recursive): ");
        printTree(inverted3);
        
        // Let's trace through the execution of Example 1 using the recursive approach:
        System.out.println("\nTracing recursive approach for Example 1:");
        System.out.println("Original tree: [4, 2, 7, 1, 3, 6, 9]");
        
        System.out.println("1. Call invertTree(4):");
        System.out.println("   - Call invertTree(2) for left subtree");
        System.out.println("     - Call invertTree(1) for left subtree of 2");
        System.out.println("       - Both children are null, return 1");
        System.out.println("     - Call invertTree(3) for right subtree of 2");
        System.out.println("       - Both children are null, return 3");
        System.out.println("     - Swap children of 2: left = 3, right = 1");
        System.out.println("     - Return 2");
        System.out.println("   - Call invertTree(7) for right subtree");
        System.out.println("     - Call invertTree(6) for left subtree of 7");
        System.out.println("       - Both children are null, return 6");
        System.out.println("     - Call invertTree(9) for right subtree of 7");
        System.out.println("       - Both children are null, return 9");
        System.out.println("     - Swap children of 7: left = 9, right = 6");
        System.out.println("     - Return 7");
        System.out.println("   - Swap children of 4: left = 7, right = 2");
        System.out.println("   - Return 4");
        
        System.out.println("2. Final inverted tree: [4, 7, 2, 9, 6, 3, 1]");
    }
}
