package neetcode.linked_list;

/**
 * NeetCode Problem 7 (Linked List): Linked List Cycle
 * 
 * Problem Description:
 * Given head, the head of a linked list, determine if the linked list has a cycle in it.
 * 
 * There is a cycle in a linked list if there is some node in the list that can be reached again
 * by continuously following the next pointer. Internally, pos is used to denote the index of the node
 * that tail's next pointer is connected to. Note that pos is not passed as a parameter.
 * 
 * Return true if there is a cycle in the linked list. Otherwise, return false.
 * 
 * Examples:
 * Input: head = [3,2,0,-4], pos = 1
 * Output: true
 * Explanation: There is a cycle in the linked list, where the tail connects to the 1st node (0-indexed).
 * 
 * Input: head = [1,2], pos = 0
 * Output: true
 * Explanation: There is a cycle in the linked list, where the tail connects to the 0th node.
 * 
 * Input: head = [1], pos = -1
 * Output: false
 * Explanation: There is no cycle in the linked list.
 * 
 * Constraints:
 * - The number of the nodes in the list is in the range [0, 10^4].
 * - -10^5 <= Node.val <= 10^5
 * - pos is -1 or a valid index in the linked-list.
 * 
 * Approach:
 * We can solve this problem using the Floyd's Cycle-Finding Algorithm (also known as the "tortoise and hare" algorithm):
 * 
 * 1. Use two pointers, slow and fast, both initially pointing to the head of the list
 * 2. Move the slow pointer one step at a time and the fast pointer two steps at a time
 * 3. If there is a cycle, the fast pointer will eventually meet the slow pointer
 * 4. If there is no cycle, the fast pointer will reach the end of the list
 * 
 * Time Complexity: O(n) where n is the number of nodes in the linked list
 * Space Complexity: O(1) as we're only using two pointers
 */
public class LinkedListCycle {
    
    /**
     * Definition for singly-linked list.
     */
    static class ListNode {
        int val;
        ListNode next;
        
        ListNode(int x) {
            val = x;
            next = null;
        }
    }
    
    /**
     * Determines if the linked list has a cycle using Floyd's Cycle-Finding Algorithm.
     * 
     * @param head The head of the linked list
     * @return true if there is a cycle, false otherwise
     */
    public boolean hasCycle(ListNode head) {
        // Edge case: empty list or single node
        if (head == null || head.next == null) {
            return false;
        }
        
        // Initialize slow and fast pointers
        ListNode slow = head;
        ListNode fast = head;
        
        // Move slow one step and fast two steps at a time
        while (fast != null && fast.next != null) {
            slow = slow.next;          // Move slow one step
            fast = fast.next.next;     // Move fast two steps
            
            // If slow and fast meet, there is a cycle
            if (slow == fast) {
                return true;
            }
        }
        
        // If fast reaches the end, there is no cycle
        return false;
    }
    
    /**
     * Finds the node where the cycle begins, if a cycle exists.
     * 
     * @param head The head of the linked list
     * @return The node where the cycle begins, or null if there is no cycle
     */
    public ListNode detectCycle(ListNode head) {
        // Edge case: empty list or single node
        if (head == null || head.next == null) {
            return null;
        }
        
        // Initialize slow and fast pointers
        ListNode slow = head;
        ListNode fast = head;
        
        // First phase: detect if there is a cycle
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            
            // If slow and fast meet, there is a cycle
            if (slow == fast) {
                // Second phase: find the start of the cycle
                // Reset slow to head
                slow = head;
                
                // Move both pointers one step at a time
                while (slow != fast) {
                    slow = slow.next;
                    fast = fast.next;
                }
                
                // When they meet again, it's at the start of the cycle
                return slow;
            }
        }
        
        // If fast reaches the end, there is no cycle
        return null;
    }
    
    /**
     * Helper method to create a linked list with a cycle for testing.
     */
    private static ListNode createCyclicList(int[] values, int pos) {
        if (values == null || values.length == 0) {
            return null;
        }
        
        // Create nodes
        ListNode[] nodes = new ListNode[values.length];
        for (int i = 0; i < values.length; i++) {
            nodes[i] = new ListNode(values[i]);
        }
        
        // Set next pointers
        for (int i = 0; i < values.length - 1; i++) {
            nodes[i].next = nodes[i + 1];
        }
        
        // Create cycle if pos is valid
        if (pos >= 0 && pos < values.length) {
            nodes[values.length - 1].next = nodes[pos];
        }
        
        return nodes[0];
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        LinkedListCycle solution = new LinkedListCycle();
        
        // Example 1: [3,2,0,-4], pos = 1
        ListNode head1 = createCyclicList(new int[]{3, 2, 0, -4}, 1);
        boolean result1 = solution.hasCycle(head1);
        System.out.println("Example 1: Does the list have a cycle? " + result1);
        
        ListNode cycleStart1 = solution.detectCycle(head1);
        System.out.println("Example 1: Cycle starts at node with value: " + 
                          (cycleStart1 != null ? cycleStart1.val : "N/A"));
        
        // Example 2: [1,2], pos = 0
        ListNode head2 = createCyclicList(new int[]{1, 2}, 0);
        boolean result2 = solution.hasCycle(head2);
        System.out.println("\nExample 2: Does the list have a cycle? " + result2);
        
        ListNode cycleStart2 = solution.detectCycle(head2);
        System.out.println("Example 2: Cycle starts at node with value: " + 
                          (cycleStart2 != null ? cycleStart2.val : "N/A"));
        
        // Example 3: [1], pos = -1
        ListNode head3 = createCyclicList(new int[]{1}, -1);
        boolean result3 = solution.hasCycle(head3);
        System.out.println("\nExample 3: Does the list have a cycle? " + result3);
        
        ListNode cycleStart3 = solution.detectCycle(head3);
        System.out.println("Example 3: Cycle starts at node with value: " + 
                          (cycleStart3 != null ? cycleStart3.val : "N/A"));
        
        // Let's trace through the execution of Example 1:
        // head = [3,2,0,-4], pos = 1
        // The list looks like: 3 -> 2 -> 0 -> -4 -> 2 -> ...
        
        // Initialize slow = head = 3, fast = head = 3
        
        // Iteration 1:
        // slow = slow.next = 2
        // fast = fast.next.next = 0
        // slow != fast, continue
        
        // Iteration 2:
        // slow = slow.next = 0
        // fast = fast.next.next = 2
        // slow != fast, continue
        
        // Iteration 3:
        // slow = slow.next = -4
        // fast = fast.next.next = -4
        // slow == fast, return true
        
        // For finding the cycle start:
        // Reset slow = head = 3
        // fast is still at -4
        
        // Iteration 1:
        // slow = slow.next = 2
        // fast = fast.next = 2
        // slow == fast, return slow (node with value 2)
    }
}
