package neetcode.sliding_window;

import java.util.Arrays;

/**
 * NeetCode Problem 4 (Sliding Window): Permutation in String
 * 
 * Problem Description:
 * Given two strings s1 and s2, return true if s2 contains a permutation of s1, or false otherwise.
 * 
 * In other words, return true if one of s1's permutations is the substring of s2.
 * 
 * Examples:
 * Input: s1 = "ab", s2 = "eidbaooo"
 * Output: true
 * Explanation: s2 contains one permutation of s1 ("ba").
 * 
 * Input: s1 = "ab", s2 = "eidboaoo"
 * Output: false
 * 
 * Constraints:
 * - 1 <= s1.length, s2.length <= 10^4
 * - s1 and s2 consist of lowercase English letters.
 * 
 * Approach:
 * This problem can be solved using the sliding window technique with character frequency counting:
 * 
 * 1. Create a frequency array for characters in s1
 * 2. Use a sliding window of size s1.length in s2
 * 3. For each window, check if the character frequencies match those of s1
 * 4. If they match, return true
 * 5. If no window matches, return false
 * 
 * We can optimize this by maintaining a frequency array for the current window and
 * updating it as we slide the window, rather than recalculating it for each position.
 * 
 * Time Complexity: O(n) where n is the length of s2
 * Space Complexity: O(1) since we're only dealing with lowercase English letters (26 characters)
 */
public class PermutationInString {
    
    /**
     * Determines if s2 contains a permutation of s1.
     * 
     * @param s1 The first string
     * @param s2 The second string
     * @return true if s2 contains a permutation of s1, false otherwise
     */
    public boolean checkInclusion(String s1, String s2) {
        // Edge cases
        if (s1 == null || s2 == null || s1.length() > s2.length()) {
            return false;
        }
        
        // Create frequency arrays for s1 and the current window in s2
        int[] s1Freq = new int[26];
        int[] windowFreq = new int[26];
        
        // Fill the frequency array for s1
        for (char c : s1.toCharArray()) {
            s1Freq[c - 'a']++;
        }
        
        // Initialize the window in s2
        int windowSize = s1.length();
        for (int i = 0; i < windowSize; i++) {
            windowFreq[s2.charAt(i) - 'a']++;
        }
        
        // Check if the initial window is a permutation of s1
        if (Arrays.equals(s1Freq, windowFreq)) {
            return true;
        }
        
        // Slide the window through s2
        for (int i = windowSize; i < s2.length(); i++) {
            // Add the new character to the window
            windowFreq[s2.charAt(i) - 'a']++;
            
            // Remove the character that's no longer in the window
            windowFreq[s2.charAt(i - windowSize) - 'a']--;
            
            // Check if the current window is a permutation of s1
            if (Arrays.equals(s1Freq, windowFreq)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Alternative implementation using a more efficient approach with a counter.
     * Instead of comparing the entire frequency arrays, we keep track of how many
     * characters have the correct frequency.
     */
    public boolean checkInclusionOptimized(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() > s2.length()) {
            return false;
        }
        
        int[] s1Freq = new int[26];
        int[] windowFreq = new int[26];
        
        // Fill the frequency array for s1
        for (char c : s1.toCharArray()) {
            s1Freq[c - 'a']++;
        }
        
        // Count how many characters have the correct frequency
        int matches = 0;
        
        // Initialize the window in s2
        int windowSize = s1.length();
        for (int i = 0; i < windowSize; i++) {
            int idx = s2.charAt(i) - 'a';
            windowFreq[idx]++;
            
            // If the frequency matches exactly, increment matches
            if (windowFreq[idx] == s1Freq[idx]) {
                matches++;
            }
            // If we just exceeded the required frequency, decrement matches
            else if (windowFreq[idx] == s1Freq[idx] + 1) {
                matches--;
            }
        }
        
        // If all 26 characters have the correct frequency, we found a permutation
        if (matches == 26) {
            return true;
        }
        
        // Slide the window through s2
        for (int i = windowSize; i < s2.length(); i++) {
            // Handle the new character entering the window
            int idxIn = s2.charAt(i) - 'a';
            windowFreq[idxIn]++;
            
            // Update matches for the new character
            if (windowFreq[idxIn] == s1Freq[idxIn]) {
                matches++;
            } else if (windowFreq[idxIn] == s1Freq[idxIn] + 1) {
                matches--;
            }
            
            // Handle the character leaving the window
            int idxOut = s2.charAt(i - windowSize) - 'a';
            windowFreq[idxOut]--;
            
            // Update matches for the removed character
            if (windowFreq[idxOut] == s1Freq[idxOut]) {
                matches++;
            } else if (windowFreq[idxOut] == s1Freq[idxOut] - 1) {
                matches--;
            }
            
            // Check if we found a permutation
            if (matches == 26) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        PermutationInString solution = new PermutationInString();
        
        // Example 1: Should return true
        String s1_1 = "ab";
        String s2_1 = "eidbaooo";
        System.out.println("Example 1: " + solution.checkInclusion(s1_1, s2_1));
        System.out.println("Example 1 (Optimized): " + solution.checkInclusionOptimized(s1_1, s2_1));
        
        // Example 2: Should return false
        String s1_2 = "ab";
        String s2_2 = "eidboaoo";
        System.out.println("Example 2: " + solution.checkInclusion(s1_2, s2_2));
        System.out.println("Example 2 (Optimized): " + solution.checkInclusionOptimized(s1_2, s2_2));
        
        // Let's trace through the execution of Example 1:
        // s1 = "ab", s2 = "eidbaooo"
        
        // s1Freq = [1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
        // (a=1, b=1, rest=0)
        
        // Initial window: "ei"
        // windowFreq = [0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
        // (e=1, i=1, rest=0)
        // Arrays are not equal
        
        // Slide window: "id"
        // windowFreq = [0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
        // (i=1, d=1, rest=0)
        // Arrays are not equal
        
        // Slide window: "db"
        // windowFreq = [0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
        // (d=1, b=1, rest=0)
        // Arrays are not equal
        
        // Slide window: "ba"
        // windowFreq = [1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
        // (a=1, b=1, rest=0)
        // Arrays are equal, so return true
    }
}
