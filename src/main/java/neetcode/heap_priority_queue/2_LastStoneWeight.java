package neetcode.heap_priority_queue;

import java.util.PriorityQueue;

/**
 * NeetCode Problem 2 (Heap/Priority Queue): Last Stone Weight
 * 
 * Problem Description:
 * You are given an array of integers stones where stones[i] is the weight of the ith stone.
 * 
 * We are playing a game with the stones. On each turn, we choose the heaviest two stones and smash them together.
 * Suppose the heaviest two stones have weights x and y with x <= y. The result of this smash is:
 * - If x == y, both stones are destroyed.
 * - If x != y, the stone of weight x is destroyed, and the stone of weight y has its weight reduced to y - x.
 * 
 * At the end of the game, there is at most one stone left. Return the weight of the last remaining stone.
 * If there are no stones left, return 0.
 * 
 * Examples:
 * Input: stones = [2,7,4,1,8,1]
 * Output: 1
 * Explanation:
 * We combine 7 and 8 to get 1, so the array converts to [2,4,1,1,1].
 * We combine 2 and 4 to get 2, so the array converts to [2,1,1,1].
 * We combine 2 and 1 to get 1, so the array converts to [1,1,1].
 * We combine 1 and 1 to get 0, so the array converts to [1].
 * There is only one stone left, and its weight is 1.
 * 
 * Input: stones = [1]
 * Output: 1
 * Explanation: There is only one stone, and its weight is 1.
 * 
 * Constraints:
 * - 1 <= stones.length <= 30
 * - 1 <= stones[i] <= 1000
 * 
 * Approach:
 * We can use a max-heap (priority queue) to efficiently find and remove the two heaviest stones:
 * 
 * 1. Add all stones to a max-heap.
 * 2. While there are at least two stones in the heap:
 *    - Remove the two heaviest stones.
 *    - If they have different weights, add the difference back to the heap.
 * 3. If there's a stone left in the heap, return its weight; otherwise, return 0.
 * 
 * Time Complexity: O(n log n) where n is the number of stones
 * - Building the heap: O(n)
 * - Each operation (removing two stones and potentially adding one back): O(log n)
 * - In the worst case, we perform O(n) such operations
 * 
 * Space Complexity: O(n) for the heap
 */
public class LastStoneWeight {
    
    /**
     * Finds the weight of the last remaining stone after all smashes.
     * 
     * @param stones The array of stone weights
     * @return The weight of the last remaining stone, or 0 if no stones remain
     */
    public int lastStoneWeight(int[] stones) {
        // Create a max-heap (by default, PriorityQueue in Java is a min-heap,
        // so we use a custom comparator to make it a max-heap)
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        
        // Add all stones to the heap
        for (int stone : stones) {
            maxHeap.offer(stone);
        }
        
        // Smash stones until at most one remains
        while (maxHeap.size() > 1) {
            // Remove the two heaviest stones
            int y = maxHeap.poll(); // Heaviest
            int x = maxHeap.poll(); // Second heaviest
            
            // If they have different weights, add the difference back to the heap
            if (x != y) {
                maxHeap.offer(y - x);
            }
        }
        
        // Return the weight of the last remaining stone, or 0 if no stones remain
        return maxHeap.isEmpty() ? 0 : maxHeap.peek();
    }
    
    /**
     * Alternative implementation using a custom binary heap.
     * This is provided for educational purposes to understand how a max-heap works internally.
     */
    public int lastStoneWeightWithCustomHeap(int[] stones) {
        // Create a custom max-heap
        MaxHeap maxHeap = new MaxHeap(stones.length);
        
        // Add all stones to the heap
        for (int stone : stones) {
            maxHeap.insert(stone);
        }
        
        // Smash stones until at most one remains
        while (maxHeap.size() > 1) {
            // Remove the two heaviest stones
            int y = maxHeap.extractMax(); // Heaviest
            int x = maxHeap.extractMax(); // Second heaviest
            
            // If they have different weights, add the difference back to the heap
            if (x != y) {
                maxHeap.insert(y - x);
            }
        }
        
        // Return the weight of the last remaining stone, or 0 if no stones remain
        return maxHeap.isEmpty() ? 0 : maxHeap.peek();
    }
    
    /**
     * Custom implementation of a max-heap.
     */
    private static class MaxHeap {
        private final int[] heap;
        private int size;
        
        public MaxHeap(int capacity) {
            heap = new int[capacity + 1]; // 1-indexed heap
            size = 0;
        }
        
        public void insert(int value) {
            // Add the new value at the end of the heap
            heap[++size] = value;
            
            // Sift up to maintain the heap property
            siftUp(size);
        }
        
