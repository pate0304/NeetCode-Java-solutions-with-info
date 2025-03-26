package neetcode.linked_list;

import java.util.PriorityQueue;

/**
 * NeetCode Problem 10 (Linked List): Merge k Sorted Lists
 * 
 * Problem Description:
 * You are given an array of k linked-lists lists, each linked-list is sorted in ascending order.
 * Merge all the linked-lists into one sorted linked-list and return it.
 * 
 * Examples:
 * Input: lists = [[1,4,5],[1,3,4],[2,6]]
 * Output: [1,1,2,3,4,4,5,6]
 * Explanation: The linked-lists are:
 * [
 *   1->4->5,
 *   1->3->4,
 *   2->6
 * ]
 * Merging them into one sorted list:
 * 1->1->2->3->4->4->5->6
 * 
 * Input: lists = []
 * Output: []
 * 
 * Input: lists = [[]]
 * Output: []
 * 
 * Constraints:
 * - k == lists.length
 * - 0 <= k <= 10^4
 * - 0 <= lists[i].length <= 500
 * - -10^4 <= lists[i][j] <= 10^4
 * - lists[i] is sorted in ascending order.
 * - The sum of lists[i].length will not exceed 10^4.
 * 
 * Approach:
 * We can solve this problem using several approaches:
 * 
 * 1. Using a Priority Queue (Min Heap):
 *    - Add the first node from each list to a min heap
 *    - Repeatedly extract the minimum node, add it to the result list, and add the next node from the same list to the heap
 *    - Time Complexity: O(N log k) where N is the total number of nodes and k is the number of lists
 *    - Space Complexity: O(k) for the heap
 * 
 * 2. Using Divide and Conquer:
 *    - Repeatedly merge pairs of lists until only one list remains
 *    - Time Complexity: O(N log k) where N is the total number of nodes and k is the number of lists
 *    - Space Complexity: O(log k) for the recursion stack
 * 
 * 3. Brute Force:
 *    - Merge all lists one by one
 *    - Time Complexity: O(N * k) where N is the total number of nodes and k is the number of lists
 *    - Space Complexity: O(1) extra space
 * 
 * We'll implement the first two approaches as they are more efficient.
 */
public class MergeKSortedLists {
    
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
     * Merges k sorted linked lists using a priority queue (min heap).
     * 
     * @param lists Array of the heads of the k sorted linked lists
     * @return The head of the merged sorted linked list
     */
    public ListNode mergeKLists(ListNode[] lists) {
        // Edge case: empty lists
        if (lists == null || lists.length == 0) {
            return null;
        }
        
        // Create a min heap to store the nodes
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>((a, b) -> a.val - b.val);
        
        // Add the first node from each list to the heap
        for (ListNode list : lists) {
            if (list != null) {
                minHeap.offer(list);
            }
        }
        
        // Create a dummy head for the result list
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        // Process nodes in the heap
        while (!minHeap.isEmpty()) {
            // Extract the minimum node
            ListNode node = minHeap.poll();
            
            // Add it to the result list
            current.next = node;
            current = current.next;
            
            // Add the next node from the same list to the heap
            if (node.next != null) {
                minHeap.offer(node.next);
            }
        }
        
        // Return the head of the merged list
        return dummy.next;
    }
    
    /**
     * Merges k sorted linked lists using the divide and conquer approach.
     * 
     * @param lists Array of the heads of the k sorted linked lists
     * @return The head of the merged sorted linked list
     */
    public ListNode mergeKListsDivideAndConquer(ListNode[] lists) {
        // Edge case: empty lists
        if (lists == null || lists.length == 0) {
            return null;
        }
        
        // Call the recursive helper method
        return mergeKListsHelper(lists, 0, lists.length - 1);
    }
    
    /**
     * Helper method for the divide and conquer approach.
     * 
     * @param lists Array of the heads of the k sorted linked lists
     * @param start Start index
     * @param end End index
     * @return The head of the merged sorted linked list
     */
    private ListNode mergeKListsHelper(ListNode[] lists, int start, int end) {
        // Base case: only one list
        if (start == end) {
            return lists[start];
        }
        
        // Base case: two lists
        if (start + 1 == end) {
            return mergeTwoLists(lists[start], lists[end]);
        }
        
        // Divide and conquer
        int mid = start + (end - start) / 2;
        ListNode left = mergeKListsHelper(lists, start, mid);
        ListNode right = mergeKListsHelper(lists, mid + 1, end);
        
        // Merge the two halves
        return mergeTwoLists(left, right);
    }
    
