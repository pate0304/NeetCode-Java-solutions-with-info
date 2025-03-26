package neetcode.trees;

import java.util.LinkedList;
import java.util.Queue;

/**
 * NeetCode Problem 3 (Trees): Diameter of Binary Tree
 * 
 * Problem Description:
 * Given the root of a binary tree, return the length of the diameter of the tree.
 * 
 * The diameter of a binary tree is the length of the longest path between any two nodes in a tree.
 * This path may or may not pass through the root.
 * 
 * The length of a path between two nodes is represented by the number of edges between them.
 * 
 * Examples:
 * Input: root = [1,2,3,4,5]
 * Output: 3
 * Explanation: The diameter is the path [4,2,1,3] or [5,2,1,3], which has length 3.
 * 
 * Input: root = [1,2]
 * Output: 1
 * 
 * Constraints:
 * - The number of nodes in the tree is in the range [1, 10^4].
 * - -100 <= Node.val <= 100
 * 
 * Approach:
 * The key insight is that the diameter of a tree is the maximum of:
 * 1. The diameter of the left subtree
 * 2. The diameter of the right subtree
 * 3. The longest path that passes through the root (height of left subtree + height of right subtree)
 * 
 * We can solve this using a recursive approach:
 * 1. Define a helper function to calculate the height of a subtree.
 * 2. For each node, calculate the path length through it (left height + right height).
 * 3. Update the maximum diameter found so far.
 * 4. Return the height of the current subtree to the caller.
 * 
 * Time Complexity: O(n) where n is the number of nodes in the tree
 * Space Complexity: O(h) where h is the height of the tree (for the recursion stack)
 */
public class DiameterOfBinaryTree {
    
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
    
    // Variable to store the maximum diameter found so far
    private int maxDiameter;
    
    /**
     * Calculates the diameter of a binary tree.
     * 
     * @param root The root of the binary tree
     * @return The diameter of the binary tree
     */
    public int diameterOfBinaryTree(TreeNode root) {
        // Initialize the maximum diameter
        maxDiameter = 0;
        
        // Calculate the height of the tree and update maxDiameter
        height(root);
        
        // Return the maximum diameter found
        return maxDiameter;
    }
    
    /**
     * Helper method to calculate the height of a subtree and update maxDiameter.
     * 
     * @param node The root of the subtree
     * @return The height of the subtree
     */
    private int height(TreeNode node) {
        // Base case: empty subtree
        if (node == null) {
            return 0;
        }
        
        // Calculate the height of left and right subtrees
        int leftHeight = height(node.left);
        int rightHeight = height(node.right);
        
        // Update the maximum diameter
        // The path length through the current node is leftHeight + rightHeight
        maxDiameter = Math.max(maxDiameter, leftHeight + rightHeight);
        
        // Return the height of the current subtree
        return 1 + Math.max(leftHeight, rightHeight);
    }
    
    /**
     * Alternative implementation without using a class variable.
     */
    public int diameterOfBinaryTreeAlternative(TreeNode root) {
        // Use an array to store the maximum diameter
        // (using an array instead of an integer because we need to modify it in the recursive function)
        int[] maxDiameter = new int[1];
        
        // Calculate the height of the tree and update maxDiameter
        heightAlternative(root, maxDiameter);
        
        // Return the maximum diameter found
        return maxDiameter[0];
    }
    
    /**
     * Helper method for the alternative implementation.
     */
    private int heightAlternative(TreeNode node, int[] maxDiameter) {
        // Base case: empty subtree
        if (node == null) {
            return 0;
        }
        
        // Calculate the height of left and right subtrees
        int leftHeight = heightAlternative(node.left, maxDiameter);
        int rightHeight = heightAlternative(node.right, maxDiameter);
        
        // Update the maximum diameter
        maxDiameter[0] = Math.max(maxDiameter[0], leftHeight + rightHeight);
        
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
        DiameterOfBinaryTree solution = new DiameterOfBinaryTree();
        
        // Example 1: [1,2,3,4,5]
        Integer[] values1 = {1, 2, 3, 4, 5};
        TreeNode root1 = createTree(values1);
        System.out.println("Example 1:");
        System.out.print("Tree: ");
        printTree(root1);
        
        int diameter1 = solution.diameterOfBinaryTree(root1);
        System.out.println("Diameter: " + diameter1);
        
        int diameter1Alt = solution.diameterOfBinaryTreeAlternative(root1);
        System.out.println("Diameter (alternative): " + diameter1Alt);
        
        // Example 2: [1,2]
        Integer[] values2 = {1, 2};
        TreeNode root2 = createTree(values2);
        System.out.println("\nExample 2:");
        System.out.print("Tree: ");
        printTree(root2);
        
        int diameter2 = solution.diameterOfBinaryTree(root2);
        System.out.println("Diameter: " + diameter2);
        
        // Additional Example: [1,2,3,4,5,null,null,6,7,8,9]
        Integer[] values3 = {1, 2, 3, 4, 5, null, null, 6, 7, 8, 9};
        TreeNode root3 = createTree(values3);
        System.out.println("\nAdditional Example:");
        System.out.print("Tree: ");
        printTree(root3);
        
        int diameter3 = solution.diameterOfBinaryTree(root3);
        System.out.println("Diameter: " + diameter3);
        
        // Let's trace through the execution of Example 1:
        System.out.println("\nTracing execution for Example 1:");
        System.out.println("Tree: [1, 2, 3, 4, 5]");
        
        System.out.println("1. Call height(1):");
        System.out.println("   - Call height(2) for left subtree");
        System.out.println("     - Call height(4) for left subtree of 2");
        System.out.println("       - Call height(null) for left subtree of 4, returns 0");
        System.out.println("       - Call height(null) for right subtree of 4, returns 0");
        System.out.println("       - Update maxDiameter = max(0, 0 + 0) = 0");
        System.out.println("       - Return 1 + max(0, 0) = 1");
        System.out.println("     - Call height(5) for right subtree of 2");
        System.out.println("       - Call height(null) for left subtree of 5, returns 0");
        System.out.println("       - Call height(null) for right subtree of 5, returns 0");
        System.out.println("       - Update maxDiameter = max(0, 0 + 0) = 0");
        System.out.println("       - Return 1 + max(0, 0) = 1");
        System.out.println("     - Update maxDiameter = max(0, 1 + 1) = 2");
        System.out.println("     - Return 1 + max(1, 1) = 2");
        System.out.println("   - Call height(3) for right subtree");
        System.out.println("     - Call height(null) for left subtree of 3, returns 0");
        System.out.println("     - Call height(null) for right subtree of 3, returns 0");
        System.out.println("     - Update maxDiameter = max(2, 0 + 0) = 2");
        System.out.println("     - Return 1 + max(0, 0) = 1");
        System.out.println("   - Update maxDiameter = max(2, 2 + 1) = 3");
        System.out.println("   - Return 1 + max(2, 1) = 3");
        
        System.out.println("2. Final diameter: 3");
        
        // Visualize the longest path
        System.out.println("\nLongest path in Example 1: 4 -> 2 -> 1 -> 3 (or 5 -> 2 -> 1 -> 3)");
        System.out.println("This path has 3 edges, which is the diameter of the tree.");
    }
}
