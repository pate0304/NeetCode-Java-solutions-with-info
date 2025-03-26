package neetcode.stack;

import java.util.Stack;

/**
 * NeetCode Problem 3 (Stack): Evaluate Reverse Polish Notation
 * 
 * Problem Description:
 * Evaluate the value of an arithmetic expression in Reverse Polish Notation (RPN).
 * 
 * Valid operators are +, -, *, and /. Each operand may be an integer or another expression.
 * 
 * Note that division between two integers should truncate toward zero.
 * 
 * It is guaranteed that the given RPN expression is always valid. That means the expression would
 * always evaluate to a result, and there will not be any division by zero operation.
 * 
 * Examples:
 * Input: tokens = ["2","1","+","3","*"]
 * Output: 9
 * Explanation: ((2 + 1) * 3) = 9
 * 
 * Input: tokens = ["4","13","5","/","+"]
 * Output: 6
 * Explanation: (4 + (13 / 5)) = 6
 * 
 * Input: tokens = ["10","6","9","3","+","-11","*","/","*","17","+","5","+"]
 * Output: 22
 * Explanation: ((10 * (6 / ((9 + 3) * -11))) + 17) + 5
 * = ((10 * (6 / (12 * -11))) + 17) + 5
 * = ((10 * (6 / -132)) + 17) + 5
 * = ((10 * 0) + 17) + 5
 * = (0 + 17) + 5
 * = 17 + 5
 * = 22
 * 
 * Constraints:
 * - 1 <= tokens.length <= 10^4
 * - tokens[i] is either an operator: "+", "-", "*", or "/", or an integer in the range [-200, 200].
 * 
 * Approach:
 * Reverse Polish Notation (RPN) is designed to be evaluated using a stack:
 * 
 * 1. Initialize an empty stack to store operands
 * 2. Iterate through each token in the expression:
 *    - If the token is an operand (number), push it onto the stack
 *    - If the token is an operator (+, -, *, /):
 *      - Pop the top two elements from the stack (the second operand first, then the first operand)
 *      - Perform the operation
 *      - Push the result back onto the stack
 * 3. After processing all tokens, the stack should contain only one element, which is the final result
 * 
 * Time Complexity: O(n) where n is the number of tokens
 * Space Complexity: O(n) for the stack in the worst case
 */
public class EvaluateReversePolishNotation {
    
    /**
     * Evaluates an arithmetic expression in Reverse Polish Notation.
     * 
     * @param tokens The array of tokens representing the RPN expression
     * @return The result of evaluating the expression
     */
    public int evalRPN(String[] tokens) {
        // Initialize a stack to store operands
        Stack<Integer> stack = new Stack<>();
        
        // Iterate through each token
        for (String token : tokens) {
            // If the token is an operator, perform the operation
            if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")) {
                // Pop the top two elements from the stack
                int secondOperand = stack.pop();
                int firstOperand = stack.pop();
                
                // Perform the operation and push the result back onto the stack
                if (token.equals("+")) {
                    stack.push(firstOperand + secondOperand);
                } else if (token.equals("-")) {
                    stack.push(firstOperand - secondOperand);
                } else if (token.equals("*")) {
                    stack.push(firstOperand * secondOperand);
                } else { // token.equals("/")
                    stack.push(firstOperand / secondOperand); // Integer division truncates toward zero
                }
            }
            // If the token is an operand (number), push it onto the stack
            else {
                stack.push(Integer.parseInt(token));
            }
        }
        
        // The final result should be the only element left in the stack
        return stack.pop();
    }
    
    /**
     * Alternative implementation using a switch statement for cleaner code.
     */
    public int evalRPNAlternative(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        
        for (String token : tokens) {
            switch (token) {
                case "+":
                    stack.push(stack.pop() + stack.pop());
                    break;
                case "-":
                    int secondOperand = stack.pop();
                    int firstOperand = stack.pop();
                    stack.push(firstOperand - secondOperand);
                    break;
                case "*":
                    stack.push(stack.pop() * stack.pop());
                    break;
                case "/":
                    int divisor = stack.pop();
                    int dividend = stack.pop();
                    stack.push(dividend / divisor);
                    break;
                default:
                    // If the token is an operand (number), push it onto the stack
                    stack.push(Integer.parseInt(token));
            }
        }
        
        return stack.pop();
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        EvaluateReversePolishNotation solution = new EvaluateReversePolishNotation();
        
        // Example 1: Should return 9
        String[] tokens1 = {"2", "1", "+", "3", "*"};
        System.out.println("Example 1: " + solution.evalRPN(tokens1));
        System.out.println("Example 1 (Alternative): " + solution.evalRPNAlternative(tokens1));
        
        // Example 2: Should return 6
        String[] tokens2 = {"4", "13", "5", "/", "+"};
        System.out.println("Example 2: " + solution.evalRPN(tokens2));
        System.out.println("Example 2 (Alternative): " + solution.evalRPNAlternative(tokens2));
        
        // Example 3: Should return 22
        String[] tokens3 = {"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"};
        System.out.println("Example 3: " + solution.evalRPN(tokens3));
        System.out.println("Example 3 (Alternative): " + solution.evalRPNAlternative(tokens3));
        
        // Let's trace through the execution of Example 1:
        // tokens = ["2", "1", "+", "3", "*"]
        
        // Initialize stack = []
        
        // token = "2" (operand)
        // Push onto stack: stack = [2]
        
        // token = "1" (operand)
        // Push onto stack: stack = [2, 1]
        
        // token = "+" (operator)
        // Pop secondOperand = 1, firstOperand = 2
        // Perform operation: 2 + 1 = 3
        // Push result onto stack: stack = [3]
        
        // token = "3" (operand)
        // Push onto stack: stack = [3, 3]
        
        // token = "*" (operator)
        // Pop secondOperand = 3, firstOperand = 3
        // Perform operation: 3 * 3 = 9
        // Push result onto stack: stack = [9]
        
        // Final result: stack.pop() = 9
    }
}
