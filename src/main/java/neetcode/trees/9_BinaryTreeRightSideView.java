package neetcode.trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * NeetCode Problem 9 (Trees): Binary Tree Right Side View
 * 
 * Problem Description:
 * Given the root of a binary tree, imagine yourself standing on the right side of it,
 * return the values of the nodes you can see ordered from top to bottom.
 * 
 * Examples:
 * Input: root = [1,2,3,null,5,null,4]
 * Output: [1,3,4]
 * 
 * Input: root = [1,null,3]
 * Output: [1,3]
 * 
 * Input: root = []
 * Output: []
 * 
 * Constraints:
 * - The number of nodes in the tree is in the range [0, 100].
 * - -100 <= Node.val <= 100
 * 
 * Approach:
 * We can solve this problem using both BFS and DFS approaches:
 * 
 * 1. BFS Approach (Level Order Traversal):
 *    - Perform a level order traversal of the tree.
 *    - For each level, add the rightmost node's value to the result.
 * 
 * 2. DFS Approach (Preorder Traversal with Right Priority):
 *    - Perform a modified preorder traversal (root -> right -> left).
 *    - Keep track of the current level.
 *    - If we're visiting a level for the first time, add the node's value to the result.
 * 
 * Time Complexity: O(n) where n is the number of nodes in the tree
 * Space Complexity: O(h) where h is the height of the tree (for the recursion stack in DFS)
 *                  O(w) where w is the maximum width of the tree (for the queue in BFS)
 */
public class BinaryTreeRightSideView {
    
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
     * Returns the right side view of a binary tree using BFS.
     * 
     * @param root The root of the binary tree
     * @return The right side view as a list of values
     */
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        
        // Edge case: empty tree
        if (root == null) {
            return result;
        }
        
        // Initialize a queue for BFS
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        // Process nodes level by level
        while (!queue.isEmpty()) {
            // Get the number of nodes at the current level
            int levelSize = queue.size();
            
            // Process all nodes at the current level
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                
                // If this is the rightmost node of the current level, add its value to the result
                if (i == levelSize - 1) {
                    result.add(node.val);
                }
                
                // Add the node's children to the queue (left first, then right)
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        
        return result;
    }
    
    /**
     * Returns the right side view of a binary tree using DFS.
     * 
     * @param root The root of the binary tree
     * @return The right side view as a list of values
     */
    public List<Integer> rightSideViewDFS(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        
        // Edge case: empty tree
        if (root == null) {
            return result;
        }
        
        // Call the recursive helper method
        dfs(root, 0, result);
        
        return result;
    }
    
