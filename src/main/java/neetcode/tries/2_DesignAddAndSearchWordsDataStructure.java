package neetcode.tries;

/**
 * NeetCode Problem 2 (Tries): Design Add and Search Words Data Structure
 * 
 * Problem Description:
 * Design a data structure that supports adding new words and finding if a string matches any previously added string.
 * 
 * Implement the WordDictionary class:
 * - WordDictionary() Initializes the object.
 * - void addWord(String word) Adds word to the data structure, it can be matched later.
 * - boolean search(String word) Returns true if there is any string in the data structure that matches word,
 *   or false otherwise. word may contain dots '.' where dots can be matched with any letter.
 * 
 * Examples:
 * Input:
 * ["WordDictionary","addWord","addWord","addWord","search","search","search","search"]
 * [[],["bad"],["dad"],["mad"],["pad"],["bad"],[".ad"],["b.."]]
 * Output:
 * [null,null,null,null,false,true,true,true]
 * 
 * Explanation:
 * WordDictionary wordDictionary = new WordDictionary();
 * wordDictionary.addWord("bad");
 * wordDictionary.addWord("dad");
 * wordDictionary.addWord("mad");
 * wordDictionary.search("pad"); // return False
 * wordDictionary.search("bad"); // return True
 * wordDictionary.search(".ad"); // return True
 * wordDictionary.search("b.."); // return True
 * 
 * Constraints:
 * - 1 <= word.length <= 25
 * - word in addWord consists of lowercase English letters.
 * - word in search consist of '.' or lowercase English letters.
 * - There will be at most 3 dots in word for search queries.
 * - At most 10^4 calls will be made to addWord and search.
 * 
 * Approach:
 * We can use a Trie (Prefix Tree) to efficiently store and search for words.
 * The key challenge is handling the wildcard character '.', which can match any letter.
 * 
 * 1. For adding words, we use the standard Trie insertion method.
 * 
 * 2. For searching, we need to modify the search algorithm to handle the wildcard character:
 *    - If the current character is a letter, we follow the corresponding child node.
 *    - If the current character is '.', we need to try all possible child nodes.
 *    - We use recursion to handle the wildcard character.
 * 
 * Time Complexity:
 * - addWord: O(m) where m is the length of the word
 * - search: O(m) for words without wildcards, O(26^n) in the worst case for words with wildcards,
 *   where n is the number of wildcards and m is the length of the word
 * 
 * Space Complexity: O(n * m) where n is the number of words and m is the average length of the words
 */
public class DesignAddAndSearchWordsDataStructure {
    
    /**
     * Implementation of a WordDictionary using a Trie.
     */
    static class WordDictionary {
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
         * Initializes the WordDictionary object.
         */
        public WordDictionary() {
            root = new TrieNode();
        }
        
        /**
         * Adds a word to the data structure.
         * 
         * @param word The word to add
         */
        public void addWord(String word) {
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
         * Returns true if there is any string in the data structure that matches word,
         * or false otherwise. word may contain dots '.' where dots can be matched with any letter.
         * 
         * @param word The word to search for
         * @return true if the word is found, false otherwise
         */
        public boolean search(String word) {
            return searchHelper(word, 0, root);
        }
        
        /**
         * Helper method for searching with wildcards.
         * 
         * @param word The word to search for
         * @param index The current index in the word
         * @param node The current node in the trie
         * @return true if the word is found, false otherwise
         */
        private boolean searchHelper(String word, int index, TrieNode node) {
            // Base case: reached the end of the word
            if (index == word.length()) {
                return node.isEndOfWord;
            }
            
            char c = word.charAt(index);
            
            // Case 1: Current character is a wildcard
            if (c == '.') {
                // Try all possible child nodes
                for (TrieNode child : node.children) {
                    if (child != null && searchHelper(word, index + 1, child)) {
                        return true;
                    }
                }
                return false;
            }
            // Case 2: Current character is a letter
            else {
                int childIndex = c - 'a';
                TrieNode child = node.children[childIndex];
                
                // If the child node doesn't exist, the word doesn't exist
                if (child == null) {
                    return false;
                }
                
                // Continue searching with the next character
                return searchHelper(word, index + 1, child);
            }
        }
    }
    
    /**
     * Alternative implementation using a HashMap for children.
     * This is more flexible (can handle any character, not just lowercase English letters)
     * but may be less memory-efficient.
     */
    static class WordDictionaryWithMap {
        private TrieNode root;
        
        private class TrieNode {
            private java.util.Map<Character, TrieNode> children;
            private boolean isEndOfWord;
            
            public TrieNode() {
                children = new java.util.HashMap<>();
                isEndOfWord = false;
            }
        }
        
        public WordDictionaryWithMap() {
            root = new TrieNode();
        }
        
