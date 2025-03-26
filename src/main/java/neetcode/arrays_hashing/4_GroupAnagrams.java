package neetcode.arrays_hashing;

import java.util.*;

/**
 * NeetCode Problem 4: Group Anagrams
 * 
 * Problem Description:
 * Given an array of strings strs, group the anagrams together. You can return the answer in any order.
 * An anagram is a word or phrase formed by rearranging the letters of a different word or phrase,
 * typically using all the original letters exactly once.
 * 
 * Examples:
 * Input: strs = ["eat","tea","tan","ate","nat","bat"]
 * Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
 * 
 * Input: strs = [""]
 * Output: [[""]]
 * 
 * Input: strs = ["a"]
 * Output: [["a"]]
 * 
 * Constraints:
 * - 1 <= strs.length <= 10^4
 * - 0 <= strs[i].length <= 100
 * - strs[i] consists of lowercase English letters
 * 
 * Approach:
 * To group anagrams together, we need to identify which strings are anagrams of each other.
 * Two strings are anagrams if they have the same characters with the same frequencies.
 * 
 * We can use a hash map where:
 * - The key is a representation of the character frequencies (either a sorted string or a count array)
 * - The value is a list of strings that have those same character frequencies
 * 
 * There are two main approaches for the key:
 * 1. Sort each string and use the sorted string as the key
 * 2. Create a character count array and use it as the key
 * 
 * The optimal solution uses a hash map with character count arrays as keys:
 * - Create a hash map where keys are character frequency representations and values are lists of strings
 * - For each string in the input array:
 *   - Create a character count array for the string
 *   - Use this count array as a key in the hash map
 *   - Add the string to the list associated with this key
 * - Return all the lists from the hash map as the result
 * 
 * Time Complexity: O(n * k) where n is the number of strings and k is the maximum length of a string
 * Space Complexity: O(n * k) for storing all strings in the hash map
 */
public class GroupAnagrams {
    
    /**
     * Groups anagrams together from an array of strings.
     * 
     * @param strs The input array of strings
     * @return A list of lists, where each inner list contains a group of anagrams
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        // Edge case: empty input
        if (strs == null || strs.length == 0) {
            return new ArrayList<>();
        }
        
        // Create a hash map to store groups of anagrams
        // Key: a string representation of character counts
        // Value: a list of strings that are anagrams of each other
        Map<String, List<String>> anagramGroups = new HashMap<>();
        
        // Process each string in the input array
        for (String str : strs) {
            // Create a character count array for this string
            char[] charCount = new char[26]; // 26 lowercase English letters
            
            // Count the frequency of each character
            for (char c : str.toCharArray()) {
                charCount[c - 'a']++;
            }
            
            // Convert the count array to a string to use as a key
            // We use # as a delimiter to avoid collisions
            // For example, [1,2] and [12] would be different
            StringBuilder keyBuilder = new StringBuilder();
            for (int i = 0; i < 26; i++) {
                if (charCount[i] > 0) {
                    keyBuilder.append((char)('a' + i));
                    keyBuilder.append(charCount[i]);
                }
            }
            String key = keyBuilder.toString();
            
            // Add the string to its anagram group
            if (!anagramGroups.containsKey(key)) {
                anagramGroups.put(key, new ArrayList<>());
            }
            anagramGroups.get(key).add(str);
        }
        
        // Return all the anagram groups as a list of lists
        return new ArrayList<>(anagramGroups.values());
    }
    
    /**
     * Alternative solution using sorted strings as keys.
     * This is simpler but potentially less efficient for very long strings.
     */
    public List<List<String>> groupAnagramsUsingSorting(String[] strs) {
        // Create a hash map to store groups of anagrams
        // Key: sorted version of the string
        // Value: a list of original strings that are anagrams of each other
        Map<String, List<String>> anagramGroups = new HashMap<>();
        
        // Process each string in the input array
        for (String str : strs) {
            // Sort the characters in the string to create a key
            char[] charArray = str.toCharArray();
            Arrays.sort(charArray);
            String sortedStr = new String(charArray);
            
            // Add the string to its anagram group
            if (!anagramGroups.containsKey(sortedStr)) {
                anagramGroups.put(sortedStr, new ArrayList<>());
            }
            anagramGroups.get(sortedStr).add(str);
        }
        
        // Return all the anagram groups as a list of lists
        return new ArrayList<>(anagramGroups.values());
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        GroupAnagrams solution = new GroupAnagrams();
        
        // Example 1
        String[] strs1 = {"eat", "tea", "tan", "ate", "nat", "bat"};
        System.out.println("Example 1:");
        List<List<String>> result1 = solution.groupAnagrams(strs1);
        for (List<String> group : result1) {
            System.out.println(group);
        }
        
        System.out.println("\nExample 1 (Using Sorting):");
        List<List<String>> result1Alt = solution.groupAnagramsUsingSorting(strs1);
        for (List<String> group : result1Alt) {
            System.out.println(group);
        }
        
        // Example 2
        String[] strs2 = {""};
        System.out.println("\nExample 2:");
        List<List<String>> result2 = solution.groupAnagrams(strs2);
        for (List<String> group : result2) {
            System.out.println(group);
        }
        
        // Example 3
        String[] strs3 = {"a"};
        System.out.println("\nExample 3:");
        List<List<String>> result3 = solution.groupAnagrams(strs3);
        for (List<String> group : result3) {
            System.out.println(group);
        }
    }
}
