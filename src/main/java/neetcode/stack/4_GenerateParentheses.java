package neetcode.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * NeetCode Problem 4 (Stack): Generate Parentheses
 * 
 * Problem Description:
 * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
 * 
 * Examples:
 * Input: n = 3
 * Output: ["((()))","(()())","(())()","()(())","()()()"]
 * 
 * Input: n = 1
 * Output: ["()"]
 * 
 * Constraints:
 * - 1 <= n <= 8
 * 
 * Approach:
 * This problem can be solved using backtracking, which is a form of recursion that explores all possible solutions.
 * 
 * The key insight is that we can build the solution incrementally by adding either an opening parenthesis '('
 * or a closing parenthesis ')' at each step, while maintaining the validity of the parentheses string.
 * 
 * A string of well-formed parentheses must satisfy two conditions:
 * 1. The total number of opening parentheses '(' equals the total number of closing parentheses ')'.
 * 2. At any point, the number of closing parentheses cannot exceed the number of opening parentheses.
 * 
 * We can use these rules to guide our backtracking:
 * - We can add an opening parenthesis '(' if we haven't used all n opening parentheses yet.
 * - We can add a closing parenthesis ')' if the number of closing parentheses is less than the number of opening parentheses.
 * 
 * Time Complexity: O(4^n / sqrt(n)) - This is the nth Catalan number, which represents the number of valid combinations.
 * Space Complexity: O(n) for the recursion stack and to store each combination.
 */
public class GenerateParentheses {
    
    /**
     * Generates all combinations of well-formed parentheses.
     * 
     * @param n The number of pairs of parentheses
     * @return A list of all possible valid parentheses strings
     */
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        backtrack(result, new StringBuilder(), 0, 0, n);
        return result;
    }
    
    /**
     * Helper method for backtracking to generate valid parentheses combinations.
     * 
     * @param result The list to store all valid combinations
     * @param current The current string being built
     * @param open The count of opening parentheses used so far
     * @param close The count of closing parentheses used so far
     * @param max The maximum number of pairs allowed
     */
    private void backtrack(List<String> result, StringBuilder current, int open, int close, int max) {
        // Base case: if the current string has 2*n characters, it's complete
        if (current.length() == max * 2) {
            result.add(current.toString());
            return;
        }
        
        // We can add an opening parenthesis if we haven't used all n
        if (open < max) {
            current.append('(');
            backtrack(result, current, open + 1, close, max);
            current.deleteCharAt(current.length() - 1); // Backtrack by removing the last character
        }
        
        // We can add a closing parenthesis if it doesn't exceed the number of opening parentheses
        if (close < open) {
            current.append(')');
            backtrack(result, current, open, close + 1, max);
            current.deleteCharAt(current.length() - 1); // Backtrack by removing the last character
        }
    }
    
    /**
     * Alternative implementation using a stack-based approach (iterative).
     * This is less common but demonstrates how to solve the problem without recursion.
     */
    public List<String> generateParenthesisIterative(int n) {
        List<String> result = new ArrayList<>();
        
        // Use a stack to keep track of the state: [current string, open count, close count]
        Stack<Object[]> stack = new Stack<>();
        stack.push(new Object[]{"", 0, 0});
        
        while (!stack.isEmpty()) {
            Object[] state = stack.pop();
            String current = (String) state[0];
            int open = (int) state[1];
            int close = (int) state[2];
            
            // Base case: if the current string has 2*n characters, it's complete
            if (current.length() == n * 2) {
                result.add(current);
                continue;
            }
            
            // We can add a closing parenthesis if it doesn't exceed the number of opening parentheses
            if (close < open) {
                stack.push(new Object[]{current + ")", open, close + 1});
            }
            
            // We can add an opening parenthesis if we haven't used all n
            if (open < n) {
                stack.push(new Object[]{current + "(", open + 1, close});
            }
        }
        
        return result;
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        GenerateParentheses solution = new GenerateParentheses();
        
        // Example 1: Should return ["((()))","(()())","(())()","()(())","()()()"]
        int n1 = 3;
        System.out.println("Example 1 (Recursive): " + solution.generateParenthesis(n1));
        System.out.println("Example 1 (Iterative): " + solution.generateParenthesisIterative(n1));
        
        // Example 2: Should return ["()"]
        int n2 = 1;
        System.out.println("Example 2 (Recursive): " + solution.generateParenthesis(n2));
        System.out.println("Example 2 (Iterative): " + solution.generateParenthesisIterative(n2));
        
        // Let's trace through a simplified example with n = 2:
        // Call backtrack(result, "", 0, 0, 2)
        
        // open = 0 < max = 2, so we can add '('
        // Call backtrack(result, "(", 1, 0, 2)
        
        //   open = 1 < max = 2, so we can add '('
        //   Call backtrack(result, "((", 2, 0, 2)
        
        //     open = 2 == max = 2, so we can't add more '('
        //     close = 0 < open = 2, so we can add ')'
        //     Call backtrack(result, "(()", 2, 1, 2)
        
        //       open = 2 == max = 2, so we can't add more '('
        //       close = 1 < open = 2, so we can add ')'
        //       Call backtrack(result, "(())", 2, 2, 2)
        
        //         current.length() = 4 == max*2 = 4, so add "(())" to result
        //         result = ["(())"]
        
        //       Backtrack: remove the last ')', current = "(()"
        
        //     Backtrack: remove the last ')', current = "(("
        
        //   Backtrack: remove the last '(', current = "("
        
        //   close = 0 < open = 1, so we can add ')'
        //   Call backtrack(result, "()", 1, 1, 2)
        
        //     open = 1 < max = 2, so we can add '('
        //     Call backtrack(result, "()(", 2, 1, 2)
        
        //       open = 2 == max = 2, so we can't add more '('
        //       close = 1 < open = 2, so we can add ')'
        //       Call backtrack(result, "()()", 2, 2, 2)
        
        //         current.length() = 4 == max*2 = 4, so add "()()" to result
        //         result = ["(())", "()()"]
        
        //       Backtrack: remove the last ')', current = "()("
        
        //     Backtrack: remove the last '(', current = "()"
        
        //   Backtrack: remove the last ')', current = "("
        
        // Backtrack: remove the last '(', current = ""
        
        // Final result = ["(())", "()()"]
    }
}
