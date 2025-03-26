package neetcode.arrays_hashing;

import java.util.HashSet;
import java.util.Set;

/**
 * NeetCode Problem 8: Longest Consecutive Sequence
 * 
 * Problem Description:
 * Given an unsorted array of integers nums, return the length of the longest consecutive elements sequence.
 * You must write an algorithm that runs in O(n) time.
 * 
 * Examples:
 * Input: nums = [100,4,200,1,3,2]
 * Output: 4
 * Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.
 * 
 * Input: nums = [0,3,7,2,5,8,4,6,0,1]
 * Output: 9
 * 
 * Constraints:
 * - 0 <= nums.length <= 10^5
 * - -10^9 <= nums[i] <= 10^9
 * 
 * Approach:
 * The key insight to solve this problem in O(n) time is to use a HashSet for O(1) lookups.
 * 
 * Here's the approach:
 * 1. Add all numbers to a HashSet for O(1) lookups
 * 2. For each number in the array, check if it's the start of a sequence
 *    - A number is the start of a sequence if its predecessor (num-1) is not in the set
 * 3. If a number is the start of a sequence, count how long the sequence is by checking
 *    if consecutive numbers (num+1, num+2, etc.) are in the set
 * 4. Keep track of the longest sequence found
 * 
 * This approach ensures O(n) time complexity because:
 * - Adding all numbers to the set takes O(n) time
 * - For each number, we do at most O(n) checks for consecutive numbers in total
 *   (not for each number, but across all numbers)
 * 
 * Time Complexity: O(n) where n is the length of the array
 * Space Complexity: O(n) for the HashSet
 */
public class LongestConsecutiveSequence {
    
    /**
     * Finds the length of the longest consecutive elements sequence.
     * 
     * @param nums The input array of integers
     * @return The length of the longest consecutive sequence
     */
    public int longestConsecutive(int[] nums) {
        // Edge case: empty array
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        // Add all numbers to a HashSet for O(1) lookups
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }
        
        int longestStreak = 0;
        
        // Check each number to see if it's the start of a sequence
        for (int num : numSet) {
            // Only check for sequences starting with the smallest number in the sequence
            // This ensures we only count each sequence once
            if (!numSet.contains(num - 1)) {
                // This number is the start of a sequence
                int currentNum = num;
                int currentStreak = 1;
                
                // Count how long this sequence is
                while (numSet.contains(currentNum + 1)) {
                    currentNum++;
                    currentStreak++;
                }
                
                // Update the longest streak if this one is longer
                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }
        
        return longestStreak;
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        LongestConsecutiveSequence solution = new LongestConsecutiveSequence();
        
        // Example 1: Should return 4
        int[] nums1 = {100, 4, 200, 1, 3, 2};
        System.out.println("Example 1: " + solution.longestConsecutive(nums1));
        
        // Example 2: Should return 9
        int[] nums2 = {0, 3, 7, 2, 5, 8, 4, 6, 0, 1};
        System.out.println("Example 2: " + solution.longestConsecutive(nums2));
        
        // Let's trace through the execution of Example 1 to understand the algorithm better:
        // nums = [100, 4, 200, 1, 3, 2]
        
        // First, we add all numbers to the set: {1, 2, 3, 4, 100, 200}
        
        // Then, we check each number:
        // 100: 99 is not in the set, so 100 is the start of a sequence
        //      Check for 101, 102, etc. - none are in the set
        //      Sequence length = 1
        // 4: 3 is in the set, so 4 is not the start of a sequence (skip)
        // 200: 199 is not in the set, so 200 is the start of a sequence
        //      Check for 201, 202, etc. - none are in the set
        //      Sequence length = 1
        // 1: 0 is not in the set, so 1 is the start of a sequence
        //      Check for 2, 3, 4, etc. - 2, 3, 4 are in the set, 5 is not
        //      Sequence length = 4
        // 3: 2 is in the set, so 3 is not the start of a sequence (skip)
        // 2: 1 is in the set, so 2 is not the start of a sequence (skip)
        
        // The longest sequence found is [1, 2, 3, 4] with length 4
        
        // Additional examples
        int[] nums3 = {1, 2, 0, 1};
        System.out.println("Example 3: " + solution.longestConsecutive(nums3));
        
        int[] nums4 = {};
        System.out.println("Example 4: " + solution.longestConsecutive(nums4));
    }
}
