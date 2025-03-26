package neetcode.sliding_window;

import java.util.HashMap;
import java.util.Map;

/**
 * NeetCode Problem 5 (Sliding Window): Minimum Window Substring
 * 
 * Problem Description:
 * Given two strings s and t of lengths m and n respectively, return the minimum window substring
 * of s such that every character in t (including duplicates) is included in the window.
 * If there is no such substring, return the empty string "".
 * 
 * The testcases will be generated such that the answer is unique.
 * 
 * Examples:
 * Input: s = "ADOBECODEBANC", t = "ABC"
 * Output: "BANC"
 * Explanation: The minimum window substring "BANC" includes 'A', 'B', and 'C' from string t.
 * 
 * Input: s = "a", t = "a"
 * Output: "a"
 * Explanation: The entire string s is the minimum window.
 * 
 * Input: s = "a", t = "aa"
 * Output: ""
 * Explanation: Both 'a's from t must be included in the window.
 * Since the largest window of s only has one 'a', return empty string.
 * 
 * Constraints:
 * - m == s.length
 * - n == t.length
 * - 1 <= m, n <= 10^5
 * - s and t consist of uppercase and lowercase English letters.
 * 
 * Approach:
 * This problem can be solved using the sliding window technique:
 * 
 * 1. Create a frequency map for characters in string t
 * 2. Initialize two pointers (left and right) to represent the current window
 * 3. Expand the window by moving the right pointer until all characters in t are included
 * 4. Once all characters are included, try to minimize the window by moving the left pointer
 * 5. Keep track of the minimum window seen so far
 * 
 * Time Complexity: O(m + n) where m is the length of s and n is the length of t
 * Space Complexity: O(n) for the frequency maps
 */
public class MinimumWindowSubstring {
    
    /**
     * Finds the minimum window substring of s that contains all characters in t.
     * 
     * @param s The source string
     * @param t The target string
     * @return The minimum window substring, or an empty string if no such window exists
     */
    public String minWindow(String s, String t) {
        // Edge cases
        if (s == null || t == null || s.isEmpty() || t.isEmpty() || s.length() < t.length()) {
            return "";
        }
        
        // Create frequency maps for characters in t and the current window
        Map<Character, Integer> targetFreq = new HashMap<>();
        Map<Character, Integer> windowFreq = new HashMap<>();
        
        // Fill the frequency map for t
        for (char c : t.toCharArray()) {
            targetFreq.put(c, targetFreq.getOrDefault(c, 0) + 1);
        }
        
        // Initialize variables to track the window
        int left = 0;
        int right = 0;
        int required = targetFreq.size(); // Number of unique characters in t
        int formed = 0; // Number of characters in the current window that match the required frequency
        
        // Variables to store the minimum window
        int minLen = Integer.MAX_VALUE;
        int minLeft = 0;
        
        // Expand the window by moving the right pointer
        while (right < s.length()) {
            char rightChar = s.charAt(right);
            
            // Add the current character to the window frequency map
            windowFreq.put(rightChar, windowFreq.getOrDefault(rightChar, 0) + 1);
            
            // Check if this character helps form a valid window
            if (targetFreq.containsKey(rightChar) && 
                windowFreq.get(rightChar).intValue() == targetFreq.get(rightChar).intValue()) {
                formed++;
            }
            
            // Try to minimize the window by moving the left pointer
            while (left <= right && formed == required) {
                char leftChar = s.charAt(left);
                
                // Update the minimum window if the current window is smaller
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    minLeft = left;
                }
                
                // Remove the leftmost character from the window
                windowFreq.put(leftChar, windowFreq.get(leftChar) - 1);
                
                // Check if removing this character breaks the valid window
                if (targetFreq.containsKey(leftChar) && 
                    windowFreq.get(leftChar).intValue() < targetFreq.get(leftChar).intValue()) {
                    formed--;
                }
                
                // Move the left pointer
                left++;
            }
            
            // Expand the window
            right++;
        }
        
