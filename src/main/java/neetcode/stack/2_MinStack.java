package neetcode.stack;

import java.util.Stack;

/**
 * NeetCode Problem 2 (Stack): Min Stack
 * 
 * Problem Description:
 * Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.
 * 
 * Implement the MinStack class:
 * - MinStack() initializes the stack object.
 * - void push(int val) pushes the element val onto the stack.
 * - void pop() removes the element on the top of the stack.
 * - int top() gets the top element of the stack.
 * - int getMin() retrieves the minimum element in the stack.
 * 
 * You must implement a solution with O(1) time complexity for each function.
 * 
 * Examples:
 * Input:
 * ["MinStack","push","push","push","getMin","pop","top","getMin"]
 * [[],[-2],[0],[-3],[],[],[],[]]
 * 
 * Output:
 * [null,null,null,null,-3,null,0,-2]
 * 
 * Explanation:
 * MinStack minStack = new MinStack();
 * minStack.push(-2);
 * minStack.push(0);
 * minStack.push(-3);
 * minStack.getMin(); // return -3
 * minStack.pop();
 * minStack.top();    // return 0
 * minStack.getMin(); // return -2
 * 
 * Constraints:
 * - -2^31 <= val <= 2^31 - 1
 * - Methods pop, top and getMin operations will always be called on non-empty stacks.
 * - At most 3 * 10^4 calls will be made to push, pop, top, and getMin.
 * 
 * Approach:
 * To implement a min stack with O(1) time complexity for all operations, we can use two stacks:
 * 1. One stack to store the actual values
 * 2. Another stack to keep track of the minimum values
 * 
 * The key insight is that the minimum value can only change when we push a new value that's
 * smaller than the current minimum, or when we pop the current minimum.
 * 
 * For each push operation:
 * - Push the value onto the main stack
 * - If the min stack is empty or the new value is less than or equal to the current minimum,
 *   push it onto the min stack as well
 * 
 * For each pop operation:
 * - Pop the value from the main stack
 * - If the popped value equals the current minimum, pop from the min stack as well
 * 
 * Time Complexity: O(1) for all operations
 * Space Complexity: O(n) where n is the number of elements in the stack
 */
public class MinStack {
    
    private Stack<Integer> stack;      // Main stack to store values
    private Stack<Integer> minStack;   // Stack to keep track of minimum values
    
    /**
     * Initializes the stack object.
     */
    public MinStack() {
        stack = new Stack<>();
        minStack = new Stack<>();
    }
    
    /**
     * Pushes the element val onto the stack.
     * 
     * @param val The value to push onto the stack
     */
    public void push(int val) {
        // Push the value onto the main stack
        stack.push(val);
        
        // If the min stack is empty or the new value is less than or equal to the current minimum,
        // push it onto the min stack as well
        if (minStack.isEmpty() || val <= minStack.peek()) {
            minStack.push(val);
        }
    }
    
    /**
     * Removes the element on the top of the stack.
     */
    public void pop() {
        // Pop the value from the main stack
        int val = stack.pop();
        
        // If the popped value equals the current minimum, pop from the min stack as well
        if (val == minStack.peek()) {
            minStack.pop();
        }
    }
    
    /**
     * Gets the top element of the stack.
     * 
     * @return The top element of the stack
     */
    public int top() {
        return stack.peek();
    }
    
    /**
     * Retrieves the minimum element in the stack.
     * 
     * @return The minimum element in the stack
     */
    public int getMin() {
        return minStack.peek();
    }
    
    /**
     * Alternative implementation using a single stack with pairs.
     * Each element in the stack is a pair: (value, current minimum).
     */
    static class MinStackAlternative {
        private Stack<int[]> stack;  // Stack of pairs: [value, current minimum]
        
        public MinStackAlternative() {
            stack = new Stack<>();
        }
        
        public void push(int val) {
            // If the stack is empty, the current minimum is the value itself
            // Otherwise, the current minimum is the minimum of the value and the previous minimum
            int min = stack.isEmpty() ? val : Math.min(val, stack.peek()[1]);
            stack.push(new int[]{val, min});
        }
        
        public void pop() {
            stack.pop();
        }
        
        public int top() {
            return stack.peek()[0];
        }
        
        public int getMin() {
            return stack.peek()[1];
        }
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        // Test the MinStack implementation
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println("getMin(): " + minStack.getMin()); // return -3
        minStack.pop();
        System.out.println("top(): " + minStack.top());       // return 0
        System.out.println("getMin(): " + minStack.getMin()); // return -2
        
        System.out.println("\nTesting Alternative Implementation:");
        
        // Test the alternative implementation
        MinStackAlternative minStackAlt = new MinStackAlternative();
        minStackAlt.push(-2);
        minStackAlt.push(0);
        minStackAlt.push(-3);
        System.out.println("getMin(): " + minStackAlt.getMin()); // return -3
        minStackAlt.pop();
        System.out.println("top(): " + minStackAlt.top());       // return 0
        System.out.println("getMin(): " + minStackAlt.getMin()); // return -2
        
        // Let's trace through the execution of the example:
        // 1. Initialize: stack = [], minStack = []
        
        // 2. push(-2):
        //    stack = [-2]
        //    minStack = [-2] (minStack is empty, so push -2)
        
        // 3. push(0):
        //    stack = [-2, 0]
        //    minStack = [-2] (0 > -2, so don't push to minStack)
        
        // 4. push(-3):
        //    stack = [-2, 0, -3]
        //    minStack = [-2, -3] (-3 < -2, so push -3)
        
        // 5. getMin(): return minStack.peek() = -3
        
        // 6. pop():
        //    Remove -3 from stack: stack = [-2, 0]
        //    -3 equals minStack.peek(), so remove from minStack: minStack = [-2]
        
        // 7. top(): return stack.peek() = 0
        
        // 8. getMin(): return minStack.peek() = -2
    }
}
