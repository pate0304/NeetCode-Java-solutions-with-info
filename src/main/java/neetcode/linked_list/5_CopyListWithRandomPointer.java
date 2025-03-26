package neetcode.linked_list;

import java.util.HashMap;
import java.util.Map;

/**
 * NeetCode Problem 5 (Linked List): Copy List with Random Pointer
 * 
 * Problem Description:
 * A linked list of length n is given such that each node contains an additional random pointer,
 * which could point to any node in the list, or null.
 * 
 * Construct a deep copy of the list. The deep copy should consist of exactly n brand new nodes,
 * where each new node has its value set to the value of its corresponding original node.
 * Both the next and random pointer of the new nodes should point to new nodes in the copied list
 * such that the pointers in the original list and copied list represent the same list state.
 * None of the pointers in the new list should point to nodes in the original list.
 * 
 * For example, if there are two nodes X and Y in the original list, where X.random --> Y,
 * then for the corresponding two nodes x and y in the copied list, x.random --> y.
 * 
 * Return the head of the copied linked list.
 * 
 * Examples:
 * Input: head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
 * Output: [[7,null],[13,0],[11,4],[10,2],[1,0]]
 * 
 * Input: head = [[1,1],[2,1]]
 * Output: [[1,1],[2,1]]
 * 
 * Input: head = [[3,null],[3,0],[3,null]]
 * Output: [[3,null],[3,0],[3,null]]
 * 
 * Constraints:
 * - 0 <= n <= 1000
 * - -10^4 <= Node.val <= 10^4
 * - Node.random is null or is pointing to some node in the linked list.
 * 
 * Approach:
 * We can solve this problem using two different approaches:
 * 
 * Approach 1: Using a HashMap (Two-pass)
 * 1. Create a HashMap to map original nodes to their corresponding new nodes
 * 2. First pass: Create new nodes with the same values and store the mapping
 * 3. Second pass: Set the next and random pointers of the new nodes using the mapping
 * 
 * Approach 2: Interweaving (Three-pass)
 * 1. First pass: Create a new node for each original node and place it between the original node and its next node
 * 2. Second pass: Set the random pointers of the new nodes
 * 3. Third pass: Separate the original and copied lists
 * 
 * Time Complexity: O(n) where n is the number of nodes in the linked list
 * Space Complexity: O(n) for the HashMap approach, O(1) for the interweaving approach (excluding the output)
 */
public class CopyListWithRandomPointer {
    
