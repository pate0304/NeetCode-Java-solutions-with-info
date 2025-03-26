package neetcode.arrays_hashing;

import java.util.HashMap;
import java.util.Map;

/**
 * NeetCode Problem 2: Valid Anagram
 * 
 * Problem Description:
 * Given two strings s and t, return true if t is an anagram of s, and false otherwise.
 * An anagram is a word or phrase formed by rearranging the letters of a different word or phrase,
 * typically using all the original letters exactly once.
 * 
 * Examples:
 * Input: s = "anagram", t = "nagaram"
 * Output: true
 * 
 * Input: s = "rat", t = "car"
 * Output: false
 * 
 * Constraints:
 * - 1 <= s.length, t.length <= 5 * 10^4
 * - s and t consist of lowercase English letters
 * 
 * Approach:
 * To determine if two strings are anagrams, we need to check if they have the same characters
 * with the same frequencies. There are several ways to approach this:
 * 
 * 1. Sort both strings and compare (O(n log n) time complexity)
 * 2. Count character frequencies using arrays or hash maps (O(n) time complexity)
 * 
 * The optimal solution uses character counting for O(n) time complexity:
 * - First, check if both strings have the same length (if not, they can't be anagrams)
 * - Count the frequency of each character in the first string
 * - Decrement the frequency for each character in the second string
 * - If any character frequency becomes negative, or if a character in the second string
 *   doesn't exist in our frequency map, return false
 * - Otherwise, return true
 * 
 * Time Complexity: O(n) where n is the length of the strings
 * Space Complexity: O(k) where k is the size of the character set (26 for lowercase English letters)
 */
public class ValidAnagram {
    
    /**
     * Determines if string t is an anagram of string s.
     * 
     * @param s First input string
     * @param t Second input string
     * @return true if t is an anagram of s, false otherwise
     */
    public boolean isAnagram(String s, String t) {
        // If the lengths are different, they can't be anagrams
        if (s.length() != t.length()) {
            return false;
        }
        
        // Create a map to store character frequencies
        Map<Character, Integer> charCount = new HashMap<>();
        
        // Count characters in string s
        for (char c : s.toCharArray()) {
            // Increment the count for this character (or initialize to 1 if not present)
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);
        }
        
        // Check characters in string t
        for (char c : t.toCharArray()) {
            // If this character doesn't exist in our map or its count is already 0,
            // then t has more occurrences of this character than s
            if (!charCount.containsKey(c) || charCount.get(c) == 0) {
                return false;
            }
            
            // Decrement the count for this character
            charCount.put(c, charCount.get(c) - 1);
        }
        
        // If we've made it here, t is an anagram of s
        return true;
    }
    
    /**
     * Alternative solution using an array instead of a HashMap.
     * This is more efficient for this specific problem since we know
     * we're only dealing with lowercase English letters.
     */
    public boolean isAnagramOptimized(String s, String t) {
        // If the lengths are different, they can't be anagrams
        if (s.length() != t.length()) {
            return false;
        }
        
        // Create an array to store character counts (26 lowercase letters)
        int[] counts = new int[26];
        
        // Count characters in both strings
        for (int i = 0; i < s.length(); i++) {
            // Increment count for character in s
            counts[s.charAt(i) - 'a']++;
            // Decrement count for character in t
            counts[t.charAt(i) - 'a']--;
        }
        
        // Check if all counts are zero
        for (int count : counts) {
            if (count != 0) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        ValidAnagram solution = new ValidAnagram();
        
        // Example 1: Should return true
        String s1 = "anagram";
        String t1 = "nagaram";
        System.out.println("Example 1: " + solution.isAnagram(s1, t1));
        System.out.println("Example 1 (Optimized): " + solution.isAnagramOptimized(s1, t1));
        
        // Example 2: Should return false
        String s2 = "rat";
        String t2 = "car";
        System.out.println("Example 2: " + solution.isAnagram(s2, t2));
        System.out.println("Example 2 (Optimized): " + solution.isAnagramOptimized(s2, t2));
    }
}
