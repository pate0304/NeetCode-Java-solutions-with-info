package neetcode.heap_priority_queue;

import java.util.PriorityQueue;

/**
 * NeetCode Problem 1 (Heap/Priority Queue): Kth Largest Element in a Stream
 * 
 * Problem Description:
 * Design a class to find the kth largest element in a stream. Note that it is the kth largest element
 * in the sorted order, not the kth distinct element.
 * 
 * Implement KthLargest class:
 * - KthLargest(int k, int[] nums) Initializes the object with the integer k and the stream of integers nums.
 * - int add(int val) Appends the integer val to the stream and returns the element representing the kth largest element in the stream.
 * 
 * Examples:
 * Input:
 * ["KthLargest", "add", "add", "add", "add", "add"]
 * [[3, [4, 5, 8, 2]], [3], [5], [10], [9], [4]]
 * Output:
 * [null, 4, 5, 5, 8, 8]
 * 
 * Explanation:
 * KthLargest kthLargest = new KthLargest(3, [4, 5, 8, 2]);
 * kthLargest.add(3);   // return 4
 * kthLargest.add(5);   // return 5
 * kthLargest.add(10);  // return 5
 * kthLargest.add(9);   // return 8
 * kthLargest.add(4);   // return 8
 * 
 * Constraints:
 * - 1 <= k <= 10^4
 * - 0 <= nums.length <= 10^4
 * - -10^4 <= nums[i] <= 10^4
 * - -10^4 <= val <= 10^4
 * - At most 10^4 calls will be made to add.
 * - It is guaranteed that there will be at least k elements in the array when you search for the kth element.
 * 
 * Approach:
 * We can use a min-heap (priority queue) to efficiently keep track of the k largest elements:
 * 
 * 1. Initialize a min-heap with a capacity of k.
 * 2. Add all elements from the initial array to the heap.
 * 3. If the heap size exceeds k, remove the smallest element (the root of the min-heap).
 * 4. When adding a new element:
 *    - Add it to the heap.
 *    - If the heap size exceeds k, remove the smallest element.
 *    - The kth largest element is now at the root of the heap.
 * 
 * Time Complexity:
 * - Constructor: O(n log k) where n is the length of the initial array
 * - add: O(log k)
 * 
 * Space Complexity: O(k) for storing the k largest elements
 */
public class KthLargestElementInStream {
    
    /**
     * Implementation of the KthLargest class.
     */
    static class KthLargest {
        private final int k;
        private final PriorityQueue<Integer> minHeap;
        
        /**
         * Initializes the object with the integer k and the stream of integers nums.
         * 
         * @param k The value of k
         * @param nums The initial array of integers
         */
        public KthLargest(int k, int[] nums) {
            this.k = k;
            this.minHeap = new PriorityQueue<>(k);
            
            // Add all elements from the initial array
            for (int num : nums) {
                add(num);
            }
        }
        
        /**
         * Appends the integer val to the stream and returns the element representing
         * the kth largest element in the stream.
         * 
         * @param val The value to add
         * @return The kth largest element in the stream
         */
        public int add(int val) {
            // Add the new value to the heap
            minHeap.offer(val);
            
            // If the heap size exceeds k, remove the smallest element
            if (minHeap.size() > k) {
                minHeap.poll();
            }
            
            // The kth largest element is at the root of the heap
            return minHeap.peek();
        }
    }
    
    /**
     * Alternative implementation using a custom binary heap.
     * This is provided for educational purposes to understand how a heap works internally.
     */
    static class KthLargestWithCustomHeap {
        private final int k;
        private final int[] heap;
        private int size;
        
        public KthLargestWithCustomHeap(int k, int[] nums) {
            this.k = k;
            this.heap = new int[k + 1]; // 1-indexed heap
            this.size = 0;
            
            // Add all elements from the initial array
            for (int num : nums) {
                add(num);
            }
        }
        
        public int add(int val) {
            // If the heap is not full, add the new value
            if (size < k) {
                heap[++size] = val;
                siftUp(size);
            }
            // If the new value is larger than the smallest element in the heap, replace it
            else if (val > heap[1]) {
                heap[1] = val;
                siftDown(1);
            }
            
            // Return the kth largest element
            return heap[1];
        }
        
