package neetcode.arrays_hashing;

import java.util.*;

/**
 * NeetCode Problem 5: Top K Frequent Elements
 * 
 * Problem Description:
 * Given an integer array nums and an integer k, return the k most frequent elements.
 * You may return the answer in any order.
 * 
 * Examples:
 * Input: nums = [1,1,1,2,2,3], k = 2
 * Output: [1,2]
 * 
 * Input: nums = [1], k = 1
 * Output: [1]
 * 
 * Constraints:
 * - 1 <= nums.length <= 10^5
 * - -10^4 <= nums[i] <= 10^4
 * - k is in the range [1, the number of unique elements in the array]
 * - It is guaranteed that the answer is unique
 * 
 * Approach:
 * To find the k most frequent elements, we need to:
 * 1. Count the frequency of each element
 * 2. Sort the elements by their frequencies
 * 3. Return the top k elements
 * 
 * There are several approaches to implement this:
 * 
 * 1. HashMap + Sorting: Count frequencies with a HashMap, then sort by frequency
 * 2. HashMap + Heap: Count frequencies with a HashMap, then use a min-heap of size k
 * 3. HashMap + Bucket Sort: Count frequencies with a HashMap, then use bucket sort
 * 
 * The optimal solution uses a HashMap and Bucket Sort for O(n) time complexity:
 * - Count the frequency of each element using a HashMap
 * - Create buckets where the index represents the frequency
 * - Place elements in their corresponding frequency buckets
 * - Collect the top k elements by iterating through the buckets from highest to lowest frequency
 * 
 * Time Complexity: O(n) where n is the length of the array
 * Space Complexity: O(n) for the HashMap and buckets
 */
public class TopKFrequentElements {
    
    /**
     * Finds the k most frequent elements in the array.
     * 
     * @param nums The input array of integers
     * @param k The number of top frequent elements to return
     * @return An array of the k most frequent elements
     */
    public int[] topKFrequent(int[] nums, int k) {
        // Step 1: Count the frequency of each element
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }
        
        // Step 2: Create buckets where the index represents the frequency
        // The maximum frequency possible is the length of the array
        List<Integer>[] buckets = new ArrayList[nums.length + 1];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList<>();
        }
        
        // Place elements in their corresponding frequency buckets
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            int num = entry.getKey();
            int frequency = entry.getValue();
            buckets[frequency].add(num);
        }
        
        // Step 3: Collect the top k elements by iterating through the buckets
        // from highest to lowest frequency
        int[] result = new int[k];
        int resultIndex = 0;
        
        // Start from the highest possible frequency and work backwards
        for (int i = buckets.length - 1; i >= 0 && resultIndex < k; i--) {
            // Skip empty buckets
            if (buckets[i].isEmpty()) {
                continue;
            }
            
            // Add all elements in this bucket to the result
            // (or as many as needed to reach k)
            for (int num : buckets[i]) {
                if (resultIndex < k) {
                    result[resultIndex++] = num;
                } else {
                    break;
                }
            }
        }
        
        return result;
    }
    
    /**
     * Alternative solution using a HashMap and a Priority Queue (Min Heap).
     * This has O(n log k) time complexity.
     */
    public int[] topKFrequentUsingHeap(int[] nums, int k) {
        // Count the frequency of each element
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }
        
        // Use a min heap to keep track of the k most frequent elements
        // The heap will be ordered by frequency (smallest frequency at the top)
        PriorityQueue<Map.Entry<Integer, Integer>> minHeap = new PriorityQueue<>(
            (a, b) -> a.getValue() - b.getValue()
        );
        
        // Add elements to the heap
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            minHeap.add(entry);
            
            // If the heap size exceeds k, remove the element with the smallest frequency
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }
        
        // Build the result array from the heap
        int[] result = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            result[i] = minHeap.poll().getKey();
        }
        
        return result;
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        TopKFrequentElements solution = new TopKFrequentElements();
        
        // Example 1: Should return [1, 2] in any order
        int[] nums1 = {1, 1, 1, 2, 2, 3};
        int k1 = 2;
        System.out.println("Example 1 (Bucket Sort): " + Arrays.toString(solution.topKFrequent(nums1, k1)));
        System.out.println("Example 1 (Heap): " + Arrays.toString(solution.topKFrequentUsingHeap(nums1, k1)));
        
        // Example 2: Should return [1]
        int[] nums2 = {1};
        int k2 = 1;
        System.out.println("Example 2 (Bucket Sort): " + Arrays.toString(solution.topKFrequent(nums2, k2)));
        System.out.println("Example 2 (Heap): " + Arrays.toString(solution.topKFrequentUsingHeap(nums2, k2)));
        
        // Additional example
        int[] nums3 = {4, 1, 1, 1, 2, 2, 3, 4, 4, 4, 4};
        int k3 = 2;
        System.out.println("Example 3 (Bucket Sort): " + Arrays.toString(solution.topKFrequent(nums3, k3)));
        System.out.println("Example 3 (Heap): " + Arrays.toString(solution.topKFrequentUsingHeap(nums3, k3)));
    }
}