    /**
     * Helper method for the DFS approach.
     * 
     * @param node The current node
     * @param level The current level
     * @param result The result list
     */
    private void dfs(TreeNode node, int level, List<Integer> result) {
        // Base case: null node
        if (node == null) {
            return;
        }
        
        // If we're visiting this level for the first time, add the node's value to the result
        if (level == result.size()) {
            result.add(node.val);
        }
        
        // Visit the right subtree first, then the left subtree
        dfs(node.right, level + 1, result);
        dfs(node.left, level + 1, result);
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
        
        List<Integer> values = new ArrayList<>();
        
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
        BinaryTreeRightSideView solution = new BinaryTreeRightSideView();
        
        // Example 1: root = [1,2,3,null,5,null,4]
        Integer[] values1 = {1, 2, 3, null, 5, null, 4};
        TreeNode root1 = createTree(values1);
        
        System.out.println("Example 1:");
        System.out.print("Tree: ");
        printTree(root1);
        
        List<Integer> result1 = solution.rightSideView(root1);
        System.out.println("Right Side View (BFS): " + result1);
        
        List<Integer> result1DFS = solution.rightSideViewDFS(root1);
        System.out.println("Right Side View (DFS): " + result1DFS);
        
        // Example 2: root = [1,null,3]
        Integer[] values2 = {1, null, 3};
        TreeNode root2 = createTree(values2);
        
        System.out.println("\nExample 2:");
        System.out.print("Tree: ");
        printTree(root2);
        
        List<Integer> result2 = solution.rightSideView(root2);
        System.out.println("Right Side View (BFS): " + result2);
        
        // Example 3: root = []
        TreeNode root3 = null;
        
        System.out.println("\nExample 3:");
        System.out.print("Tree: ");
        printTree(root3);
        
        List<Integer> result3 = solution.rightSideView(root3);
        System.out.println("Right Side View (BFS): " + result3);
        
        // Additional Example: root = [1,2,3,4,null,5,6,null,null,7]
        Integer[] values4 = {1, 2, 3, 4, null, 5, 6, null, null, 7};
        TreeNode root4 = createTree(values4);
        
        System.out.println("\nAdditional Example:");
        System.out.print("Tree: ");
        printTree(root4);
        
        List<Integer> result4 = solution.rightSideView(root4);
        System.out.println("Right Side View (BFS): " + result4);
        
        // Let's trace through the execution of Example 1 using the BFS approach:
        System.out.println("\nTracing BFS approach for Example 1:");
        System.out.println("Tree: [1, 2, 3, null, 5, null, 4]");
        
        System.out.println("1. Initialize queue with root (1) and result as an empty list.");
        System.out.println("2. Process level 0:");
        System.out.println("   - Queue size = 1, rightmost node is 1");
        System.out.println("   - Add 1 to result");
        System.out.println("   - Enqueue left child (2) and right child (3)");
        System.out.println("   - Result = [1]");
        System.out.println("3. Process level 1:");
        System.out.println("   - Queue size = 2, rightmost node is 3");
        System.out.println("   - Add 3 to result");
        System.out.println("   - Enqueue left child of 2 (null) and right child of 2 (5)");
        System.out.println("   - Enqueue left child of 3 (null) and right child of 3 (4)");
        System.out.println("   - Result = [1, 3]");
        System.out.println("4. Process level 2:");
        System.out.println("   - Queue size = 2, rightmost node is 4");
        System.out.println("   - Add 4 to result");
        System.out.println("   - No more children to enqueue");
        System.out.println("   - Result = [1, 3, 4]");
        System.out.println("5. Queue is empty, return the result: [1, 3, 4]");
        
        // Let's trace through the execution of Example 1 using the DFS approach:
        System.out.println("\nTracing DFS approach for Example 1:");
        System.out.println("Tree: [1, 2, 3, null, 5, null, 4]");
        
        System.out.println("1. Call dfs(1, 0, []):");
        System.out.println("   - Level 0 is new, add 1 to result");
        System.out.println("   - Result = [1]");
        System.out.println("   - Call dfs(3, 1, [1]):");
        System.out.println("     - Level 1 is new, add 3 to result");
        System.out.println("     - Result = [1, 3]");
        System.out.println("     - Call dfs(4, 2, [1, 3]):");
        System.out.println("       - Level 2 is new, add 4 to result");
        System.out.println("       - Result = [1, 3, 4]");
        System.out.println("       - Call dfs(null, 3, [1, 3, 4]), return");
        System.out.println("       - Call dfs(null, 3, [1, 3, 4]), return");
        System.out.println("     - Call dfs(null, 2, [1, 3, 4]), return");
        System.out.println("   - Call dfs(2, 1, [1, 3, 4]):");
        System.out.println("     - Level 1 is not new, don't add to result");
        System.out.println("     - Call dfs(null, 2, [1, 3, 4]), return");
        System.out.println("     - Call dfs(5, 2, [1, 3, 4]):");
        System.out.println("       - Level 2 is not new, don't add to result");
        System.out.println("       - Call dfs(null, 3, [1, 3, 4]), return");
        System.out.println("       - Call dfs(null, 3, [1, 3, 4]), return");
        
        System.out.println("2. Final result: [1, 3, 4]");
        
        // Visualize the tree and its right side view
        System.out.println("\nVisualization of Example 1:");
        System.out.println("    1         <- Visible from right side");
        System.out.println("   / \\");
        System.out.println("  2   3       <- Visible from right side");
        System.out.println("   \\   \\");
        System.out.println("    5   4     <- Visible from right side");
        System.out.println("\nRight Side View: [1, 3, 4]");
    }
}
