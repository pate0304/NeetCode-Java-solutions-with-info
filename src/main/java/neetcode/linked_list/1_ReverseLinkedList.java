package neetcode.linked_list;

/**
 * NeetCode Problem 1 (Linked List): Reverse Linked List
 * 
 * Problem Description:
 * Given the head of a singly linked list, reverse the list, and return the reversed list.
 * 
 * Examples:
 * Input: head = [1,2,3,4,5]
 * Output: [5,4,3,2,1]
 * 
 * Input: head = [1,2]
 * Output: [2,1]
 * 
 * Input: head = []
 * Output: []
 * 
 * Constraints:
 * - The number of nodes in the list is the range [0, 5000].
 * - -5000 <= Node.val <= 5000
 * 
 * Approach:
 * To reverse a linked list, we need to change the direction of all the pointers in the list.
 * We can do this iteratively or recursively:
 * 
 * Iterative Approach:
 * 1. Initialize three pointers: prev (initially null), current (initially head), and next
 * 2. Iterate through the list:
 *    - Save the next node: next = current.next
 *    - Reverse the current node's pointer: current.next = prev
 *    - Move prev and current one step forward: prev = current, current = next
 * 3. Return prev (which will be the new head of the reversed list)
 * 
 * Recursive Approach:
 * 1. Base case: if head is null or has only one node, return head
 * 2. Recursively reverse the rest of the list: newHead = reverseList(head.next)
 * 3. Reverse the pointer of the next node: head.next.next = head
 * 4. Set head.next to null to avoid cycles
 * 5. Return newHead (which is the head of the reversed list)
 * 
 * Time Complexity: O(n) where n is the number of nodes in the linked list
 * Space Complexity: O(1) for the iterative approach, O(n) for the recursive approach due to the call stack
 */
public class ReverseLinkedList {
    
    /**
     * Definition for singly-linked list.
     */
    public static class ListNode {
        int val;
        ListNode next;
        
        ListNode() {}
        
        ListNode(int val) {
            this.val = val;
        }
        
        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            ListNode current = this;
            while (current != null) {
                sb.append(current.val);
                if (current.next != null) {
                    sb.append(" -> ");
                }
                current = current.next;
            }
            return sb.toString();
        }
    }
    
    /**
     * Reverses a linked list iteratively.
     * 
     * @param head The head of the linked list
     * @return The head of the reversed linked list
     */
    public ListNode reverseList(ListNode head) {
        // Edge case: empty list or single node
        if (head == null || head.next == null) {
            return head;
        }
        
        // Initialize pointers
        ListNode prev = null;
        ListNode current = head;
        ListNode next = null;
        
        // Iterate through the list
        while (current != null) {
            // Save the next node
            next = current.next;
            
            // Reverse the current node's pointer
            current.next = prev;
            
            // Move prev and current one step forward
            prev = current;
            current = next;
        }
        
        // prev is now the new head of the reversed list
        return prev;
    }
    
    /**
     * Reverses a linked list recursively.
     * 
     * @param head The head of the linked list
     * @return The head of the reversed linked list
     */
    public ListNode reverseListRecursive(ListNode head) {
        // Base case: empty list or single node
        if (head == null || head.next == null) {
            return head;
        }
        
        // Recursively reverse the rest of the list
        ListNode newHead = reverseListRecursive(head.next);
        
        // Reverse the pointer of the next node
        head.next.next = head;
        
        // Set head.next to null to avoid cycles
        head.next = null;
        
        // Return the new head of the reversed list
        return newHead;
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        ReverseLinkedList solution = new ReverseLinkedList();
        
        // Example 1: [1,2,3,4,5] -> [5,4,3,2,1]
        ListNode head1 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        System.out.println("Original List 1: " + head1);
        ListNode reversed1 = solution.reverseList(head1);
        System.out.println("Reversed List 1 (Iterative): " + reversed1);
        
        // Example 2: [1,2] -> [2,1]
        ListNode head2 = new ListNode(1, new ListNode(2));
        System.out.println("\nOriginal List 2: " + head2);
        ListNode reversed2 = solution.reverseListRecursive(head2);
        System.out.println("Reversed List 2 (Recursive): " + reversed2);
        
        // Example 3: [] -> []
        ListNode head3 = null;
        System.out.println("\nOriginal List 3: " + head3);
        ListNode reversed3 = solution.reverseList(head3);
        System.out.println("Reversed List 3 (Iterative): " + reversed3);
        
        // Let's trace through the execution of Example 1 using the iterative approach:
        // head = 1 -> 2 -> 3 -> 4 -> 5
        
        // Initialize prev = null, current = 1 -> 2 -> 3 -> 4 -> 5, next = null
        
        // Iteration 1:
        // next = current.next = 2 -> 3 -> 4 -> 5
        // current.next = prev = null
        // prev = current = 1 -> null
        // current = next = 2 -> 3 -> 4 -> 5
        
        // Iteration 2:
        // next = current.next = 3 -> 4 -> 5
        // current.next = prev = 1 -> null
        // prev = current = 2 -> 1 -> null
        // current = next = 3 -> 4 -> 5
        
        // Iteration 3:
        // next = current.next = 4 -> 5
        // current.next = prev = 2 -> 1 -> null
        // prev = current = 3 -> 2 -> 1 -> null
        // current = next = 4 -> 5
        
        // Iteration 4:
        // next = current.next = 5
        // current.next = prev = 3 -> 2 -> 1 -> null
        // prev = current = 4 -> 3 -> 2 -> 1 -> null
        // current = next = 5
        
        // Iteration 5:
        // next = current.next = null
        // current.next = prev = 4 -> 3 -> 2 -> 1 -> null
        // prev = current = 5 -> 4 -> 3 -> 2 -> 1 -> null
        // current = next = null
        
        // current is now null, so we exit the loop
        // Return prev = 5 -> 4 -> 3 -> 2 -> 1 -> null
    }
}
