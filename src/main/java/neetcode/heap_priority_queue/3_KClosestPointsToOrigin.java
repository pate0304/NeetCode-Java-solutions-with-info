package neetcode.heap_priority_queue;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * NeetCode Problem 3 (Heap/Priority Queue): K Closest Points to Origin
 * 
 * Problem Description:
 * Given an array of points where points[i] = [xi, yi] represents a point on the X-Y plane and an integer k,
 * return the k closest points to the origin (0, 0).
 * 
 * The distance between two points on the X-Y plane is the Euclidean distance: sqrt((x1 - x2)^2 + (y1 - y2)^2).
 * 
 * You may return the answer in any order. The answer is guaranteed to be unique (except for the order that it is in).
 * 
 * Examples:
 * Input: points = [[1,3],[-2,2]], k = 1
 * Output: [[-2,2]]
 * Explanation: The distance between (1, 3) and the origin is sqrt(10).
 * The distance between (-2, 2) and the origin is sqrt(8).
 * Since sqrt(8) < sqrt(10), (-2, 2) is closer to the origin.
 * We only want the closest k = 1 points from the origin, so the answer is just [[-2, 2]].
 * 
 * Input: points = [[3,3],[5,-1],[-2,4]], k = 2
 * Output: [[3,3],[-2,4]]
 * Explanation: The answer [[-2,4],[3,3]] would also be accepted.
 * 
 * Constraints:
 * - 1 <= k <= points.length <= 10^4
 * - -10^4 <= xi, yi <= 10^4
 * 
 * Approach:
 * We can solve this problem using several approaches:
 * 
 * 1. Using a Max-Heap (Priority Queue):
 *    - Maintain a max-heap of size k, where the priority is the negative of the distance to the origin.
 *    - Iterate through all points, adding each point to the heap.
 *    - If the heap size exceeds k, remove the point with the largest distance.
 *    - At the end, the heap contains the k closest points.
 * 
 * 2. Using Sorting:
 *    - Calculate the distance of each point to the origin.
 *    - Sort the points based on their distances.
 *    - Return the first k points.
 * 
 * 3. Using QuickSelect (most efficient):
 *    - Use the QuickSelect algorithm to find the kth closest point.
 *    - This has an average time complexity of O(n).
 * 
 * Time Complexity:
 * - Max-Heap approach: O(n log k) where n is the number of points
 * - Sorting approach: O(n log n)
 * - QuickSelect approach: O(n) on average, O(n^2) in the worst case
 * 
 * Space Complexity: O(k) for the heap approach, O(1) for the sorting and QuickSelect approaches (excluding the output)
 */
public class KClosestPointsToOrigin {
    
    /**
     * Finds the k closest points to the origin using a max-heap.
     * 
     * @param points The array of points
     * @param k The number of closest points to find
     * @return The k closest points to the origin
     */
    public int[][] kClosest(int[][] points, int k) {
        // Create a max-heap based on the distance to the origin
        // We use the negative of the distance because PriorityQueue is a min-heap by default
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>(
            (p1, p2) -> Double.compare(
                distance(p2),
                distance(p1)
            )
        );
        
        // Add points to the heap, maintaining a size of k
        for (int[] point : points) {
            maxHeap.offer(point);
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }
        
        // Extract the k closest points from the heap
        int[][] result = new int[k][2];
        for (int i = 0; i < k; i++) {
            result[i] = maxHeap.poll();
        }
        
        return result;
    }
    
    /**
     * Finds the k closest points to the origin using sorting.
     * 
     * @param points The array of points
     * @param k The number of closest points to find
     * @return The k closest points to the origin
     */
    public int[][] kClosestSorting(int[][] points, int k) {
        // Sort the points based on their distance to the origin
        Arrays.sort(points, (p1, p2) -> Double.compare(distance(p1), distance(p2)));
        
        // Return the first k points
        return Arrays.copyOfRange(points, 0, k);
    }
    
    /**
     * Finds the k closest points to the origin using QuickSelect.
     * 
     * @param points The array of points
     * @param k The number of closest points to find
     * @return The k closest points to the origin
     */
    public int[][] kClosestQuickSelect(int[][] points, int k) {
        // Edge case: k equals the number of points
        if (k == points.length) {
            return points;
        }
        
        // Perform QuickSelect to find the kth closest point
        quickSelect(points, 0, points.length - 1, k);
        
        // Return the first k points
        return Arrays.copyOfRange(points, 0, k);
    }
    
    /**
     * QuickSelect algorithm to find the kth closest point.
     * 
     * @param points The array of points
     * @param left The left index
     * @param right The right index
     * @param k The number of closest points to find
     */
    private void quickSelect(int[][] points, int left, int right, int k) {
        if (left >= right) {
            return;
        }
        
        // Choose a pivot and partition the array
        int pivotIndex = partition(points, left, right);
        
        // If the pivot is at index k-1, we've found the kth closest point
        if (pivotIndex == k - 1) {
            return;
        }
        // If the pivot is to the right of k-1, search the left subarray
        else if (pivotIndex > k - 1) {
            quickSelect(points, left, pivotIndex - 1, k);
        }
        // If the pivot is to the left of k-1, search the right subarray
        else {
            quickSelect(points, pivotIndex + 1, right, k);
        }
    }
    
