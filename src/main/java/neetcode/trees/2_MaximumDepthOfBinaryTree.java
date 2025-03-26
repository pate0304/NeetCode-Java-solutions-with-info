package neetcode.trees;

import java.util.LinkedList;
import java.util.Queue;

/**
 * NeetCode Problem 2 (Trees): Maximum Depth of Binary Tree
 * 
 * Problem Description:
 * Given the root of a binary tree, return its maximum depth.
 * 
 * A binary tree's maximum depth is the number of nodes along the longest path from the root node
 * down to the farthest leaf node.
 * 
 * Examples:
 * Input: root = [3,9,20,null,null,15,7]
 * Output: 3
 * 
 * Input: root = [1,null,2]
 * Output: 2
 * 
 * Constraints:
 * - The number of nodes in the tree is in the range [0, 10^4].
 * - -100 <= Node.val <= 100
 * 
 * Approach:
 * We can solve this problem using both recursive and iterative approaches:
 * 
 * 1. Recursive Approach (DFS):
 *    - Base case: If the root is null, return 0.
 *    - Recursively find the maximum depth of the left and right subtrees.
 *    - Return 1 + max(left depth, right depth).
 * 
 * 2. Iterative Approach (BFS):
 *    - If the root is null, return 0.
 *    - Use a queue to perform level-order traversal.
 *    - For each level, process all nodes at that level before moving to the next level.
 *    - Keep track of the number of levels processed.
 * 
 * 3. Iterative Approach (DFS):
 *    - If the root is null, return 0.
 *    - Use a stack to perform depth-first traversal.
 *    - Keep track of the current depth for each node.
 *    - Update the maximum depth as we traverse the tree.
 * 
 * Time Complexity: O(n) where n is the number of nodes in the tree
 * Space Complexity: O(h) for the recursive approach where h is the height of the tree
 *                  O(n) for the iterative approach in the worst case
 */
public class MaximumDepthOfBinaryTree {
    
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
     * Finds the maximum depth of a binary tree using recursion (DFS).
     * 
     * @param root The root of the binary tree
     * @return The maximum depth of the binary tree
     */
    public int maxDepth(TreeNode root) {
        // Base case: empty tree
        if (root == null) {
            return 0;
        }
        
        // Recursively find the maximum depth of the left and right subtrees
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        
        // Return 1 (for the current node) plus the maximum of the left and right depths
        return 1 + Math.max(leftDepth, rightDepth);
    }
    
    /**
     * Finds the maximum depth of a binary tree using level-order traversal (BFS).
     * 
     * @param root The root of the binary tree
     * @return The maximum depth of the binary tree
     */
    public int maxDepthBFS(TreeNode root) {
        // Edge case: empty tree
        if (root == null) {
            return 0;
        }
        
        // Initialize a queue for level-order traversal
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        // Initialize the depth counter
        int depth = 0;
        
        // Process nodes level by level
        while (!queue.isEmpty()) {
            // Increment the depth for each level
            depth++;
            
            // Process all nodes at the current level
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                
                // Add the children to the queue
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        
        // Return the final depth
        return depth;
    }
    
    /**
     * Finds the maximum depth of a binary tree using iterative DFS.
     * 
     * @param root The root of the binary tree
     * @return The maximum depth of the binary tree
     */
    public int maxDepthIterativeDFS(TreeNode root) {
        // Edge case: empty tree
        if (root == null) {
            return 0;
        }
        
        // Initialize a stack for DFS
        java.util.Stack<java.util.Map.Entry<TreeNode, Integer>> stack = new java.util.Stack<>();
        stack.push(new java.util.AbstractMap.SimpleEntry<>(root, 1));
        
        // Initialize the maximum depth
        int maxDepth = 0;
        
        // Process nodes using DFS
        while (!stack.isEmpty()) {
            // Pop a node and its depth from the stack
            java.util.Map.Entry<TreeNode, Integer> entry = stack.pop();
            TreeNode node = entry.getKey();
            int currentDepth = entry.getValue();
            
            // Update the maximum depth
            maxDepth = Math.max(maxDepth, currentDepth);
            
            // Push the children to the stack with incremented depth
            if (node.right != null) {
                stack.push(new java.util.AbstractMap.SimpleEntry<>(node.right, currentDepth + 1));
            }
            if (node.left != null) {
                stack.push(new java.util.AbstractMap.SimpleEntry<>(node.left, currentDepth + 1));
            }
        }
        
        // Return the final maximum depth
        return maxDepth;
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
        MaximumDepthOfBinaryTree solution = new MaximumDepthOfBinaryTree();
        
        // Example 1: [3,9,20,null,null,15,7]
        Integer[] values1 = {3, 9, 20, null, null, 15, 7};
        TreeNode root1 = createTree(values1);
        System.out.println("Example 1:");
        System.out.print("Tree: ");
        printTree(root1);
        
        int depth1 = solution.maxDepth(root1);
        System.out.println("Maximum depth (recursive): " + depth1);
        
        int depth1BFS = solution.maxDepthBFS(root1);
        System.out.println("Maximum depth (BFS): " + depth1BFS);
        
        int depth1IterativeDFS = solution.maxDepthIterativeDFS(root1);
        System.out.println("Maximum depth (iterative DFS): " + depth1IterativeDFS);
        
        // Example 2: [1,null,2]
        Integer[] values2 = {1, null, 2};
        TreeNode root2 = createTree(values2);
        System.out.println("\nExample 2:");
        System.out.print("Tree: ");
        printTree(root2);
        
        int depth2 = solution.maxDepth(root2);
        System.out.println("Maximum depth (recursive): " + depth2);
        
        // Example 3: []
        TreeNode root3 = null;
        System.out.println("\nExample 3:");
        System.out.print("Tree: ");
        printTree(root3);
        
        int depth3 = solution.maxDepth(root3);
        System.out.println("Maximum depth (recursive): " + depth3);
        
        // Let's trace through the execution of Example 1 using the recursive approach:
        System.out.println("\nTracing recursive approach for Example 1:");
        System.out.println("Tree: [3, 9, 20, null, null, 15, 7]");
        
        System.out.println("1. Call maxDepth(3):");
        System.out.println("   - Call maxDepth(9) for left subtree");
        System.out.println("     - Call maxDepth(null) for left subtree of 9, returns 0");
        System.out.println("     - Call maxDepth(null) for right subtree of 9, returns 0");
        System.out.println("     - Return 1 + max(0, 0) = 1");
        System.out.println("   - Call maxDepth(20) for right subtree");
        System.out.println("     - Call maxDepth(15) for left subtree of 20");
        System.out.println("       - Call maxDepth(null) for left subtree of 15, returns 0");
        System.out.println("       - Call maxDepth(null) for right subtree of 15, returns 0");
        System.out.println("       - Return 1 + max(0, 0) = 1");
        System.out.println("     - Call maxDepth(7) for right subtree of 20");
        System.out.println("       - Call maxDepth(null) for left subtree of 7, returns 0");
        System.out.println("       - Call maxDepth(null) for right subtree of 7, returns 0");
        System.out.println("       - Return 1 + max(0, 0) = 1");
        System.out.println("     - Return 1 + max(1, 1) = 2");
        System.out.println("   - Return 1 + max(1, 2) = 3");
        
        System.out.println("2. Final maximum depth: 3");
    }
}
