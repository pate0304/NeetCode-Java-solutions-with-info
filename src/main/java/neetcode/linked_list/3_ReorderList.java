package neetcode.linked_list;

/**
 * NeetCode Problem 3 (Linked List): Reorder List
 * 
 * Problem Description:
 * You are given the head of a singly linked list. The list can be represented as:
 * L0 → L1 → L2 → ... → Ln-1 → Ln
 * 
 * Reorder the list to be on the following form:
 * L0 → Ln → L1 → Ln-1 → L2 → Ln-2 → ...
 * 
 * You may not modify the values in the list's nodes. Only nodes themselves may be changed.
 * 
 * Examples:
 * Input: head = [1,2,3,4]
 * Output: [1,4,2,3]
 * 
 * Input: head = [1,2,3,4,5]
 * Output: [1,5,2,4,3]
 * 
 * Constraints:
 * - The number of nodes in the list is in the range [1, 5 * 10^4].
 * - 1 <= Node.val <= 1000
 * 
 * Approach:
 * This problem can be broken down into three steps:
 * 
 * 1. Find the middle of the linked list using the slow and fast pointer technique
 * 2. Reverse the second half of the linked list
 * 3. Merge the first half and the reversed second half
 * 
 * Time Complexity: O(n) where n is the number of nodes in the linked list
 * Space Complexity: O(1) as we're only using a constant amount of extra space
 */
public class ReorderList {
    
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
     * Reorders the linked list according to the problem description.
     * 
     * @param head The head of the linked list
     */
    public void reorderList(ListNode head) {
        // Edge case: empty list or single node
        if (head == null || head.next == null) {
            return;
        }
        
        // Step 1: Find the middle of the linked list
        ListNode slow = head;
        ListNode fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        // At this point, slow points to the middle of the list
        
        // Step 2: Reverse the second half of the linked list
        ListNode prev = null;
        ListNode current = slow;
        ListNode next = null;
        
        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        
        // At this point, prev points to the head of the reversed second half
        
        // Step 3: Merge the first half and the reversed second half
        ListNode first = head;
        ListNode second = prev;
        
        while (second.next != null) {
            // Save the next nodes
            ListNode firstNext = first.next;
            ListNode secondNext = second.next;
            
            // Connect the nodes in the desired order
            first.next = second;
            second.next = firstNext;
            
            // Move the pointers forward
            first = firstNext;
            second = secondNext;
        }
    }
    
    /**
     * Alternative implementation that uses a more explicit approach.
     */
    public void reorderListAlternative(ListNode head) {
        // Edge case: empty list or single node
        if (head == null || head.next == null) {
            return;
        }
        
        // Step 1: Find the middle of the linked list
        ListNode slow = head;
        ListNode fast = head;
        ListNode prev = null;
        
        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        
        // Split the list into two halves
        prev.next = null;
        
        // Step 2: Reverse the second half of the linked list
        ListNode secondHalf = reverseList(slow);
        
        // Step 3: Merge the two halves
        mergeLists(head, secondHalf);
    }
    
    /**
     * Helper method to reverse a linked list.
     */
    private ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;
        ListNode next = null;
        
        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        
        return prev;
    }
    
    /**
     * Helper method to merge two linked lists in alternating order.
     */
    private void mergeLists(ListNode list1, ListNode list2) {
        while (list1 != null && list2 != null) {
            // Save the next nodes
            ListNode list1Next = list1.next;
            ListNode list2Next = list2.next;
            
            // Connect the nodes in the desired order
            list1.next = list2;
            list2.next = list1Next;
            
            // Move the pointers forward
            list1 = list1Next;
            list2 = list2Next;
        }
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        ReorderList solution = new ReorderList();
        
        // Example 1: [1,2,3,4] -> [1,4,2,3]
        ListNode head1 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4))));
        System.out.println("Original List 1: " + head1);
        solution.reorderList(head1);
        System.out.println("Reordered List 1: " + head1);
        
        // Example 2: [1,2,3,4,5] -> [1,5,2,4,3]
        ListNode head2 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        System.out.println("\nOriginal List 2: " + head2);
        solution.reorderListAlternative(head2);
        System.out.println("Reordered List 2: " + head2);
        
        // Let's trace through the execution of Example 1:
        // head = 1 -> 2 -> 3 -> 4
        
        // Step 1: Find the middle of the linked list
        // Initialize slow = 1, fast = 1
        // Iteration 1: slow = 2, fast = 3
        // Iteration 2: slow = 3, fast = null
        // At this point, slow points to 3 (the middle of the list)
        
        // Step 2: Reverse the second half of the linked list
        // Initialize prev = null, current = 3, next = null
        // Iteration 1: next = 4, current.next = null, prev = 3, current = 4
        // Iteration 2: next = null, current.next = 3, prev = 4, current = null
        // At this point, prev points to 4 -> 3 -> null
        
        // Step 3: Merge the first half and the reversed second half
        // Initialize first = 1 -> 2 -> null, second = 4 -> 3 -> null
        // Iteration 1: firstNext = 2, secondNext = 3, first.next = 4, second.next = 2, first = 2, second = 3
        // Iteration 2: firstNext = null, secondNext = null, first.next = 3, second.next = null, first = null, second = null
        // At this point, the merged list is 1 -> 4 -> 2 -> 3 -> null
    }
}
