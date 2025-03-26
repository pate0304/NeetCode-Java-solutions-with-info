package neetcode.arrays_hashing;

import java.util.HashSet;
import java.util.Set;

/**
 * NeetCode Problem 7: Valid Sudoku
 * 
 * Problem Description:
 * Determine if a 9 x 9 Sudoku board is valid. Only the filled cells need to be validated according to the following rules:
 * 1. Each row must contain the digits 1-9 without repetition.
 * 2. Each column must contain the digits 1-9 without repetition.
 * 3. Each of the nine 3 x 3 sub-boxes of the grid must contain the digits 1-9 without repetition.
 * 
 * Note:
 * - A Sudoku board (partially filled) could be valid but is not necessarily solvable.
 * - Only the filled cells need to be validated according to the mentioned rules.
 * 
 * Examples:
 * Input: board = 
 * [["5","3",".",".","7",".",".",".","."]
 * ,["6",".",".","1","9","5",".",".","."]
 * ,[".","9","8",".",".",".",".","6","."]
 * ,["8",".",".",".","6",".",".",".","3"]
 * ,["4",".",".","8",".","3",".",".","1"]
 * ,["7",".",".",".","2",".",".",".","6"]
 * ,[".","6",".",".",".",".","2","8","."]
 * ,[".",".",".","4","1","9",".",".","5"]
 * ,[".",".",".",".","8",".",".","7","9"]]
 * Output: true
 * 
 * Input: board = 
 * [["8","3",".",".","7",".",".",".","."]
 * ,["6",".",".","1","9","5",".",".","."]
 * ,[".","9","8",".",".",".",".","6","."]
 * ,["8",".",".",".","6",".",".",".","3"]
 * ,["4",".",".","8",".","3",".",".","1"]
 * ,["7",".",".",".","2",".",".",".","6"]
 * ,[".","6",".",".",".",".","2","8","."]
 * ,[".",".",".","4","1","9",".",".","5"]
 * ,[".",".",".",".","8",".",".","7","9"]]
 * Output: false
 * Explanation: Same as Example 1, except with the 5 in the top left corner being modified to 8.
 * Since there are two 8's in the top left 3x3 sub-box, it is invalid.
 * 
 * Constraints:
 * - board.length == 9
 * - board[i].length == 9
 * - board[i][j] is a digit 1-9 or '.'.
 * 
 * Approach:
 * To validate a Sudoku board, we need to check three conditions:
 * 1. Each row contains no duplicate digits
 * 2. Each column contains no duplicate digits
 * 3. Each 3x3 sub-box contains no duplicate digits
 * 
 * We can solve this by:
 * - Iterating through each cell in the board
 * - For each cell with a digit, check if that digit already exists in the current row, column, or 3x3 sub-box
 * - If a duplicate is found, return false
 * - If no duplicates are found after checking all cells, return true
 * 
 * We can use HashSets to efficiently check for duplicates.
 * 
 * Time Complexity: O(9²) = O(1) since the board size is fixed at 9x9
 * Space Complexity: O(9²) = O(1) for the HashSets
 */
public class ValidSudoku {
    
    /**
     * Determines if a 9x9 Sudoku board is valid.
     * 
     * @param board The 9x9 Sudoku board represented as a 2D character array
     * @return true if the board is valid, false otherwise
     */
    public boolean isValidSudoku(char[][] board) {
        // Create sets to track digits in each row, column, and 3x3 sub-box
        Set<Character>[] rows = new HashSet[9];
        Set<Character>[] cols = new HashSet[9];
        Set<Character>[] boxes = new HashSet[9];
        
        // Initialize the sets
        for (int i = 0; i < 9; i++) {
            rows[i] = new HashSet<>();
            cols[i] = new HashSet<>();
            boxes[i] = new HashSet<>();
        }
        
        // Validate the board
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char cell = board[i][j];
                
                // Skip empty cells
                if (cell == '.') {
                    continue;
                }
                
                // Calculate the index of the 3x3 sub-box
                // (i/3) gives the row of the box (0, 1, or 2)
                // (j/3) gives the column of the box (0, 1, or 2)
                // (i/3)*3 + (j/3) gives a unique index from 0 to 8 for each box
                int boxIndex = (i / 3) * 3 + j / 3;
                
                // Check if the digit already exists in the current row, column, or box
                if (rows[i].contains(cell) || cols[j].contains(cell) || boxes[boxIndex].contains(cell)) {
                    return false; // Found a duplicate
                }
                
                // Add the digit to the sets
                rows[i].add(cell);
                cols[j].add(cell);
                boxes[boxIndex].add(cell);
            }
        }
        
        // If no duplicates were found, the board is valid
        return true;
    }
    
    /**
     * Alternative approach using a single pass with string encoding.
     * Instead of using separate sets, we use a single set with encoded strings.
     */
    public boolean isValidSudokuAlternative(char[][] board) {
        Set<String> seen = new HashSet<>();
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char cell = board[i][j];
                
                if (cell != '.') {
                    // Create unique strings for row, column, and box checks
                    // We use different prefixes to avoid collisions
                    String rowCheck = "r" + i + cell;
                    String colCheck = "c" + j + cell;
                    String boxCheck = "b" + (i / 3) * 3 + j / 3 + cell;
                    
                    // If any of these strings are already in the set, we have a duplicate
                    if (!seen.add(rowCheck) || !seen.add(colCheck) || !seen.add(boxCheck)) {
                        return false;
                    }
                }
            }
        }
        
        return true;
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        ValidSudoku solution = new ValidSudoku();
        
        // Example 1: Valid Sudoku board
        char[][] board1 = {
            {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
            {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
            {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
            {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
            {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
            {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
            {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
            {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
            {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };
        System.out.println("Example 1: " + solution.isValidSudoku(board1));
        System.out.println("Example 1 (Alternative): " + solution.isValidSudokuAlternative(board1));
        
        // Example 2: Invalid Sudoku board (duplicate 8 in top-left 3x3 box)
        char[][] board2 = {
            {'8', '3', '.', '.', '7', '.', '.', '.', '.'},
            {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
            {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
            {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
            {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
            {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
            {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
            {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
            {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };
        System.out.println("Example 2: " + solution.isValidSudoku(board2));
        System.out.println("Example 2 (Alternative): " + solution.isValidSudokuAlternative(board2));
    }
}
