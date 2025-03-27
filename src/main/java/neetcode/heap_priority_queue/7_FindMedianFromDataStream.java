package neetcode.heap_priority_queue;

import java.util.PriorityQueue;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

/**
 * NeetCode Problem 7 (Heap/Priority Queue): Find Median from Data Stream
 * 
 * Problem Description:
 * The median is the middle value in an ordered integer list. If the size of the list is even,
 * there is no middle value, and the median is the mean of the two middle values.
 * 
 * For example, for arr = [2,3,4], the median is 3.
 * For example, for arr = [2,3], the median is (2 + 3) / 2 = 2.5.
 * 
 * Implement the MedianFinder class:
 * - MedianFinder() initializes the MedianFinder object.
 * - void addNum(int num) adds the integer num from the data stream to the data structure.
 * - double findMedian() returns the median of all elements so far.
 * 
 * Examples:
 * Input:
 * ["MedianFinder", "addNum", "addNum", "findMedian", "addNum", "findMedian"]
 * [[], [1], [2], [], [3], []]
 * 
 * Output:
 * [null, null, null, 1.5, null, 2.0]
 * 
 * Explanation:
 * MedianFinder medianFinder = new MedianFinder();
 * medianFinder.addNum(1);    // arr = [1]
 * medianFinder.addNum(2);    // arr = [1, 2]
 * medianFinder.findMedian(); // return 1.5 (i.e., (1 + 2) / 2)
 * medianFinder.addNum(3);    // arr = [1, 2, 3]
 * medianFinder.findMedian(); // return 2.0
 * 
 * Constraints:
 * - -10^5 <= num <= 10^5
 * - There will be at least one element in the data structure before calling findMedian.
 * - At most 5 * 10^4 calls will be made to addNum and findMedian.
 * 
 * Approach:
 * We can use two heaps to efficiently find the median:
 * 
 * 1. A max-heap to store the smaller half of the numbers.
 * 2. A min-heap to store the larger half of the numbers.
 * 
 * We maintain the following invariants:
 * - The max-heap contains the smaller half of the numbers.
 * - The min-heap contains the larger half of the numbers.
 * - The sizes of the two heaps differ by at most 1.
 * 
 * When adding a number:
 * - If the max-heap is empty or the number is less than the top of the max-heap, add it to the max-heap.
 * - Otherwise, add it to the min-heap.
 * - Balance the heaps if necessary to maintain the size invariant.
 * 
 * When finding the median:
 * - If the sizes are equal, return the average of the tops of both heaps.
 * - Otherwise, return the top of the heap with more elements.
 * 
 * Time Complexity:
 * - addNum: O(log n) for heap operations
 * - findMedian: O(1) to access the tops of the heaps
 * 
 * Space Complexity: O(n) to store all the numbers
 */
public class FindMedianFromDataStream {
    
    /**
     * Main class that implements the MedianFinder functionality.
     */
    static class MedianFinder {
        // Max-heap for the smaller half of the numbers
        private PriorityQueue<Integer> smallerHalf;
        
        // Min-heap for the larger half of the numbers
        private PriorityQueue<Integer> largerHalf;
        
        /**
         * Initialize your data structure here.
         */
        public MedianFinder() {
            // Initialize the max-heap for the smaller half
            smallerHalf = new PriorityQueue<>(Collections.reverseOrder());
            
            // Initialize the min-heap for the larger half
            largerHalf = new PriorityQueue<>();
        }
        
        /**
         * Adds a number into the data structure.
         * 
         * @param num The number to add
         */
        public void addNum(int num) {
            // If the max-heap is empty or the number is less than the top of the max-heap,
            // add it to the max-heap (smaller half)
            if (smallerHalf.isEmpty() || num <= smallerHalf.peek()) {
                smallerHalf.offer(num);
            } else {
                // Otherwise, add it to the min-heap (larger half)
                largerHalf.offer(num);
            }
            
            // Balance the heaps if necessary
            // If the max-heap has more than one more element than the min-heap,
            // move the top element from the max-heap to the min-heap
            if (smallerHalf.size() > largerHalf.size() + 1) {
                largerHalf.offer(smallerHalf.poll());
            }
            // If the min-heap has more elements than the max-heap,
            // move the top element from the min-heap to the max-heap
            else if (largerHalf.size() > smallerHalf.size()) {
                smallerHalf.offer(largerHalf.poll());
            }
        }
        
        /**
         * Returns the median of all elements so far.
         * 
         * @return The median
         */
        public double findMedian() {
            // If the max-heap has more elements, the median is the top of the max-heap
            if (smallerHalf.size() > largerHalf.size()) {
                return smallerHalf.peek();
            }
            // Otherwise, the median is the average of the tops of both heaps
            else {
                return (smallerHalf.peek() + largerHalf.peek()) / 2.0;
            }
        }
    }
    
    /**
     * Alternative implementation using a single sorted list.
     * This is less efficient for large data streams but simpler to understand.
     */
    static class MedianFinderWithList {
        // List to store all numbers
        private List<Integer> numbers;
        
        /**
         * Initialize your data structure here.
         */
        public MedianFinderWithList() {
            numbers = new ArrayList<>();
        }
        
        /**
         * Adds a number into the data structure.
         * 
         * @param num The number to add
         */
        public void addNum(int num) {
            // Find the position to insert the number to maintain the sorted order
            int insertPos = findInsertPosition(num);
            numbers.add(insertPos, num);
        }
        
