package neetcode.two_pointers;

import java.util.*;

/**
 * NeetCode Problem 3 (Two Pointers): 3Sum
 * 
 * Problem Description:
 * Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]] such that 
 * i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.
 * 
 * Notice that the solution set must not contain duplicate triplets.
 * 
 * Examples:
 * Input: nums = [-1,0,1,2,-1,-4]
 * Output: [[-1,-1,2],[-1,0,1]]
 * Explanation: 
 * nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0.
 * nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0.
 * nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0.
 * The distinct triplets are [-1,0,1] and [-1,-1,2].
 * Notice that the order of the output and the order of the triplets does not matter.
 * 
 * Input: nums = [0,1,1]
 * Output: []
 * Explanation: The only possible triplet does not sum up to 0.
 * 
 * Input: nums = [0,0,0]
 * Output: [[0,0,0]]
 * Explanation: The only possible triplet sums up to 0.
 * 
 * Constraints:
 * - 3 <= nums.length <= 3000
 * - -10^5 <= nums[i] <= 10^5
 * 
 * Approach:
 * We can solve this problem using a combination of sorting and the two-pointer technique:
 * 
 * 1. Sort the array to make it easier to handle duplicates and use the two-pointer approach
 * 2. Iterate through the array with a fixed first element (nums[i])
 * 3. For each first element, use two pointers (left and right) to find pairs that sum to -nums[i]
 * 4. Skip duplicate values to avoid duplicate triplets
 * 
 * Time Complexity: O(n²) where n is the length of the array
 * - Sorting takes O(n log n)
 * - The nested loops take O(n²)
 * 
 * Space Complexity: O(1) excluding the output array
 * - We use constant extra space regardless of the input size
 */
public class ThreeSum {
    
    /**
     * Finds all unique triplets in the array that sum to zero.
     * 
     * @param nums The input array of integers
     * @return A list of all unique triplets that sum to zero
     */
    public List<List<Integer>> threeSum(int[] nums) {
        // Initialize the result list
        List<List<Integer>> result = new ArrayList<>();
        
        // Edge case: if the array has fewer than 3 elements, return empty list
        if (nums == null || nums.length < 3) {
            return result;
        }
        
        // Sort the array to handle duplicates and use the two-pointer approach
        Arrays.sort(nums);
        
        // Iterate through the array with a fixed first element
        for (int i = 0; i < nums.length - 2; i++) {
            // Skip duplicate values for the first element
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            
            // Use two pointers to find pairs that sum to -nums[i]
            int left = i + 1;
            int right = nums.length - 1;
            int target = -nums[i]; // We want the sum of three numbers to be 0
            
            while (left < right) {
                int sum = nums[left] + nums[right];
                
                if (sum == target) {
                    // Found a triplet that sums to zero
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    
                    // Skip duplicate values for the second element
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    
                    // Skip duplicate values for the third element
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    
                    // Move both pointers
                    left++;
                    right--;
                } else if (sum < target) {
                    // Sum is too small, move left pointer to the right to increase the sum
                    left++;
                } else {
                    // Sum is too large, move right pointer to the left to decrease the sum
                    right--;
                }
            }
        }
        
        return result;
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        ThreeSum solution = new ThreeSum();
        
        // Example 1: Should return [[-1,-1,2],[-1,0,1]]
        int[] nums1 = {-1, 0, 1, 2, -1, -4};
        System.out.println("Example 1: " + solution.threeSum(nums1));
        
        // Example 2: Should return []
        int[] nums2 = {0, 1, 1};
        System.out.println("Example 2: " + solution.threeSum(nums2));
        
        // Example 3: Should return [[0,0,0]]
        int[] nums3 = {0, 0, 0};
        System.out.println("Example 3: " + solution.threeSum(nums3));
        
        // Let's trace through a simplified example to understand the algorithm better:
        // nums = [-1, 0, 1]
        // After sorting: [-1, 0, 1] (already sorted)
        
        // i = 0, nums[i] = -1, target = 1
        // left = 1, right = 2
        // sum = nums[1] + nums[2] = 0 + 1 = 1 == target
        // Add triplet [-1, 0, 1] to result
        // Move pointers: left = 2, right = 1 (now left >= right, so exit the while loop)
        
        // i = 1, nums[i] = 0, target = 0
        // left = 2, right = 2 (left == right, so don't enter the while loop)
        
        // i = 2, nums[i] = 1, target = -1
        // left = 3, right = 2 (left > right, so don't enter the while loop)
        
        // Final result: [[-1, 0, 1]]
    }
}
