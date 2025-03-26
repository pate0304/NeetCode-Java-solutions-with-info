package neetcode.linked_list;

import java.util.HashMap;
import java.util.Map;

/**
 * NeetCode Problem 9 (Linked List): LRU Cache
 * 
 * Problem Description:
 * Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.
 * 
 * Implement the LRUCache class:
 * - LRUCache(int capacity) Initialize the LRU cache with positive size capacity.
 * - int get(int key) Return the value of the key if the key exists, otherwise return -1.
 * - void put(int key, int value) Update the value of the key if the key exists. Otherwise, add the key-value pair to the cache.
 *   If the number of keys exceeds the capacity from this operation, evict the least recently used key.
 * 
 * The functions get and put must each run in O(1) average time complexity.
 * 
 * Examples:
 * Input:
 * ["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
 * [[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
 * Output:
 * [null, null, null, 1, null, -1, null, -1, 3, 4]
 * 
 * Explanation:
 * LRUCache lRUCache = new LRUCache(2);
 * lRUCache.put(1, 1); // cache is {1=1}
 * lRUCache.put(2, 2); // cache is {1=1, 2=2}
 * lRUCache.get(1);    // return 1
 * lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
 * lRUCache.get(2);    // returns -1 (not found)
 * lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
 * lRUCache.get(1);    // return -1 (not found)
 * lRUCache.get(3);    // return 3
 * lRUCache.get(4);    // return 4
 * 
 * Constraints:
 * - 1 <= capacity <= 3000
 * - 0 <= key <= 10^4
 * - 0 <= value <= 10^5
 * - At most 2 * 10^5 calls will be made to get and put.
 * 
 * Approach:
 * To achieve O(1) time complexity for both get and put operations, we need to use a combination of:
 * 
 * 1. HashMap: For O(1) lookup of keys
 * 2. Doubly Linked List: To maintain the order of usage and allow O(1) removal and insertion
 * 
 * The implementation works as follows:
 * - We maintain a doubly linked list of nodes, each containing a key-value pair
 * - The most recently used items are kept at the head of the list, and the least recently used at the tail
 * - We also maintain a HashMap that maps keys to their corresponding nodes in the linked list
 * - When get is called, we move the accessed node to the head of the list
 * - When put is called, we either update the existing node and move it to the head, or add a new node at the head
 * - If the capacity is exceeded after a put operation, we remove the node at the tail (the least recently used)
 * 
 * Time Complexity: O(1) for both get and put operations
 * Space Complexity: O(capacity) for storing the nodes and the HashMap
 */
public class LRUCache {
    
    /**
     * Node class for the doubly linked list.
     */
    private class Node {
        int key;
        int value;
        Node prev;
        Node next;
        
        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
    
    private final int capacity;
    private final Map<Integer, Node> cache;
    private final Node head; // Most recently used
    private final Node tail; // Least recently used
    
    /**
     * Initializes the LRU cache with the given capacity.
     * 
     * @param capacity The maximum number of key-value pairs the cache can hold
     */
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        
        // Initialize dummy head and tail nodes
        this.head = new Node(0, 0);
        this.tail = new Node(0, 0);
        
        // Connect head and tail
        this.head.next = this.tail;
        this.tail.prev = this.head;
    }
    
    /**
     * Returns the value of the key if the key exists, otherwise returns -1.
     * This operation also marks the key as recently used.
     * 
     * @param key The key to look up
     * @return The value associated with the key, or -1 if the key is not in the cache
     */
    public int get(int key) {
        // Check if the key exists in the cache
        if (!cache.containsKey(key)) {
            return -1;
        }
        
        // Get the node and move it to the head (mark as recently used)
        Node node = cache.get(key);
        moveToHead(node);
        
        return node.value;
    }
    
    /**
     * Updates the value of the key if the key exists. Otherwise, adds the key-value pair to the cache.
     * If the number of keys exceeds the capacity, evicts the least recently used key.
     * 
     * @param key The key to update or add
     * @param value The value to associate with the key
     */
    public void put(int key, int value) {
        // Check if the key already exists in the cache
        if (cache.containsKey(key)) {
            // Update the value and move the node to the head
            Node node = cache.get(key);
            node.value = value;
            moveToHead(node);
        } else {
            // Create a new node
            Node newNode = new Node(key, value);
            
            // Add to the cache and the head of the list
            cache.put(key, newNode);
            addToHead(newNode);
            
            // Check if we need to evict the least recently used key
            if (cache.size() > capacity) {
                // Remove the tail node (least recently used)
                Node tailNode = removeTail();
                cache.remove(tailNode.key);
            }
        }
    }
    
