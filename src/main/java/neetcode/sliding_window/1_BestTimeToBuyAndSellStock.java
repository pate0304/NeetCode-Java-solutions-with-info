package neetcode.sliding_window;

/**
 * NeetCode Problem 1 (Sliding Window): Best Time to Buy and Sell Stock
 * 
 * Problem Description:
 * You are given an array prices where prices[i] is the price of a given stock on the ith day.
 * You want to maximize your profit by choosing a single day to buy one stock and choosing a 
 * different day in the future to sell that stock.
 * 
 * Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return 0.
 * 
 * Examples:
 * Input: prices = [7,1,5,3,6,4]
 * Output: 5
 * Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
 * Note that buying on day 2 and selling on day 1 is not allowed because you must buy before you sell.
 * 
 * Input: prices = [7,6,4,3,1]
 * Output: 0
 * Explanation: In this case, no transactions are done and the max profit = 0.
 * 
 * Constraints:
 * - 1 <= prices.length <= 10^5
 * - 0 <= prices[i] <= 10^4
 * 
 * Approach:
 * This problem can be solved using the sliding window technique:
 * 
 * 1. Keep track of the minimum price seen so far (this will be our "buy" price)
 * 2. For each price, calculate the profit if we sell at that price
 * 3. Update the maximum profit if the current profit is higher
 * 
 * The key insight is that to maximize profit, we want to buy at the lowest price
 * and sell at the highest price after the buy date.
 * 
 * Time Complexity: O(n) where n is the length of the prices array
 * Space Complexity: O(1) as we're using constant extra space
 */
public class BestTimeToBuyAndSellStock {
    
    /**
     * Calculates the maximum profit that can be achieved by buying and selling a stock once.
     * 
     * @param prices The array of stock prices where prices[i] is the price on day i
     * @return The maximum profit that can be achieved, or 0 if no profit is possible
     */
    public int maxProfit(int[] prices) {
        // Edge case: empty array or array with only one price
        if (prices == null || prices.length <= 1) {
            return 0;
        }
        
        // Initialize the minimum price seen so far and the maximum profit
        int minPrice = prices[0];
        int maxProfit = 0;
        
        // Iterate through the prices starting from the second day
        for (int i = 1; i < prices.length; i++) {
            // Update the minimum price seen so far
            minPrice = Math.min(minPrice, prices[i]);
            
            // Calculate the profit if we sell at the current price
            int currentProfit = prices[i] - minPrice;
            
            // Update the maximum profit if the current profit is higher
            maxProfit = Math.max(maxProfit, currentProfit);
        }
        
        return maxProfit;
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        BestTimeToBuyAndSellStock solution = new BestTimeToBuyAndSellStock();
        
        // Example 1: Should return 5
        int[] prices1 = {7, 1, 5, 3, 6, 4};
        System.out.println("Example 1: " + solution.maxProfit(prices1));
        
        // Example 2: Should return 0
        int[] prices2 = {7, 6, 4, 3, 1};
        System.out.println("Example 2: " + solution.maxProfit(prices2));
        
        // Let's trace through the execution of Example 1 to understand the algorithm better:
        // prices = [7, 1, 5, 3, 6, 4]
        
        // Initialize minPrice = 7, maxProfit = 0
        
        // i = 1, prices[i] = 1
        // minPrice = min(7, 1) = 1
        // currentProfit = 1 - 1 = 0
        // maxProfit = max(0, 0) = 0
        
        // i = 2, prices[i] = 5
        // minPrice = min(1, 5) = 1
        // currentProfit = 5 - 1 = 4
        // maxProfit = max(0, 4) = 4
        
        // i = 3, prices[i] = 3
        // minPrice = min(1, 3) = 1
        // currentProfit = 3 - 1 = 2
        // maxProfit = max(4, 2) = 4
        
        // i = 4, prices[i] = 6
        // minPrice = min(1, 6) = 1
        // currentProfit = 6 - 1 = 5
        // maxProfit = max(4, 5) = 5
        
        // i = 5, prices[i] = 4
        // minPrice = min(1, 4) = 1
        // currentProfit = 4 - 1 = 3
        // maxProfit = max(5, 3) = 5
        
        // Final result: maxProfit = 5
        
        // Additional example
        int[] prices3 = {2, 4, 1, 7};
        System.out.println("Example 3: " + solution.maxProfit(prices3));
    }
}
