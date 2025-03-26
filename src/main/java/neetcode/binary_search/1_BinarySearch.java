package neetcode.binary_search;

/**
 * NeetCode Problem 1 (Binary Search): Binary Search
 * 
 * Problem Description:
 * Given an array of integers nums which is sorted in ascending order, and an integer target,
 * write a function to search target in nums. If target exists, then return its index. Otherwise, return -1.
 * 
 * You must write an algorithm with O(log n) runtime complexity.
 * 
 * Examples:
 * Input: nums = [-1,0,3,5,9,12], target = 9
 * Output: 4
 * Explanation: 9 exists in nums and its index is 4
 * 
 * Input: nums = [-1,0,3,5,9,12], target = 2
 * Output: -1
 * Explanation: 2 does not exist in nums so return -1
 * 
 * Constraints:
 * - 1 <= nums.length <= 10^4
 * - -10^4 <= nums[i], target <= 10^4
 * - All the integers in nums are unique.
 * - nums is sorted in ascending order.
 * 
 * Approach:
 * Binary search is an efficient algorithm for finding an element in a sorted array:
 * 
 * 1. Initialize two pointers, left and right, pointing to the start and end of the array
 * 2. While left <= right:
 *    - Calculate the middle index as mid = left + (right - left) / 2
 *    - If nums[mid] == target, return mid
 *    - If nums[mid] < target, search the right half by setting left = mid + 1
 *    - If nums[mid] > target, search the left half by setting right = mid - 1
 * 3. If the loop ends without finding the target, return -1
 * 
 * Time Complexity: O(log n) where n is the length of the array
 * Space Complexity: O(1) as we're using constant extra space
 */
public class BinarySearch {
    
    /**
     * Searches for a target value in a sorted array using binary search.
     * 
     * @param nums The sorted array of integers
     * @param target The target value to search for
     * @return The index of the target if found, -1 otherwise
     */
    public int search(int[] nums, int target) {
        // Initialize the left and right pointers
        int left = 0;
        int right = nums.length - 1;
        
        // Continue searching while the pointers don't cross
        while (left <= right) {
            // Calculate the middle index
            // Using left + (right - left) / 2 instead of (left + right) / 2 to avoid integer overflow
            int mid = left + (right - left) / 2;
            
            // Check if the middle element is the target
            if (nums[mid] == target) {
                return mid; // Target found, return its index
            }
            // If the middle element is less than the target, search the right half
            else if (nums[mid] < target) {
                left = mid + 1;
            }
            // If the middle element is greater than the target, search the left half
            else {
                right = mid - 1;
            }
        }
        
        // Target not found
        return -1;
    }
    
    /**
     * Recursive implementation of binary search.
     */
    public int searchRecursive(int[] nums, int target) {
        return binarySearchRecursive(nums, target, 0, nums.length - 1);
    }
    
    /**
     * Helper method for recursive binary search.
     */
    private int binarySearchRecursive(int[] nums, int target, int left, int right) {
        // Base case: pointers have crossed, target not found
        if (left > right) {
            return -1;
        }
        
        // Calculate the middle index
        int mid = left + (right - left) / 2;
        
        // Check if the middle element is the target
        if (nums[mid] == target) {
            return mid; // Target found, return its index
        }
        // If the middle element is less than the target, search the right half
        else if (nums[mid] < target) {
            return binarySearchRecursive(nums, target, mid + 1, right);
        }
        // If the middle element is greater than the target, search the left half
        else {
            return binarySearchRecursive(nums, target, left, mid - 1);
        }
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        BinarySearch solution = new BinarySearch();
        
        // Example 1: Should return 4
        int[] nums1 = {-1, 0, 3, 5, 9, 12};
        int target1 = 9;
        System.out.println("Example 1 (Iterative): " + solution.search(nums1, target1));
        System.out.println("Example 1 (Recursive): " + solution.searchRecursive(nums1, target1));
        
        // Example 2: Should return -1
        int[] nums2 = {-1, 0, 3, 5, 9, 12};
        int target2 = 2;
        System.out.println("Example 2 (Iterative): " + solution.search(nums2, target2));
        System.out.println("Example 2 (Recursive): " + solution.searchRecursive(nums2, target2));
        
        // Let's trace through the execution of Example 1:
        // nums = [-1, 0, 3, 5, 9, 12], target = 9
        
        // Initialize left = 0, right = 5
        
        // Iteration 1:
        // mid = 0 + (5 - 0) / 2 = 2
        // nums[mid] = 3 < target = 9, so search the right half
        // left = mid + 1 = 3, right = 5
        
        // Iteration 2:
        // mid = 3 + (5 - 3) / 2 = 4
        // nums[mid] = 9 == target = 9, so return mid = 4
        
        // Additional examples
        int[] nums3 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int target3 = 8;
        System.out.println("Example 3 (Iterative): " + solution.search(nums3, target3));
        System.out.println("Example 3 (Recursive): " + solution.searchRecursive(nums3, target3));
        
        int[] nums4 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int target4 = 11;
        System.out.println("Example 4 (Iterative): " + solution.search(nums4, target4));
        System.out.println("Example 4 (Recursive): " + solution.searchRecursive(nums4, target4));
    }
}
