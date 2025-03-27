package neetcode.heap_priority_queue;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * NeetCode Problem 4 (Heap/Priority Queue): Kth Largest Element in an Array
 * 
 * Problem Description:
 * Given an integer array nums and an integer k, return the kth largest element in the array.
 * 
 * Note that it is the kth largest element in the sorted order, not the kth distinct element.
 * 
 * You must solve it in O(n) time complexity.
 * 
 * Examples:
 * Input: nums = [3,2,1,5,6,4], k = 2
 * Output: 5
 * 
 * Input: nums = [3,2,3,1,2,4,5,5,6], k = 4
 * Output: 4
 * 
 * Constraints:
 * - 1 <= k <= nums.length <= 10^5
 * - -10^4 <= nums[i] <= 10^4
 * 
 * Approach:
 * We can solve this problem using several approaches:
 * 
 * 1. Using a Min-Heap (Priority Queue):
 *    - Maintain a min-heap of size k.
 *    - Iterate through the array, adding each element to the heap.
 *    - If the heap size exceeds k, remove the smallest element.
 *    - At the end, the smallest element in the heap is the kth largest element.
 * 
 * 2. Using Sorting:
 *    - Sort the array in descending order.
 *    - Return the element at index k-1.
 * 
 * 3. Using QuickSelect (most efficient for O(n) average time complexity):
 *    - Use the QuickSelect algorithm to find the kth largest element.
 *    - This has an average time complexity of O(n).
 * 
 * Time Complexity:
 * - Min-Heap approach: O(n log k)
 * - Sorting approach: O(n log n)
 * - QuickSelect approach: O(n) on average, O(n^2) in the worst case
 * 
 * Space Complexity:
 * - Min-Heap approach: O(k)
 * - Sorting approach: O(1) (excluding the input array)
 * - QuickSelect approach: O(1) (excluding the input array)
 */
public class KthLargestElementInArray {
    
    /**
     * Finds the kth largest element in the array using a min-heap.
     * 
     * @param nums The array of integers
     * @param k The value of k
     * @return The kth largest element
     */
    public int findKthLargest(int[] nums, int k) {
        // Create a min-heap
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        
        // Add elements to the heap, maintaining a size of k
        for (int num : nums) {
            minHeap.offer(num);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }
        
        // The root of the heap is the kth largest element
        return minHeap.peek();
    }
    
    /**
     * Finds the kth largest element in the array using sorting.
     * 
     * @param nums The array of integers
     * @param k The value of k
     * @return The kth largest element
     */
    public int findKthLargestSorting(int[] nums, int k) {
        // Sort the array in descending order
        Arrays.sort(nums);
        
        // Return the kth largest element
        return nums[nums.length - k];
    }
    
    /**
     * Finds the kth largest element in the array using QuickSelect.
     * 
     * @param nums The array of integers
     * @param k The value of k
     * @return The kth largest element
     */
    public int findKthLargestQuickSelect(int[] nums, int k) {
        // Convert k to 0-indexed (the kth largest is the (n-k)th smallest)
        int targetIndex = nums.length - k;
        
        // Perform QuickSelect to find the kth largest element
        return quickSelect(nums, 0, nums.length - 1, targetIndex);
    }
    
    /**
     * QuickSelect algorithm to find the kth smallest element.
     * 
     * @param nums The array of integers
     * @param left The left index
     * @param right The right index
     * @param k The index of the element to find
     * @return The kth smallest element
     */
    private int quickSelect(int[] nums, int left, int right, int k) {
        // Base case: array contains only one element
        if (left == right) {
            return nums[left];
        }
        
        // Choose a random pivot to avoid worst-case performance
        int pivotIndex = left + new Random().nextInt(right - left + 1);
        
        // Partition the array and get the position of the pivot
        pivotIndex = partition(nums, left, right, pivotIndex);
        
        // If the pivot is at index k, we've found the kth smallest element
        if (pivotIndex == k) {
            return nums[pivotIndex];
        }
        // If the pivot is to the right of k, search the left subarray
        else if (pivotIndex > k) {
            return quickSelect(nums, left, pivotIndex - 1, k);
        }
        // If the pivot is to the left of k, search the right subarray
        else {
            return quickSelect(nums, pivotIndex + 1, right, k);
        }
    }
    