        public void addWord(String word) {
            TrieNode current = root;
            
            for (char c : word.toCharArray()) {
                current = current.children.computeIfAbsent(c, k -> new TrieNode());
            }
            
            current.isEndOfWord = true;
        }
        
        public boolean search(String word) {
            return searchHelper(word, 0, root);
        }
        
        private boolean searchHelper(String word, int index, TrieNode node) {
            if (index == word.length()) {
                return node.isEndOfWord;
            }
            
            char c = word.charAt(index);
            
            if (c == '.') {
                for (TrieNode child : node.children.values()) {
                    if (searchHelper(word, index + 1, child)) {
                        return true;
                    }
                }
                return false;
            } else {
                if (!node.children.containsKey(c)) {
                    return false;
                }
                return searchHelper(word, index + 1, node.children.get(c));
            }
        }
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        // Example from the problem statement
        WordDictionary wordDictionary = new WordDictionary();
        
        System.out.println("Example with array-based implementation:");
        System.out.println("Initialize WordDictionary");
        
        wordDictionary.addWord("bad");
        System.out.println("Add word 'bad'");
        
        wordDictionary.addWord("dad");
        System.out.println("Add word 'dad'");
        
        wordDictionary.addWord("mad");
        System.out.println("Add word 'mad'");
        
        boolean result1 = wordDictionary.search("pad");
        System.out.println("Search 'pad': " + result1);
        
        boolean result2 = wordDictionary.search("bad");
        System.out.println("Search 'bad': " + result2);
        
        boolean result3 = wordDictionary.search(".ad");
        System.out.println("Search '.ad': " + result3);
        
        boolean result4 = wordDictionary.search("b..");
        System.out.println("Search 'b..': " + result4);
        
        // Additional example with HashMap-based implementation
        WordDictionaryWithMap wordDictionaryWithMap = new WordDictionaryWithMap();
        
        System.out.println("\nExample with HashMap-based implementation:");
        System.out.println("Initialize WordDictionary");
        
        wordDictionaryWithMap.addWord("hello");
        System.out.println("Add word 'hello'");
        
        wordDictionaryWithMap.addWord("world");
        System.out.println("Add word 'world'");
        
        boolean result5 = wordDictionaryWithMap.search("hello");
        System.out.println("Search 'hello': " + result5);
        
        boolean result6 = wordDictionaryWithMap.search("h.l.o");
        System.out.println("Search 'h.l.o': " + result6);
        
        boolean result7 = wordDictionaryWithMap.search("w..ld");
        System.out.println("Search 'w..ld': " + result7);
        
        boolean result8 = wordDictionaryWithMap.search("wo.l.");
        System.out.println("Search 'wo.l.': " + result8);
        
        // Let's trace through the execution of searching for ".ad":
        System.out.println("\nTracing the search for '.ad':");
        System.out.println("1. Call searchHelper('.ad', 0, root)");
        System.out.println("   - Current character is '.' at index 0");
        System.out.println("   - Try all child nodes of root");
        System.out.println("   - For child node 'a':");
        System.out.println("     - Call searchHelper('.ad', 1, a-node) -> false (no 'd' child)");
        System.out.println("   - For child node 'b':");
        System.out.println("     - Call searchHelper('.ad', 1, b-node)");
        System.out.println("       - Current character is 'a' at index 1");
        System.out.println("       - Follow child node 'a'");
        System.out.println("       - Call searchHelper('.ad', 2, a-node)");
        System.out.println("         - Current character is 'd' at index 2");
        System.out.println("         - Follow child node 'd'");
        System.out.println("         - Call searchHelper('.ad', 3, d-node)");
        System.out.println("           - Reached the end of the word");
        System.out.println("           - Check if d-node is the end of a word: Yes");
        System.out.println("           - Return true");
        System.out.println("         - Return true");
        System.out.println("       - Return true");
        System.out.println("     - Return true");
        System.out.println("   - Found a match, return true");
        
        // Visualize the trie after adding "bad", "dad", and "mad"
        System.out.println("\nVisualization of the trie after adding 'bad', 'dad', and 'mad':");
        System.out.println("root");
        System.out.println("├── b");
        System.out.println("│   └── a");
        System.out.println("│       └── d (end of word: 'bad')");
        System.out.println("├── d");
        System.out.println("│   └── a");
        System.out.println("│       └── d (end of word: 'dad')");
        System.out.println("└── m");
        System.out.println("    └── a");
        System.out.println("        └── d (end of word: 'mad')");
        
        System.out.println("\nWhen searching for '.ad', we need to check all paths starting with any letter and ending with 'ad'.");
        System.out.println("In this case, we find matches with 'bad', 'dad', and 'mad', so the search returns true.");
    }
}
