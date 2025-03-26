package neetcode.stack;

import java.util.Arrays;
import java.util.Stack;

/**
 * NeetCode Problem 6 (Stack): Car Fleet
 * 
 * Problem Description:
 * There are n cars going to the same destination along a one-lane road. The destination is target miles away.
 * 
 * You are given two integer arrays position and speed, both of length n, where position[i] is the position
 * of the ith car and speed[i] is the speed of the ith car (in miles per hour).
 * 
 * A car can never pass another car ahead of it, but it can catch up to it and drive bumper to bumper at the
 * same speed. The faster car will slow down to match the slower car's speed. The distance between these two
 * cars is ignored (i.e., they are assumed to have the same position).
 * 
 * A car fleet is some non-empty set of cars driving at the same speed. Note that a single car is also a car fleet.
 * 
 * If a car catches up to a car fleet right at the destination point, it will still be considered as one car fleet.
 * 
 * Return the number of car fleets that will arrive at the destination.
 * 
 * Examples:
 * Input: target = 12, position = [10,8,0,5,3], speed = [2,4,1,1,3]
 * Output: 3
 * Explanation:
 * The cars starting at 10 (speed 2) and 8 (speed 4) become a fleet, meeting at 12.
 * The car starting at 0 (speed 1) doesn't catch up to any other car, so it is a fleet by itself.
 * The cars starting at 5 (speed 1) and 3 (speed 3) become a fleet, meeting at 6.
 * There are 3 fleets at the end.
 * 
 * Input: target = 10, position = [3], speed = [3]
 * Output: 1
 * Explanation: There is only one car, so there is only one fleet.
 * 
 * Input: target = 100, position = [0,2,4], speed = [4,2,1]
 * Output: 1
 * Explanation:
 * The cars starting at 0 (speed 4) and 2 (speed 2) become a fleet, meeting at 4.
 * The fleet moves at speed 2.
 * Then, the fleet (speed 2) and the car starting at 4 (speed 1) become one fleet, meeting at 6.
 * The fleet moves at speed 1.
 * There is only 1 fleet at the end.
 * 
 * Constraints:
 * - n == position.length == speed.length
 * - 1 <= n <= 10^5
 * - 0 <= position[i] < target
 * - 0 < speed[i] <= 10^6
 * - All the values of position are unique.
 * 
 * Approach:
 * The key insight is to calculate the time each car will take to reach the destination.
 * If a car catches up to another car before reaching the destination, they form a fleet.
 * 
 * We can solve this problem using the following steps:
 * 1. Create a list of car positions and speeds, sorted by position in descending order (from closest to target to farthest)
 * 2. Calculate the time each car would take to reach the target if it were driving alone
 * 3. Process cars from closest to farthest from the target:
 *    - If a car would reach the target faster than the car ahead of it, it will catch up and form a fleet
 *    - If a car would reach the target slower than the car ahead of it, it will never catch up and form a new fleet
 * 4. Count the number of fleets
 * 
 * Time Complexity: O(n log n) due to sorting
 * Space Complexity: O(n) for storing the car positions, speeds, and times
 */
public class CarFleet {
    
    /**
     * Calculates the number of car fleets that will arrive at the destination.
     * 
     * @param target The destination distance
     * @param position The positions of the cars
     * @param speed The speeds of the cars
     * @return The number of car fleets that will arrive at the destination
     */
    public int carFleet(int target, int[] position, int[] speed) {
        int n = position.length;
        
        // Edge case: no cars
        if (n == 0) {
            return 0;
        }
        
        // Create an array to store car positions and speeds
        Car[] cars = new Car[n];
        for (int i = 0; i < n; i++) {
            cars[i] = new Car(position[i], speed[i]);
        }
        
        // Sort cars by position in descending order (from closest to target to farthest)
        Arrays.sort(cars, (a, b) -> b.position - a.position);
        
        // Calculate the time each car would take to reach the target
        double[] times = new double[n];
        for (int i = 0; i < n; i++) {
            times[i] = (double) (target - cars[i].position) / cars[i].speed;
        }
        
        // Count the number of fleets
        int fleets = 0;
        double maxTime = 0;
        
        for (int i = 0; i < n; i++) {
            // If the current car takes longer to reach the target than the car ahead,
            // it will never catch up and forms a new fleet
            if (times[i] > maxTime) {
                fleets++;
                maxTime = times[i];
            }
            // Otherwise, it will catch up and join the fleet ahead
        }
        
        return fleets;
    }
    
