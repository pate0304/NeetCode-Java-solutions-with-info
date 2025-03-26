package neetcode.sliding_window;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * NeetCode Problem 2 (Sliding Window): Longest Substring Without Repeating Characters
 * 
 * Problem Description:
 * Given a string s, find the length of the longest substring without repeating characters.
 * 
 * Examples:
 * Input: s = "abcabcbb"
 * Output: 3
 * Explanation: The answer is "abc", with the length of 3.
 * 
 * Input: s = "bbbbb"
 * Output: 1
 * Explanation: The answer is "b", with the length of 1.
 * 
 * Input: s = "pwwkew"
 * Output: 3
 * Explanation: The answer is "wke", with the length of 3.
 * Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
 * 
 * Constraints:
 * - 0 <= s.length <= 5 * 10^4
 * - s consists of English letters, digits, symbols and spaces.
 * 
 * Approach:
 * This problem can be solved using the sliding window technique with a hash set or hash map:
 * 
 * 1. Use two pointers (left and right) to represent the current window
 * 2. Expand the window by moving the right pointer and adding characters
 * 3. If a duplicate character is found, shrink the window from the left until the duplicate is removed
 * 4. Keep track of the maximum window size seen so far
 * 
 * There are two common implementations:
 * 1. Using a HashSet to track characters in the current window
 * 2. Using a HashMap to track characters and their indices for optimized window shrinking
 * 
 * Time Complexity: O(n) where n is the length of the string
 * Space Complexity: O(min(m, n)) where m is the size of the character set
 */
public class LongestSubstringWithoutRepeatingCharacters {
    
    /**
     * Finds the length of the longest substring without repeating characters using a HashSet.
     * 
     * @param s The input string
     * @return The length of the longest substring without repeating characters
     */
    public int lengthOfLongestSubstring(String s) {
        // Edge case: empty string
        if (s == null || s.isEmpty()) {
            return 0;
        }
        
        // Initialize a set to track characters in the current window
        Set<Character> charSet = new HashSet<>();
        
        // Initialize two pointers for the sliding window
        int left = 0;
        int right = 0;
        
        // Initialize the maximum length
        int maxLength = 0;
        
        // Expand the window by moving the right pointer
        while (right < s.length()) {
            char currentChar = s.charAt(right);
            
            // If the current character is already in the window,
            // shrink the window from the left until the duplicate is removed
            while (charSet.contains(currentChar)) {
                charSet.remove(s.charAt(left));
                left++;
            }
            
            // Add the current character to the window
            charSet.add(currentChar);
            
            // Update the maximum length
            maxLength = Math.max(maxLength, right - left + 1);
            
            // Expand the window
            right++;
        }
        
        return maxLength;
    }
    
    /**
     * Optimized implementation using a HashMap to track character indices.
     * This allows us to directly jump the left pointer when a duplicate is found.
     */
    public int lengthOfLongestSubstringOptimized(String s) {
        // Edge case: empty string
        if (s == null || s.isEmpty()) {
            return 0;
        }
        
        // Initialize a map to track character indices
        Map<Character, Integer> charIndexMap = new HashMap<>();
        
        // Initialize the maximum length
        int maxLength = 0;
        
        // Initialize the left pointer
        int left = 0;
        
        // Iterate through the string with the right pointer
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            
            // If the current character is already in the window,
            // update the left pointer to skip the duplicate
            if (charIndexMap.containsKey(currentChar)) {
                // Move the left pointer to the position after the duplicate
                // But don't move it backwards (hence the Math.max)
                left = Math.max(left, charIndexMap.get(currentChar) + 1);
            }
            
            // Update the character's index in the map
            charIndexMap.put(currentChar, right);
            
            // Update the maximum length
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        LongestSubstringWithoutRepeatingCharacters solution = new LongestSubstringWithoutRepeatingCharacters();
        
        // Example 1: Should return 3
        String s1 = "abcabcbb";
        System.out.println("Example 1 (HashSet): " + solution.lengthOfLongestSubstring(s1));
        System.out.println("Example 1 (HashMap): " + solution.lengthOfLongestSubstringOptimized(s1));
        
        // Example 2: Should return 1
        String s2 = "bbbbb";
        System.out.println("Example 2 (HashSet): " + solution.lengthOfLongestSubstring(s2));
        System.out.println("Example 2 (HashMap): " + solution.lengthOfLongestSubstringOptimized(s2));
        
        // Example 3: Should return 3
        String s3 = "pwwkew";
        System.out.println("Example 3 (HashSet): " + solution.lengthOfLongestSubstring(s3));
        System.out.println("Example 3 (HashMap): " + solution.lengthOfLongestSubstringOptimized(s3));
        
        // Let's trace through the execution of Example 1 using the HashSet approach:
        // s = "abcabcbb"
        
        // Initialize charSet = {}, left = 0, right = 0, maxLength = 0
        
        // right = 0, currentChar = 'a'
        // charSet doesn't contain 'a', so add it: charSet = {'a'}
        // maxLength = max(0, 0 - 0 + 1) = 1, right = 1
        
        // right = 1, currentChar = 'b'
        // charSet doesn't contain 'b', so add it: charSet = {'a', 'b'}
        // maxLength = max(1, 1 - 0 + 1) = 2, right = 2
        
        // right = 2, currentChar = 'c'
        // charSet doesn't contain 'c', so add it: charSet = {'a', 'b', 'c'}
        // maxLength = max(2, 2 - 0 + 1) = 3, right = 3
        
        // right = 3, currentChar = 'a'
        // charSet contains 'a', so remove characters from the left until 'a' is removed
        // Remove 'a': charSet = {'b', 'c'}, left = 1
        // Add 'a': charSet = {'b', 'c', 'a'}
        // maxLength = max(3, 3 - 1 + 1) = 3, right = 4
        
        // right = 4, currentChar = 'b'
        // charSet contains 'b', so remove characters from the left until 'b' is removed
        // Remove 'b': charSet = {'c', 'a'}, left = 2
        // Add 'b': charSet = {'c', 'a', 'b'}
        // maxLength = max(3, 4 - 2 + 1) = 3, right = 5
        
        // right = 5, currentChar = 'c'
        // charSet contains 'c', so remove characters from the left until 'c' is removed
        // Remove 'c': charSet = {'a', 'b'}, left = 3
        // Add 'c': charSet = {'a', 'b', 'c'}
        // maxLength = max(3, 5 - 3 + 1) = 3, right = 6
        
        // right = 6, currentChar = 'b'
        // charSet contains 'b', so remove characters from the left until 'b' is removed
        // Remove 'a': charSet = {'b', 'c'}, left = 4
        // Remove 'b': charSet = {'c'}, left = 5
        // Add 'b': charSet = {'c', 'b'}
        // maxLength = max(3, 6 - 5 + 1) = 3, right = 7
        
        // right = 7, currentChar = 'b'
        // charSet contains 'b', so remove characters from the left until 'b' is removed
        // Remove 'c': charSet = {'b'}, left = 6
        // Remove 'b': charSet = {}, left = 7
        // Add 'b': charSet = {'b'}
        // maxLength = max(3, 7 - 7 + 1) = 3, right = 8
        
        // right = 8, which is out of bounds, so we exit the loop
        // Final result: maxLength = 3
    }
}
