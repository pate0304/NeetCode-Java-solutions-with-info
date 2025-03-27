package neetcode.heap_priority_queue;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * NeetCode Problem 8 (Heap/Priority Queue): Meeting Rooms II
 * 
 * Problem Description:
 * Given an array of meeting time intervals intervals where intervals[i] = [starti, endi],
 * return the minimum number of conference rooms required.
 * 
 * Examples:
 * Input: intervals = [[0,30],[5,10],[15,20]]
 * Output: 2
 * Explanation: We need two meeting rooms.
 * Meeting room 1: [0,30]
 * Meeting room 2: [5,10], [15,20]
 * 
 * Input: intervals = [[7,10],[2,4]]
 * Output: 1
 * Explanation: We only need one meeting room since the meetings don't overlap.
 * 
 * Constraints:
 * - 1 <= intervals.length <= 10^4
 * - 0 <= starti < endi <= 10^6
 * 
 * Approach:
 * We can solve this problem using a min-heap (priority queue):
 * 
 * 1. Sort the intervals by start time.
 * 2. Create a min-heap to keep track of the end times of meetings in progress.
 * 3. Iterate through the sorted intervals:
 *    - If the heap is empty or the current meeting starts before the earliest ending meeting,
 *      we need a new room. Add the end time of the current meeting to the heap.
 *    - If the current meeting starts after or at the same time as the earliest ending meeting,
 *      we can reuse that room. Remove the earliest ending meeting from the heap and add the
 *      end time of the current meeting.
 * 4. The size of the heap at the end represents the minimum number of rooms required.
 * 
 * Time Complexity: O(n log n) for sorting and heap operations
 * Space Complexity: O(n) for the heap
 */
public class MeetingRoomsII {
    
    /**
     * Finds the minimum number of meeting rooms required.
     * 
     * @param intervals The array of meeting intervals
     * @return The minimum number of rooms required
     */
    public int minMeetingRooms(int[][] intervals) {
        // Edge case: no meetings
        if (intervals == null || intervals.length == 0) {
            return 0;
        }
        
        // Sort intervals by start time
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        
        // Min-heap to track end times of meetings in progress
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        
        // Add the end time of the first meeting
        minHeap.offer(intervals[0][1]);
        
        // Process the rest of the meetings
        for (int i = 1; i < intervals.length; i++) {
            // If the current meeting starts after or at the same time as the earliest ending meeting,
            // we can reuse that room
            if (intervals[i][0] >= minHeap.peek()) {
                minHeap.poll(); // Remove the earliest ending meeting
            }
            
            // Add the end time of the current meeting
            minHeap.offer(intervals[i][1]);
        }
        
        // The size of the heap represents the minimum number of rooms required
        return minHeap.size();
    }
    
