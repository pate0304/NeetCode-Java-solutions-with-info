package neetcode.sliding_window;

import java.util.HashMap;
import java.util.Map;

/**
 * NeetCode Problem 3 (Sliding Window): Longest Repeating Character Replacement
 * 
 * Problem Description:
 * You are given a string s and an integer k. You can choose any character of the string and 
 * change it to any other uppercase English character. You can perform this operation at most k times.
 * 
 * Return the length of the longest substring containing the same letter you can get after 
 * performing the above operations.
 * 
 * Examples:
 * Input: s = "ABAB", k = 2
 * Output: 4
 * Explanation: Replace the two 'A's with two 'B's or vice versa.
 * 
 * Input: s = "AABABBA", k = 1
 * Output: 4
 * Explanation: Replace the one 'A' in the middle with 'B' and form "AABBBBA".
 * The substring "BBBB" has the longest repeating letters, which is 4.
 * 
 * Constraints:
 * - 1 <= s.length <= 10^5
 * - s consists of only uppercase English letters.
 * - 0 <= k <= s.length
 * 
 * Approach:
 * This problem can be solved using the sliding window technique:
 * 
 * 1. Maintain a window where at most k characters can be replaced to make all characters the same
 * 2. Use a frequency map to keep track of character counts in the current window
 * 3. The key insight: if (window_length - count_of_most_frequent_char) <= k, then we can replace
 *    the other characters to make the entire window have the same character
 * 4. Expand the window as much as possible, and when the condition is violated, shrink the window
 * 
 * Time Complexity: O(n) where n is the length of the string
 * Space Complexity: O(1) since we're only dealing with uppercase English letters (26 characters)
 */
public class LongestRepeatingCharacterReplacement {
    
    /**
     * Finds the length of the longest substring with the same letter after at most k replacements.
     * 
     * @param s The input string
     * @param k The maximum number of replacements allowed
     * @return The length of the longest substring with the same letter
     */
    public int characterReplacement(String s, int k) {
        // Edge case: empty string
        if (s == null || s.isEmpty()) {
            return 0;
        }
        
        // Initialize a frequency map for characters in the current window
        Map<Character, Integer> charCount = new HashMap<>();
        
        // Initialize two pointers for the sliding window
        int left = 0;
        int right = 0;
        
        // Initialize the maximum frequency of any character in the current window
        int maxFreq = 0;
        
        // Initialize the maximum length
        int maxLength = 0;
        
        // Expand the window by moving the right pointer
        while (right < s.length()) {
            char currentChar = s.charAt(right);
            
            // Update the frequency of the current character
            charCount.put(currentChar, charCount.getOrDefault(currentChar, 0) + 1);
            
            // Update the maximum frequency
            maxFreq = Math.max(maxFreq, charCount.get(currentChar));
            
            // Calculate the number of characters that need to be replaced
            // window_length - count_of_most_frequent_char
            int replacements = (right - left + 1) - maxFreq;
            
            // If we need more replacements than k, shrink the window from the left
            if (replacements > k) {
                char leftChar = s.charAt(left);
                charCount.put(leftChar, charCount.get(leftChar) - 1);
                left++;
                
                // Note: We don't need to update maxFreq here because it might not change
                // and we're looking for the maximum possible window length
            }
            
            // Update the maximum length
            maxLength = Math.max(maxLength, right - left + 1);
            
            // Expand the window
            right++;
        }
        
        return maxLength;
    }
    
    /**
     * Alternative implementation using an array instead of a HashMap for character counts.
     * This is more efficient for this specific problem since we're only dealing with uppercase letters.
     */
    public int characterReplacementOptimized(String s, int k) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        
        // Initialize an array to count frequencies of uppercase letters (A-Z)
        int[] charCount = new int[26];
        
        int left = 0;
        int right = 0;
        int maxFreq = 0;
        int maxLength = 0;
        
        while (right < s.length()) {
            // Update the frequency of the current character
            charCount[s.charAt(right) - 'A']++;
            
            // Update the maximum frequency
            maxFreq = Math.max(maxFreq, charCount[s.charAt(right) - 'A']);
            
            // Calculate the number of characters that need to be replaced
            int replacements = (right - left + 1) - maxFreq;
            
            // If we need more replacements than k, shrink the window from the left
            if (replacements > k) {
                charCount[s.charAt(left) - 'A']--;
                left++;
            }
            
            // Update the maximum length
            maxLength = Math.max(maxLength, right - left + 1);
            
            // Expand the window
            right++;
        }
        
        return maxLength;
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        LongestRepeatingCharacterReplacement solution = new LongestRepeatingCharacterReplacement();
        
        // Example 1: Should return 4
        String s1 = "ABAB";
        int k1 = 2;
        System.out.println("Example 1 (HashMap): " + solution.characterReplacement(s1, k1));
        System.out.println("Example 1 (Array): " + solution.characterReplacementOptimized(s1, k1));
        
        // Example 2: Should return 4
        String s2 = "AABABBA";
        int k2 = 1;
        System.out.println("Example 2 (HashMap): " + solution.characterReplacement(s2, k2));
        System.out.println("Example 2 (Array): " + solution.characterReplacementOptimized(s2, k2));
        
        // Let's trace through the execution of Example 1:
        // s = "ABAB", k = 2
        
        // Initialize charCount = {}, left = 0, right = 0, maxFreq = 0, maxLength = 0
        
        // right = 0, currentChar = 'A'
        // charCount = {'A': 1}, maxFreq = 1
        // replacements = (0 - 0 + 1) - 1 = 0 <= k, so we don't shrink the window
        // maxLength = max(0, 0 - 0 + 1) = 1, right = 1
        
        // right = 1, currentChar = 'B'
        // charCount = {'A': 1, 'B': 1}, maxFreq = 1
        // replacements = (1 - 0 + 1) - 1 = 1 <= k, so we don't shrink the window
        // maxLength = max(1, 1 - 0 + 1) = 2, right = 2
        
        // right = 2, currentChar = 'A'
        // charCount = {'A': 2, 'B': 1}, maxFreq = 2
        // replacements = (2 - 0 + 1) - 2 = 1 <= k, so we don't shrink the window
        // maxLength = max(2, 2 - 0 + 1) = 3, right = 3
        
        // right = 3, currentChar = 'B'
        // charCount = {'A': 2, 'B': 2}, maxFreq = 2
        // replacements = (3 - 0 + 1) - 2 = 2 <= k, so we don't shrink the window
        // maxLength = max(3, 3 - 0 + 1) = 4, right = 4
        
        // right = 4, which is out of bounds, so we exit the loop
        // Final result: maxLength = 4
        
        // Additional example
        String s3 = "AAAA";
        int k3 = 2;
        System.out.println("Example 3: " + solution.characterReplacement(s3, k3));
    }
}
