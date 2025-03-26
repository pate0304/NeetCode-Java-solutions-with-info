package neetcode.tries;

/**
 * NeetCode Problem 1 (Tries): Implement Trie (Prefix Tree)
 * 
 * Problem Description:
 * A trie (pronounced as "try") or prefix tree is a tree data structure used to efficiently store and
 * retrieve keys in a dataset of strings. There are various applications of this data structure, such
 * as autocomplete and spellchecker.
 * 
 * Implement the Trie class:
 * - Trie() Initializes the trie object.
 * - void insert(String word) Inserts the string word into the trie.
 * - boolean search(String word) Returns true if the string word is in the trie (i.e., was inserted before), and false otherwise.
 * - boolean startsWith(String prefix) Returns true if there is a previously inserted string word that has the prefix prefix, and false otherwise.
 * 
 * Examples:
 * Input:
 * ["Trie", "insert", "search", "search", "startsWith", "insert", "search"]
 * [[], ["apple"], ["apple"], ["app"], ["app"], ["app"], ["app"]]
 * Output:
 * [null, null, true, false, true, null, true]
 * 
 * Explanation:
 * Trie trie = new Trie();
 * trie.insert("apple");
 * trie.search("apple");   // return True
 * trie.search("app");     // return False
 * trie.startsWith("app"); // return True
 * trie.insert("app");
 * trie.search("app");     // return True
 * 
 * Constraints:
 * - 1 <= word.length, prefix.length <= 2000
 * - word and prefix consist only of lowercase English letters.
 * - At most 3 * 10^4 calls in total will be made to insert, search, and startsWith.
 * 
 * Approach:
 * A Trie is a tree-like data structure where each node represents a single character of a word.
 * The path from the root to a particular node forms a prefix, and nodes can be marked as the end of a word.
 * 
 * 1. Each node in the trie contains:
 *    - An array or map of child nodes (one for each possible character)
 *    - A boolean flag indicating if the node represents the end of a word
 * 
 * 2. For insertion:
 *    - Start from the root
 *    - For each character in the word, follow the corresponding child node or create one if it doesn't exist
 *    - Mark the last node as the end of a word
 * 
 * 3. For searching:
 *    - Start from the root
 *    - For each character in the word, follow the corresponding child node
 *    - If any character doesn't have a corresponding child, return false
 *    - If all characters are found, check if the last node is marked as the end of a word
 * 
 * 4. For prefix checking:
 *    - Similar to search, but don't need to check if the last node is the end of a word
 * 
 * Time Complexity:
 * - Insert: O(m) where m is the length of the word
 * - Search: O(m) where m is the length of the word
 * - StartsWith: O(m) where m is the length of the prefix
 * 
 * Space Complexity: O(n * m) where n is the number of words and m is the average length of the words
 */
public class ImplementTrie {
    
    /**
     * Implementation of a Trie (Prefix Tree).
     */
    static class Trie {
        private TrieNode root;
        
        /**
         * Node class for the Trie.
         */
        private class TrieNode {
            private TrieNode[] children;
            private boolean isEndOfWord;
            
            public TrieNode() {
                // Initialize with 26 children (one for each lowercase English letter)
                children = new TrieNode[26];
                isEndOfWord = false;
            }
        }
        
        /**
         * Initializes the trie object.
         */
        public Trie() {
            root = new TrieNode();
        }
        
        /**
         * Inserts the string word into the trie.
         * 
         * @param word The word to insert
         */
        public void insert(String word) {
            TrieNode current = root;
            
            // Traverse the trie for each character in the word
            for (char c : word.toCharArray()) {
                int index = c - 'a'; // Convert character to index (0-25)
                
                // If the child node doesn't exist, create it
                if (current.children[index] == null) {
                    current.children[index] = new TrieNode();
                }
                
                // Move to the child node
                current = current.children[index];
            }
            
            // Mark the last node as the end of a word
            current.isEndOfWord = true;
        }
        
        /**
         * Returns true if the string word is in the trie (i.e., was inserted before), and false otherwise.
         * 
         * @param word The word to search for
         * @return true if the word is in the trie, false otherwise
         */
        public boolean search(String word) {
            TrieNode node = searchPrefix(word);
            
            // The word exists if the node exists and is marked as the end of a word
            return node != null && node.isEndOfWord;
        }
        
        /**
         * Returns true if there is a previously inserted string word that has the prefix prefix,
         * and false otherwise.
         * 
         * @param prefix The prefix to check
         * @return true if there is a word with the given prefix, false otherwise
         */
        public boolean startsWith(String prefix) {
            // The prefix exists if the node exists
            return searchPrefix(prefix) != null;
        }
        
        /**
         * Helper method to find the node corresponding to a prefix.
         * 
         * @param prefix The prefix to search for
         * @return The node corresponding to the last character of the prefix, or null if not found
         */
        private TrieNode searchPrefix(String prefix) {
            TrieNode current = root;
            
            // Traverse the trie for each character in the prefix
            for (char c : prefix.toCharArray()) {
                int index = c - 'a'; // Convert character to index (0-25)
                
                // If the child node doesn't exist, the prefix doesn't exist
                if (current.children[index] == null) {
                    return null;
                }
                
                // Move to the child node
                current = current.children[index];
            }
            
            return current;
        }
    }
    
    /**
     * Alternative implementation using a HashMap for children.
     * This is more flexible (can handle any character, not just lowercase English letters)
     * but may be less memory-efficient.
     */
    static class TrieWithMap {
        private TrieNode root;
        
        private class TrieNode {
            private java.util.Map<Character, TrieNode> children;
            private boolean isEndOfWord;
            