    /**
     * Partitions the array around a pivot.
     * 
     * @param nums The array of integers
     * @param left The left index
     * @param right The right index
     * @param pivotIndex The index of the pivot
     * @return The index of the pivot after partitioning
     */
    private int partition(int[] nums, int left, int right, int pivotIndex) {
        // Get the pivot value
        int pivotValue = nums[pivotIndex];
        
        // Move the pivot to the end
        swap(nums, pivotIndex, right);
        
        // Index where elements less than the pivot will be placed
        int storeIndex = left;
        
        // Traverse the array and move elements less than the pivot to the left
        for (int i = left; i < right; i++) {
            if (nums[i] < pivotValue) {
                swap(nums, i, storeIndex);
                storeIndex++;
            }
        }
        
        // Move the pivot to its final position
        swap(nums, storeIndex, right);
        
        return storeIndex;
    }
    
    /**
     * Swaps two elements in the array.
     * 
     * @param nums The array of integers
     * @param i The index of the first element
     * @param j The index of the second element
     */
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        KthLargestElementInArray solution = new KthLargestElementInArray();
        
        // Example 1
        int[] nums1 = {3, 2, 1, 5, 6, 4};
        int k1 = 2;
        
        System.out.println("Example 1:");
        System.out.println("Array: " + Arrays.toString(nums1));
        System.out.println("k: " + k1);
        
        int result1 = solution.findKthLargest(nums1, k1);
        System.out.println("kth largest element (min-heap): " + result1);
        
        int result1Sorting = solution.findKthLargestSorting(nums1.clone(), k1);
        System.out.println("kth largest element (sorting): " + result1Sorting);
        
        int result1QuickSelect = solution.findKthLargestQuickSelect(nums1.clone(), k1);
        System.out.println("kth largest element (QuickSelect): " + result1QuickSelect);
        
        // Example 2
        int[] nums2 = {3, 2, 3, 1, 2, 4, 5, 5, 6};
        int k2 = 4;
        
        System.out.println("\nExample 2:");
        System.out.println("Array: " + Arrays.toString(nums2));
        System.out.println("k: " + k2);
        
        int result2 = solution.findKthLargest(nums2, k2);
        System.out.println("kth largest element (min-heap): " + result2);
        
        int result2Sorting = solution.findKthLargestSorting(nums2.clone(), k2);
        System.out.println("kth largest element (sorting): " + result2Sorting);
        
        int result2QuickSelect = solution.findKthLargestQuickSelect(nums2.clone(), k2);
        System.out.println("kth largest element (QuickSelect): " + result2QuickSelect);
        
        // Let's trace through the execution of Example 1 using the min-heap approach
        System.out.println("\nTracing the execution of Example 1 using the min-heap approach:");
        System.out.println("Array: [3, 2, 1, 5, 6, 4]");
        System.out.println("k: 2");
        
        System.out.println("1. Create a min-heap");
        System.out.println("2. Process element 3:");
        System.out.println("   - Add to heap: [3]");
        System.out.println("3. Process element 2:");
        System.out.println("   - Add to heap: [2, 3]");
        System.out.println("   - Heap size exceeds k, remove the smallest element: 2");
        System.out.println("   - Heap: [3]");
        System.out.println("4. Process element 1:");
        System.out.println("   - Add to heap: [1, 3]");
        System.out.println("   - Heap size exceeds k, remove the smallest element: 1");
        System.out.println("   - Heap: [3]");
        System.out.println("5. Process element 5:");
        System.out.println("   - Add to heap: [3, 5]");
        System.out.println("   - Heap size exceeds k, remove the smallest element: 3");
        System.out.println("   - Heap: [5]");
        System.out.println("6. Process element 6:");
        System.out.println("   - Add to heap: [5, 6]");
        System.out.println("   - Heap size exceeds k, remove the smallest element: 5");
        System.out.println("   - Heap: [6]");
        System.out.println("7. Process element 4:");
        System.out.println("   - Add to heap: [4, 6]");
        System.out.println("   - Heap size exceeds k, remove the smallest element: 4");
        System.out.println("   - Heap: [6]");
        System.out.println("8. The root of the heap is 5, which is the 2nd largest element");
        
        // Visualize the sorted array
        System.out.println("\nVisualization of the sorted array:");
        System.out.println("Sorted array: [1, 2, 3, 4, 5, 6]");
        System.out.println("                          ^");
        System.out.println("                          |");
        System.out.println("                   2nd largest (k=2)");
    }
}
