package neetcode.arrays_hashing;

import java.util.HashMap;
import java.util.Arrays;

/**
 * NeetCode Problem 3: Two Sum
 * 
 * Problem Description:
 * Given an array of integers nums and an integer target, return indices of the two numbers
 * such that they add up to target. You may assume that each input would have exactly one solution,
 * and you may not use the same element twice.
 * 
 * Examples:
 * Input: nums = [2,7,11,15], target = 9
 * Output: [0,1]
 * Explanation: Because nums[0] + nums[1] == 9, we return [0, 1]
 * 
 * Input: nums = [3,2,4], target = 6
 * Output: [1,2]
 * 
 * Input: nums = [3,3], target = 6
 * Output: [0,1]
 * 
 * Constraints:
 * - 2 <= nums.length <= 10^4
 * - -10^9 <= nums[i] <= 10^9
 * - -10^9 <= target <= 10^9
 * - Only one valid answer exists
 * 
 * Approach:
 * To find two numbers that add up to the target, we need to efficiently check if the complement
 * (target - current number) exists in the array.
 * 
 * There are two main approaches:
 * 
 * 1. Brute Force: Check all possible pairs (O(n²) time complexity)
 * 2. Hash Map: Use a hash map to store values we've seen (O(n) time complexity)
 * 
 * The optimal solution uses a Hash Map for O(n) time complexity:
 * - Create a hash map to store values and their indices
 * - Iterate through the array
 * - For each element, calculate its complement (target - current value)
 * - Check if the complement exists in our hash map
 *   - If it does, we've found our pair, return their indices
 *   - If not, add the current element and its index to the hash map
 * 
 * Time Complexity: O(n) where n is the length of the array
 * Space Complexity: O(n) for the hash map
 */
public class TwoSum {
    
    /**
     * Finds the indices of two numbers in the array that add up to the target.
     * 
     * @param nums The input array of integers
     * @param target The target sum
     * @return An array of two integers representing the indices of the two numbers
     */
    public int[] twoSum(int[] nums, int target) {
        // Create a hash map to store values and their indices
        HashMap<Integer, Integer> numMap = new HashMap<>();
        
        // Iterate through the array
        for (int i = 0; i < nums.length; i++) {
            // Calculate the complement for the current element
            int complement = target - nums[i];
            
            // Check if the complement exists in our hash map
            if (numMap.containsKey(complement)) {
                // If it does, we've found our pair
                return new int[] {numMap.get(complement), i};
            }
            
            // If not, add the current element and its index to the hash map
            numMap.put(nums[i], i);
        }
        
        // If no solution is found (this shouldn't happen based on the problem constraints)
        return new int[] {};
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        TwoSum solution = new TwoSum();
        
        // Example 1: Should return [0, 1]
        int[] nums1 = {2, 7, 11, 15};
        int target1 = 9;
        System.out.println("Example 1: " + Arrays.toString(solution.twoSum(nums1, target1)));
        
        // Example 2: Should return [1, 2]
        int[] nums2 = {3, 2, 4};
        int target2 = 6;
        System.out.println("Example 2: " + Arrays.toString(solution.twoSum(nums2, target2)));
        
        // Example 3: Should return [0, 1]
        int[] nums3 = {3, 3};
        int target3 = 6;
        System.out.println("Example 3: " + Arrays.toString(solution.twoSum(nums3, target3)));
    }
}
