package neetcode.binary_search;

/**
 * NeetCode Problem 4 (Binary Search): Find Minimum in Rotated Sorted Array
 * 
 * Problem Description:
 * Suppose an array of length n sorted in ascending order is rotated between 1 and n times.
 * For example, the array nums = [0,1,2,4,5,6,7] might become:
 * - [4,5,6,7,0,1,2] if it was rotated 4 times.
 * - [0,1,2,4,5,6,7] if it was rotated 7 times.
 * 
 * Notice that rotating an array [a[0], a[1], a[2], ..., a[n-1]] 1 time results in the array
 * [a[n-1], a[0], a[1], a[2], ..., a[n-2]].
 * 
 * Given the sorted rotated array nums of unique elements, return the minimum element of this array.
 * 
 * You must write an algorithm that runs in O(log n) time.
 * 
 * Examples:
 * Input: nums = [3,4,5,1,2]
 * Output: 1
 * Explanation: The original array was [1,2,3,4,5] rotated 3 times.
 * 
 * Input: nums = [4,5,6,7,0,1,2]
 * Output: 0
 * Explanation: The original array was [0,1,2,4,5,6,7] and it was rotated 4 times.
 * 
 * Input: nums = [11,13,15,17]
 * Output: 11
 * Explanation: The original array was [11,13,15,17] and it was rotated 4 times.
 * 
 * Constraints:
 * - n == nums.length
 * - 1 <= n <= 5000
 * - -5000 <= nums[i] <= 5000
 * - All the integers of nums are unique.
 * - nums is sorted and rotated between 1 and n times.
 * 
 * Approach:
 * We can use binary search to find the minimum element in the rotated sorted array.
 * 
 * The key insight is that in a rotated sorted array, the minimum element is the only element
 * that is smaller than its previous element. Additionally, if we compare an element with the
 * rightmost element, elements to the right of the minimum are smaller than the rightmost element,
 * and elements to the left of the minimum are greater than the rightmost element.
 * 
 * 1. Initialize left and right pointers to the start and end of the array
 * 2. While left < right:
 *    - Calculate the middle index
 *    - If the middle element is greater than the rightmost element, the minimum must be in the right half
 *    - Otherwise, the minimum must be in the left half (including the middle element)
 * 3. When the loop ends, left points to the minimum element
 * 
 * Time Complexity: O(log n) where n is the length of the array
 * Space Complexity: O(1) as we're using constant extra space
 */
public class FindMinimumInRotatedSortedArray {
    
    /**
     * Finds the minimum element in a rotated sorted array.
     * 
     * @param nums The rotated sorted array
     * @return The minimum element in the array
     */
    public int findMin(int[] nums) {
        // Edge case: array has only one element
        if (nums.length == 1) {
            return nums[0];
        }
        
        // Edge case: array is not rotated or is rotated n times
        if (nums[0] < nums[nums.length - 1]) {
            return nums[0];
        }
        
        // Initialize the left and right pointers
        int left = 0;
        int right = nums.length - 1;
        
        while (left < right) {
            // Calculate the middle index
            int mid = left + (right - left) / 2;
            
            // If the middle element is greater than the next element,
            // the next element is the minimum
            if (mid < nums.length - 1 && nums[mid] > nums[mid + 1]) {
                return nums[mid + 1];
            }
            
            // If the middle element is less than the previous element,
            // the middle element is the minimum
            if (mid > 0 && nums[mid] < nums[mid - 1]) {
                return nums[mid];
            }
            
            // If the middle element is greater than the leftmost element,
            // the minimum must be in the right half
            if (nums[mid] > nums[0]) {
                left = mid + 1;
            }
            // Otherwise, the minimum must be in the left half
            else {
                right = mid - 1;
            }
        }
        
        // If we reach here, the minimum is at the left pointer
        return nums[left];
    }
    
    /**
     * Alternative implementation that compares with the rightmost element.
     */
    public int findMinAlternative(int[] nums) {
        // Edge case: array has only one element
        if (nums.length == 1) {
            return nums[0];
        }
        
        // Edge case: array is not rotated or is rotated n times
        if (nums[0] < nums[nums.length - 1]) {
            return nums[0];
        }
        
        // Initialize the left and right pointers
        int left = 0;
        int right = nums.length - 1;
        
        while (left < right) {
            // Calculate the middle index
            int mid = left + (right - left) / 2;
            
            // If the middle element is greater than the rightmost element,
            // the minimum must be in the right half
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            }
            // Otherwise, the minimum must be in the left half (including the middle element)
            else {
                right = mid;
            }
        }
        
        // When the loop ends, left points to the minimum element
        return nums[left];
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        FindMinimumInRotatedSortedArray solution = new FindMinimumInRotatedSortedArray();
        
        // Example 1: Should return 1
        int[] nums1 = {3, 4, 5, 1, 2};
        System.out.println("Example 1: " + solution.findMin(nums1));
        System.out.println("Example 1 (Alternative): " + solution.findMinAlternative(nums1));
        
        // Example 2: Should return 0
        int[] nums2 = {4, 5, 6, 7, 0, 1, 2};
        System.out.println("Example 2: " + solution.findMin(nums2));
        System.out.println("Example 2 (Alternative): " + solution.findMinAlternative(nums2));
        
        // Example 3: Should return 11
        int[] nums3 = {11, 13, 15, 17};
        System.out.println("Example 3: " + solution.findMin(nums3));
        System.out.println("Example 3 (Alternative): " + solution.findMinAlternative(nums3));
        
        // Let's trace through the execution of Example 2 using the alternative approach:
        // nums = [4, 5, 6, 7, 0, 1, 2]
        
        // Initialize left = 0, right = 6
        
        // Iteration 1:
        // mid = 0 + (6 - 0) / 2 = 3
        // nums[mid] = 7 > nums[right] = 2, so the minimum must be in the right half
        // left = mid + 1 = 4, right = 6
        
        // Iteration 2:
        // mid = 4 + (6 - 4) / 2 = 5
        // nums[mid] = 1 < nums[right] = 2, so the minimum must be in the left half (including the middle element)
        // left = 4, right = 5
        
        // Iteration 3:
        // mid = 4 + (5 - 4) / 2 = 4
        // nums[mid] = 0 < nums[right] = 1, so the minimum must be in the left half (including the middle element)
        // left = 4, right = 4
        
        // Now left = 4, right = 4, so the loop ends
        // Return nums[left] = nums[4] = 0
        
        // Additional examples
        int[] nums4 = {3, 1, 2};
        System.out.println("Example 4: " + solution.findMin(nums4));
        System.out.println("Example 4 (Alternative): " + solution.findMinAlternative(nums4));
        
        int[] nums5 = {5, 1, 2, 3, 4};
        System.out.println("Example 5: " + solution.findMin(nums5));
        System.out.println("Example 5 (Alternative): " + solution.findMinAlternative(nums5));
    }
}