        // Return the minimum window, or an empty string if no valid window was found
        return minLen == Integer.MAX_VALUE ? "" : s.substring(minLeft, minLeft + minLen);
    }
    
    /**
     * Alternative implementation using arrays instead of HashMaps for character frequencies.
     * This is more efficient when the character set is limited (e.g., ASCII characters).
     */
    public String minWindowOptimized(String s, String t) {
        if (s == null || t == null || s.isEmpty() || t.isEmpty() || s.length() < t.length()) {
            return "";
        }
        
        // Create arrays to store character frequencies (assuming ASCII characters)
        int[] targetFreq = new int[128];
        int[] windowFreq = new int[128];
        
        // Fill the frequency array for t
        for (char c : t.toCharArray()) {
            targetFreq[c]++;
        }
        
        // Count how many unique characters we need to match
        int required = 0;
        for (int freq : targetFreq) {
            if (freq > 0) {
                required++;
            }
        }
        
        int left = 0;
        int right = 0;
        int formed = 0;
        int minLen = Integer.MAX_VALUE;
        int minLeft = 0;
        
        while (right < s.length()) {
            char rightChar = s.charAt(right);
            
            // Add the current character to the window
            windowFreq[rightChar]++;
            
            // Check if this character helps form a valid window
            if (targetFreq[rightChar] > 0 && windowFreq[rightChar] == targetFreq[rightChar]) {
                formed++;
            }
            
            // Try to minimize the window
            while (left <= right && formed == required) {
                char leftChar = s.charAt(left);
                
                // Update the minimum window
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    minLeft = left;
                }
                
                // Remove the leftmost character from the window
                windowFreq[leftChar]--;
                
                // Check if removing this character breaks the valid window
                if (targetFreq[leftChar] > 0 && windowFreq[leftChar] < targetFreq[leftChar]) {
                    formed--;
                }
                
                left++;
            }
            
            right++;
        }
        
        return minLen == Integer.MAX_VALUE ? "" : s.substring(minLeft, minLeft + minLen);
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        MinimumWindowSubstring solution = new MinimumWindowSubstring();
        
        // Example 1: Should return "BANC"
        String s1 = "ADOBECODEBANC";
        String t1 = "ABC";
        System.out.println("Example 1 (HashMap): " + solution.minWindow(s1, t1));
        System.out.println("Example 1 (Array): " + solution.minWindowOptimized(s1, t1));
        
        // Example 2: Should return "a"
        String s2 = "a";
        String t2 = "a";
        System.out.println("Example 2 (HashMap): " + solution.minWindow(s2, t2));
        System.out.println("Example 2 (Array): " + solution.minWindowOptimized(s2, t2));
        
        // Example 3: Should return ""
        String s3 = "a";
        String t3 = "aa";
        System.out.println("Example 3 (HashMap): " + solution.minWindow(s3, t3));
        System.out.println("Example 3 (Array): " + solution.minWindowOptimized(s3, t3));
        
        // Let's trace through a simplified example:
        // s = "ABAC", t = "ABC"
        
        // targetFreq = {'A': 1, 'B': 1, 'C': 1}
        // required = 3 (3 unique characters in t)
        
        // Initialize left = 0, right = 0, formed = 0, minLen = MAX_VALUE
        
        // right = 0, rightChar = 'A'
        // windowFreq = {'A': 1}
        // formed = 1 (we've matched the frequency of 'A')
        
        // right = 1, rightChar = 'B'
        // windowFreq = {'A': 1, 'B': 1}
        // formed = 2 (we've matched the frequencies of 'A' and 'B')
        
        // right = 2, rightChar = 'A'
        // windowFreq = {'A': 2, 'B': 1}
        // formed = 2 (still 2, as 'A' is now over-represented)
        
        // right = 3, rightChar = 'C'
        // windowFreq = {'A': 2, 'B': 1, 'C': 1}
        // formed = 3 (we've matched all required characters)
        
        // Now we try to minimize the window:
        // left = 0, leftChar = 'A'
        // windowFreq = {'A': 1, 'B': 1, 'C': 1}
        // formed = 3 (still valid)
        // minLen = 4, minLeft = 0
        
        // left = 1, leftChar = 'B'
        // windowFreq = {'A': 1, 'B': 0, 'C': 1}
        // formed = 2 (no longer valid, as 'B' is under-represented)
        // Exit the inner loop
        
        // Final result: s.substring(0, 0 + 4) = "ABAC"
    }
}
