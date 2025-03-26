package neetcode.stack;

import java.util.Stack;

/**
 * NeetCode Problem 7 (Stack): Largest Rectangle in Histogram
 * 
 * Problem Description:
 * Given an array of integers heights representing the histogram's bar height where the width of each bar is 1,
 * return the area of the largest rectangle in the histogram.
 * 
 * Examples:
 * Input: heights = [2,1,5,6,2,3]
 * Output: 10
 * Explanation: The largest rectangle has an area of 10 units, formed by the rectangle of height 5 and width 2
 * (bars at indices 2 and 3).
 * 
 * Input: heights = [2,4]
 * Output: 4
 * 
 * Constraints:
 * - 1 <= heights.length <= 10^5
 * - 0 <= heights[i] <= 10^4
 * 
 * Approach:
 * This problem can be efficiently solved using a stack to keep track of bars in ascending order of height.
 * 
 * The key insight is that for each bar, we want to find the largest rectangle that uses this bar's height.
 * To do this, we need to find the left and right boundaries where the height is at least as tall as the current bar.
 * 
 * We can use a stack to keep track of the indices of bars in ascending order of height:
 * 1. Iterate through each bar in the histogram
 * 2. While the current bar is shorter than the bar at the top of the stack:
 *    - Pop the top bar from the stack
 *    - Calculate the area of the rectangle using the popped bar's height and the width between the current position
 *      and the new top of the stack
 *    - Update the maximum area if necessary
 * 3. Push the current bar's index onto the stack
 * 
 * Time Complexity: O(n) where n is the number of bars in the histogram
 * Space Complexity: O(n) for the stack in the worst case
 */
public class LargestRectangleInHistogram {
    
    /**
     * Calculates the area of the largest rectangle in the histogram.
     * 
     * @param heights The array of bar heights
     * @return The area of the largest rectangle
     */
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int maxArea = 0;
        
        // Use a stack to keep track of indices of bars in ascending order of height
        Stack<Integer> stack = new Stack<>();
        
        // Process each bar in the histogram
        for (int i = 0; i <= n; i++) {
            // When we reach the end of the array or find a bar shorter than the one at the top of the stack,
            // we calculate the area of rectangles using the heights of the bars in the stack
            while (!stack.isEmpty() && (i == n || heights[i] < heights[stack.peek()])) {
                // Height of the rectangle is the height of the popped bar
                int height = heights[stack.pop()];
                
                // Width of the rectangle is the distance from the current position to the previous bar in the stack
                // If the stack is empty, the width is the current position
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                
                // Calculate the area and update the maximum
                int area = height * width;
                maxArea = Math.max(maxArea, area);
            }
            
            // Push the current bar's index onto the stack
            if (i < n) {
                stack.push(i);
            }
        }
        
        return maxArea;
    }
    
    /**
     * Alternative implementation using arrays to compute left and right boundaries for each bar.
     * This approach is more intuitive but requires more space.
     */
    public int largestRectangleAreaAlternative(int[] heights) {
        int n = heights.length;
        int maxArea = 0;
        
        // Arrays to store the left and right boundaries for each bar
        int[] leftBoundary = new int[n];
        int[] rightBoundary = new int[n];
        
        // Compute the left boundary for each bar
        for (int i = 0; i < n; i++) {
            int left = i - 1;
            while (left >= 0 && heights[left] >= heights[i]) {
                left = leftBoundary[left];
            }
            leftBoundary[i] = left;
        }
        
        // Compute the right boundary for each bar
        for (int i = n - 1; i >= 0; i--) {
            int right = i + 1;
            while (right < n && heights[right] >= heights[i]) {
                right = rightBoundary[right];
            }
            rightBoundary[i] = right;
        }
        
        // Calculate the maximum area
        for (int i = 0; i < n; i++) {
            int width = rightBoundary[i] - leftBoundary[i] - 1;
            int area = heights[i] * width;
            maxArea = Math.max(maxArea, area);
        }
        
        return maxArea;
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        LargestRectangleInHistogram solution = new LargestRectangleInHistogram();
        
        // Example 1: Should return 10
        int[] heights1 = {2, 1, 5, 6, 2, 3};
        System.out.println("Example 1 (Stack): " + solution.largestRectangleArea(heights1));
        System.out.println("Example 1 (Alternative): " + solution.largestRectangleAreaAlternative(heights1));
        
        // Example 2: Should return 4
        int[] heights2 = {2, 4};
        System.out.println("Example 2 (Stack): " + solution.largestRectangleArea(heights2));
        System.out.println("Example 2 (Alternative): " + solution.largestRectangleAreaAlternative(heights2));
        
        // Let's trace through the execution of Example 1 using the stack approach:
        // heights = [2, 1, 5, 6, 2, 3]
        
        // Initialize maxArea = 0, stack = []
        
        // i = 0, height = 2
        // stack is empty, so push 0: stack = [0]
        
        // i = 1, height = 1
        // 1 < heights[stack.peek()] = 2, so pop 0 from stack
        // height = 2, width = 1, area = 2 * 1 = 2, maxArea = 2
        // stack is empty, so push 1: stack = [1]
        
        // i = 2, height = 5
        // 5 > heights[stack.peek()] = 1, so push 2: stack = [1, 2]
        
        // i = 3, height = 6
        // 6 > heights[stack.peek()] = 5, so push 3: stack = [1, 2, 3]
        
        // i = 4, height = 2
        // 2 < heights[stack.peek()] = 6, so pop 3 from stack
        // height = 6, width = 4 - 2 - 1 = 1, area = 6 * 1 = 6, maxArea = 6
        // 2 < heights[stack.peek()] = 5, so pop 2 from stack
        // height = 5, width = 4 - 1 - 1 = 2, area = 5 * 2 = 10, maxArea = 10
        // 2 > heights[stack.peek()] = 1, so push 4: stack = [1, 4]
        
        // i = 5, height = 3
        // 3 > heights[stack.peek()] = 2, so push 5: stack = [1, 4, 5]
        
        // i = 6 (end of array)
        // Pop 5 from stack: height = 3, width = 6 - 4 - 1 = 1, area = 3 * 1 = 3, maxArea = 10
        // Pop 4 from stack: height = 2, width = 6 - 1 - 1 = 4, area = 2 * 4 = 8, maxArea = 10
        // Pop 1 from stack: height = 1, width = 6, area = 1 * 6 = 6, maxArea = 10
        
        // Final result: maxArea = 10
    }
}
