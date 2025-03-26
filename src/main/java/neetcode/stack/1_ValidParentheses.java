package neetcode.stack;

import java.util.Stack;
import java.util.HashMap;
import java.util.Map;

/**
 * NeetCode Problem 1 (Stack): Valid Parentheses
 * 
 * Problem Description:
 * Given a string s containing just the characters '(', ')', '{', '}', '[' and ']',
 * determine if the input string is valid.
 * 
 * An input string is valid if:
 * 1. Open brackets must be closed by the same type of brackets.
 * 2. Open brackets must be closed in the correct order.
 * 3. Every close bracket has a corresponding open bracket of the same type.
 * 
 * Examples:
 * Input: s = "()"
 * Output: true
 * 
 * Input: s = "()[]{}"
 * Output: true
 * 
 * Input: s = "(]"
 * Output: false
 * 
 * Input: s = "([)]"
 * Output: false
 * 
 * Input: s = "{[]}"
 * Output: true
 * 
 * Constraints:
 * - 1 <= s.length <= 10^4
 * - s consists of parentheses only '()[]{}'
 * 
 * Approach:
 * This is a classic problem that can be solved using a stack:
 * 
 * 1. Initialize an empty stack to keep track of opening brackets
 * 2. Iterate through each character in the string:
 *    - If it's an opening bracket ('(', '{', '['), push it onto the stack
 *    - If it's a closing bracket (')', '}', ']'):
 *      - If the stack is empty, return false (no matching opening bracket)
 *      - Pop the top element from the stack
 *      - If the popped element isn't the matching opening bracket, return false
 * 3. After processing all characters, the stack should be empty
 *    - If it's not empty, there are unmatched opening brackets, so return false
 *    - If it's empty, all brackets are properly matched, so return true
 * 
 * Time Complexity: O(n) where n is the length of the string
 * Space Complexity: O(n) in the worst case (e.g., all opening brackets)
 */
public class ValidParentheses {
    
    /**
     * Determines if the input string has valid parentheses.
     * 
     * @param s The input string containing only parentheses
     * @return true if the parentheses are valid, false otherwise
     */
    public boolean isValid(String s) {
        // Edge case: empty string
        if (s == null || s.isEmpty()) {
            return true;
        }
        
        // Initialize a stack to keep track of opening brackets
        Stack<Character> stack = new Stack<>();
        
        // Iterate through each character in the string
        for (char c : s.toCharArray()) {
            // If it's an opening bracket, push it onto the stack
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            }
            // If it's a closing bracket
            else {
                // If the stack is empty, there's no matching opening bracket
                if (stack.isEmpty()) {
                    return false;
                }
                
                // Pop the top element from the stack
                char top = stack.pop();
                
                // Check if the popped element matches the current closing bracket
                if ((c == ')' && top != '(') ||
                    (c == '}' && top != '{') ||
                    (c == ']' && top != '[')) {
                    return false;
                }
            }
        }
        
        // After processing all characters, the stack should be empty
        // If it's not empty, there are unmatched opening brackets
        return stack.isEmpty();
    }
    
    /**
     * Alternative implementation using a HashMap to map closing brackets to their opening brackets.
     * This makes the code more concise and easier to extend if needed.
     */
    public boolean isValidAlternative(String s) {
        // Edge case: empty string
        if (s == null || s.isEmpty()) {
            return true;
        }
        
        // Initialize a stack to keep track of opening brackets
        Stack<Character> stack = new Stack<>();
        
        // Create a map to match closing brackets with their opening brackets
        Map<Character, Character> bracketMap = new HashMap<>();
        bracketMap.put(')', '(');
        bracketMap.put('}', '{');
        bracketMap.put(']', '[');
        
        // Iterate through each character in the string
        for (char c : s.toCharArray()) {
            // If it's a closing bracket
            if (bracketMap.containsKey(c)) {
                // Get the top element from the stack, or a dummy value if the stack is empty
                char top = stack.isEmpty() ? '#' : stack.pop();
                
                // If the top element doesn't match the expected opening bracket, return false
                if (top != bracketMap.get(c)) {
                    return false;
                }
            }
            // If it's an opening bracket, push it onto the stack
            else {
                stack.push(c);
            }
        }
        
        // After processing all characters, the stack should be empty
        return stack.isEmpty();
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        ValidParentheses solution = new ValidParentheses();
        
        // Example 1: Should return true
        String s1 = "()";
        System.out.println("Example 1: " + solution.isValid(s1));
        System.out.println("Example 1 (Alternative): " + solution.isValidAlternative(s1));
        
        // Example 2: Should return true
        String s2 = "()[]{}";
        System.out.println("Example 2: " + solution.isValid(s2));
        System.out.println("Example 2 (Alternative): " + solution.isValidAlternative(s2));
        
        // Example 3: Should return false
        String s3 = "(]";
        System.out.println("Example 3: " + solution.isValid(s3));
        System.out.println("Example 3 (Alternative): " + solution.isValidAlternative(s3));
        
        // Example 4: Should return false
        String s4 = "([)]";
        System.out.println("Example 4: " + solution.isValid(s4));
        System.out.println("Example 4 (Alternative): " + solution.isValidAlternative(s4));
        
        // Example 5: Should return true
        String s5 = "{[]}";
        System.out.println("Example 5: " + solution.isValid(s5));
        System.out.println("Example 5 (Alternative): " + solution.isValidAlternative(s5));
        
        // Let's trace through the execution of Example 5 to understand the algorithm better:
        // s = "{[]}"
        
        // Initialize stack = []
        
        // c = '{' (opening bracket)
        // Push onto stack: stack = ['{']
        
        // c = '[' (opening bracket)
        // Push onto stack: stack = ['{', '[']
        
        // c = ']' (closing bracket)
        // Pop from stack: top = '['
        // Check if top matches the expected opening bracket for ']': '[' matches '['
        
        // c = '}' (closing bracket)
        // Pop from stack: top = '{'
        // Check if top matches the expected opening bracket for '}': '{' matches '{'
        
        // After processing all characters, stack = [] (empty)
        // Return true
    }
}