            public TrieNode() {
                children = new java.util.HashMap<>();
                isEndOfWord = false;
            }
        }
        
        public TrieWithMap() {
            root = new TrieNode();
        }
        
        public void insert(String word) {
            TrieNode current = root;
            
            for (char c : word.toCharArray()) {
                current = current.children.computeIfAbsent(c, k -> new TrieNode());
            }
            
            current.isEndOfWord = true;
        }
        
        public boolean search(String word) {
            TrieNode node = searchPrefix(word);
            return node != null && node.isEndOfWord;
        }
        
        public boolean startsWith(String prefix) {
            return searchPrefix(prefix) != null;
        }
        
        private TrieNode searchPrefix(String prefix) {
            TrieNode current = root;
            
            for (char c : prefix.toCharArray()) {
                if (!current.children.containsKey(c)) {
                    return null;
                }
                current = current.children.get(c);
            }
            
            return current;
        }
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        // Example from the problem statement
        Trie trie = new Trie();
        
        System.out.println("Example with array-based implementation:");
        System.out.println("Initialize Trie");
        
        trie.insert("apple");
        System.out.println("Insert 'apple'");
        
        boolean result1 = trie.search("apple");
        System.out.println("Search 'apple': " + result1);
        
        boolean result2 = trie.search("app");
        System.out.println("Search 'app': " + result2);
        
        boolean result3 = trie.startsWith("app");
        System.out.println("StartsWith 'app': " + result3);
        
        trie.insert("app");
        System.out.println("Insert 'app'");
        
        boolean result4 = trie.search("app");
        System.out.println("Search 'app': " + result4);
        
        // Additional example with HashMap-based implementation
        TrieWithMap trieWithMap = new TrieWithMap();
        
        System.out.println("\nExample with HashMap-based implementation:");
        System.out.println("Initialize Trie");
        
        trieWithMap.insert("hello");
        System.out.println("Insert 'hello'");
        
        trieWithMap.insert("world");
        System.out.println("Insert 'world'");
        
        boolean result5 = trieWithMap.search("hello");
        System.out.println("Search 'hello': " + result5);
        
        boolean result6 = trieWithMap.search("hell");
        System.out.println("Search 'hell': " + result6);
        
        boolean result7 = trieWithMap.startsWith("hell");
        System.out.println("StartsWith 'hell': " + result7);
        
        boolean result8 = trieWithMap.startsWith("world");
        System.out.println("StartsWith 'world': " + result8);
        
        // Let's trace through the execution of inserting and searching for "apple":
        System.out.println("\nTracing the insertion of 'apple':");
        System.out.println("1. Start at the root node");
        System.out.println("2. Process 'a':");
        System.out.println("   - Create a child node for 'a' (index 0)");
        System.out.println("   - Move to the 'a' node");
        System.out.println("3. Process 'p':");
        System.out.println("   - Create a child node for 'p' (index 15)");
        System.out.println("   - Move to the 'p' node");
        System.out.println("4. Process 'p':");
        System.out.println("   - Create a child node for 'p' (index 15)");
        System.out.println("   - Move to the 'p' node");
        System.out.println("5. Process 'l':");
        System.out.println("   - Create a child node for 'l' (index 11)");
        System.out.println("   - Move to the 'l' node");
        System.out.println("6. Process 'e':");
        System.out.println("   - Create a child node for 'e' (index 4)");
        System.out.println("   - Move to the 'e' node");
        System.out.println("7. Mark the 'e' node as the end of a word");
        
        System.out.println("\nTracing the search for 'apple':");
        System.out.println("1. Start at the root node");
        System.out.println("2. Process 'a':");
        System.out.println("   - Follow the child node for 'a' (index 0)");
        System.out.println("3. Process 'p':");
        System.out.println("   - Follow the child node for 'p' (index 15)");
        System.out.println("4. Process 'p':");
        System.out.println("   - Follow the child node for 'p' (index 15)");
        System.out.println("5. Process 'l':");
        System.out.println("   - Follow the child node for 'l' (index 11)");
        System.out.println("6. Process 'e':");
        System.out.println("   - Follow the child node for 'e' (index 4)");
        System.out.println("7. Check if the 'e' node is marked as the end of a word: Yes");
        System.out.println("8. Return true");
        
        System.out.println("\nTracing the search for 'app':");
        System.out.println("1. Start at the root node");
        System.out.println("2. Process 'a':");
        System.out.println("   - Follow the child node for 'a' (index 0)");
        System.out.println("3. Process 'p':");
        System.out.println("   - Follow the child node for 'p' (index 15)");
        System.out.println("4. Process 'p':");
        System.out.println("   - Follow the child node for 'p' (index 15)");
        System.out.println("5. Check if the 'p' node is marked as the end of a word: No (before inserting 'app')");
        System.out.println("6. Return false");
        
        System.out.println("\nTracing the startsWith for 'app':");
        System.out.println("1. Start at the root node");
        System.out.println("2. Process 'a':");
        System.out.println("   - Follow the child node for 'a' (index 0)");
        System.out.println("3. Process 'p':");
        System.out.println("   - Follow the child node for 'p' (index 15)");
        System.out.println("4. Process 'p':");
        System.out.println("   - Follow the child node for 'p' (index 15)");
        System.out.println("5. The node exists, so return true");
        
        // Visualize the trie after inserting "apple" and "app"
        System.out.println("\nVisualization of the trie after inserting 'apple' and 'app':");
        System.out.println("root");
        System.out.println("└── a");
        System.out.println("    └── p");
        System.out.println("        └── p (end of word: 'app')");
        System.out.println("            └── l");
        System.out.println("                └── e (end of word: 'apple')");
    }
}
