package neetcode.binary_search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * NeetCode Problem 6 (Binary Search): Time Based Key-Value Store
 * 
 * Problem Description:
 * Design a time-based key-value data structure that can store multiple values for the same key at different time stamps
 * and retrieve the key's value at a certain timestamp.
 * 
 * Implement the TimeMap class:
 * - TimeMap() Initializes the object of the data structure.
 * - void set(String key, String value, int timestamp) Stores the key key with the value value at the given time timestamp.
 * - String get(String key, int timestamp) Returns a value such that set was called previously, with timestamp_prev <= timestamp.
 *   If there are multiple such values, it returns the value associated with the largest timestamp_prev. If there are no values,
 *   it returns "".
 * 
 * Examples:
 * Input:
 * ["TimeMap", "set", "get", "get", "set", "get", "get"]
 * [[], ["foo", "bar", 1], ["foo", 1], ["foo", 3], ["foo", "bar2", 4], ["foo", 4], ["foo", 5]]
 * Output:
 * [null, null, "bar", "bar", null, "bar2", "bar2"]
 * 
 * Explanation:
 * TimeMap timeMap = new TimeMap();
 * timeMap.set("foo", "bar", 1);  // store the key "foo" and value "bar" along with timestamp = 1
 * timeMap.get("foo", 1);         // return "bar"
 * timeMap.get("foo", 3);         // return "bar" since there is no value corresponding to foo at timestamp 3 and timestamp 2, then the only value is at timestamp 1 is "bar".
 * timeMap.set("foo", "bar2", 4); // store the key "foo" and value "bar2" along with timestamp = 4
 * timeMap.get("foo", 4);         // return "bar2"
 * timeMap.get("foo", 5);         // return "bar2"
 * 
 * Constraints:
 * - 1 <= key.length, value.length <= 100
 * - key and value consist of lowercase English letters and digits.
 * - 1 <= timestamp <= 10^7
 * - All the timestamps timestamp of set are strictly increasing.
 * - At most 2 * 10^5 calls will be made to set and get.
 * 
 * Approach:
 * We can use a HashMap to store the key-value pairs, where the key is the input key and the value is a list of
 * timestamped values. Since the timestamps are strictly increasing, we can use binary search to find the value
 * with the largest timestamp that is less than or equal to the target timestamp.
 * 
 * Time Complexity:
 * - set: O(1)
 * - get: O(log n) where n is the number of timestamps for a given key
 * 
 * Space Complexity: O(n) where n is the total number of key-value pairs stored
 */
public class TimeBasedKeyValueStore {
    
    /**
     * Implementation of the TimeMap class using a HashMap and binary search.
     */
    static class TimeMap {
        // Map to store key -> list of (timestamp, value) pairs
        private Map<String, List<TimestampValue>> map;
        
        /**
         * Initializes the TimeMap object.
         */
        public TimeMap() {
            map = new HashMap<>();
        }
        
        /**
         * Stores the key with the value at the given timestamp.
         * 
         * @param key The key to store
         * @param value The value to store
         * @param timestamp The timestamp at which the value is stored
         */
        public void set(String key, String value, int timestamp) {
            // If the key doesn't exist in the map, create a new list for it
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            
            // Add the new timestamp-value pair to the list
            map.get(key).add(new TimestampValue(timestamp, value));
        }
        
        /**
         * Returns the value associated with the key at the largest timestamp less than or equal to the given timestamp.
         * 
         * @param key The key to retrieve
         * @param timestamp The timestamp to search for
         * @return The value associated with the key at the largest timestamp less than or equal to the given timestamp,
         *         or an empty string if no such value exists
         */
        public String get(String key, int timestamp) {
            // If the key doesn't exist in the map, return an empty string
            if (!map.containsKey(key)) {
                return "";
            }
            
            // Get the list of timestamp-value pairs for the key
            List<TimestampValue> values = map.get(key);
            
            // Perform binary search to find the value with the largest timestamp <= the target timestamp
            int left = 0;
            int right = values.size() - 1;
            
            // If the target timestamp is smaller than the smallest timestamp, return an empty string
            if (timestamp < values.get(0).timestamp) {
                return "";
            }
            
            // If the target timestamp is greater than or equal to the largest timestamp, return the latest value
            if (timestamp >= values.get(right).timestamp) {
                return values.get(right).value;
            }
            
            // Perform binary search
            while (left <= right) {
                int mid = left + (right - left) / 2;
                
                if (values.get(mid).timestamp == timestamp) {
                    // Exact match found
                    return values.get(mid).value;
                } else if (values.get(mid).timestamp < timestamp) {
                    // If the middle timestamp is less than the target, search the right half
                    left = mid + 1;
                } else {
                    // If the middle timestamp is greater than the target, search the left half
                    right = mid - 1;
                }
            }
            
            // At this point, right points to the largest timestamp that is less than the target
            return values.get(right).value;
        }
        