        private void siftUp(int index) {
            int temp = heap[index];
            
            while (index > 1 && temp < heap[index / 2]) {
                heap[index] = heap[index / 2];
                index /= 2;
            }
            
            heap[index] = temp;
        }
        
        private void siftDown(int index) {
            int temp = heap[index];
            int child;
            
            while (2 * index <= size) {
                child = 2 * index;
                
                if (child < size && heap[child] > heap[child + 1]) {
                    child++;
                }
                
                if (temp <= heap[child]) {
                    break;
                }
                
                heap[index] = heap[child];
                index = child;
            }
            
            heap[index] = temp;
        }
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        // Example from the problem statement
        int k = 3;
        int[] nums = {4, 5, 8, 2};
        
        System.out.println("Example with Java's PriorityQueue:");
        System.out.println("Initialize KthLargest with k = " + k + " and nums = " + java.util.Arrays.toString(nums));
        
        KthLargest kthLargest = new KthLargest(k, nums);
        
        int result1 = kthLargest.add(3);
        System.out.println("Add 3, return " + result1);
        
        int result2 = kthLargest.add(5);
        System.out.println("Add 5, return " + result2);
        
        int result3 = kthLargest.add(10);
        System.out.println("Add 10, return " + result3);
        
        int result4 = kthLargest.add(9);
        System.out.println("Add 9, return " + result4);
        
        int result5 = kthLargest.add(4);
        System.out.println("Add 4, return " + result5);
        
        // Additional example with custom heap implementation
        System.out.println("\nExample with custom heap implementation:");
        System.out.println("Initialize KthLargest with k = " + k + " and nums = " + java.util.Arrays.toString(nums));
        
        KthLargestWithCustomHeap kthLargestCustom = new KthLargestWithCustomHeap(k, nums);
        
        int resultCustom1 = kthLargestCustom.add(3);
        System.out.println("Add 3, return " + resultCustom1);
        
        int resultCustom2 = kthLargestCustom.add(5);
        System.out.println("Add 5, return " + resultCustom2);
        
        int resultCustom3 = kthLargestCustom.add(10);
        System.out.println("Add 10, return " + resultCustom3);
        
        int resultCustom4 = kthLargestCustom.add(9);
        System.out.println("Add 9, return " + resultCustom4);
        
        int resultCustom5 = kthLargestCustom.add(4);
        System.out.println("Add 4, return " + resultCustom5);
        
        // Let's trace through the execution of the example
        System.out.println("\nTracing the execution of the example:");
        System.out.println("1. Initialize KthLargest with k = 3 and nums = [4, 5, 8, 2]");
        System.out.println("   - Create a min-heap with capacity 3");
        System.out.println("   - Add 4: heap = [4]");
        System.out.println("   - Add 5: heap = [4, 5]");
        System.out.println("   - Add 8: heap = [4, 5, 8]");
        System.out.println("   - Add 2: heap = [2, 4, 8], then remove smallest: heap = [4, 5, 8]");
        System.out.println("   - The 3rd largest element is 4");
        
        System.out.println("2. Add 3:");
        System.out.println("   - Add 3: heap = [3, 4, 5, 8], then remove smallest: heap = [4, 5, 8]");
        System.out.println("   - The 3rd largest element is 4");
        
        System.out.println("3. Add 5:");
        System.out.println("   - Add 5: heap = [4, 5, 5, 8], then remove smallest: heap = [5, 5, 8]");
        System.out.println("   - The 3rd largest element is 5");
        
        System.out.println("4. Add 10:");
        System.out.println("   - Add 10: heap = [5, 5, 8, 10], then remove smallest: heap = [5, 8, 10]");
        System.out.println("   - The 3rd largest element is 5");
        
        System.out.println("5. Add 9:");
        System.out.println("   - Add 9: heap = [5, 8, 9, 10], then remove smallest: heap = [8, 9, 10]");
        System.out.println("   - The 3rd largest element is 8");
        
        System.out.println("6. Add 4:");
        System.out.println("   - Add 4: heap = [4, 8, 9, 10], then remove smallest: heap = [8, 9, 10]");
        System.out.println("   - The 3rd largest element is 8");
        
        // Visualize the min-heap
        System.out.println("\nVisualization of the min-heap after all operations:");
        System.out.println("    8    <- Root (3rd largest element)");
        System.out.println("   / \\");
        System.out.println("  9  10   <- Larger elements");
    }
}
