package neetcode.trees;

import java.util.LinkedList;
import java.util.Queue;

/**
 * NeetCode Problem 10 (Trees): Count Good Nodes in Binary Tree
 * 
 * Problem Description:
 * Given a binary tree root, a node X in the tree is named good if in the path from root to X
 * there are no nodes with a value greater than X.
 * 
 * Return the number of good nodes in the binary tree.
 * 
 * Examples:
 * Input: root = [3,1,4,3,null,1,5]
 * Output: 4
 * Explanation: Nodes in blue are good.
 * Root Node (3) is always a good node.
 * Node 4 -> (3,4) is the maximum value in the path starting from the root.
 * Node 5 -> (3,4,5) is the maximum value in the path
 * Node 3 -> (3,1,3) is the maximum value in the path.
 * 
 * Input: root = [3,3,null,4,2]
 * Output: 3
 * Explanation: Node 2 -> (3,3,2) is not good, because "3" is higher than it.
 * 
 * Input: root = [1]
 * Output: 1
 * Explanation: Root is considered as good.
 * 
 * Constraints:
 * - The number of nodes in the binary tree is in the range [1, 10^5].
 * - Each node's value is between [-10^4, 10^4].
 * 
 * Approach:
 * We can solve this problem using a depth-first search (DFS) approach:
 * 
 * 1. Start a DFS traversal from the root.
 * 2. Keep track of the maximum value seen so far in the path from the root to the current node.
 * 3. If the current node's value is greater than or equal to the maximum value seen so far,
 *    it's a good node, so increment the count.
 * 4. Update the maximum value for the recursive calls to the children.
 * 
 * We can implement this approach both recursively and iteratively.
 * 
 * Time Complexity: O(n) where n is the number of nodes in the tree
 * Space Complexity: O(h) where h is the height of the tree (for the recursion stack)
 */
public class CountGoodNodesInBinaryTree {
    
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
     * Counts the number of good nodes in a binary tree using recursion.
     * 
     * @param root The root of the binary tree
     * @return The number of good nodes
     */
    public int goodNodes(TreeNode root) {
        // Edge case: empty tree
        if (root == null) {
            return 0;
        }
        
        // Start DFS from the root with the root's value as the maximum seen so far
        return dfs(root, root.val);
    }
    
    /**
     * Helper method for the recursive DFS approach.
     * 
     * @param node The current node
     * @param maxSoFar The maximum value seen so far in the path from the root
     * @return The number of good nodes in the subtree rooted at node
     */
    private int dfs(TreeNode node, int maxSoFar) {
        // Base case: null node
        if (node == null) {
            return 0;
        }
        
        // Check if the current node is a good node
        int count = (node.val >= maxSoFar) ? 1 : 0;
        
        // Update the maximum value for the recursive calls
        int newMax = Math.max(maxSoFar, node.val);
        
        // Recursively count good nodes in the left and right subtrees
        count += dfs(node.left, newMax);
        count += dfs(node.right, newMax);
        
        return count;
    }
    
