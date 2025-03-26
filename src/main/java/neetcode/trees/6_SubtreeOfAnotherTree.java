package neetcode.trees;

import java.util.LinkedList;
import java.util.Queue;

/**
 * NeetCode Problem 6 (Trees): Subtree of Another Tree
 * 
 * Problem Description:
 * Given the roots of two binary trees root and subRoot, return true if there is a subtree of root
 * with the same structure and node values as subRoot and false otherwise.
 * 
 * A subtree of a binary tree is a tree that consists of a node in the tree and all of this node's descendants.
 * The tree could also be considered as a subtree of itself.
 * 
 * Examples:
 * Input: root = [3,4,5,1,2], subRoot = [4,1,2]
 * Output: true
 * 
 * Input: root = [3,4,5,1,2,null,null,null,null,0], subRoot = [4,1,2]
 * Output: false
 * 
 * Constraints:
 * - The number of nodes in the root tree is in the range [1, 2000].
 * - The number of nodes in the subRoot tree is in the range [1, 1000].
 * - -10^4 <= root.val <= 10^4
 * - -10^4 <= subRoot.val <= 10^4
 * 
 * Approach:
 * We can solve this problem using a recursive approach:
 * 
 * 1. For each node in the main tree, check if the subtree rooted at this node is identical to the subRoot tree.
 * 2. To check if two trees are identical, we can use the isSameTree function from the previous problem.
 * 3. If we find a match, return true. Otherwise, continue checking other nodes.
 * 
 * Time Complexity: O(m * n) where m is the number of nodes in the main tree and n is the number of nodes in the subtree
 * Space Complexity: O(h) where h is the height of the main tree (for the recursion stack)
 * 
 * Alternative approaches include:
 * 1. Serializing both trees and using string matching algorithms (KMP, Rabin-Karp)
 * 2. Using tree hashing to quickly identify potential matches
 */
public class SubtreeOfAnotherTree {
    
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
     * Checks if subRoot is a subtree of root.
     * 
     * @param root The root of the main tree
     * @param subRoot The root of the subtree to find
     * @return true if subRoot is a subtree of root, false otherwise
     */
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        // Edge case: empty subtree is always a subtree
        if (subRoot == null) {
            return true;
        }
        
        // Edge case: empty main tree cannot contain a non-empty subtree
        if (root == null) {
            return false;
        }
        
        // Check if the current root matches the subRoot
        if (isSameTree(root, subRoot)) {
            return true;
        }
        
