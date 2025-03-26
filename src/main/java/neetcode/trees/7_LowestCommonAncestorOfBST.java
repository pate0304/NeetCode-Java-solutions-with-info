package neetcode.trees;

import java.util.LinkedList;
import java.util.Queue;

/**
 * NeetCode Problem 7 (Trees): Lowest Common Ancestor of a Binary Search Tree
 * 
 * Problem Description:
 * Given a binary search tree (BST), find the lowest common ancestor (LCA) node of two given nodes in the BST.
 * 
 * According to the definition of LCA on Wikipedia: "The lowest common ancestor is defined between two nodes p and q
 * as the lowest node in T that has both p and q as descendants (where we allow a node to be a descendant of itself)."
 * 
 * Examples:
 * Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
 * Output: 6
 * Explanation: The LCA of nodes 2 and 8 is 6.
 * 
 * Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 4
 * Output: 2
 * Explanation: The LCA of nodes 2 and 4 is 2, since a node can be a descendant of itself according to the LCA definition.
 * 
 * Input: root = [2,1], p = 2, q = 1
 * Output: 2
 * 
 * Constraints:
 * - The number of nodes in the tree is in the range [2, 10^5].
 * - -10^9 <= Node.val <= 10^9
 * - All Node.val are unique.
 * - p != q
 * - p and q will exist in the BST.
 * 
 * Approach:
 * We can solve this problem by leveraging the properties of a Binary Search Tree:
 * - All nodes in the left subtree of a node have values less than the node's value.
 * - All nodes in the right subtree of a node have values greater than the node's value.
 * 
 * 1. Start from the root of the tree.
 * 2. If both p and q are less than the current node, the LCA must be in the left subtree.
 * 3. If both p and q are greater than the current node, the LCA must be in the right subtree.
 * 4. If one is less and one is greater (or one of them equals the current node), the current node is the LCA.
 * 
 * We can implement this approach both recursively and iteratively.
 * 
 * Time Complexity: O(h) where h is the height of the tree (O(log n) for a balanced BST, O(n) for a skewed BST)
 * Space Complexity: O(h) for the recursive approach (for the recursion stack), O(1) for the iterative approach
 */
public class LowestCommonAncestorOfBST {
    
