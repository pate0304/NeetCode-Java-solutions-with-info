package neetcode.heap_priority_queue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.LinkedList;

/**
 * NeetCode Problem 5 (Heap/Priority Queue): Task Scheduler
 * 
 * Problem Description:
 * Given a characters array tasks, representing the tasks a CPU needs to do, where each letter represents a different task.
 * Tasks could be done in any order. Each task is done in one unit of time. For each unit of time, the CPU could complete
 * either one task or just be idle.
 * 
 * However, there is a non-negative integer n that represents the cooldown period between two same tasks
 * (the same letter in the array), that is that there must be at least n units of time between any two same tasks.
 * 
 * Return the least number of units of time that the CPU will take to finish all the given tasks.
 * 
 * Examples:
 * Input: tasks = ["A","A","A","B","B","B"], n = 2
 * Output: 8
 * Explanation: 
 * A -> B -> idle -> A -> B -> idle -> A -> B
 * There is at least 2 units of time between any two same tasks.
 * 
 * Input: tasks = ["A","A","A","B","B","B"], n = 0
 * Output: 6
 * Explanation: On this case any permutation of size 6 would work since n = 0.
 * ["A","A","A","B","B","B"]
 * ["A","B","A","B","A","B"]
 * ["B","B","B","A","A","A"]
 * ...
 * And so on.
 * 
 * Input: tasks = ["A","A","A","A","A","A","B","C","D","E","F","G"], n = 2
 * Output: 16
 * Explanation: 
 * One possible solution is
 * A -> B -> C -> A -> D -> E -> A -> F -> G -> A -> idle -> idle -> A -> idle -> idle -> A
 * 
 * Constraints:
 * - 1 <= task.length <= 10^4
 * - tasks[i] is upper-case English letter.
 * - The integer n is in the range [0, 100].
 * 
 * Approach:
 * We can solve this problem using a greedy approach with a max-heap and a queue:
 * 
 * 1. Count the frequency of each task.
 * 2. Add all tasks to a max-heap, prioritized by frequency.
 * 3. Process tasks in order of frequency:
 *    - At each time unit, pick the task with the highest frequency.
 *    - After processing a task, decrement its frequency and put it in a cooldown queue.
 *    - After n time units, tasks in the cooldown queue become available again.
 * 4. Continue until all tasks are processed.
 * 
 * Time Complexity: O(T) where T is the total number of tasks
 * Space Complexity: O(1) since we have at most 26 different tasks (uppercase English letters)
 */
public class TaskScheduler {
    
    /**
     * Calculates the least number of units of time needed to finish all tasks.
     * 
     * @param tasks The array of tasks
     * @param n The cooldown period
     * @return The least number of units of time
     */
    public int leastInterval(char[] tasks, int n) {
        // Edge case: no cooldown
        if (n == 0) {
            return tasks.length;
        }
        
        // Count the frequency of each task
        Map<Character, Integer> frequencies = new HashMap<>();
        for (char task : tasks) {
            frequencies.put(task, frequencies.getOrDefault(task, 0) + 1);
        }
        
        // Create a max-heap based on task frequency
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        maxHeap.addAll(frequencies.values());
        
        // Process tasks
        int time = 0;
        
        // Queue to hold tasks in cooldown, storing pairs of [frequency, time when available]
        Queue<int[]> cooldown = new LinkedList<>();
        
        while (!maxHeap.isEmpty() || !cooldown.isEmpty()) {
            // Increment time
            time++;
            
            // Process a task from the heap if available
            if (!maxHeap.isEmpty()) {
                int frequency = maxHeap.poll() - 1; // Decrement frequency
                
                // If there are still instances of this task, put it in cooldown
                if (frequency > 0) {
                    cooldown.offer(new int[]{frequency, time + n});
                }
            }
            
            // Check if any tasks in cooldown are now available
            if (!cooldown.isEmpty() && cooldown.peek()[1] <= time) {
                maxHeap.offer(cooldown.poll()[0]);
            }
        }
        
        return time;
    }
    
