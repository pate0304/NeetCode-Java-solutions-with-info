package neetcode.two_pointers;

/**
 * NeetCode Problem 5 (Two Pointers): Trapping Rain Water
 * 
 * Problem Description:
 * Given n non-negative integers representing an elevation map where the width of each bar is 1,
 * compute how much water it can trap after raining.
 * 
 * Examples:
 * Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
 * Output: 6
 * Explanation: The elevation map is represented by array [0,1,0,2,1,0,1,3,2,1,2,1].
 * In this case, 6 units of rain water are being trapped.
 * 
 * Input: height = [4,2,0,3,2,5]
 * Output: 9
 * 
 * Constraints:
 * - n == height.length
 * - 1 <= n <= 2 * 10^4
 * - 0 <= height[i] <= 10^5
 * 
 * Approach:
 * The key insight is that the amount of water trapped at any position depends on the
 * minimum of the maximum heights to its left and right, minus its own height.
 * 
 * There are several approaches to solve this problem:
 * 
 * 1. Brute Force: For each position, find the maximum height to its left and right,
 *    then calculate the water trapped. O(n²) time complexity.
 * 
 * 2. Dynamic Programming: Precompute the maximum height to the left and right of each position,
 *    then calculate the water trapped. O(n) time and O(n) space complexity.
 * 
 * 3. Two Pointers: Use two pointers to track the maximum heights from both ends,
 *    and calculate the water trapped while moving the pointers. O(n) time and O(1) space complexity.
 * 
 * We'll implement the Two Pointers approach as it's the most efficient:
 * 1. Use two pointers, left and right, starting from the ends of the array
 * 2. Keep track of the maximum heights seen so far from both ends
 * 3. Move the pointer pointing to the smaller height inward
 * 4. Calculate the water trapped at each position
 * 
 * Time Complexity: O(n) where n is the length of the array
 * Space Complexity: O(1) as we're using constant extra space
 */
public class TrappingRainWater {
    
    /**
     * Calculates how much water can be trapped after raining.
     * 
     * @param height The array representing the elevation map
     * @return The total amount of water that can be trapped
     */
    public int trap(int[] height) {
        // Edge case: empty array or array with less than 3 elements
        if (height == null || height.length < 3) {
            return 0;
        }
        
        // Initialize two pointers
        int left = 0;
        int right = height.length - 1;
        
        // Initialize maximum heights seen so far from both ends
        int leftMax = 0;
        int rightMax = 0;
        
        // Initialize the total amount of water trapped
        int totalWater = 0;
        
        // Move pointers towards each other until they meet
        while (left < right) {
            // Update the maximum heights seen so far
            leftMax = Math.max(leftMax, height[left]);
            rightMax = Math.max(rightMax, height[right]);
            
            // If the left height is smaller, calculate water trapped at the left position
            // and move the left pointer inward
            if (height[left] < height[right]) {
                totalWater += leftMax - height[left];
                left++;
            }
            // Otherwise, calculate water trapped at the right position
            // and move the right pointer inward
            else {
                totalWater += rightMax - height[right];
                right--;
            }
        }
        
        return totalWater;
    }
    
    /**
     * Alternative approach using dynamic programming.
     * This is easier to understand but uses O(n) extra space.
     */
    public int trapUsingDP(int[] height) {
        if (height == null || height.length < 3) {
            return 0;
        }
        
        int n = height.length;
        
        // Precompute the maximum height to the left of each position
        int[] leftMax = new int[n];
        leftMax[0] = height[0];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }
        
        // Precompute the maximum height to the right of each position
        int[] rightMax = new int[n];
        rightMax[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }
        
        // Calculate the water trapped at each position
        int totalWater = 0;
        for (int i = 0; i < n; i++) {
            totalWater += Math.min(leftMax[i], rightMax[i]) - height[i];
        }
        
        return totalWater;
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        TrappingRainWater solution = new TrappingRainWater();
        
        // Example 1: Should return 6
        int[] height1 = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        System.out.println("Example 1 (Two Pointers): " + solution.trap(height1));
        System.out.println("Example 1 (DP): " + solution.trapUsingDP(height1));
        
        // Example 2: Should return 9
        int[] height2 = {4, 2, 0, 3, 2, 5};
        System.out.println("Example 2 (Two Pointers): " + solution.trap(height2));
        System.out.println("Example 2 (DP): " + solution.trapUsingDP(height2));
        
        // Let's trace through a simplified example to understand the algorithm better:
        // height = [3, 0, 1, 0, 2]
        
        // Using the Two Pointers approach:
        // Initialize left = 0, right = 4, leftMax = 0, rightMax = 0, totalWater = 0
        
        // Iteration 1: leftMax = max(0, 3) = 3, rightMax = max(0, 2) = 2
        //              height[left] > height[right], so calculate water at right:
        //              totalWater += rightMax - height[right] = 2 - 2 = 0, right--
        
        // Iteration 2: left = 0, right = 3, leftMax = 3, rightMax = max(2, 0) = 2
        //              height[left] > height[right], so calculate water at right:
        //              totalWater += rightMax - height[right] = 2 - 0 = 2, right--
        
        // Iteration 3: left = 0, right = 2, leftMax = 3, rightMax = max(2, 1) = 2
        //              height[left] > height[right], so calculate water at right:
        //              totalWater += rightMax - height[right] = 2 - 1 = 1, right--
        
        // Iteration 4: left = 0, right = 1, leftMax = 3, rightMax = max(2, 0) = 2
        //              height[left] > height[right], so calculate water at right:
        //              totalWater += rightMax - height[right] = 2 - 0 = 2, right--
        
        // Now left = 0, right = 0, left == right, so we exit the loop
        // Final result: totalWater = 0 + 2 + 1 + 2 = 5
    }
}
