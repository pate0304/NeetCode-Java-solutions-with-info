package neetcode.arrays_hashing;

import java.util.HashSet;
import java.util.Set;

/**
 * NeetCode Problem 1: Contains Duplicate
 * 
 * Problem Description:
 * Given an integer array nums, return true if any value appears at least twice in the array,
 * and return false if every element is distinct.
 * 
 * Examples:
 * Input: nums = [1,2,3,1]
 * Output: true
 * 
 * Input: nums = [1,2,3,4]
 * Output: false
 * 
 * Input: nums = [1,1,1,3,3,4,3,2,4,2]
 * Output: true
 * 
 * Approach:
 * To solve this problem, we need to determine if any element appears more than once in the array.
 * 
 * There are several approaches we could take:
 * 
 * 1. Brute Force: Compare each element with every other element (O(n²) time complexity)
 * 2. Sorting: Sort the array and check adjacent elements (O(n log n) time complexity)
 * 3. Hash Set: Use a set to track seen elements (O(n) time complexity)
 * 
 * The optimal solution uses a Hash Set for O(n) time complexity:
 * - Iterate through each element in the array
 * - For each element, check if it's already in our set
 *   - If it is, we've found a duplicate, return true
 *   - If not, add it to our set
 * - If we finish iterating without finding duplicates, return false
 * 
 * Time Complexity: O(n) where n is the length of the array
 * Space Complexity: O(n) in the worst case where all elements are unique
 */
public class ContainsDuplicate {
    
    /**
     * Determines if the input array contains any duplicate elements.
     * 
     * @param nums The input array of integers
     * @return true if any value appears at least twice, false if all elements are distinct
     */
    public boolean containsDuplicate(int[] nums) {
        // Create a hash set to store elements we've seen
        Set<Integer> seen = new HashSet<>();
        
        // Iterate through each element in the array
        for (int num : nums) {
            // If the current element is already in our set, we've found a duplicate
            if (seen.contains(num)) {
                return true; // Duplicate found
            }
            
            // Otherwise, add the current element to our set
            seen.add(num);
        }
        
        // If we've gone through the entire array without finding duplicates, return false
        return false;
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        ContainsDuplicate solution = new ContainsDuplicate();
        
        // Example 1: Should return true
        int[] nums1 = {1, 2, 3, 1};
        System.out.println("Example 1: " + solution.containsDuplicate(nums1));
        
        // Example 2: Should return false
        int[] nums2 = {1, 2, 3, 4};
        System.out.println("Example 2: " + solution.containsDuplicate(nums2));
        
        // Example 3: Should return true
        int[] nums3 = {1, 1, 1, 3, 3, 4, 3, 2, 4, 2};
        System.out.println("Example 3: " + solution.containsDuplicate(nums3));
    }
}
