// attempt at splitting into classes


package minesweeper;

import java.util.Scanner;

public class Game {
    private Grid grid;
    private Scanner scanner;

    public Game(int difficulty) {
        scanner = new Scanner(System.in);
        if (difficulty == 1) {
            grid = new Grid(10, 10, 10);
        } else if (difficulty == 2) {
            grid = new Grid(16, 16, 40);
        } else if (difficulty == 3) {
            System.out.print("Enter rows, columns, and mines separated by space: ");
            int rows = scanner.nextInt();
            int columns = scanner.nextInt();
            int mines = scanner.nextInt();
            grid = new Grid(rows, columns, mines);
        } else {
            System.out.println("Invalid choice.");
            System.exit(0);
        }
    }

    public void play() {
        // Game logic for player moves, revealing cells, etc.
    	initializedGrid(); 
		   printGrid();
		   getPlayerMove();
		   int row = move[0];
		   int col = move[1];
		   placeMines();
		   calculateAdjacentMines();
		   while (!gameWon && !gameLost) {
		       if (grid[row][col] == 'M') { 
		           gameLost = true;
		           revealAllCells();
		           printGrid();
		           System.out.println("BOOM Game Over! You stepped on a mine.");
		           break;
		       } else {
		           revealCell(row, col);
		           remainingCells--;
		           printGrid();
		           if (checkWin()) {
		               gameWon = true;
		               System.out.println("Congratulations! You win!");
		               break;
		           }
		           getPlayerMove();
		           row = move[0];
		           col = move[1];
		       }
		   }
		}

    
    
    
    private static boolean checkWin() {
		   for (int i = 0; i < rows; i++) {
		       for (int j = 0; j < columns; j++) {
		           if (grid[i][j] != 'M' && !revealed[i][j]) {
		               return false;
		           }
		       }
		   }
		return false;
	}
    
    
    
}