    /**
     * Counts the number of good nodes in a binary tree using iteration.
     * 
     * @param root The root of the binary tree
     * @return The number of good nodes
     */
    public int goodNodesIterative(TreeNode root) {
        // Edge case: empty tree
        if (root == null) {
            return 0;
        }
        
        // Initialize the count of good nodes
        int count = 0;
        
        // Use a stack to simulate recursion
        java.util.Stack<java.util.AbstractMap.SimpleEntry<TreeNode, Integer>> stack = new java.util.Stack<>();
        stack.push(new java.util.AbstractMap.SimpleEntry<>(root, root.val));
        
        // Process nodes using DFS
        while (!stack.isEmpty()) {
            // Pop a node and its maximum value so far from the stack
            java.util.AbstractMap.SimpleEntry<TreeNode, Integer> entry = stack.pop();
            TreeNode node = entry.getKey();
            int maxSoFar = entry.getValue();
            
            // Check if the current node is a good node
            if (node.val >= maxSoFar) {
                count++;
            }
            
            // Update the maximum value for the children
            int newMax = Math.max(maxSoFar, node.val);
            
            // Push the children to the stack
            if (node.right != null) {
                stack.push(new java.util.AbstractMap.SimpleEntry<>(node.right, newMax));
            }
            if (node.left != null) {
                stack.push(new java.util.AbstractMap.SimpleEntry<>(node.left, newMax));
            }
        }
        
        return count;
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
        CountGoodNodesInBinaryTree solution = new CountGoodNodesInBinaryTree();
        
        // Example 1: root = [3,1,4,3,null,1,5]
        Integer[] values1 = {3, 1, 4, 3, null, 1, 5};
        TreeNode root1 = createTree(values1);
        
        System.out.println("Example 1:");
        System.out.print("Tree: ");
        printTree(root1);
        
        int result1 = solution.goodNodes(root1);
        System.out.println("Number of good nodes (recursive): " + result1);
        
        int result1Iterative = solution.goodNodesIterative(root1);
        System.out.println("Number of good nodes (iterative): " + result1Iterative);
        
        // Example 2: root = [3,3,null,4,2]
        Integer[] values2 = {3, 3, null, 4, 2};
        TreeNode root2 = createTree(values2);
        
        System.out.println("\nExample 2:");
        System.out.print("Tree: ");
        printTree(root2);
        
        int result2 = solution.goodNodes(root2);
        System.out.println("Number of good nodes (recursive): " + result2);
        
        // Example 3: root = [1]
        Integer[] values3 = {1};
        TreeNode root3 = createTree(values3);
        
        System.out.println("\nExample 3:");
        System.out.print("Tree: ");
        printTree(root3);
        
        int result3 = solution.goodNodes(root3);
        System.out.println("Number of good nodes (recursive): " + result3);
        
        // Let's trace through the execution of Example 1:
        System.out.println("\nTracing recursive approach for Example 1:");
        System.out.println("Tree: [3, 1, 4, 3, null, 1, 5]");
        
        System.out.println("1. Call goodNodes(3):");
        System.out.println("   - Call dfs(3, 3):");
        System.out.println("     - 3 >= 3, so this is a good node, count = 1");
        System.out.println("     - Call dfs(1, 3) for left subtree:");
        System.out.println("       - 1 < 3, so this is not a good node, count = 0");
        System.out.println("       - Call dfs(3, 3) for left subtree of 1:");
        System.out.println("         - 3 >= 3, so this is a good node, count = 1");
        System.out.println("         - Call dfs(null, 3) for left subtree of 3, returns 0");
        System.out.println("         - Call dfs(null, 3) for right subtree of 3, returns 0");
        System.out.println("         - Return 1 + 0 + 0 = 1");
        System.out.println("       - Call dfs(null, 3) for right subtree of 1, returns 0");
        System.out.println("       - Return 0 + 1 + 0 = 1");
        System.out.println("     - Call dfs(4, 3) for right subtree:");
        System.out.println("       - 4 >= 3, so this is a good node, count = 1");
        System.out.println("       - Call dfs(1, 4) for left subtree of 4:");
        System.out.println("         - 1 < 4, so this is not a good node, count = 0");
        System.out.println("         - Call dfs(null, 4) for left subtree of 1, returns 0");
        System.out.println("         - Call dfs(null, 4) for right subtree of 1, returns 0");
        System.out.println("         - Return 0 + 0 + 0 = 0");
        System.out.println("       - Call dfs(5, 4) for right subtree of 4:");
        System.out.println("         - 5 >= 4, so this is a good node, count = 1");
        System.out.println("         - Call dfs(null, 5) for left subtree of 5, returns 0");
        System.out.println("         - Call dfs(null, 5) for right subtree of 5, returns 0");
        System.out.println("         - Return 1 + 0 + 0 = 1");
        System.out.println("       - Return 1 + 0 + 1 = 2");
        System.out.println("     - Return 1 + 1 + 2 = 4");
        
        System.out.println("2. Final result: 4");
        
        // Visualize the tree and its good nodes
        System.out.println("\nVisualization of Example 1:");
        System.out.println("      3       <- Good node (max so far: 3)");
        System.out.println("     / \\");
        System.out.println("    1   4     <- 1 is not good (max so far: 3), 4 is good (max so far: 3)");
        System.out.println("   /   / \\");
        System.out.println("  3   1   5   <- 3 is good (max so far: 3), 1 is not good (max so far: 4), 5 is good (max so far: 4)");
        System.out.println("\nGood nodes: 3, 4, 3, 5");
    }
}
