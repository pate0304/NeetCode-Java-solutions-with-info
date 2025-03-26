package neetcode.two_pointers;

/**
 * NeetCode Problem 1 (Two Pointers): Valid Palindrome
 * 
 * Problem Description:
 * A phrase is a palindrome if, after converting all uppercase letters into lowercase letters
 * and removing all non-alphanumeric characters, it reads the same forward and backward.
 * Alphanumeric characters include letters and numbers.
 * 
 * Given a string s, return true if it is a palindrome, or false otherwise.
 * 
 * Examples:
 * Input: s = "A man, a plan, a canal: Panama"
 * Output: true
 * Explanation: "amanaplanacanalpanama" is a palindrome.
 * 
 * Input: s = "race a car"
 * Output: false
 * Explanation: "raceacar" is not a palindrome.
 * 
 * Input: s = " "
 * Output: true
 * Explanation: s is an empty string "" after removing non-alphanumeric characters.
 * Since an empty string reads the same forward and backward, it is a palindrome.
 * 
 * Constraints:
 * - 1 <= s.length <= 2 * 10^5
 * - s consists only of printable ASCII characters
 * 
 * Approach:
 * The two-pointer technique is perfect for this problem:
 * 1. Use two pointers, one starting from the beginning and one from the end of the string
 * 2. Skip non-alphanumeric characters
 * 3. Compare characters (case-insensitive) at both pointers
 * 4. If characters don't match, return false
 * 5. If pointers meet or cross, return true (we've checked all characters)
 * 
 * Time Complexity: O(n) where n is the length of the string
 * Space Complexity: O(1) as we're using constant extra space
 */
public class ValidPalindrome {
    
    /**
     * Determines if a string is a valid palindrome.
     * 
     * @param s The input string
     * @return true if the string is a palindrome, false otherwise
     */
    public boolean isPalindrome(String s) {
        // Edge case: empty string is a palindrome
        if (s == null || s.isEmpty()) {
            return true;
        }
        
        // Initialize two pointers
        int left = 0;
        int right = s.length() - 1;
        
        // Move pointers towards each other until they meet or cross
        while (left < right) {
            // Skip non-alphanumeric characters from the left
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }
            
            // Skip non-alphanumeric characters from the right
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }
            
            // Compare characters (case-insensitive)
            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                return false; // Characters don't match, not a palindrome
            }
            
            // Move pointers towards each other
            left++;
            right--;
        }
        
        // If we've checked all characters and found no mismatches, it's a palindrome
        return true;
    }
    
    /**
     * Alternative approach: Clean the string first, then check if it's a palindrome.
     * This is less efficient in terms of space complexity but might be more readable.
     */
    public boolean isPalindromeAlternative(String s) {
        // Clean the string: convert to lowercase and remove non-alphanumeric characters
        StringBuilder cleanString = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                cleanString.append(Character.toLowerCase(c));
            }
        }
        
        // Check if the clean string is a palindrome
        String cleaned = cleanString.toString();
        int left = 0;
        int right = cleaned.length() - 1;
        
        while (left < right) {
            if (cleaned.charAt(left) != cleaned.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        
        return true;
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        ValidPalindrome solution = new ValidPalindrome();
        
        // Example 1: Should return true
        String s1 = "A man, a plan, a canal: Panama";
        System.out.println("Example 1: " + solution.isPalindrome(s1));
        System.out.println("Example 1 (Alternative): " + solution.isPalindromeAlternative(s1));
        
        // Example 2: Should return false
        String s2 = "race a car";
        System.out.println("Example 2: " + solution.isPalindrome(s2));
        System.out.println("Example 2 (Alternative): " + solution.isPalindromeAlternative(s2));
        
        // Example 3: Should return true
        String s3 = " ";
        System.out.println("Example 3: " + solution.isPalindrome(s3));
        System.out.println("Example 3 (Alternative): " + solution.isPalindromeAlternative(s3));
        
        // Additional examples
        String s4 = "A Santa at NASA";
        System.out.println("Example 4: " + solution.isPalindrome(s4));
        
        String s5 = "0P";
        System.out.println("Example 5: " + solution.isPalindrome(s5));
    }
}
