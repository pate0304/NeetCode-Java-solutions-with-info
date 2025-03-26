package neetcode.trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * NeetCode Problem 8 (Trees): Binary Tree Level Order Traversal
 * 
 * Problem Description:
 * Given the root of a binary tree, return the level order traversal of its nodes' values.
 * (i.e., from left to right, level by level).
 * 
 * Examples:
 * Input: root = [3,9,20,null,null,15,7]
 * Output: [[3],[9,20],[15,7]]
 * 
 * Input: root = [1]
 * Output: [[1]]
 * 
 * Input: root = []
 * Output: []
 * 
 * Constraints:
 * - The number of nodes in the tree is in the range [0, 2000].
 * - -1000 <= Node.val <= 1000
 * 
 * Approach:
 * We can solve this problem using a breadth-first search (BFS) approach with a queue:
 * 
 * 1. If the root is null, return an empty list.
 * 2. Initialize a queue and add the root to it.
 * 3. While the queue is not empty:
 *    a. Get the size of the queue, which represents the number of nodes at the current level.
 *    b. Create a list to store the values of nodes at the current level.
 *    c. Process all nodes at the current level:
 *       i. Dequeue a node.
 *       ii. Add its value to the current level list.
 *       iii. Enqueue its left and right children if they exist.
 *    d. Add the current level list to the result.
 * 4. Return the result.
 * 
 * We can also implement a recursive approach using depth-first search (DFS), but BFS is more natural for level-order traversal.
 * 
 * Time Complexity: O(n) where n is the number of nodes in the tree
 * Space Complexity: O(n) for the queue and the result list
 */
public class BinaryTreeLevelOrderTraversal {
    
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
     * Returns the level order traversal of a binary tree using BFS.
     * 
     * @param root The root of the binary tree
     * @return The level order traversal as a list of lists
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        
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
            
            // Create a list to store the values of nodes at the current level
            List<Integer> currentLevel = new ArrayList<>();
            
            // Process all nodes at the current level
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                
                // Add the node's value to the current level list
                currentLevel.add(node.val);
                
                // Add the node's children to the queue
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            
            // Add the current level list to the result
            result.add(currentLevel);
        }
        
        return result;
    }
    
    /**
     * Returns the level order traversal of a binary tree using DFS.
     * This is an alternative approach, but BFS is more natural for level-order traversal.
     * 
     * @param root The root of the binary tree
     * @return The level order traversal as a list of lists
     */
    public List<List<Integer>> levelOrderDFS(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        
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
    private void dfs(TreeNode node, int level, List<List<Integer>> result) {
        // If this is the first node at this level, add a new list for this level
        if (result.size() == level) {
            result.add(new ArrayList<>());
        }
        
        // Add the node's value to the list for the current level
        result.get(level).add(node.val);
        
        // Recursively process the left and right subtrees
        if (node.left != null) {
            dfs(node.left, level + 1, result);
        }
        if (node.right != null) {
            dfs(node.right, level + 1, result);
        }
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
        BinaryTreeLevelOrderTraversal solution = new BinaryTreeLevelOrderTraversal();
        
        // Example 1: root = [3,9,20,null,null,15,7]
        Integer[] values1 = {3, 9, 20, null, null, 15, 7};
        TreeNode root1 = createTree(values1);
        
        System.out.println("Example 1:");
        System.out.print("Tree: ");
        printTree(root1);
        
        List<List<Integer>> result1 = solution.levelOrder(root1);
        System.out.println("Level Order Traversal (BFS): " + result1);
        
        List<List<Integer>> result1DFS = solution.levelOrderDFS(root1);
        System.out.println("Level Order Traversal (DFS): " + result1DFS);
        
        // Example 2: root = [1]
        Integer[] values2 = {1};
        TreeNode root2 = createTree(values2);
        
        System.out.println("\nExample 2:");
        System.out.print("Tree: ");
        printTree(root2);
        
        List<List<Integer>> result2 = solution.levelOrder(root2);
        System.out.println("Level Order Traversal (BFS): " + result2);
        
        // Example 3: root = []
        TreeNode root3 = null;
        
        System.out.println("\nExample 3:");
        System.out.print("Tree: ");
        printTree(root3);
        
        List<List<Integer>> result3 = solution.levelOrder(root3);
        System.out.println("Level Order Traversal (BFS): " + result3);
        
        // Additional Example: root = [1,2,3,4,5,6,7]
        Integer[] values4 = {1, 2, 3, 4, 5, 6, 7};
        TreeNode root4 = createTree(values4);
        
        System.out.println("\nAdditional Example:");
        System.out.print("Tree: ");
        printTree(root4);
        
        List<List<Integer>> result4 = solution.levelOrder(root4);
        System.out.println("Level Order Traversal (BFS): " + result4);
        
        // Let's trace through the execution of Example 1 using the BFS approach:
        System.out.println("\nTracing BFS approach for Example 1:");
        System.out.println("Tree: [3, 9, 20, null, null, 15, 7]");
        
        System.out.println("1. Initialize queue with root (3) and result as an empty list.");
        System.out.println("2. Process level 0:");
        System.out.println("   - Queue size = 1, create a new list for level 0");
        System.out.println("   - Dequeue 3, add it to level 0 list");
        System.out.println("   - Enqueue left child (9) and right child (20)");
        System.out.println("   - Level 0 list = [3], add to result");
        System.out.println("   - Result = [[3]]");
        System.out.println("3. Process level 1:");
        System.out.println("   - Queue size = 2, create a new list for level 1");
        System.out.println("   - Dequeue 9, add it to level 1 list");
        System.out.println("   - 9 has no children, nothing to enqueue");
        System.out.println("   - Dequeue 20, add it to level 1 list");
        System.out.println("   - Enqueue left child (15) and right child (7)");
        System.out.println("   - Level 1 list = [9, 20], add to result");
        System.out.println("   - Result = [[3], [9, 20]]");
        System.out.println("4. Process level 2:");
        System.out.println("   - Queue size = 2, create a new list for level 2");
        System.out.println("   - Dequeue 15, add it to level 2 list");
        System.out.println("   - 15 has no children, nothing to enqueue");
        System.out.println("   - Dequeue 7, add it to level 2 list");
        System.out.println("   - 7 has no children, nothing to enqueue");
        System.out.println("   - Level 2 list = [15, 7], add to result");
        System.out.println("   - Result = [[3], [9, 20], [15, 7]]");
        System.out.println("5. Queue is empty, return the result: [[3], [9, 20], [15, 7]]");
        
        // Visualize the tree and its level order traversal
        System.out.println("\nVisualization of Example 1:");
        System.out.println("    3      <- Level 0: [3]");
        System.out.println("   / \\");
        System.out.println("  9  20    <- Level 1: [9, 20]");
        System.out.println("    / \\");
        System.out.println("   15  7   <- Level 2: [15, 7]");
    }
}