    /**
     * Definition for a Node.
     */
    static class Node {
        int val;
        Node next;
        Node random;
        
        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            Node current = this;
            while (current != null) {
                sb.append("[").append(current.val).append(",");
                if (current.random != null) {
                    sb.append(current.random.val);
                } else {
                    sb.append("null");
                }
                sb.append("]");
                if (current.next != null) {
                    sb.append(" -> ");
                }
                current = current.next;
            }
            return sb.toString();
        }
    }
    
    /**
     * Creates a deep copy of the linked list using a HashMap.
     * 
     * @param head The head of the original linked list
     * @return The head of the copied linked list
     */
    public Node copyRandomList(Node head) {
        // Edge case: empty list
        if (head == null) {
            return null;
        }
        
        // Create a HashMap to map original nodes to their corresponding new nodes
        Map<Node, Node> map = new HashMap<>();
        
        // First pass: Create new nodes with the same values and store the mapping
        Node current = head;
        while (current != null) {
            map.put(current, new Node(current.val));
            current = current.next;
        }
        
        // Second pass: Set the next and random pointers of the new nodes
        current = head;
        while (current != null) {
            // Set the next pointer
            map.get(current).next = map.get(current.next);
            
            // Set the random pointer
            map.get(current).random = map.get(current.random);
            
            current = current.next;
        }
        
        // Return the head of the copied list
        return map.get(head);
    }
    
    /**
     * Creates a deep copy of the linked list using the interweaving approach.
     * 
     * @param head The head of the original linked list
     * @return The head of the copied linked list
     */
    public Node copyRandomListInterweaving(Node head) {
        // Edge case: empty list
        if (head == null) {
            return null;
        }
        
        // First pass: Create a new node for each original node and place it between
        // the original node and its next node
        Node current = head;
        while (current != null) {
            // Create a new node with the same value
            Node copy = new Node(current.val);
            
            // Insert the copy between current and current.next
            copy.next = current.next;
            current.next = copy;
            
            // Move to the next original node
            current = copy.next;
        }
        
        // Second pass: Set the random pointers of the new nodes
        current = head;
        while (current != null) {
            // If the original node has a random pointer, set the copy's random pointer
            if (current.random != null) {
                current.next.random = current.random.next;
            }
            
            // Move to the next original node
            current = current.next.next;
        }
        
        // Third pass: Separate the original and copied lists
        current = head;
        Node copyHead = head.next;
        Node copyCurrent = copyHead;
        
        while (copyCurrent.next != null) {
            // Restore the original list
            current.next = current.next.next;
            current = current.next;
            
            // Build the copy list
            copyCurrent.next = copyCurrent.next.next;
            copyCurrent = copyCurrent.next;
        }
        
        // Restore the last node of the original list
        current.next = null;
        
        // Return the head of the copied list
        return copyHead;
    }
    
    /**
     * Helper method to create a linked list with random pointers for testing.
     */
    private static Node createTestList(int[][] nodeInfo) {
        if (nodeInfo == null || nodeInfo.length == 0) {
            return null;
        }
        
        // Create nodes
        Node[] nodes = new Node[nodeInfo.length];
        for (int i = 0; i < nodeInfo.length; i++) {
            nodes[i] = new Node(nodeInfo[i][0]);
        }
        
        // Set next pointers
        for (int i = 0; i < nodeInfo.length - 1; i++) {
            nodes[i].next = nodes[i + 1];
        }
        
        // Set random pointers
        for (int i = 0; i < nodeInfo.length; i++) {
            if (nodeInfo[i][1] != -1) {
                nodes[i].random = nodes[nodeInfo[i][1]];
            }
        }
        
        return nodes[0];
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        CopyListWithRandomPointer solution = new CopyListWithRandomPointer();
        
        // Example 1: [[7,null],[13,0],[11,4],[10,2],[1,0]]
        int[][] nodeInfo1 = {{7, -1}, {13, 0}, {11, 4}, {10, 2}, {1, 0}};
        Node head1 = createTestList(nodeInfo1);
        System.out.println("Original List 1: " + head1);
        Node copy1 = solution.copyRandomList(head1);
        System.out.println("Copied List 1 (HashMap): " + copy1);
        
        // Example 2: [[1,1],[2,1]]
        int[][] nodeInfo2 = {{1, 1}, {2, 1}};
        Node head2 = createTestList(nodeInfo2);
        System.out.println("\nOriginal List 2: " + head2);
        Node copy2 = solution.copyRandomListInterweaving(head2);
        System.out.println("Copied List 2 (Interweaving): " + copy2);
        
        // Example 3: [[3,null],[3,0],[3,null]]
        int[][] nodeInfo3 = {{3, -1}, {3, 0}, {3, -1}};
        Node head3 = createTestList(nodeInfo3);
        System.out.println("\nOriginal List 3: " + head3);
        Node copy3 = solution.copyRandomList(head3);
        System.out.println("Copied List 3 (HashMap): " + copy3);
        
        // Let's trace through the execution of Example 1 using the HashMap approach:
        // Original list: [7,null] -> [13,0] -> [11,4] -> [10,2] -> [1,0]
        
        // First pass: Create new nodes and store the mapping
        // map = {
        //   [7,null] -> [7,null],
        //   [13,0] -> [13,null],
        //   [11,4] -> [11,null],
        //   [10,2] -> [10,null],
        //   [1,0] -> [1,null]
        // }
        
        // Second pass: Set the next and random pointers
        // For node [7,null]:
        //   map.get([7,null]).next = map.get([13,0]) = [13,null]
        //   map.get([7,null]).random = map.get(null) = null
        // For node [13,0]:
        //   map.get([13,0]).next = map.get([11,4]) = [11,null]
        //   map.get([13,0]).random = map.get([7,null]) = [7,null]
        // For node [11,4]:
        //   map.get([11,4]).next = map.get([10,2]) = [10,null]
        //   map.get([11,4]).random = map.get([1,0]) = [1,null]
        // For node [10,2]:
        //   map.get([10,2]).next = map.get([1,0]) = [1,null]
        //   map.get([10,2]).random = map.get([11,4]) = [11,null]
        // For node [1,0]:
        //   map.get([1,0]).next = map.get(null) = null
        //   map.get([1,0]).random = map.get([7,null]) = [7,null]
        
        // Return map.get(head) = map.get([7,null]) = [7,null]
        // Final copied list: [7,null] -> [13,0] -> [11,4] -> [10,2] -> [1,0]
    }
}