        /**
         * Helper class to store a timestamp-value pair.
         */
        private static class TimestampValue {
            int timestamp;
            String value;
            
            public TimestampValue(int timestamp, String value) {
                this.timestamp = timestamp;
                this.value = value;
            }
        }
    }
    
    /**
     * Alternative implementation using TreeMap, which provides a floorEntry method
     * that returns the greatest key less than or equal to the given key.
     */
    static class TimeMapUsingTreeMap {
        // Map to store key -> TreeMap of (timestamp -> value)
        private Map<String, TreeMap<Integer, String>> map;
        
        public TimeMapUsingTreeMap() {
            map = new HashMap<>();
        }
        
        public void set(String key, String value, int timestamp) {
            // If the key doesn't exist in the map, create a new TreeMap for it
            if (!map.containsKey(key)) {
                map.put(key, new TreeMap<>());
            }
            
            // Add the timestamp-value pair to the TreeMap
            map.get(key).put(timestamp, value);
        }
        
        public String get(String key, int timestamp) {
            // If the key doesn't exist in the map, return an empty string
            if (!map.containsKey(key)) {
                return "";
            }
            
            // Get the TreeMap for the key
            TreeMap<Integer, String> treeMap = map.get(key);
            
            // Find the greatest timestamp less than or equal to the target timestamp
            Integer floorKey = treeMap.floorKey(timestamp);
            
            // If no such timestamp exists, return an empty string
            if (floorKey == null) {
                return "";
            }
            
            // Return the value associated with the found timestamp
            return treeMap.get(floorKey);
        }
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        // Test the TimeMap implementation
        TimeMap timeMap = new TimeMap();
        
        timeMap.set("foo", "bar", 1);
        System.out.println("get(\"foo\", 1): " + timeMap.get("foo", 1));  // Should return "bar"
        System.out.println("get(\"foo\", 3): " + timeMap.get("foo", 3));  // Should return "bar"
        
        timeMap.set("foo", "bar2", 4);
        System.out.println("get(\"foo\", 4): " + timeMap.get("foo", 4));  // Should return "bar2"
        System.out.println("get(\"foo\", 5): " + timeMap.get("foo", 5));  // Should return "bar2"
        
        System.out.println("\nTesting Alternative Implementation:");
        
        // Test the alternative implementation
        TimeMapUsingTreeMap timeMapAlt = new TimeMapUsingTreeMap();
        
        timeMapAlt.set("foo", "bar", 1);
        System.out.println("get(\"foo\", 1): " + timeMapAlt.get("foo", 1));  // Should return "bar"
        System.out.println("get(\"foo\", 3): " + timeMapAlt.get("foo", 3));  // Should return "bar"
        
        timeMapAlt.set("foo", "bar2", 4);
        System.out.println("get(\"foo\", 4): " + timeMapAlt.get("foo", 4));  // Should return "bar2"
        System.out.println("get(\"foo\", 5): " + timeMapAlt.get("foo", 5));  // Should return "bar2"
        
        // Let's trace through the execution of the example:
        // 1. timeMap.set("foo", "bar", 1)
        //    - Add "foo" -> [(1, "bar")] to the map
        
        // 2. timeMap.get("foo", 1)
        //    - Retrieve the list for "foo": [(1, "bar")]
        //    - Perform binary search for timestamp 1
        //    - Exact match found, return "bar"
        
        // 3. timeMap.get("foo", 3)
        //    - Retrieve the list for "foo": [(1, "bar")]
        //    - Perform binary search for timestamp 3
        //    - No exact match, but 1 is the largest timestamp <= 3, return "bar"
        
        // 4. timeMap.set("foo", "bar2", 4)
        //    - Update "foo" -> [(1, "bar"), (4, "bar2")] in the map
        
        // 5. timeMap.get("foo", 4)
        //    - Retrieve the list for "foo": [(1, "bar"), (4, "bar2")]
        //    - Perform binary search for timestamp 4
        //    - Exact match found, return "bar2"
        
        // 6. timeMap.get("foo", 5)
        //    - Retrieve the list for "foo": [(1, "bar"), (4, "bar2")]
        //    - Perform binary search for timestamp 5
        //    - No exact match, but 4 is the largest timestamp <= 5, return "bar2"
    }
}