    /**
     * Alternative implementation using chronological ordering of start and end times.
     * This approach is more intuitive for some people.
     * 
     * @param intervals The array of meeting intervals
     * @return The minimum number of rooms required
     */
    public int minMeetingRoomsChronological(int[][] intervals) {
        // Edge case: no meetings
        if (intervals == null || intervals.length == 0) {
            return 0;
        }
        
        int n = intervals.length;
        
        // Extract start and end times
        int[] startTimes = new int[n];
        int[] endTimes = new int[n];
        
        for (int i = 0; i < n; i++) {
            startTimes[i] = intervals[i][0];
            endTimes[i] = intervals[i][1];
        }
        
        // Sort start and end times
        Arrays.sort(startTimes);
        Arrays.sort(endTimes);
        
        // Count the number of rooms needed
        int rooms = 0;
        int endIndex = 0;
        
        for (int startIndex = 0; startIndex < n; startIndex++) {
            // If the current meeting starts before the earliest ending meeting,
            // we need a new room
            if (startTimes[startIndex] < endTimes[endIndex]) {
                rooms++;
            } else {
                // If the current meeting starts after or at the same time as the earliest ending meeting,
                // we can reuse that room
                endIndex++;
            }
        }
        
        return rooms;
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        MeetingRoomsII solution = new MeetingRoomsII();
        
        // Example 1
        int[][] intervals1 = {{0, 30}, {5, 10}, {15, 20}};
        
        System.out.println("Example 1:");
        System.out.println("Intervals: " + Arrays.deepToString(intervals1));
        
        int result1 = solution.minMeetingRooms(intervals1);
        System.out.println("Minimum number of rooms (heap): " + result1);
        
        int result1Chronological = solution.minMeetingRoomsChronological(intervals1);
        System.out.println("Minimum number of rooms (chronological): " + result1Chronological);
        
        // Example 2
        int[][] intervals2 = {{7, 10}, {2, 4}};
        
        System.out.println("\nExample 2:");
        System.out.println("Intervals: " + Arrays.deepToString(intervals2));
        
        int result2 = solution.minMeetingRooms(intervals2);
        System.out.println("Minimum number of rooms (heap): " + result2);
        
        int result2Chronological = solution.minMeetingRoomsChronological(intervals2);
        System.out.println("Minimum number of rooms (chronological): " + result2Chronological);
        
        // Additional example
        int[][] intervals3 = {{1, 5}, {8, 9}, {8, 15}, {2, 6}, {3, 7}, {4, 10}};
        
        System.out.println("\nAdditional Example:");
        System.out.println("Intervals: " + Arrays.deepToString(intervals3));
        
        int result3 = solution.minMeetingRooms(intervals3);
        System.out.println("Minimum number of rooms (heap): " + result3);
        
        // Let's trace through the execution of Example 1 using the heap approach
        System.out.println("\nTracing the execution of Example 1 using the heap approach:");
        System.out.println("Intervals: [[0, 30], [5, 10], [15, 20]]");
        
        System.out.println("1. Sort intervals by start time: [[0, 30], [5, 10], [15, 20]]");
        System.out.println("2. Initialize min-heap");
        System.out.println("3. Process interval [0, 30]:");
        System.out.println("   - Add end time 30 to heap: [30]");
        System.out.println("   - Rooms needed: 1");
        System.out.println("4. Process interval [5, 10]:");
        System.out.println("   - Current start time 5 < earliest end time 30");
        System.out.println("   - Need a new room");
        System.out.println("   - Add end time 10 to heap: [10, 30]");
        System.out.println("   - Rooms needed: 2");
        System.out.println("5. Process interval [15, 20]:");
        System.out.println("   - Current start time 15 > earliest end time 10");
        System.out.println("   - Can reuse a room");
        System.out.println("   - Remove end time 10 from heap: [30]");
        System.out.println("   - Add end time 20 to heap: [20, 30]");
        System.out.println("   - Rooms needed: 2");
        System.out.println("6. Final heap: [20, 30]");
        System.out.println("7. Minimum number of rooms required: 2");
        
        // Let's trace through the execution of Example 1 using the chronological approach
        System.out.println("\nTracing the execution of Example 1 using the chronological approach:");
        System.out.println("Intervals: [[0, 30], [5, 10], [15, 20]]");
        
        System.out.println("1. Extract start times: [0, 5, 15]");
        System.out.println("2. Extract end times: [10, 20, 30]");
        System.out.println("3. Sort start times: [0, 5, 15]");
        System.out.println("4. Sort end times: [10, 20, 30]");
        System.out.println("5. Process chronologically:");
        System.out.println("   - Start time 0 < end time 10, need a new room, rooms = 1");
        System.out.println("   - Start time 5 < end time 10, need a new room, rooms = 2");
        System.out.println("   - Start time 15 > end time 10, can reuse a room, endIndex = 1");
        System.out.println("   - Start time 15 < end time 20, need a new room, rooms = 3");
        System.out.println("   - But we already counted this room, so rooms = 2");
        System.out.println("6. Minimum number of rooms required: 2");
        
        // Visualize the meetings on a timeline
        System.out.println("\nVisualization of the meetings on a timeline for Example 1:");
        System.out.println("Time: 0    5    10   15   20   25   30");
        System.out.println("     |    |    |    |    |    |    |");
        System.out.println("Room 1: [============================]");
        System.out.println("Room 2:      [====]     [======]");
    }
}