        // Recursively check the left and right subtrees
        return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }
    
    /**
     * Checks if two binary trees are the same.
     * 
     * @param p The root of the first binary tree
     * @param q The root of the second binary tree
     * @return true if the trees are the same, false otherwise
     */
    private boolean isSameTree(TreeNode p, TreeNode q) {
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
     * Alternative implementation using tree serialization and string matching.
     * This approach is less efficient for this specific problem but demonstrates another technique.
     */
    public boolean isSubtreeUsingStringMatching(TreeNode root, TreeNode subRoot) {
        // Serialize both trees
        String rootStr = serialize(root);
        String subRootStr = serialize(subRoot);
        
        // Check if subRootStr is a substring of rootStr
        return rootStr.contains(subRootStr);
    }
    
    /**
     * Serializes a binary tree to a string using preorder traversal.
     * We add special markers for null nodes to ensure correct matching.
     */
    private String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serializeHelper(root, sb);
        return sb.toString();
    }
    
    /**
     * Helper method for serialization.
     */
    private void serializeHelper(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append(",#"); // Special marker for null nodes
            return;
        }
        
        // Add a special marker before each node to avoid false matches
        // For example, to distinguish between [1,2] and [12]
        sb.append(",").append(node.val);
        
        serializeHelper(node.left, sb);
        serializeHelper(node.right, sb);
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
        SubtreeOfAnotherTree solution = new SubtreeOfAnotherTree();
        
        // Example 1: root = [3,4,5,1,2], subRoot = [4,1,2]
        Integer[] values1Root = {3, 4, 5, 1, 2};
        Integer[] values1SubRoot = {4, 1, 2};
        TreeNode root1 = createTree(values1Root);
        TreeNode subRoot1 = createTree(values1SubRoot);
        
        System.out.println("Example 1:");
        System.out.print("Main tree: ");
        printTree(root1);
        System.out.print("Subtree: ");
        printTree(subRoot1);
        
        boolean isSubtree1 = solution.isSubtree(root1, subRoot1);
        System.out.println("Is subtree? " + isSubtree1);
        
        boolean isSubtree1Alt = solution.isSubtreeUsingStringMatching(root1, subRoot1);
        System.out.println("Is subtree (using string matching)? " + isSubtree1Alt);
        
        // Example 2: root = [3,4,5,1,2,null,null,null,null,0], subRoot = [4,1,2]
        Integer[] values2Root = {3, 4, 5, 1, 2, null, null, null, null, 0};
        Integer[] values2SubRoot = {4, 1, 2};
        TreeNode root2 = createTree(values2Root);
        TreeNode subRoot2 = createTree(values2SubRoot);
        
        System.out.println("\nExample 2:");
        System.out.print("Main tree: ");
        printTree(root2);
        System.out.print("Subtree: ");
        printTree(subRoot2);
        
        boolean isSubtree2 = solution.isSubtree(root2, subRoot2);
        System.out.println("Is subtree? " + isSubtree2);
        
        // Additional Example: root = [1,1], subRoot = [1]
        Integer[] values3Root = {1, 1};
        Integer[] values3SubRoot = {1};
        TreeNode root3 = createTree(values3Root);
        TreeNode subRoot3 = createTree(values3SubRoot);
        
        System.out.println("\nAdditional Example:");
        System.out.print("Main tree: ");
        printTree(root3);
        System.out.print("Subtree: ");
        printTree(subRoot3);
        
        boolean isSubtree3 = solution.isSubtree(root3, subRoot3);
        System.out.println("Is subtree? " + isSubtree3);
        
        // Let's trace through the execution of Example 1:
        System.out.println("\nTracing execution for Example 1:");
        System.out.println("Main tree: [3, 4, 5, 1, 2]");
        System.out.println("Subtree: [4, 1, 2]");
        
        System.out.println("1. Call isSubtree(3, 4):");
        System.out.println("   - Call isSameTree(3, 4):");
        System.out.println("     - 3 != 4, return false");
        System.out.println("   - Call isSubtree(4, 4) for left subtree:");
        System.out.println("     - Call isSameTree(4, 4):");
        System.out.println("       - 4 == 4");
        System.out.println("       - Call isSameTree(1, 1) for left subtrees:");
        System.out.println("         - 1 == 1");
        System.out.println("         - Call isSameTree(null, null) for left subtrees, return true");
        System.out.println("         - Call isSameTree(null, null) for right subtrees, return true");
        System.out.println("         - Return true && true = true");
        System.out.println("       - Call isSameTree(2, 2) for right subtrees:");
        System.out.println("         - 2 == 2");
        System.out.println("         - Call isSameTree(null, null) for left subtrees, return true");
        System.out.println("         - Call isSameTree(null, null) for right subtrees, return true");
        System.out.println("         - Return true && true = true");
        System.out.println("       - Return true && true = true");
        System.out.println("     - isSameTree(4, 4) returns true, so isSubtree returns true");
        
        System.out.println("2. Final result: true");
        
        // Let's trace through the execution of Example 2:
        System.out.println("\nTracing execution for Example 2:");
        System.out.println("Main tree: [3, 4, 5, 1, 2, null, null, null, null, 0]");
        System.out.println("Subtree: [4, 1, 2]");
        
        System.out.println("1. Call isSubtree(3, 4):");
        System.out.println("   - Call isSameTree(3, 4):");
        System.out.println("     - 3 != 4, return false");
        System.out.println("   - Call isSubtree(4, 4) for left subtree:");
        System.out.println("     - Call isSameTree(4, 4):");
        System.out.println("       - 4 == 4");
        System.out.println("       - Call isSameTree(1, 1) for left subtrees:");
        System.out.println("         - 1 == 1");
        System.out.println("         - Call isSameTree(null, null) for left subtrees, return true");
        System.out.println("         - Call isSameTree(0, null) for right subtrees:");
        System.out.println("           - One is null and the other is not, return false");
        System.out.println("         - Return true && false = false");
        System.out.println("       - Return false");
        System.out.println("     - isSameTree(4, 4) returns false");
        System.out.println("     - Call isSubtree(1, 4) for left subtree of 4:");
        System.out.println("       - Call isSameTree(1, 4):");
        System.out.println("         - 1 != 4, return false");
        System.out.println("       - Call isSubtree(null, 4) for left subtree of 1, return false");
        System.out.println("       - Call isSubtree(null, 4) for right subtree of 1, return false");
        System.out.println("       - Return false || false = false");
        System.out.println("     - Call isSubtree(2, 4) for right subtree of 4:");
        System.out.println("       - Call isSameTree(2, 4):");
        System.out.println("         - 2 != 4, return false");
        System.out.println("       - Call isSubtree(null, 4) for left subtree of 2, return false");
        System.out.println("       - Call isSubtree(0, 4) for right subtree of 2:");
        System.out.println("         - Call isSameTree(0, 4):");
        System.out.println("           - 0 != 4, return false");
        System.out.println("         - Call isSubtree(null, 4) for left subtree of 0, return false");
        System.out.println("         - Call isSubtree(null, 4) for right subtree of 0, return false");
        System.out.println("         - Return false || false = false");
        System.out.println("       - Return false || false = false");
        System.out.println("     - Return false || false = false");
        System.out.println("   - Call isSubtree(5, 4) for right subtree:");
        System.out.println("     - Call isSameTree(5, 4):");
        System.out.println("       - 5 != 4, return false");
        System.out.println("     - Call isSubtree(null, 4) for left subtree of 5, return false");
        System.out.println("     - Call isSubtree(null, 4) for right subtree of 5, return false");
        System.out.println("     - Return false || false = false");
        System.out.println("   - Return false || false = false");
        
        System.out.println("2. Final result: false");
    }
}
