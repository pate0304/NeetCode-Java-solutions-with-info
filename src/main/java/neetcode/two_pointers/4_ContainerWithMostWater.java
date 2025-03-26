package neetcode.two_pointers;

/**
 * NeetCode Problem 4 (Two Pointers): Container With Most Water
 * 
 * Problem Description:
 * You are given an integer array height of length n. There are n vertical lines drawn such that the 
 * two endpoints of the ith line are (i, 0) and (i, height[i]).
 * 
 * Find two lines that together with the x-axis form a container, such that the container contains the most water.
 * Return the maximum amount of water a container can store.
 * 
 * Notice that you may not slant the container.
 * 
 * Examples:
 * Input: height = [1,8,6,2,5,4,8,3,7]
 * Output: 49
 * Explanation: The maximum area is obtained by choosing the 2nd and 8th lines (height[1] = 8 and height[8] = 7)
 * The area of this container is min(8, 7) * (8 - 1) = 7 * 7 = 49
 * 
 * Input: height = [1,1]
 * Output: 1
 * 
 * Constraints:
 * - n == height.length
 * - 2 <= n <= 10^5
 * - 0 <= height[i] <= 10^4
 * 
 * Approach:
 * This is a classic two-pointer problem. The key insight is that the area of water contained
 * between two lines is determined by the shorter line and the distance between them.
 * 
 * We can use the two-pointer technique:
 * 1. Start with two pointers at the extreme ends of the array
 * 2. Calculate the area between them (min height * width)
 * 3. Move the pointer pointing to the shorter line inward (since moving the taller line would only decrease the area)
 * 4. Keep track of the maximum area seen so far
 * 
 * Time Complexity: O(n) where n is the length of the array
 * Space Complexity: O(1) as we're using constant extra space
 */
public class ContainerWithMostWater {
    
    /**
     * Finds the maximum area of water that can be contained.
     * 
     * @param height The array representing the heights of the lines
     * @return The maximum area of water
     */
    public int maxArea(int[] height) {
        // Initialize two pointers at the extreme ends of the array
        int left = 0;
        int right = height.length - 1;
        
        // Initialize the maximum area
        int maxArea = 0;
        
        // Move pointers towards each other until they meet
        while (left < right) {
            // Calculate the width between the two lines
            int width = right - left;
            
            // Calculate the height of the container (limited by the shorter line)
            int h = Math.min(height[left], height[right]);
            
            // Calculate the area and update the maximum area if needed
            int area = width * h;
            maxArea = Math.max(maxArea, area);
            
            // Move the pointer pointing to the shorter line inward
            // If heights are equal, we can move either pointer
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        
        return maxArea;
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        ContainerWithMostWater solution = new ContainerWithMostWater();
        
        // Example 1: Should return 49
        int[] height1 = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println("Example 1: " + solution.maxArea(height1));
        
        // Example 2: Should return 1
        int[] height2 = {1, 1};
        System.out.println("Example 2: " + solution.maxArea(height2));
        
        // Let's trace through the execution of Example 1 to understand the algorithm better:
        // height = [1, 8, 6, 2, 5, 4, 8, 3, 7]
        
        // Initialize left = 0, right = 8, maxArea = 0
        
        // Iteration 1: width = 8, h = min(1, 7) = 1, area = 8 * 1 = 8, maxArea = 8
        //              height[left] < height[right], so left++
        
        // Iteration 2: left = 1, right = 8, width = 7, h = min(8, 7) = 7, area = 7 * 7 = 49, maxArea = 49
        //              height[left] > height[right], so right--
        
        // Iteration 3: left = 1, right = 7, width = 6, h = min(8, 3) = 3, area = 6 * 3 = 18, maxArea = 49
        //              height[left] > height[right], so right--
        
        // Iteration 4: left = 1, right = 6, width = 5, h = min(8, 8) = 8, area = 5 * 8 = 40, maxArea = 49
        //              heights are equal, so right--
        
        // Iteration 5: left = 1, right = 5, width = 4, h = min(8, 4) = 4, area = 4 * 4 = 16, maxArea = 49
        //              height[left] > height[right], so right--
        
        // Iteration 6: left = 1, right = 4, width = 3, h = min(8, 5) = 5, area = 3 * 5 = 15, maxArea = 49
        //              height[left] > height[right], so right--
        
        // Iteration 7: left = 1, right = 3, width = 2, h = min(8, 2) = 2, area = 2 * 2 = 4, maxArea = 49
        //              height[left] > height[right], so right--
        
        // Iteration 8: left = 1, right = 2, width = 1, h = min(8, 6) = 6, area = 1 * 6 = 6, maxArea = 49
        //              height[left] > height[right], so right--
        
        // Now left = 1, right = 1, left == right, so we exit the loop
        // Final result: maxArea = 49
        
        // Additional example
        int[] height3 = {4, 3, 2, 1, 4};
        System.out.println("Example 3: " + solution.maxArea(height3));
    }
}