    /**
     * Definition for a binary tree node.
     */
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        
        TreeNode(int x) {
            val = x;
        }
    }
    
    /**
     * Finds the lowest common ancestor of two nodes in a BST using recursion.
     * 
     * @param root The root of the BST
     * @param p The first node
     * @param q The second node
     * @return The lowest common ancestor of p and q
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // If both p and q are less than root, LCA is in the left subtree
        if (p.val < root.val && q.val < root.val) {
            return lowestCommonAncestor(root.left, p, q);
        }
        
        // If both p and q are greater than root, LCA is in the right subtree
        if (p.val > root.val && q.val > root.val) {
            return lowestCommonAncestor(root.right, p, q);
        }
        
        // If one is less and one is greater (or one of them equals root), root is the LCA
        return root;
    }
    
    /**
     * Finds the lowest common ancestor of two nodes in a BST using iteration.
     * 
     * @param root The root of the BST
     * @param p The first node
     * @param q The second node
     * @return The lowest common ancestor of p and q
     */
    public TreeNode lowestCommonAncestorIterative(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode current = root;
        
        while (current != null) {
            // If both p and q are less than current, go to the left subtree
            if (p.val < current.val && q.val < current.val) {
                current = current.left;
            }
            // If both p and q are greater than current, go to the right subtree
            else if (p.val > current.val && q.val > current.val) {
                current = current.right;
            }
            // If one is less and one is greater (or one of them equals current), current is the LCA
            else {
                return current;
            }
        }
        
        return null; // This should never happen if p and q exist in the BST
    }
    
    /**
     * Helper method to create a binary search tree from a level-order traversal array.
     * Null values in the array represent null nodes.
     */
    private static TreeNode createBST(Integer[] values) {
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
     * Helper method to find a node with a given value in a BST.
     */
    private static TreeNode findNode(TreeNode root, int val) {
        if (root == null) {
            return null;
        }
        
        if (root.val == val) {
            return root;
        }
        
        if (val < root.val) {
            return findNode(root.left, val);
        } else {
            return findNode(root.right, val);
        }
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
        LowestCommonAncestorOfBST solution = new LowestCommonAncestorOfBST();
        
        // Example 1: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
        Integer[] values1 = {6, 2, 8, 0, 4, 7, 9, null, null, 3, 5};
        TreeNode root1 = createBST(values1);
        TreeNode p1 = findNode(root1, 2);
        TreeNode q1 = findNode(root1, 8);
        
        System.out.println("Example 1:");
        System.out.print("BST: ");
        printTree(root1);
        System.out.println("p: " + p1.val);
        System.out.println("q: " + q1.val);
        
        TreeNode lca1 = solution.lowestCommonAncestor(root1, p1, q1);
        System.out.println("Lowest Common Ancestor (recursive): " + lca1.val);
        
        TreeNode lca1Iterative = solution.lowestCommonAncestorIterative(root1, p1, q1);
        System.out.println("Lowest Common Ancestor (iterative): " + lca1Iterative.val);
        
        // Example 2: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 4
        TreeNode p2 = findNode(root1, 2);
        TreeNode q2 = findNode(root1, 4);
        
        System.out.println("\nExample 2:");
        System.out.print("BST: ");
        printTree(root1);
        System.out.println("p: " + p2.val);
        System.out.println("q: " + q2.val);
        
        TreeNode lca2 = solution.lowestCommonAncestor(root1, p2, q2);
        System.out.println("Lowest Common Ancestor (recursive): " + lca2.val);
        
        // Example 3: root = [2,1], p = 2, q = 1
        Integer[] values3 = {2, 1};
        TreeNode root3 = createBST(values3);
        TreeNode p3 = findNode(root3, 2);
        TreeNode q3 = findNode(root3, 1);
        
        System.out.println("\nExample 3:");
        System.out.print("BST: ");
        printTree(root3);
        System.out.println("p: " + p3.val);
        System.out.println("q: " + q3.val);
        
        TreeNode lca3 = solution.lowestCommonAncestor(root3, p3, q3);
        System.out.println("Lowest Common Ancestor (recursive): " + lca3.val);
        
        // Let's trace through the execution of Example 1:
        System.out.println("\nTracing recursive approach for Example 1:");
        System.out.println("BST: [6, 2, 8, 0, 4, 7, 9, null, null, 3, 5]");
        System.out.println("p: 2, q: 8");
        
        System.out.println("1. Call lowestCommonAncestor(6, 2, 8):");
        System.out.println("   - Is 2 < 6 && 8 < 6? No (2 < 6 but 8 > 6)");
        System.out.println("   - Is 2 > 6 && 8 > 6? No (2 < 6)");
        System.out.println("   - Return 6 (the current root)");
        
        System.out.println("2. Final result: 6");
        
        // Let's trace through the execution of Example 2:
        System.out.println("\nTracing recursive approach for Example 2:");
        System.out.println("BST: [6, 2, 8, 0, 4, 7, 9, null, null, 3, 5]");
        System.out.println("p: 2, q: 4");
        
        System.out.println("1. Call lowestCommonAncestor(6, 2, 4):");
        System.out.println("   - Is 2 < 6 && 4 < 6? Yes");
        System.out.println("   - Call lowestCommonAncestor(2, 2, 4):");
        System.out.println("     - Is 2 < 2 && 4 < 2? No (2 == 2 and 4 > 2)");
        System.out.println("     - Is 2 > 2 && 4 > 2? No (2 == 2)");
        System.out.println("     - Return 2 (the current root)");
        
        System.out.println("2. Final result: 2");
        
        // Visualize the BST and the LCA for Example 1
        System.out.println("\nVisualization of Example 1:");
        System.out.println("       6        <- LCA");
        System.out.println("      / \\");
        System.out.println("     2   8      <- p and q");
        System.out.println("    / \\ / \\");
        System.out.println("   0  4 7  9");
        System.out.println("     / \\");
        System.out.println("    3   5");
        
        // Visualize the BST and the LCA for Example 2
        System.out.println("\nVisualization of Example 2:");
        System.out.println("       6");
        System.out.println("      / \\");
        System.out.println("     2   8      <- p (LCA)");
        System.out.println("    / \\ / \\");
        System.out.println("   0  4 7  9    <- q");
        System.out.println("     / \\");
        System.out.println("    3   5");
    }
}
