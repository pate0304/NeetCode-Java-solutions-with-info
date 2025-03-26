package neetcode.arrays_hashing;

import java.util.Arrays;

/**
 * NeetCode Problem 6: Product of Array Except Self
 * 
 * Problem Description:
 * Given an integer array nums, return an array answer such that answer[i] is equal to the product
 * of all the elements of nums except nums[i].
 * 
 * The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.
 * You must write an algorithm that runs in O(n) time and without using the division operation.
 * 
 * Examples:
 * Input: nums = [1,2,3,4]
 * Output: [24,12,8,6]
 * 
 * Input: nums = [-1,1,0,-3,3]
 * Output: [0,0,9,0,0]
 * 
 * Constraints:
 * - 2 <= nums.length <= 10^5
 * - -30 <= nums[i] <= 30
 * - The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer
 * 
 * Approach:
 * The key insight is that the product of all elements except nums[i] is equal to:
 * (product of all elements to the left of nums[i]) * (product of all elements to the right of nums[i])
 * 
 * We can solve this in O(n) time without division by:
 * 1. Create an output array initialized with 1s
 * 2. Compute the product of all elements to the left of each position
 * 3. Compute the product of all elements to the right of each position
 * 4. Multiply these two products for each position
 * 
 * We can optimize further by combining steps 2-4 into just two passes:
 * - First pass: Calculate the product of all elements to the left
 * - Second pass: Calculate the product of all elements to the right and multiply with the left product
 * 
 * Time Complexity: O(n) where n is the length of the array
 * Space Complexity: O(1) excluding the output array
 */
public class ProductOfArrayExceptSelf {
    
    /**
     * Calculates the product of all elements in the array except the element at each index.
     * 
     * @param nums The input array of integers
     * @return An array where each element is the product of all elements in nums except nums[i]
     */
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        
        // Initialize the output array with 1s
        int[] result = new int[n];
        
        // First pass: Calculate the product of all elements to the left of each position
        int leftProduct = 1;
        for (int i = 0; i < n; i++) {
            // Store the product of all elements to the left
            result[i] = leftProduct;
            // Update the left product for the next position
            leftProduct *= nums[i];
        }
        
        // Second pass: Calculate the product of all elements to the right
        // and multiply with the left product
        int rightProduct = 1;
        for (int i = n - 1; i >= 0; i--) {
            // Multiply the current result (product of all elements to the left)
            // with the product of all elements to the right
            result[i] *= rightProduct;
            // Update the right product for the next position
            rightProduct *= nums[i];
        }
        
        return result;
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        ProductOfArrayExceptSelf solution = new ProductOfArrayExceptSelf();
        
        // Example 1: Should return [24, 12, 8, 6]
        int[] nums1 = {1, 2, 3, 4};
        System.out.println("Example 1: " + Arrays.toString(solution.productExceptSelf(nums1)));
        
        // Example 2: Should return [0, 0, 9, 0, 0]
        int[] nums2 = {-1, 1, 0, -3, 3};
        System.out.println("Example 2: " + Arrays.toString(solution.productExceptSelf(nums2)));
        
        // Let's trace through the execution of Example 1 to understand the algorithm better:
        // nums = [1, 2, 3, 4]
        
        // First pass (left to right):
        // leftProduct starts at 1
        // i=0: result[0] = leftProduct = 1, leftProduct = leftProduct * nums[0] = 1 * 1 = 1
        // i=1: result[1] = leftProduct = 1, leftProduct = leftProduct * nums[1] = 1 * 2 = 2
        // i=2: result[2] = leftProduct = 2, leftProduct = leftProduct * nums[2] = 2 * 3 = 6
        // i=3: result[3] = leftProduct = 6, leftProduct = leftProduct * nums[3] = 6 * 4 = 24
        // After first pass, result = [1, 1, 2, 6]
        
        // Second pass (right to left):
        // rightProduct starts at 1
        // i=3: result[3] = result[3] * rightProduct = 6 * 1 = 6, rightProduct = rightProduct * nums[3] = 1 * 4 = 4
        // i=2: result[2] = result[2] * rightProduct = 2 * 4 = 8, rightProduct = rightProduct * nums[2] = 4 * 3 = 12
        // i=1: result[1] = result[1] * rightProduct = 1 * 12 = 12, rightProduct = rightProduct * nums[1] = 12 * 2 = 24
        // i=0: result[0] = result[0] * rightProduct = 1 * 24 = 24, rightProduct = rightProduct * nums[0] = 24 * 1 = 24
        // After second pass, result = [24, 12, 8, 6]
    }
}
