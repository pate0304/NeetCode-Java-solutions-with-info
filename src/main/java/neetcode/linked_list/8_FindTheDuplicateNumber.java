package neetcode.linked_list;

/**
 * NeetCode Problem 8 (Linked List): Find the Duplicate Number
 * 
 * Problem Description:
 * Given an array of integers nums containing n + 1 integers where each integer is in the range [1, n] inclusive.
 * 
 * There is only one repeated number in nums, return this repeated number.
 * 
 * You must solve the problem without modifying the array nums and using only constant extra space.
 * 
 * Examples:
 * Input: nums = [1,3,4,2,2]
 * Output: 2
 * 
 * Input: nums = [3,1,3,4,2]
 * Output: 3
 * 
 * Input: nums = [1,1]
 * Output: 1
 * 
 * Input: nums = [1,1,2]
 * Output: 1
 * 
 * Constraints:
 * - 1 <= n <= 10^5
 * - nums.length == n + 1
 * - 1 <= nums[i] <= n
 * - All the integers in nums appear only once except for precisely one integer which appears two or more times.
 * 
 * Approach:
 * This problem can be solved using Floyd's Cycle Detection algorithm (also known as the "tortoise and hare" algorithm).
 * We can treat the array as a linked list where nums[i] represents the next index to visit.
 * 
 * The key insight is that since there's a duplicate number, there must be a cycle in this "linked list".
 * The duplicate number is the entry point of the cycle.
 * 
 * 1. Use two pointers, slow and fast, both starting at index 0
 * 2. Move slow one step at a time (slow = nums[slow]) and fast two steps at a time (fast = nums[nums[fast]])
 * 3. When they meet, reset slow to the start and keep fast at the meeting point
 * 4. Move both pointers one step at a time until they meet again
 * 5. The meeting point is the duplicate number
 * 
 * Time Complexity: O(n) where n is the length of the array
 * Space Complexity: O(1) as we're only using two pointers
 */
public class FindTheDuplicateNumber {
    
    /**
     * Finds the duplicate number using Floyd's Cycle Detection algorithm.
     * 
     * @param nums The input array
     * @return The duplicate number
     */
    public int findDuplicate(int[] nums) {
        // Edge case
        if (nums == null || nums.length <= 1) {
            return -1;
        }
        
        // Initialize slow and fast pointers
        int slow = nums[0];
        int fast = nums[0];
        
        // Phase 1: Find the meeting point inside the cycle
        do {
            slow = nums[slow];         // Move slow one step
            fast = nums[nums[fast]];   // Move fast two steps
        } while (slow != fast);
        
        // Phase 2: Find the entry point of the cycle
        slow = nums[0];
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        
        // The meeting point is the duplicate number
        return slow;
    }
    
    /**
     * Alternative approach using binary search.
     * 
     * @param nums The input array
     * @return The duplicate number
     */
    public int findDuplicateBinarySearch(int[] nums) {
        // Edge case
        if (nums == null || nums.length <= 1) {
            return -1;
        }
        
        int left = 1;
        int right = nums.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            // Count numbers less than or equal to mid
            int count = 0;
            for (int num : nums) {
                if (num <= mid) {
                    count++;
                }
            }
            
            // If count is greater than mid, the duplicate is in the left half
            if (count > mid) {
                right = mid;
            } else {
                // Otherwise, the duplicate is in the right half
                left = mid + 1;
            }
        }
        
        // At this point, left == right, which is the duplicate number
        return left;
    }
    
    /**
     * Alternative approach using bit manipulation.
     * This approach works only if the duplicate appears exactly twice.
     * 
     * @param nums The input array
     * @return The duplicate number
     */
    public int findDuplicateBitManipulation(int[] nums) {
        // Edge case
        if (nums == null || nums.length <= 1) {
            return -1;
        }
        
        int result = 0;
        
        // For each bit position
        for (int bit = 0; bit < 32; bit++) {
            int mask = (1 << bit);
            int countNums = 0;
            int countIndices = 0;
            
            // Count the number of elements with this bit set
            for (int i = 0; i < nums.length; i++) {
                // Count in the array
                if ((nums[i] & mask) != 0) {
                    countNums++;
                }
                
                // Count in the range [1, n]
                if (i > 0 && (i & mask) != 0) {
                    countIndices++;
                }
            }
            
            // If the count in the array is greater, this bit is part of the duplicate
            if (countNums > countIndices) {
                result |= mask;
            }
        }
        
        return result;
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        FindTheDuplicateNumber solution = new FindTheDuplicateNumber();
        
        // Example 1: [1,3,4,2,2]
        int[] nums1 = {1, 3, 4, 2, 2};
        int result1 = solution.findDuplicate(nums1);
        System.out.println("Example 1: The duplicate number is " + result1);
        
        // Example 2: [3,1,3,4,2]
        int[] nums2 = {3, 1, 3, 4, 2};
        int result2 = solution.findDuplicateBinarySearch(nums2);
        System.out.println("Example 2: The duplicate number is " + result2);
        
        // Example 3: [1,1]
        int[] nums3 = {1, 1};
        int result3 = solution.findDuplicate(nums3);
        System.out.println("Example 3: The duplicate number is " + result3);
        
        // Example 4: [1,1,2]
        int[] nums4 = {1, 1, 2};
        int result4 = solution.findDuplicateBinarySearch(nums4);
        System.out.println("Example 4: The duplicate number is " + result4);
        
        // Let's trace through the execution of Example 1 using Floyd's Cycle Detection:
        // nums = [1,3,4,2,2]
        
        // Initialize slow = nums[0] = 1, fast = nums[0] = 1
        
        // Phase 1:
        // Iteration 1:
        // slow = nums[slow] = nums[1] = 3
        // fast = nums[nums[fast]] = nums[nums[1]] = nums[3] = 2
        // slow != fast, continue
        
        // Iteration 2:
        // slow = nums[slow] = nums[3] = 2
        // fast = nums[nums[fast]] = nums[nums[2]] = nums[4] = 2
        // slow == fast, exit loop
        
        // Phase 2:
        // Reset slow = nums[0] = 1, fast remains at 2
        
        // Iteration 1:
        // slow = nums[slow] = nums[1] = 3
        // fast = nums[fast] = nums[2] = 4
        // slow != fast, continue
        
        // Iteration 2:
        // slow = nums[slow] = nums[3] = 2
        // fast = nums[fast] = nums[4] = 2
        // slow == fast, exit loop
        
        // Return slow = 2, which is the duplicate number
        
        System.out.println("\nTracing Floyd's Cycle Detection for Example 1:");
        System.out.println("nums = [1,3,4,2,2]");
        System.out.println("Initial: slow = nums[0] = 1, fast = nums[0] = 1");
        System.out.println("Phase 1, Iteration 1: slow = nums[1] = 3, fast = nums[nums[1]] = nums[3] = 2");
        System.out.println("Phase 1, Iteration 2: slow = nums[3] = 2, fast = nums[nums[2]] = nums[4] = 2");
        System.out.println("slow == fast, end Phase 1");
        System.out.println("Phase 2, Reset: slow = nums[0] = 1, fast remains at 2");
        System.out.println("Phase 2, Iteration 1: slow = nums[1] = 3, fast = nums[2] = 4");
        System.out.println("Phase 2, Iteration 2: slow = nums[3] = 2, fast = nums[4] = 2");
        System.out.println("slow == fast, end Phase 2");
        System.out.println("The duplicate number is 2");
    }
}
