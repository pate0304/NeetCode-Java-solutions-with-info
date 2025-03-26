package neetcode.binary_search;

/**
 * NeetCode Problem 3 (Binary Search): Koko Eating Bananas
 * 
 * Problem Description:
 * Koko loves to eat bananas. There are n piles of bananas, the ith pile has piles[i] bananas.
 * The guards have gone and will come back in h hours.
 * 
 * Koko can decide her bananas-per-hour eating speed of k. Each hour, she chooses some pile of bananas
 * and eats k bananas from that pile. If the pile has less than k bananas, she eats all of them instead
 * and will not eat any more bananas during this hour.
 * 
 * Koko likes to eat slowly but still wants to finish eating all the bananas before the guards return.
 * 
 * Return the minimum integer k such that she can eat all the bananas within h hours.
 * 
 * Examples:
 * Input: piles = [3,6,7,11], h = 8
 * Output: 4
 * 
 * Input: piles = [30,11,23,4,20], h = 5
 * Output: 30
 * 
 * Input: piles = [30,11,23,4,20], h = 6
 * Output: 23
 * 
 * Constraints:
 * - 1 <= piles.length <= 10^4
 * - piles.length <= h <= 10^9
 * - 1 <= piles[i] <= 10^9
 * 
 * Approach:
 * This problem can be solved using binary search to find the minimum eating speed k.
 * 
 * The key insight is that we can check if a given eating speed k is sufficient to eat all bananas within h hours.
 * If it is, we can try a smaller speed; if not, we need a larger speed.
 * 
 * 1. Find the maximum number of bananas in any pile (maxPile)
 * 2. Perform binary search on the range [1, maxPile]:
 *    - For each middle value k, calculate how many hours it would take to eat all bananas at speed k
 *    - If the total hours is <= h, try a smaller speed (search the left half)
 *    - If the total hours is > h, try a larger speed (search the right half)
 * 3. Return the minimum valid speed found
 * 
 * Time Complexity: O(n * log(max(piles))) where n is the number of piles
 * Space Complexity: O(1) as we're using constant extra space
 */
public class KokoEatingBananas {
    
    /**
     * Finds the minimum eating speed required to eat all bananas within h hours.
     * 
     * @param piles The array of banana piles
     * @param h The maximum number of hours allowed
     * @return The minimum eating speed
     */
    public int minEatingSpeed(int[] piles, int h) {
        // Find the maximum number of bananas in any pile
        int maxPile = 0;
        for (int pile : piles) {
            maxPile = Math.max(maxPile, pile);
        }
        
        // Perform binary search on the range [1, maxPile]
        int left = 1;
        int right = maxPile;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            // Check if eating at speed mid is sufficient
            if (canEatAll(piles, mid, h)) {
                // If we can eat all bananas at speed mid, try a smaller speed
                right = mid;
            } else {
                // If we can't eat all bananas at speed mid, try a larger speed
                left = mid + 1;
            }
        }
        
        // At this point, left is the minimum eating speed required
        return left;
    }
    
    /**
     * Checks if Koko can eat all bananas within h hours at the given speed.
     * 
     * @param piles The array of banana piles
     * @param speed The eating speed to check
     * @param h The maximum number of hours allowed
     * @return true if Koko can eat all bananas within h hours, false otherwise
     */
    private boolean canEatAll(int[] piles, int speed, int h) {
        int hoursNeeded = 0;
        
        for (int pile : piles) {
            // Calculate how many hours it takes to eat this pile
            // We use the ceiling division formula: (pile + speed - 1) / speed
            // This is equivalent to Math.ceil((double) pile / speed) but avoids floating-point operations
            hoursNeeded += (pile + speed - 1) / speed;
            
            // If we've already exceeded the allowed hours, return false
            if (hoursNeeded > h) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Alternative implementation using Math.ceil for clarity.
     */
    private boolean canEatAllAlternative(int[] piles, int speed, int h) {
        long hoursNeeded = 0; // Use long to avoid integer overflow
        
        for (int pile : piles) {
            // Calculate how many hours it takes to eat this pile
            hoursNeeded += (long) Math.ceil((double) pile / speed);
            
            // If we've already exceeded the allowed hours, return false
            if (hoursNeeded > h) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        KokoEatingBananas solution = new KokoEatingBananas();
        
        // Example 1: Should return 4
        int[] piles1 = {3, 6, 7, 11};
        int h1 = 8;
        System.out.println("Example 1: " + solution.minEatingSpeed(piles1, h1));
        
        // Example 2: Should return 30
        int[] piles2 = {30, 11, 23, 4, 20};
        int h2 = 5;
        System.out.println("Example 2: " + solution.minEatingSpeed(piles2, h2));
        
        // Example 3: Should return 23
        int[] piles3 = {30, 11, 23, 4, 20};
        int h3 = 6;
        System.out.println("Example 3: " + solution.minEatingSpeed(piles3, h3));
        
        // Let's trace through the execution of Example 1:
        // piles = [3, 6, 7, 11], h = 8
        
        // maxPile = 11
        // Initialize left = 1, right = 11
        
        // Iteration 1:
        // mid = 1 + (11 - 1) / 2 = 6
        // Check if speed = 6 is sufficient:
        // pile = 3: hours = (3 + 6 - 1) / 6 = 1
        // pile = 6: hours = (6 + 6 - 1) / 6 = 2
        // pile = 7: hours = (7 + 6 - 1) / 6 = 2
        // pile = 11: hours = (11 + 6 - 1) / 6 = 3
        // Total hours = 1 + 2 + 2 + 3 = 8 <= h, so speed = 6 is sufficient
        // right = 6
        
        // Iteration 2:
        // mid = 1 + (6 - 1) / 2 = 3
        // Check if speed = 3 is sufficient:
        // pile = 3: hours = (3 + 3 - 1) / 3 = 2
        // pile = 6: hours = (6 + 3 - 1) / 3 = 3
        // pile = 7: hours = (7 + 3 - 1) / 3 = 3
        // pile = 11: hours = (11 + 3 - 1) / 3 = 5
        // Total hours = 2 + 3 + 3 + 5 = 13 > h, so speed = 3 is not sufficient
        // left = 3 + 1 = 4
        
        // Iteration 3:
        // mid = 4 + (6 - 4) / 2 = 5
        // Check if speed = 5 is sufficient:
        // pile = 3: hours = (3 + 5 - 1) / 5 = 1
        // pile = 6: hours = (6 + 5 - 1) / 5 = 2
        // pile = 7: hours = (7 + 5 - 1) / 5 = 2
        // pile = 11: hours = (11 + 5 - 1) / 5 = 3
        // Total hours = 1 + 2 + 2 + 3 = 8 <= h, so speed = 5 is sufficient
        // right = 5
        
        // Iteration 4:
        // mid = 4 + (5 - 4) / 2 = 4
        // Check if speed = 4 is sufficient:
        // pile = 3: hours = (3 + 4 - 1) / 4 = 1
        // pile = 6: hours = (6 + 4 - 1) / 4 = 2
        // pile = 7: hours = (7 + 4 - 1) / 4 = 2
        // pile = 11: hours = (11 + 4 - 1) / 4 = 3
        // Total hours = 1 + 2 + 2 + 3 = 8 <= h, so speed = 4 is sufficient
        // right = 4
        
        // Now left = 4, right = 4, so the loop ends
        // Return left = 4
    }
}