        public int extractMax() {
            // Edge case: empty heap
            if (isEmpty()) {
                throw new IllegalStateException("Heap is empty");
            }
            
            // The maximum element is at the root
            int max = heap[1];
            
            // Replace the root with the last element
            heap[1] = heap[size--];
            
            // Sift down to maintain the heap property
            siftDown(1);
            
            return max;
        }
        
        public int peek() {
            // Edge case: empty heap
            if (isEmpty()) {
                throw new IllegalStateException("Heap is empty");
            }
            
            // The maximum element is at the root
            return heap[1];
        }
        
        public boolean isEmpty() {
            return size == 0;
        }
        
        public int size() {
            return size;
        }
        
        private void siftUp(int index) {
            int temp = heap[index];
            
            // While the element is greater than its parent, swap them
            while (index > 1 && temp > heap[index / 2]) {
                heap[index] = heap[index / 2];
                index /= 2;
            }
            
            heap[index] = temp;
        }
        
        private void siftDown(int index) {
            int temp = heap[index];
            int child;
            
            // While the element has at least one child
            while (2 * index <= size) {
                child = 2 * index;
                
                // Choose the larger child
                if (child < size && heap[child] < heap[child + 1]) {
                    child++;
                }
                
                // If the element is greater than or equal to its largest child, we're done
                if (temp >= heap[child]) {
                    break;
                }
                
                // Otherwise, swap with the child
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
        LastStoneWeight solution = new LastStoneWeight();
        
        // Example 1
        int[] stones1 = {2, 7, 4, 1, 8, 1};
        int result1 = solution.lastStoneWeight(stones1);
        
        System.out.println("Example 1:");
        System.out.println("Stones: " + java.util.Arrays.toString(stones1));
        System.out.println("Last stone weight: " + result1);
        
        // Example 2
        int[] stones2 = {1};
        int result2 = solution.lastStoneWeight(stones2);
        
        System.out.println("\nExample 2:");
        System.out.println("Stones: " + java.util.Arrays.toString(stones2));
        System.out.println("Last stone weight: " + result2);
        
        // Additional example
        int[] stones3 = {3, 7, 2, 8, 3, 4, 2};
        int result3 = solution.lastStoneWeight(stones3);
        
        System.out.println("\nAdditional Example:");
        System.out.println("Stones: " + java.util.Arrays.toString(stones3));
        System.out.println("Last stone weight: " + result3);
        
        // Test with custom heap implementation
        int result3Custom = solution.lastStoneWeightWithCustomHeap(stones3);
        System.out.println("Last stone weight (custom heap): " + result3Custom);
        
        // Let's trace through the execution of Example 1
        System.out.println("\nTracing the execution of Example 1:");
        System.out.println("Stones: [2, 7, 4, 1, 8, 1]");
        
        System.out.println("1. Create a max-heap and add all stones:");
        System.out.println("   - Heap: [8, 7, 4, 1, 2, 1]");
        
        System.out.println("2. First iteration:");
        System.out.println("   - Remove the two heaviest stones: 8 and 7");
        System.out.println("   - Smash them: 8 - 7 = 1");
        System.out.println("   - Add the result back to the heap");
        System.out.println("   - Heap: [4, 2, 1, 1, 1]");
        
        System.out.println("3. Second iteration:");
        System.out.println("   - Remove the two heaviest stones: 4 and 2");
        System.out.println("   - Smash them: 4 - 2 = 2");
        System.out.println("   - Add the result back to the heap");
        System.out.println("   - Heap: [2, 1, 1, 1]");
        
        System.out.println("4. Third iteration:");
        System.out.println("   - Remove the two heaviest stones: 2 and 1");
        System.out.println("   - Smash them: 2 - 1 = 1");
        System.out.println("   - Add the result back to the heap");
        System.out.println("   - Heap: [1, 1, 1]");
        
        System.out.println("5. Fourth iteration:");
        System.out.println("   - Remove the two heaviest stones: 1 and 1");
        System.out.println("   - Smash them: 1 - 1 = 0");
        System.out.println("   - Since the result is 0, don't add anything back to the heap");
        System.out.println("   - Heap: [1]");
        
        System.out.println("6. Only one stone remains with weight 1, so return 1");
        
        // Visualize the max-heap
        System.out.println("\nVisualization of the max-heap at the start:");
        System.out.println("      8      <- Root (maximum element)");
        System.out.println("     / \\");
        System.out.println("    7   4    <- Children of the root");
        System.out.println("   / \\ /");
        System.out.println("  1  2 1     <- Leaf nodes");
    }
}