    /**
     * Partitions the array around a pivot.
     * 
     * @param points The array of points
     * @param left The left index
     * @param right The right index
     * @return The index of the pivot after partitioning
     */
    private int partition(int[][] points, int left, int right) {
        // Choose the rightmost element as the pivot
        double pivot = distance(points[right]);
        
        // Index of the smaller element
        int i = left;
        
        // Traverse the array and move elements smaller than the pivot to the left
        for (int j = left; j < right; j++) {
            if (distance(points[j]) <= pivot) {
                // Swap points[i] and points[j]
                int[] temp = points[i];
                points[i] = points[j];
                points[j] = temp;
                
                i++;
            }
        }
        
        // Swap points[i] and points[right] (the pivot)
        int[] temp = points[i];
        points[i] = points[right];
        points[right] = temp;
        
        return i;
    }
    
    /**
     * Calculates the squared Euclidean distance of a point from the origin.
     * We use the squared distance to avoid the expensive square root operation.
     * 
     * @param point The point
     * @return The squared distance from the origin
     */
    private double distance(int[] point) {
        return point[0] * point[0] + point[1] * point[1];
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        KClosestPointsToOrigin solution = new KClosestPointsToOrigin();
        
        // Example 1
        int[][] points1 = {{1, 3}, {-2, 2}};
        int k1 = 1;
        
        System.out.println("Example 1:");
        System.out.println("Points: " + Arrays.deepToString(points1));
        System.out.println("k: " + k1);
        
        int[][] result1 = solution.kClosest(points1, k1);
        System.out.println("k closest points (max-heap): " + Arrays.deepToString(result1));
        
        int[][] result1Sorting = solution.kClosestSorting(points1, k1);
        System.out.println("k closest points (sorting): " + Arrays.deepToString(result1Sorting));
        
        int[][] result1QuickSelect = solution.kClosestQuickSelect(points1, k1);
        System.out.println("k closest points (QuickSelect): " + Arrays.deepToString(result1QuickSelect));
        
        // Example 2
        int[][] points2 = {{3, 3}, {5, -1}, {-2, 4}};
        int k2 = 2;
        
        System.out.println("\nExample 2:");
        System.out.println("Points: " + Arrays.deepToString(points2));
        System.out.println("k: " + k2);
        
        int[][] result2 = solution.kClosest(points2, k2);
        System.out.println("k closest points (max-heap): " + Arrays.deepToString(result2));
        
        int[][] result2Sorting = solution.kClosestSorting(points2, k2);
        System.out.println("k closest points (sorting): " + Arrays.deepToString(result2Sorting));
        
        int[][] result2QuickSelect = solution.kClosestQuickSelect(points2, k2);
        System.out.println("k closest points (QuickSelect): " + Arrays.deepToString(result2QuickSelect));
        
        // Additional example
        int[][] points3 = {{1, 1}, {-1, -1}, {2, 2}, {-2, -2}, {3, 3}, {-3, -3}};
        int k3 = 3;
        
        System.out.println("\nAdditional Example:");
        System.out.println("Points: " + Arrays.deepToString(points3));
        System.out.println("k: " + k3);
        
        int[][] result3 = solution.kClosest(points3, k3);
        System.out.println("k closest points (max-heap): " + Arrays.deepToString(result3));
        
        // Let's trace through the execution of Example 1 using the max-heap approach
        System.out.println("\nTracing the execution of Example 1 using the max-heap approach:");
        System.out.println("Points: [[1, 3], [-2, 2]]");
        System.out.println("k: 1");
        
        System.out.println("1. Create a max-heap based on the distance to the origin");
        System.out.println("2. Process point [1, 3]:");
        System.out.println("   - Distance: sqrt(1^2 + 3^2) = sqrt(10) ≈ 3.16");
        System.out.println("   - Add to heap: [[1, 3]]");
        System.out.println("3. Process point [-2, 2]:");
        System.out.println("   - Distance: sqrt((-2)^2 + 2^2) = sqrt(8) ≈ 2.83");
        System.out.println("   - Add to heap: [[1, 3], [-2, 2]]");
        System.out.println("   - Heap size exceeds k, remove the point with the largest distance: [1, 3]");
        System.out.println("   - Heap: [[-2, 2]]");
        System.out.println("4. Extract the k closest points from the heap: [[-2, 2]]");
        
        // Visualize the distances
        System.out.println("\nVisualization of the distances:");
        System.out.println("  ^");
        System.out.println("  |");
        System.out.println("3 |    o (1,3) - Distance: sqrt(10) ≈ 3.16");
        System.out.println("  |");
        System.out.println("2 |o (-2,2) - Distance: sqrt(8) ≈ 2.83");
        System.out.println("  |");
        System.out.println("1 |");
        System.out.println("  |");
        System.out.println("0 +--+--+--+--+-->");
        System.out.println("  -2 -1  0  1  2");
        
        System.out.println("\nSince sqrt(8) < sqrt(10), (-2, 2) is closer to the origin.");
    }
}