    /**
     * Alternative implementation using a mathematical formula.
     * This is more efficient for large inputs.
     * 
     * The formula is: max(tasks.length, (maxFreq - 1) * (n + 1) + countMaxFreq)
     * where:
     * - maxFreq is the maximum frequency of any task
     * - countMaxFreq is the number of tasks with the maximum frequency
     * 
     * @param tasks The array of tasks
     * @param n The cooldown period
     * @return The least number of units of time
     */
    public int leastIntervalMath(char[] tasks, int n) {
        // Count the frequency of each task
        int[] frequencies = new int[26];
        for (char task : tasks) {
            frequencies[task - 'A']++;
        }
        
        // Sort the frequencies in ascending order
        Arrays.sort(frequencies);
        
        // Get the maximum frequency
        int maxFreq = frequencies[25];
        
        // Count how many tasks have the maximum frequency
        int countMaxFreq = 0;
        for (int freq : frequencies) {
            if (freq == maxFreq) {
                countMaxFreq++;
            }
        }
        
        // Apply the formula
        return Math.max(tasks.length, (maxFreq - 1) * (n + 1) + countMaxFreq);
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        TaskScheduler solution = new TaskScheduler();
        
        // Example 1
        char[] tasks1 = {'A', 'A', 'A', 'B', 'B', 'B'};
        int n1 = 2;
        
        System.out.println("Example 1:");
        System.out.println("Tasks: " + Arrays.toString(tasks1));
        System.out.println("Cooldown period (n): " + n1);
        
        int result1 = solution.leastInterval(tasks1, n1);
        System.out.println("Least interval (heap): " + result1);
        
        int result1Math = solution.leastIntervalMath(tasks1, n1);
        System.out.println("Least interval (math): " + result1Math);
        
        // Example 2
        char[] tasks2 = {'A', 'A', 'A', 'B', 'B', 'B'};
        int n2 = 0;
        
        System.out.println("\nExample 2:");
        System.out.println("Tasks: " + Arrays.toString(tasks2));
        System.out.println("Cooldown period (n): " + n2);
        
        int result2 = solution.leastInterval(tasks2, n2);
        System.out.println("Least interval (heap): " + result2);
        
        // Example 3
        char[] tasks3 = {'A', 'A', 'A', 'A', 'A', 'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int n3 = 2;
        
        System.out.println("\nExample 3:");
        System.out.println("Tasks: " + Arrays.toString(tasks3));
        System.out.println("Cooldown period (n): " + n3);
        
        int result3 = solution.leastInterval(tasks3, n3);
        System.out.println("Least interval (heap): " + result3);
        
        // Let's trace through the execution of Example 1
        System.out.println("\nTracing the execution of Example 1:");
        System.out.println("Tasks: [A, A, A, B, B, B]");
        System.out.println("Cooldown period (n): 2");
        
        System.out.println("1. Count task frequencies: A=3, B=3");
        System.out.println("2. Add frequencies to max-heap: [3, 3]");
        System.out.println("3. Process tasks:");
        System.out.println("   - Time 1: Process A (freq=3), decrement to 2, put in cooldown until time 4");
        System.out.println("     Heap: [3], Cooldown: [(2, 4)]");
        System.out.println("   - Time 2: Process B (freq=3), decrement to 2, put in cooldown until time 5");
        System.out.println("     Heap: [], Cooldown: [(2, 4), (2, 5)]");
        System.out.println("   - Time 3: No task available, idle");
        System.out.println("     Heap: [], Cooldown: [(2, 4), (2, 5)]");
        System.out.println("   - Time 4: A is available again, process A (freq=2), decrement to 1, put in cooldown until time 7");
        System.out.println("     Heap: [], Cooldown: [(2, 5), (1, 7)]");
        System.out.println("   - Time 5: B is available again, process B (freq=2), decrement to 1, put in cooldown until time 8");
        System.out.println("     Heap: [], Cooldown: [(1, 7), (1, 8)]");
        System.out.println("   - Time 6: No task available, idle");
        System.out.println("     Heap: [], Cooldown: [(1, 7), (1, 8)]");
        System.out.println("   - Time 7: A is available again, process A (freq=1), decrement to 0, no more A tasks");
        System.out.println("     Heap: [], Cooldown: [(1, 8)]");
        System.out.println("   - Time 8: B is available again, process B (freq=1), decrement to 0, no more B tasks");
        System.out.println("     Heap: [], Cooldown: []");
        System.out.println("4. All tasks processed, total time: 8");
        
        // Visualize the schedule
        System.out.println("\nVisualization of the schedule for Example 1:");
        System.out.println("Time: 1 2 3 4 5 6 7 8");
        System.out.println("Task: A B - A B - A B");
        
        // Explain the mathematical formula
        System.out.println("\nExplanation of the mathematical formula for Example 1:");
        System.out.println("- Maximum frequency (maxFreq): 3");
        System.out.println("- Number of tasks with maximum frequency (countMaxFreq): 2 (both A and B have frequency 3)");
        System.out.println("- Formula: max(tasks.length, (maxFreq - 1) * (n + 1) + countMaxFreq)");
        System.out.println("- max(6, (3 - 1) * (2 + 1) + 2) = max(6, 2 * 3 + 2) = max(6, 8) = 8");
    }
}
