package neetcode.two_pointers;

import java.util.Arrays;

/**
 * NeetCode Problem 2 (Two Pointers): Two Sum II - Input Array Is Sorted
 * 
 * Problem Description:
 * Given a 1-indexed array of integers numbers that is already sorted in non-decreasing order,
 * find two numbers such that they add up to a specific target number.
 * 
 * Return the indices of the two numbers, index1 and index2, added by one as an integer array [index1, index2].
 * 
 * The tests are generated such that there is exactly one solution. You may not use the same element twice.
 * Your solution must use only constant extra space.
 * 
 * Examples:
 * Input: numbers = [2,7,11,15], target = 9
 * Output: [1,2]
 * Explanation: The sum of 2 and 7 is 9. Therefore, index1 = 1, index2 = 2. We return [1, 2].
 * 
 * Input: numbers = [2,3,4], target = 6
 * Output: [1,3]
 * Explanation: The sum of 2 and 4 is 6. Therefore, index1 = 1, index2 = 3. We return [1, 3].
 * 
 * Input: numbers = [-1,0], target = -1
 * Output: [1,2]
 * Explanation: The sum of -1 and 0 is -1. Therefore, index1 = 1, index2 = 2. We return [1, 2].
 * 
 * Constraints:
 * - 2 <= numbers.length <= 3 * 10^4
 * - -1000 <= numbers[i] <= 1000
 * - numbers is sorted in non-decreasing order
 * - -1000 <= target <= 1000
 * - The tests are generated such that there is exactly one solution
 * 
 * Approach:
 * Since the array is sorted, we can use the two-pointer technique:
 * 1. Initialize two pointers: left at the beginning and right at the end of the array
 * 2. Calculate the sum of the elements at the two pointers
 * 3. If the sum is equal to the target, we've found our answer
 * 4. If the sum is less than the target, move the left pointer to the right (to increase the sum)
 * 5. If the sum is greater than the target, move the right pointer to the left (to decrease the sum)
 * 6. Repeat until we find the target sum
 * 
 * Time Complexity: O(n) where n is the length of the array
 * Space Complexity: O(1) as we're using constant extra space
 */
public class TwoSumII {
    
    /**
     * Finds two numbers in a sorted array that add up to the target.
     * 
     * @param numbers The input array of integers (1-indexed in the problem description)
     * @param target The target sum
     * @return The indices of the two numbers (1-indexed)
     */
    public int[] twoSum(int[] numbers, int target) {
        // Initialize two pointers
        int left = 0;
        int right = numbers.length - 1;
        
        // Move pointers towards each other until they meet
        while (left < right) {
            // Calculate the current sum
            int sum = numbers[left] + numbers[right];
            
            if (sum == target) {
                // Found the target sum, return the indices (1-indexed)
                return new int[] {left + 1, right + 1};
            } else if (sum < target) {
                // Sum is too small, move left pointer to the right to increase the sum
                left++;
            } else {
                // Sum is too large, move right pointer to the left to decrease the sum
                right--;
            }
        }
        
        // This should never happen given the problem constraints
        return new int[] {-1, -1};
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        TwoSumII solution = new TwoSumII();
        
        // Example 1: Should return [1, 2]
        int[] numbers1 = {2, 7, 11, 15};
        int target1 = 9;
        System.out.println("Example 1: " + Arrays.toString(solution.twoSum(numbers1, target1)));
        
        // Example 2: Should return [1, 3]
        int[] numbers2 = {2, 3, 4};
        int target2 = 6;
        System.out.println("Example 2: " + Arrays.toString(solution.twoSum(numbers2, target2)));
        
        // Example 3: Should return [1, 2]
        int[] numbers3 = {-1, 0};
        int target3 = -1;
        System.out.println("Example 3: " + Arrays.toString(solution.twoSum(numbers3, target3)));
        
        // Let's trace through the execution of Example 1 to understand the algorithm better:
        // numbers = [2, 7, 11, 15], target = 9
        
        // Initialize left = 0, right = 3
        // Iteration 1: sum = numbers[0] + numbers[3] = 2 + 15 = 17 > target
        //              Move right pointer: right = 2
        // Iteration 2: sum = numbers[0] + numbers[2] = 2 + 11 = 13 > target
        //              Move right pointer: right = 1
        // Iteration 3: sum = numbers[0] + numbers[1] = 2 + 7 = 9 == target
        //              Return [0+1, 1+1] = [1, 2]
    }
}
