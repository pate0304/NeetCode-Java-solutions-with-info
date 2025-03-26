package neetcode.linked_list;

/**
 * NeetCode Problem 6 (Linked List): Add Two Numbers
 * 
 * Problem Description:
 * You are given two non-empty linked lists representing two non-negative integers.
 * The digits are stored in reverse order, and each of their nodes contains a single digit.
 * Add the two numbers and return the sum as a linked list.
 * 
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 * 
 * Examples:
 * Input: l1 = [2,4,3], l2 = [5,6,4]
 * Output: [7,0,8]
 * Explanation: 342 + 465 = 807.
 * 
 * Input: l1 = [0], l2 = [0]
 * Output: [0]
 * 
 * Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
 * Output: [8,9,9,9,0,0,0,1]
 * Explanation: 9999999 + 9999 = 10009998.
 * 
 * Constraints:
 * - The number of nodes in each linked list is in the range [1, 100].
 * - 0 <= Node.val <= 9
 * - It is guaranteed that the list represents a number that does not have leading zeros.
 * 
 * Approach:
 * We can solve this problem by simulating the addition process digit by digit:
 * 
 * 1. Initialize a dummy head node to build the result linked list
 * 2. Initialize a carry variable to 0
 * 3. Iterate through both lists simultaneously:
 *    - Get the values of the current nodes (or 0 if a list has ended)
 *    - Calculate the sum: sum = l1.val + l2.val + carry
 *    - Calculate the new carry: carry = sum / 10
 *    - Create a new node with value sum % 10 and add it to the result list
 *    - Move to the next nodes in both lists
 * 4. If there's still a carry after both lists are exhausted, add a new node with value 1
 * 5. Return the next of the dummy head (the actual head of the result list)
 * 
 * Time Complexity: O(max(n, m)) where n and m are the lengths of the two lists
 * Space Complexity: O(max(n, m)) for the result list
 */
public class AddTwoNumbers {
    
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
     * Adds two numbers represented as linked lists.
     * 
     * @param l1 The head of the first linked list
     * @param l2 The head of the second linked list
     * @return The head of the linked list representing the sum
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // Initialize a dummy head node
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        // Initialize the carry
        int carry = 0;
        
        // Iterate through both lists
        while (l1 != null || l2 != null) {
            // Get the values of the current nodes (or 0 if a list has ended)
            int x = (l1 != null) ? l1.val : 0;
            int y = (l2 != null) ? l2.val : 0;
            
            // Calculate the sum and the new carry
            int sum = x + y + carry;
            carry = sum / 10;
            
            // Create a new node with value sum % 10 and add it to the result list
            current.next = new ListNode(sum % 10);
            current = current.next;
            
            // Move to the next nodes in both lists
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }
        
        // If there's still a carry, add a new node
        if (carry > 0) {
            current.next = new ListNode(carry);
        }
        
        // Return the head of the result list
        return dummy.next;
    }
    
    /**
     * Alternative implementation that avoids using a dummy node.
     */
    public ListNode addTwoNumbersAlternative(ListNode l1, ListNode l2) {
        // Edge cases
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        
        // Initialize the head of the result list
        ListNode head = null;
        ListNode current = null;
        
        // Initialize the carry
        int carry = 0;
        
        // Iterate through both lists
        while (l1 != null || l2 != null) {
            // Get the values of the current nodes (or 0 if a list has ended)
            int x = (l1 != null) ? l1.val : 0;
            int y = (l2 != null) ? l2.val : 0;
            
            // Calculate the sum and the new carry
            int sum = x + y + carry;
            carry = sum / 10;
            
            // Create a new node with value sum % 10
            ListNode newNode = new ListNode(sum % 10);
            
            // If this is the first node, set it as the head
            if (head == null) {
                head = newNode;
                current = head;
            } else {
                // Otherwise, add it to the end of the result list
                current.next = newNode;
                current = current.next;
            }
            
            // Move to the next nodes in both lists
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }
        
        // If there's still a carry, add a new node
        if (carry > 0) {
            current.next = new ListNode(carry);
        }
        
        // Return the head of the result list
        return head;
    }
    
    /**
     * Helper method to create a linked list from an array of digits.
     */
    private static ListNode createList(int[] digits) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        for (int digit : digits) {
            current.next = new ListNode(digit);
            current = current.next;
        }
        
        return dummy.next;
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        AddTwoNumbers solution = new AddTwoNumbers();
        
        // Example 1: [2,4,3] + [5,6,4] = [7,0,8]
        ListNode l1 = createList(new int[]{2, 4, 3});
        ListNode l2 = createList(new int[]{5, 6, 4});
        System.out.println("List 1: " + l1);
        System.out.println("List 2: " + l2);
        ListNode result1 = solution.addTwoNumbers(l1, l2);
        System.out.println("Sum: " + result1);
        
        // Example 2: [0] + [0] = [0]
        ListNode l3 = createList(new int[]{0});
        ListNode l4 = createList(new int[]{0});
        System.out.println("\nList 3: " + l3);
        System.out.println("List 4: " + l4);
        ListNode result2 = solution.addTwoNumbersAlternative(l3, l4);
        System.out.println("Sum: " + result2);
        
        // Example 3: [9,9,9,9,9,9,9] + [9,9,9,9] = [8,9,9,9,0,0,0,1]
        ListNode l5 = createList(new int[]{9, 9, 9, 9, 9, 9, 9});
        ListNode l6 = createList(new int[]{9, 9, 9, 9});
        System.out.println("\nList 5: " + l5);
        System.out.println("List 6: " + l6);
        ListNode result3 = solution.addTwoNumbers(l5, l6);
        System.out.println("Sum: " + result3);
        
        // Let's trace through the execution of Example 1:
        // l1 = [2,4,3], l2 = [5,6,4]
        
        // Initialize dummy = [0], current = dummy, carry = 0
        
        // Iteration 1:
        // x = 2, y = 5
        // sum = 2 + 5 + 0 = 7, carry = 7 / 10 = 0
        // current.next = [7], current = [7]
        // l1 = [4,3], l2 = [6,4]
        
        // Iteration 2:
        // x = 4, y = 6
        // sum = 4 + 6 + 0 = 10, carry = 10 / 10 = 1
        // current.next = [0], current = [0]
        // l1 = [3], l2 = [4]
        
        // Iteration 3:
        // x = 3, y = 4
        // sum = 3 + 4 + 1 = 8, carry = 8 / 10 = 0
        // current.next = [8], current = [8]
        // l1 = null, l2 = null
        
        // Both lists are exhausted and carry = 0, so we're done
        // Return dummy.next = [7,0,8]
    }
}