    /**
     * Merges two sorted linked lists.
     * 
     * @param l1 The head of the first sorted linked list
     * @param l2 The head of the second sorted linked list
     * @return The head of the merged sorted linked list
     */
    private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // Create a dummy head
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        // Merge the two lists
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                current.next = l1;
                l1 = l1.next;
            } else {
                current.next = l2;
                l2 = l2.next;
            }
            current = current.next;
        }
        
        // Attach the remaining nodes
        if (l1 != null) {
            current.next = l1;
        } else {
            current.next = l2;
        }
        
        // Return the head of the merged list
        return dummy.next;
    }
    
    /**
     * Helper method to create a linked list from an array of integers.
     */
    private static ListNode createList(int[] values) {
        if (values == null || values.length == 0) {
            return null;
        }
        
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        for (int value : values) {
            current.next = new ListNode(value);
            current = current.next;
        }
        
        return dummy.next;
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        MergeKSortedLists solution = new MergeKSortedLists();
        
        // Example 1: [[1,4,5],[1,3,4],[2,6]]
        ListNode[] lists1 = new ListNode[3];
        lists1[0] = createList(new int[]{1, 4, 5});
        lists1[1] = createList(new int[]{1, 3, 4});
        lists1[2] = createList(new int[]{2, 6});
        
        System.out.println("Example 1:");
        System.out.println("List 1: " + lists1[0]);
        System.out.println("List 2: " + lists1[1]);
        System.out.println("List 3: " + lists1[2]);
        
        ListNode result1 = solution.mergeKLists(lists1);
        System.out.println("Merged list (Priority Queue): " + result1);
        
        // Reset the lists for the second approach
        lists1[0] = createList(new int[]{1, 4, 5});
        lists1[1] = createList(new int[]{1, 3, 4});
        lists1[2] = createList(new int[]{2, 6});
        
        ListNode result2 = solution.mergeKListsDivideAndConquer(lists1);
        System.out.println("Merged list (Divide and Conquer): " + result2);
        
        // Example 2: []
        ListNode[] lists2 = new ListNode[0];
        System.out.println("\nExample 2:");
        System.out.println("Empty array of lists");
        
        ListNode result3 = solution.mergeKLists(lists2);
        System.out.println("Merged list (Priority Queue): " + (result3 == null ? "null" : result3));
        
        // Example 3: [[]]
        ListNode[] lists3 = new ListNode[1];
        lists3[0] = null;
        System.out.println("\nExample 3:");
        System.out.println("Array with one empty list");
        
        ListNode result4 = solution.mergeKListsDivideAndConquer(lists3);
        System.out.println("Merged list (Divide and Conquer): " + (result4 == null ? "null" : result4));
        
        // Let's trace through the execution of Example 1 using the Priority Queue approach:
        System.out.println("\nTracing Priority Queue approach for Example 1:");
        System.out.println("Lists: [1->4->5], [1->3->4], [2->6]");
        
        System.out.println("1. Initialize min heap and add the first node from each list:");
        System.out.println("   Heap: [1, 1, 2] (values of the nodes)");
        
        System.out.println("2. Extract min (1) from list 1, add to result, add next node (4) to heap:");
        System.out.println("   Result: 1");
        System.out.println("   Heap: [1, 2, 4]");
        
        System.out.println("3. Extract min (1) from list 2, add to result, add next node (3) to heap:");
        System.out.println("   Result: 1->1");
        System.out.println("   Heap: [2, 3, 4]");
        
        System.out.println("4. Extract min (2) from list 3, add to result, add next node (6) to heap:");
        System.out.println("   Result: 1->1->2");
        System.out.println("   Heap: [3, 4, 6]");
        
        System.out.println("5. Extract min (3) from list 2, add to result, add next node (4) to heap:");
        System.out.println("   Result: 1->1->2->3");
        System.out.println("   Heap: [4, 4, 6]");
        
        System.out.println("6. Extract min (4) from list 1, add to result, add next node (5) to heap:");
        System.out.println("   Result: 1->1->2->3->4");
        System.out.println("   Heap: [4, 5, 6]");
        
        System.out.println("7. Extract min (4) from list 2, add to result, no more nodes in list 2:");
        System.out.println("   Result: 1->1->2->3->4->4");
        System.out.println("   Heap: [5, 6]");
        
        System.out.println("8. Extract min (5) from list 1, add to result, no more nodes in list 1:");
        System.out.println("   Result: 1->1->2->3->4->4->5");
        System.out.println("   Heap: [6]");
        
        System.out.println("9. Extract min (6) from list 3, add to result, no more nodes in list 3:");
        System.out.println("   Result: 1->1->2->3->4->4->5->6");
        System.out.println("   Heap: []");
        
        System.out.println("10. Heap is empty, return the result: 1->1->2->3->4->4->5->6");
    }
}