    /**
     * Removes a node from the doubly linked list.
     * 
     * @param node The node to remove
     */
    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
    
    /**
     * Adds a node to the head of the doubly linked list (marks as most recently used).
     * 
     * @param node The node to add
     */
    private void addToHead(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }
    
    /**
     * Moves a node to the head of the doubly linked list (marks as most recently used).
     * 
     * @param node The node to move
     */
    private void moveToHead(Node node) {
        removeNode(node);
        addToHead(node);
    }
    
    /**
     * Removes and returns the tail node (least recently used).
     * 
     * @return The removed tail node
     */
    private Node removeTail() {
        Node tailNode = tail.prev;
        removeNode(tailNode);
        return tailNode;
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        // Example from the problem statement
        LRUCache lRUCache = new LRUCache(2);
        
        System.out.println("Initialize LRUCache with capacity 2");
        
        lRUCache.put(1, 1);
        System.out.println("put(1, 1) - Cache: {1=1}");
        
        lRUCache.put(2, 2);
        System.out.println("put(2, 2) - Cache: {1=1, 2=2}");
        
        int result1 = lRUCache.get(1);
        System.out.println("get(1) returns " + result1 + " - Cache: {2=2, 1=1} (1 is now most recently used)");
        
        lRUCache.put(3, 3);
        System.out.println("put(3, 3) - LRU key was 2, evicts key 2, Cache: {1=1, 3=3}");
        
        int result2 = lRUCache.get(2);
        System.out.println("get(2) returns " + result2 + " (not found)");
        
        lRUCache.put(4, 4);
        System.out.println("put(4, 4) - LRU key was 1, evicts key 1, Cache: {3=3, 4=4}");
        
        int result3 = lRUCache.get(1);
        System.out.println("get(1) returns " + result3 + " (not found)");
        
        int result4 = lRUCache.get(3);
        System.out.println("get(3) returns " + result4 + " - Cache: {4=4, 3=3} (3 is now most recently used)");
        
        int result5 = lRUCache.get(4);
        System.out.println("get(4) returns " + result5 + " - Cache: {3=3, 4=4} (4 is now most recently used)");
        
        // Let's trace through a detailed example to understand the internal workings:
        System.out.println("\nDetailed tracing of internal state for a new LRUCache:");
        
        LRUCache cache = new LRUCache(2);
        System.out.println("Initialize cache with capacity 2");
        System.out.println("Internal state: head <-> tail, cache is empty");
        
        cache.put(1, 10);
        System.out.println("put(1, 10)");
        System.out.println("Internal state: head <-> [1,10] <-> tail, cache = {1:[1,10]}");
        
        cache.put(2, 20);
        System.out.println("put(2, 20)");
        System.out.println("Internal state: head <-> [2,20] <-> [1,10] <-> tail, cache = {1:[1,10], 2:[2,20]}");
        
        System.out.println("get(1) returns " + cache.get(1));
        System.out.println("Internal state: head <-> [1,10] <-> [2,20] <-> tail, cache = {1:[1,10], 2:[2,20]}");
        
        cache.put(3, 30);
        System.out.println("put(3, 30) - Capacity exceeded, evict LRU key (2)");
        System.out.println("Internal state: head <-> [3,30] <-> [1,10] <-> tail, cache = {1:[1,10], 3:[3,30]}");
        
        System.out.println("get(2) returns " + cache.get(2) + " (not found)");
        
        cache.put(4, 40);
        System.out.println("put(4, 40) - Capacity exceeded, evict LRU key (1)");
        System.out.println("Internal state: head <-> [4,40] <-> [3,30] <-> tail, cache = {3:[3,30], 4:[4,40]}");
        
        System.out.println("get(1) returns " + cache.get(1) + " (not found)");
        System.out.println("get(3) returns " + cache.get(3));
        System.out.println("Internal state: head <-> [3,30] <-> [4,40] <-> tail, cache = {3:[3,30], 4:[4,40]}");
        System.out.println("get(4) returns " + cache.get(4));
        System.out.println("Internal state: head <-> [4,40] <-> [3,30] <-> tail, cache = {3:[3,30], 4:[4,40]}");
    }
}
