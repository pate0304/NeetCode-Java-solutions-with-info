package neetcode.linked_list;

/**
 * NeetCode Problem 4 (Linked List): Remove Nth Node From End of List
 * 
 * Problem Description:
 * Given the head of a linked list, remove the nth node from the end of the list and return its head.
 * 
 * Examples:
 * Input: head = [1,2,3,4,5], n = 2
 * Output: [1,2,3,5]
 * 
 * Input: head = [1], n = 1
 * Output: []
 * 
 * Input: head = [1,2], n = 1
 * Output: [1]
 * 
 * Constraints:
 * - The number of nodes in the list is sz.
 * - 1 <= sz <= 30
 * - 0 <= Node.val <= 100
 * - 1 <= n <= sz
 * 
 * Approach:
 * We can solve this problem using the two-pointer technique:
 * 
 * 1. Create a dummy node that points to the head of the list (to handle edge cases)
 * 2. Use two pointers, first and second, initially both pointing to the dummy node
 * 3. Move the first pointer n+1 steps ahead
 * 4. Move both pointers together until the first pointer reaches the end of the list
 * 5. At this point, the second pointer will be at the node just before the one to be removed
 * 6. Remove the nth node from the end by updating the next pointer of the second pointer
 * 7. Return the next of the dummy node (the head of the modified list)
 * 
 * Time Complexity: O(L) where L is the length of the linked list
 * Space Complexity: O(1) as we're only using a constant amount of extra space
 */
public class RemoveNthNodeFromEndOfList {
    
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
     * Removes the nth node from the end of the list.
     * 
     * @param head The head of the linked list
     * @param n The position from the end to remove (1-indexed)
     * @return The head of the modified linked list
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // Create a dummy node to handle edge cases (e.g., removing the head)
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        // Initialize two pointers
        ListNode first = dummy;
        ListNode second = dummy;
        
        // Move the first pointer n+1 steps ahead
        for (int i = 0; i <= n; i++) {
            first = first.next;
            
            // If n is equal to the length of the list, first will be null after n steps
            if (first == null && i < n) {
                // This shouldn't happen given the constraints, but just in case
                return head;
            }
        }
        
        // Move both pointers together until the first pointer reaches the end
        while (first != null) {
            first = first.next;
            second = second.next;
        }
        
        // Remove the nth node from the end
        second.next = second.next.next;
        
        // Return the head of the modified list
        return dummy.next;
    }
    
    /**
     * Alternative implementation that first calculates the length of the list.
     */
    public ListNode removeNthFromEndAlternative(ListNode head, int n) {
        // Create a dummy node to handle edge cases
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        // Calculate the length of the list
        int length = 0;
        ListNode current = head;
        while (current != null) {
            length++;
            current = current.next;
        }
        
        // Find the node just before the one to be removed
        current = dummy;
        for (int i = 0; i < length - n; i++) {
            current = current.next;
        }
        
        // Remove the nth node from the end
        current.next = current.next.next;
        
        // Return the head of the modified list
        return dummy.next;
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        RemoveNthNodeFromEndOfList solution = new RemoveNthNodeFromEndOfList();
        
        // Example 1: [1,2,3,4,5], n = 2 -> [1,2,3,5]
        ListNode head1 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        System.out.println("Original List 1: " + head1);
        ListNode result1 = solution.removeNthFromEnd(head1, 2);
        System.out.println("After removing 2nd node from end: " + result1);
        
        // Example 2: [1], n = 1 -> []
        ListNode head2 = new ListNode(1);
        System.out.println("\nOriginal List 2: " + head2);
        ListNode result2 = solution.removeNthFromEndAlternative(head2, 1);
        System.out.println("After removing 1st node from end: " + result2);
        
        // Example 3: [1,2], n = 1 -> [1]
        ListNode head3 = new ListNode(1, new ListNode(2));
        System.out.println("\nOriginal List 3: " + head3);
        ListNode result3 = solution.removeNthFromEnd(head3, 1);
        System.out.println("After removing 1st node from end: " + result3);
        
        // Let's trace through the execution of Example 1:
        // head = 1 -> 2 -> 3 -> 4 -> 5, n = 2
        
        // Initialize dummy = 0, dummy.next = head
        // Initialize first = dummy, second = dummy
        
        // Move first n+1 steps ahead:
        // Iteration 1: first = 1
        // Iteration 2: first = 2
        // Iteration 3: first = 3
        // At this point, first is at node 3
        
        // Move both pointers together until first reaches the end:
        // Iteration 1: first = 4, second = 1
        // Iteration 2: first = 5, second = 2
        // Iteration 3: first = null, second = 3
        // At this point, second is at node 3, which is just before the node to be removed (node 4)
        
        // Remove the nth node from the end: second.next = second.next.next
        // This changes 3 -> 4 -> 5 to 3 -> 5
        
        // Return dummy.next = 1 -> 2 -> 3 -> 5
    }
}