    /**
     * Helper class to represent a car with its position and speed.
     */
    private static class Car {
        int position;
        int speed;
        
        public Car(int position, int speed) {
            this.position = position;
            this.speed = speed;
        }
    }
    
    /**
     * Alternative implementation using a stack.
     */
    public int carFleetUsingStack(int target, int[] position, int[] speed) {
        int n = position.length;
        
        // Edge case: no cars
        if (n == 0) {
            return 0;
        }
        
        // Create an array to store car positions and speeds
        Car[] cars = new Car[n];
        for (int i = 0; i < n; i++) {
            cars[i] = new Car(position[i], speed[i]);
        }
        
        // Sort cars by position in descending order (from closest to target to farthest)
        Arrays.sort(cars, (a, b) -> b.position - a.position);
        
        // Use a stack to keep track of fleets
        Stack<Double> stack = new Stack<>();
        
        for (int i = 0; i < n; i++) {
            // Calculate the time this car would take to reach the target
            double time = (double) (target - cars[i].position) / cars[i].speed;
            
            // If this car is slower than the fleet ahead (takes more time),
            // it will never catch up and forms a new fleet
            if (stack.isEmpty() || time > stack.peek()) {
                stack.push(time);
            }
            // Otherwise, it will catch up and join the fleet ahead (no need to push to stack)
        }
        
        // The number of elements in the stack is the number of fleets
        return stack.size();
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        CarFleet solution = new CarFleet();
        
        // Example 1: Should return 3
        int target1 = 12;
        int[] position1 = {10, 8, 0, 5, 3};
        int[] speed1 = {2, 4, 1, 1, 3};
        System.out.println("Example 1: " + solution.carFleet(target1, position1, speed1));
        System.out.println("Example 1 (Stack): " + solution.carFleetUsingStack(target1, position1, speed1));
        
        // Example 2: Should return 1
        int target2 = 10;
        int[] position2 = {3};
        int[] speed2 = {3};
        System.out.println("Example 2: " + solution.carFleet(target2, position2, speed2));
        System.out.println("Example 2 (Stack): " + solution.carFleetUsingStack(target2, position2, speed2));
        
        // Example 3: Should return 1
        int target3 = 100;
        int[] position3 = {0, 2, 4};
        int[] speed3 = {4, 2, 1};
        System.out.println("Example 3: " + solution.carFleet(target3, position3, speed3));
        System.out.println("Example 3 (Stack): " + solution.carFleetUsingStack(target3, position3, speed3));
        
        // Let's trace through the execution of Example 1:
        // target = 12, position = [10, 8, 0, 5, 3], speed = [2, 4, 1, 1, 3]
        
        // Sort cars by position in descending order:
        // positions = [10, 8, 5, 3, 0], speeds = [2, 4, 1, 3, 1]
        
        // Calculate times to reach the target:
        // times = [(12-10)/2, (12-8)/4, (12-5)/1, (12-3)/3, (12-0)/1]
        // times = [1, 1, 7, 3, 12]
        
        // Process cars from closest to farthest:
        // Car at position 10: time = 1, maxTime = 1, fleets = 1
        // Car at position 8: time = 1, maxTime = 1, fleets = 1 (joins the fleet ahead)
        // Car at position 5: time = 7, maxTime = 7, fleets = 2 (forms a new fleet)
        // Car at position 3: time = 3, maxTime = 7, fleets = 2 (joins the fleet ahead)
        // Car at position 0: time = 12, maxTime = 12, fleets = 3 (forms a new fleet)
        
        // Final result: 3 fleets
    }
}