        /**
         * Returns the median of all elements so far.
         * 
         * @return The median
         */
        public double findMedian() {
            int size = numbers.size();
            
            // If the size is odd, return the middle element
            if (size % 2 == 1) {
                return numbers.get(size / 2);
            }
            // If the size is even, return the average of the two middle elements
            else {
                return (numbers.get(size / 2 - 1) + numbers.get(size / 2)) / 2.0;
            }
        }
        
        /**
         * Finds the position to insert the number to maintain the sorted order.
         * 
         * @param num The number to insert
         * @return The position to insert the number
         */
        private int findInsertPosition(int num) {
            int left = 0;
            int right = numbers.size() - 1;
            
            // Binary search to find the insert position
            while (left <= right) {
                int mid = left + (right - left) / 2;
                
                if (numbers.get(mid) < num) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            
            return left;
        }
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        System.out.println("Example using the two-heap implementation:");
        
        MedianFinder medianFinder = new MedianFinder();
        
        // Add 1
        medianFinder.addNum(1);
        System.out.println("After adding 1:");
        System.out.println("Smaller half (max-heap): " + medianFinder.smallerHalf);
        System.out.println("Larger half (min-heap): " + medianFinder.largerHalf);
        System.out.println("Median: " + medianFinder.findMedian());
        
        // Add 2
        medianFinder.addNum(2);
        System.out.println("\nAfter adding 2:");
        System.out.println("Smaller half (max-heap): " + medianFinder.smallerHalf);
        System.out.println("Larger half (min-heap): " + medianFinder.largerHalf);
        System.out.println("Median: " + medianFinder.findMedian());
        
        // Add 3
        medianFinder.addNum(3);
        System.out.println("\nAfter adding 3:");
        System.out.println("Smaller half (max-heap): " + medianFinder.smallerHalf);
        System.out.println("Larger half (min-heap): " + medianFinder.largerHalf);
        System.out.println("Median: " + medianFinder.findMedian());
        
        System.out.println("\nExample using the list implementation:");
        
        MedianFinderWithList medianFinderWithList = new MedianFinderWithList();
        
        // Add 1
        medianFinderWithList.addNum(1);
        System.out.println("After adding 1:");
        System.out.println("Numbers: " + medianFinderWithList.numbers);
        System.out.println("Median: " + medianFinderWithList.findMedian());
        
        // Add 2
        medianFinderWithList.addNum(2);
        System.out.println("\nAfter adding 2:");
        System.out.println("Numbers: " + medianFinderWithList.numbers);
        System.out.println("Median: " + medianFinderWithList.findMedian());
        
        // Add 3
        medianFinderWithList.addNum(3);
        System.out.println("\nAfter adding 3:");
        System.out.println("Numbers: " + medianFinderWithList.numbers);
        System.out.println("Median: " + medianFinderWithList.findMedian());
        
        // Additional test case with more numbers
        System.out.println("\nAdditional test case:");
        
        MedianFinder medianFinder2 = new MedianFinder();
        
        // Add numbers
        int[] nums = {41, 35, 62, 5, 97, 108};
        for (int num : nums) {
            medianFinder2.addNum(num);
            System.out.println("After adding " + num + ":");
            System.out.println("Smaller half (max-heap): " + medianFinder2.smallerHalf);
            System.out.println("Larger half (min-heap): " + medianFinder2.largerHalf);
            System.out.println("Median: " + medianFinder2.findMedian());
            System.out.println();
        }
        
        // Tracing the execution
        System.out.println("Tracing the execution of the example:");
        System.out.println("1. Initialize MedianFinder");
        System.out.println("   - smallerHalf (max-heap): []");
        System.out.println("   - largerHalf (min-heap): []");
        
        System.out.println("2. Add 1");
        System.out.println("   - 1 is added to smallerHalf: [1]");
        System.out.println("   - largerHalf: []");
        System.out.println("   - Median: 1.0");
        
        System.out.println("3. Add 2");
        System.out.println("   - 2 > 1, so 2 is added to largerHalf: [2]");
        System.out.println("   - smallerHalf: [1]");
        System.out.println("   - Both heaps have the same size, so median is (1 + 2) / 2 = 1.5");
        
        System.out.println("4. Add 3");
        System.out.println("   - 3 > 1, so 3 is added to largerHalf: [2, 3]");
        System.out.println("   - largerHalf has more elements, so move the smallest (2) to smallerHalf");
        System.out.println("   - smallerHalf: [2, 1]");
        System.out.println("   - largerHalf: [3]");
        System.out.println("   - Both heaps have the same size, so median is (2 + 3) / 2 = 2.5");
        
        // Visualize the heaps
        System.out.println("\nVisualization of the heaps after adding 1, 2, and 3:");
        System.out.println("smallerHalf (max-heap):    largerHalf (min-heap):");
        System.out.println("       2                          3       ");
        System.out.println("      /                                  ");
        System.out.println("     1                                   ");
        
        // Explain the intuition
        System.out.println("\nIntuition behind the two-heap approach:");
        System.out.println("- The max-heap stores the smaller half of the numbers, with the largest of them at the top.");
        System.out.println("- The min-heap stores the larger half of the numbers, with the smallest of them at the top.");
        System.out.println("- If both heaps have the same size, the median is the average of their tops.");
        System.out.println("- If the max-heap has one more element, the median is its top.");
        System.out.println("- We maintain the balance between the heaps to ensure these properties hold.");
    }
}
