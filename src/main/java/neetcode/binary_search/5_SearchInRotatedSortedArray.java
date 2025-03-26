package neetcode.binary_search;

/**
 * NeetCode Problem 5 (Binary Search): Search in Rotated Sorted Array
 * 
 * Problem Description:
 * There is an integer array nums sorted in ascending order (with distinct values).
 * 
 * Prior to being passed to your function, nums is possibly rotated at an unknown pivot index k
 * (1 <= k < nums.length) such that the resulting array is 
 * [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]] (0-indexed).
 * 
 * For example, [0,1,2,4,5,6,7] might be rotated at pivot index 3 and become [4,5,6,7,0,1,2].
 * 
 * Given the array nums after the possible rotation and an integer target, return the index of target
 * if it is in nums, or -1 if it is not in nums.
 * 
 * You must write an algorithm with O(log n) runtime complexity.
 * 
 * Examples:
 * Input: nums = [4,5,6,7,0,1,2], target = 0
 * Output: 4
 * 
 * Input: nums = [4,5,6,7,0,1,2], target = 3
 * Output: -1
 * 
 * Input: nums = [1], target = 0
 * Output: -1
 * 
 * Constraints:
 * - 1 <= nums.length <= 5000
 * - -10^4 <= nums[i] <= 10^4
 * - All values of nums are unique.
 * - nums is an ascending array that is possibly rotated.
 * - -10^4 <= target <= 10^4
 * 
 * Approach:
 * We can use a modified binary search to find the target in the rotated sorted array.
 * 
 * The key insight is that when we divide a rotated sorted array into two halves, at least one of the
 * halves will always be sorted. We can check which half is sorted and then determine if the target
 * lies in the sorted half or the other half.
 * 
 * 1. Initialize left and right pointers to the start and end of the array
 * 2. While left <= right:
 *    - Calculate the middle index
 *    - If the middle element is the target, return its index
 *    - Check which half is sorted (by comparing the middle element with the leftmost element)
 *    - If the left half is sorted:
 *      - If the target is in the range of the left half, search the left half
 *      - Otherwise, search the right half
 *    - If the right half is sorted:
 *      - If the target is in the range of the right half, search the right half
 *      - Otherwise, search the left half
 * 3. If the loop ends without finding the target, return -1
 * 
 * Time Complexity: O(log n) where n is the length of the array
 * Space Complexity: O(1) as we're using constant extra space
 */
public class SearchInRotatedSortedArray {
    
    /**
     * Searches for a target value in a rotated sorted array.
     * 
     * @param nums The rotated sorted array
     * @param target The target value to search for
     * @return The index of the target if found, -1 otherwise
     */
    public int search(int[] nums, int target) {
        // Edge case: empty array
        if (nums == null || nums.length == 0) {
            return -1;
        }
        
        // Initialize the left and right pointers
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            // Calculate the middle index
            int mid = left + (right - left) / 2;
            
            // Check if the middle element is the target
            if (nums[mid] == target) {
                return mid;
            }
            
            // Check which half is sorted
            if (nums[left] <= nums[mid]) {
                // Left half is sorted
                
                // Check if the target is in the range of the left half
                if (nums[left] <= target && target < nums[mid]) {
                    // Search the left half
                    right = mid - 1;
                } else {
                    // Search the right half
                    left = mid + 1;
                }
            } else {
                // Right half is sorted
                
                // Check if the target is in the range of the right half
                if (nums[mid] < target && target <= nums[right]) {
                    // Search the right half
                    left = mid + 1;
                } else {
                    // Search the left half
                    right = mid - 1;
                }
            }
        }
        
        // Target not found
        return -1;
    }
    
    /**
     * Alternative implementation that first finds the pivot, then performs binary search
     * on the appropriate half.
     */
    public int searchAlternative(int[] nums, int target) {
        // Edge case: empty array
        if (nums == null || nums.length == 0) {
            return -1;
        }
        
        // Find the pivot index (the smallest element)
        int pivot = findPivot(nums);
        
        // If the array is not rotated, perform regular binary search
        if (pivot == 0) {
            return binarySearch(nums, 0, nums.length - 1, target);
        }
        
        // Decide which half to search based on the target value
        if (target >= nums[0]) {
            // Search the left half (from 0 to pivot - 1)
            return binarySearch(nums, 0, pivot - 1, target);
        } else {
            // Search the right half (from pivot to the end)
            return binarySearch(nums, pivot, nums.length - 1, target);
        }
    }
    
    /**
     * Helper method to find the pivot index (the smallest element) in the rotated sorted array.
     */
    private int findPivot(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        
        // If the array is not rotated
        if (nums[left] < nums[right]) {
            return 0;
        }
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] > nums[right]) {
                // The pivot is in the right half
                left = mid + 1;
            } else {
                // The pivot is in the left half (including the middle element)
                right = mid;
            }
        }
        
        return left;
    }
    
    /**
     * Helper method to perform binary search on a sorted array within a specified range.
     */
    private int binarySearch(int[] nums, int left, int right, int target) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return -1;
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        SearchInRotatedSortedArray solution = new SearchInRotatedSortedArray();
        
        // Example 1: Should return 4
        int[] nums1 = {4, 5, 6, 7, 0, 1, 2};
        int target1 = 0;
        System.out.println("Example 1: " + solution.search(nums1, target1));
        System.out.println("Example 1 (Alternative): " + solution.searchAlternative(nums1, target1));
        
        // Example 2: Should return -1
        int[] nums2 = {4, 5, 6, 7, 0, 1, 2};
        int target2 = 3;
        System.out.println("Example 2: " + solution.search(nums2, target2));
        System.out.println("Example 2 (Alternative): " + solution.searchAlternative(nums2, target2));
        
        // Example 3: Should return -1
        int[] nums3 = {1};
        int target3 = 0;
        System.out.println("Example 3: " + solution.search(nums3, target3));
        System.out.println("Example 3 (Alternative): " + solution.searchAlternative(nums3, target3));
        
        // Let's trace through the execution of Example 1:
        // nums = [4, 5, 6, 7, 0, 1, 2], target = 0
        
        // Initialize left = 0, right = 6
        
        // Iteration 1:
        // mid = 0 + (6 - 0) / 2 = 3
        // nums[mid] = 7 != target = 0
        // nums[left] = 4 <= nums[mid] = 7, so the left half is sorted
        // target = 0 is not in the range [4, 7), so search the right half
        // left = mid + 1 = 4, right = 6
        
        // Iteration 2:
        // mid = 4 + (6 - 4) / 2 = 5
        // nums[mid] = 1 != target = 0
        // nums[left] = 0 <= nums[mid] = 1, so the left half is sorted
        // target = 0 is in the range [0, 1), so search the left half
        // left = 4, right = mid - 1 = 4
        
        // Iteration 3:
        // mid = 4 + (4 - 4) / 2 = 4
        // nums[mid] = 0 == target = 0, so return mid = 4
        
        // Additional examples
        int[] nums4 = {3, 1};
        int target4 = 1;
        System.out.println("Example 4: " + solution.search(nums4, target4));
        System.out.println("Example 4 (Alternative): " + solution.searchAlternative(nums4, target4));
        
        int[] nums5 = {5, 1, 3};
        int target5 = 5;
        System.out.println("Example 5: " + solution.search(nums5, target5));
        System.out.println("Example 5 (Alternative): " + solution.searchAlternative(nums5, target5));
    }
}
