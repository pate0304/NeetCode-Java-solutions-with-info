package neetcode.stack;

import java.util.Arrays;
import java.util.Stack;

/**
 * NeetCode Problem 5 (Stack): Daily Temperatures
 * 
 * Problem Description:
 * Given an array of integers temperatures represents the daily temperatures, return an array answer
 * such that answer[i] is the number of days you have to wait after the ith day to get a warmer temperature.
 * If there is no future day for which this is possible, keep answer[i] == 0 instead.
 * 
 * Examples:
 * Input: temperatures = [73,74,75,71,69,72,76,73]
 * Output: [1,1,4,2,1,1,0,0]
 * 
 * Input: temperatures = [30,40,50,60]
 * Output: [1,1,1,0]
 * 
 * Input: temperatures = [30,60,90]
 * Output: [1,1,0]
 * 
 * Constraints:
 * - 1 <= temperatures.length <= 10^5
 * - 30 <= temperatures[i] <= 100
 * 
 * Approach:
 * This problem can be efficiently solved using a stack to keep track of indices of temperatures
 * that haven't found a warmer future temperature yet.
 * 
 * The key insight is to process the temperatures from right to left or from left to right:
 * 
 * 1. Initialize an array 'answer' of the same length as 'temperatures', filled with zeros
 * 2. Initialize an empty stack to store indices
 * 3. Iterate through the temperatures:
 *    - While the stack is not empty and the current temperature is warmer than the temperature at the index at the top of the stack:
 *      - Pop the index from the stack
 *      - Calculate the number of days to wait and store it in the answer array
 *    - Push the current index onto the stack
 * 4. Return the answer array
 * 
 * Time Complexity: O(n) where n is the length of the temperatures array
 * Space Complexity: O(n) for the stack in the worst case
 */
public class DailyTemperatures {
    
    /**
     * Calculates the number of days to wait for a warmer temperature.
     * 
     * @param temperatures The array of daily temperatures
     * @return An array where each element is the number of days to wait for a warmer temperature
     */
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] answer = new int[n];
        
        // Initialize a stack to store indices of temperatures
        Stack<Integer> stack = new Stack<>();
        
        // Process each temperature from left to right
        for (int i = 0; i < n; i++) {
            // While the stack is not empty and the current temperature is warmer than the temperature at the index at the top of the stack
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                // Pop the index from the stack
                int prevIndex = stack.pop();
                
                // Calculate the number of days to wait and store it in the answer array
                answer[prevIndex] = i - prevIndex;
            }
            
            // Push the current index onto the stack
            stack.push(i);
        }
        
        // Indices that remain in the stack don't have a warmer future temperature, so their answer remains 0
        
        return answer;
    }
    
    /**
     * Alternative implementation using an optimized approach without a stack.
     * This approach processes the temperatures from right to left.
     */
    public int[] dailyTemperaturesOptimized(int[] temperatures) {
        int n = temperatures.length;
        int[] answer = new int[n];
        
        // Process each temperature from right to left
        for (int i = n - 1; i >= 0; i--) {
            int j = i + 1;
            
            // Find the next warmer temperature
            while (j < n && temperatures[j] <= temperatures[i]) {
                // If there's no warmer temperature after j, skip to the next warmer temperature after j
                if (answer[j] > 0) {
                    j += answer[j];
                } else {
                    // If there's no warmer temperature after j, there's no warmer temperature after i either
                    j = n;
                }
            }
            
            // If a warmer temperature was found, calculate the number of days to wait
            if (j < n) {
                answer[i] = j - i;
            }
        }
        
        return answer;
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        DailyTemperatures solution = new DailyTemperatures();
        
        // Example 1: Should return [1,1,4,2,1,1,0,0]
        int[] temperatures1 = {73, 74, 75, 71, 69, 72, 76, 73};
        System.out.println("Example 1 (Stack): " + Arrays.toString(solution.dailyTemperatures(temperatures1)));
        System.out.println("Example 1 (Optimized): " + Arrays.toString(solution.dailyTemperaturesOptimized(temperatures1)));
        
        // Example 2: Should return [1,1,1,0]
        int[] temperatures2 = {30, 40, 50, 60};
        System.out.println("Example 2 (Stack): " + Arrays.toString(solution.dailyTemperatures(temperatures2)));
        System.out.println("Example 2 (Optimized): " + Arrays.toString(solution.dailyTemperaturesOptimized(temperatures2)));
        
        // Example 3: Should return [1,1,0]
        int[] temperatures3 = {30, 60, 90};
        System.out.println("Example 3 (Stack): " + Arrays.toString(solution.dailyTemperatures(temperatures3)));
        System.out.println("Example 3 (Optimized): " + Arrays.toString(solution.dailyTemperaturesOptimized(temperatures3)));
        
        // Let's trace through the execution of Example 1 using the stack approach:
        // temperatures = [73, 74, 75, 71, 69, 72, 76, 73]
        
        // Initialize answer = [0, 0, 0, 0, 0, 0, 0, 0], stack = []
        
        // i = 0, temperature = 73
        // stack is empty, so push 0: stack = [0]
        
        // i = 1, temperature = 74
        // 74 > temperatures[stack.peek()] = 73, so pop 0 from stack
        // answer[0] = 1 - 0 = 1
        // stack is empty, so push 1: stack = [1]
        
        // i = 2, temperature = 75
        // 75 > temperatures[stack.peek()] = 74, so pop 1 from stack
        // answer[1] = 2 - 1 = 1
        // stack is empty, so push 2: stack = [2]
        
        // i = 3, temperature = 71
        // 71 < temperatures[stack.peek()] = 75, so push 3: stack = [2, 3]
        
        // i = 4, temperature = 69
        // 69 < temperatures[stack.peek()] = 71, so push 4: stack = [2, 3, 4]
        
        // i = 5, temperature = 72
        // 72 > temperatures[stack.peek()] = 69, so pop 4 from stack
        // answer[4] = 5 - 4 = 1
        // 72 > temperatures[stack.peek()] = 71, so pop 3 from stack
        // answer[3] = 5 - 3 = 2
        // 72 < temperatures[stack.peek()] = 75, so push 5: stack = [2, 5]
        
        // i = 6, temperature = 76
        // 76 > temperatures[stack.peek()] = 72, so pop 5 from stack
        // answer[5] = 6 - 5 = 1
        // 76 > temperatures[stack.peek()] = 75, so pop 2 from stack
        // answer[2] = 6 - 2 = 4
        // stack is empty, so push 6: stack = [6]
        
        // i = 7, temperature = 73
        // 73 < temperatures[stack.peek()] = 76, so push 7: stack = [6, 7]
        
        // Final stack = [6, 7]
        // Final answer = [1, 1, 4, 2, 1, 1, 0, 0]
    }
}
