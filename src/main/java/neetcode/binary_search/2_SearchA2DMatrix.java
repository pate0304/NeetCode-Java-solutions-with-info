package neetcode.binary_search;

/**
 * NeetCode Problem 2 (Binary Search): Search a 2D Matrix
 * 
 * Problem Description:
 * You are given an m x n integer matrix matrix with the following two properties:
 * 
 * 1. Each row is sorted in non-decreasing order.
 * 2. The first integer of each row is greater than the last integer of the previous row.
 * 
 * Given an integer target, return true if target is in matrix or false otherwise.
 * 
 * You must write a solution in O(log(m * n)) time complexity.
 * 
 * Examples:
 * Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
 * Output: true
 * 
 * Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 13
 * Output: false
 * 
 * Constraints:
 * - m == matrix.length
 * - n == matrix[i].length
 * - 1 <= m, n <= 100
 * - -10^4 <= matrix[i][j], target <= 10^4
 * 
 * Approach:
 * Since the matrix has special properties (each row is sorted and the first element of each row
 * is greater than the last element of the previous row), we can treat the entire 2D matrix as a
 * sorted 1D array and perform binary search on it.
 * 
 * 1. Calculate the total number of elements in the matrix: m * n
 * 2. Perform binary search on indices from 0 to (m * n - 1)
 * 3. For each middle index mid:
 *    - Convert it to row and column indices: row = mid / n, col = mid % n
 *    - Compare matrix[row][col] with the target
 *    - Adjust the search range accordingly
 * 
 * Time Complexity: O(log(m * n)) where m and n are the dimensions of the matrix
 * Space Complexity: O(1) as we're using constant extra space
 */
public class SearchA2DMatrix {
    
    /**
     * Searches for a target value in a 2D matrix using binary search.
     * 
     * @param matrix The 2D matrix with special properties
     * @param target The target value to search for
     * @return true if the target is found, false otherwise
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        // Edge case: empty matrix
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        
        int m = matrix.length;    // Number of rows
        int n = matrix[0].length; // Number of columns
        
        // Treat the 2D matrix as a sorted 1D array
        int left = 0;
        int right = m * n - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            // Convert the 1D index to 2D indices
            int row = mid / n;
            int col = mid % n;
            
            // Compare the element at the middle with the target
            if (matrix[row][col] == target) {
                return true; // Target found
            } else if (matrix[row][col] < target) {
                left = mid + 1; // Search the right half
            } else {
                right = mid - 1; // Search the left half
            }
        }
        
        return false; // Target not found
    }
    
    /**
     * Alternative approach: first find the row that might contain the target,
     * then perform binary search on that row.
     */
    public boolean searchMatrixTwoSteps(int[][] matrix, int target) {
        // Edge case: empty matrix
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        
        int m = matrix.length;    // Number of rows
        int n = matrix[0].length; // Number of columns
        
        // Step 1: Find the row that might contain the target
        int top = 0;
        int bottom = m - 1;
        
        while (top <= bottom) {
            int mid = top + (bottom - top) / 2;
            
            // Check if the target is in the range of this row
            if (matrix[mid][0] <= target && target <= matrix[mid][n - 1]) {
                // Step 2: Perform binary search on this row
                return binarySearchRow(matrix[mid], target);
            } else if (matrix[mid][0] > target) {
                bottom = mid - 1; // Search in upper rows
            } else {
                top = mid + 1; // Search in lower rows
            }
        }
        
        return false; // Target not found
    }
    
    /**
     * Helper method to perform binary search on a row.
     */
    private boolean binarySearchRow(int[] row, int target) {
        int left = 0;
        int right = row.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (row[mid] == target) {
                return true; // Target found
            } else if (row[mid] < target) {
                left = mid + 1; // Search the right half
            } else {
                right = mid - 1; // Search the left half
            }
        }
        
        return false; // Target not found
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        SearchA2DMatrix solution = new SearchA2DMatrix();
        
        // Example 1: Should return true
        int[][] matrix1 = {
            {1, 3, 5, 7},
            {10, 11, 16, 20},
            {23, 30, 34, 60}
        };
        int target1 = 3;
        System.out.println("Example 1 (One-step): " + solution.searchMatrix(matrix1, target1));
        System.out.println("Example 1 (Two-step): " + solution.searchMatrixTwoSteps(matrix1, target1));
        
        // Example 2: Should return false
        int[][] matrix2 = {
            {1, 3, 5, 7},
            {10, 11, 16, 20},
            {23, 30, 34, 60}
        };
        int target2 = 13;
        System.out.println("Example 2 (One-step): " + solution.searchMatrix(matrix2, target2));
        System.out.println("Example 2 (Two-step): " + solution.searchMatrixTwoSteps(matrix2, target2));
        
        // Let's trace through the execution of Example 1 using the one-step approach:
        // matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
        
        // m = 3, n = 4
        // Initialize left = 0, right = 11
        
        // Iteration 1:
        // mid = 0 + (11 - 0) / 2 = 5
        // row = 5 / 4 = 1, col = 5 % 4 = 1
        // matrix[row][col] = matrix[1][1] = 11 > target = 3, so search the left half
        // left = 0, right = 4
        
        // Iteration 2:
        // mid = 0 + (4 - 0) / 2 = 2
        // row = 2 / 4 = 0, col = 2 % 4 = 2
        // matrix[row][col] = matrix[0][2] = 5 > target = 3, so search the left half
        // left = 0, right = 1
        
        // Iteration 3:
        // mid = 0 + (1 - 0) / 2 = 0
        // row = 0 / 4 = 0, col = 0 % 4 = 0
        // matrix[row][col] = matrix[0][0] = 1 < target = 3, so search the right half
        // left = 1, right = 1
        
        // Iteration 4:
        // mid = 1 + (1 - 1) / 2 = 1
        // row = 1 / 4 = 0, col = 1 % 4 = 1
        // matrix[row][col] = matrix[0][1] = 3 == target = 3, so return true
        
        // Additional examples
        int[][] matrix3 = {
            {1, 3, 5, 7},
            {10, 11, 16, 20},
            {23, 30, 34, 60}
        };
        int target3 = 60;
        System.out.println("Example 3 (One-step): " + solution.searchMatrix(matrix3, target3));
        System.out.println("Example 3 (Two-step): " + solution.searchMatrixTwoSteps(matrix3, target3));
        
        int[][] matrix4 = {
            {1, 3, 5, 7},
            {10, 11, 16, 20},
            {23, 30, 34, 60}
        };
        int target4 = 0;
        System.out.println("Example 4 (One-step): " + solution.searchMatrix(matrix4, target4));
        System.out.println("Example 4 (Two-step): " + solution.searchMatrixTwoSteps(matrix4, target4));
    }
}
