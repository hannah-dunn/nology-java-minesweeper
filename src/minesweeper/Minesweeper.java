//	Project: Minesweeper
//	MVP
//	Recreate a simplified version of the game Minesweeper to be played in the java console 
//	The game should be able to randomly generate 10 mines in a 10x10 grid The user will be 
//	able to enter a command that represents a coordinate to check a location for a mine The 
//	application will display a number from 0-8 depending on how many mines surround that 
//	location If the user selects a mine, the game will respond "boom!" and the game will be 
//	lost If every non-mine square has been revealed, the game is won Render the grid to the 
//	console after every user command
//
//	Bonuses (optional)
//	Allow for the user to configure number of mines and grid size via a configuration.json 
//	file (Difficult) Discovering an empty square should reveal all squares around it, and 
//	cascade into other nearby empty squares

package minesweeper;

import java.util.Scanner;

public class Minesweeper {

	static int rows, columns, mines, remainingCells;
	static char [][] grid;
	static int[] move = new int[2];
	static boolean[][] revealed;
	static boolean gameWon, gameLost;
	static Scanner sc = new Scanner(System.in);
	
	// variables are declared and initialized
	// scanner class object reads the input from users 
	
	
	// grid size method - bonus of setting difficulty
	// using a switch case to determine how big grid will be printed 
	private static void grid_size(int difficulty) {
		switch (difficulty) {
		case 1:
			rows = 10;
			columns = 10;
			mines = 10;
			break;
		case 2:
			rows = 16;
			columns = 16;
			mines = 40;
			break;
		case 3:
			System.out.println("Set own difficulty. Please enter desired rows, columns and mines as three seperate inputs: \n");
			rows = sc.nextInt();
			columns = sc.nextInt();
			mines = sc.nextInt();
			break;
		default:
			System.out.println("\n is an invalid choice");
			System.exit(0);
		}
		grid = new char[rows][columns];
	}
			
	// method
	// initializing this method makes the grid with empty cells
	private static void initializedGrid() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				grid[i][j] = '-'; // placeholder for the empty cell
			}
		}
		revealed = new boolean[rows][columns]; // this starts/initializes the revealed array
		remainingCells = rows * columns - mines; // this calculates how many cells need to be revealed still
	}
	
	// starts with zero mines, while there are no mines
	private static void placeMines() {
		int minesPlaced = 0;
		while (minesPlaced < mines) { 
			int row = (int)(Math.random() * rows); //randomly generates and randomly places mines in grid
			int col = (int)(Math.random() * columns);
			if (grid[row][col] != 'M') {
				grid[row][col] = 'M';
				minesPlaced++;
			}
		}
	}
	
	
	// then have to calculate the adjacent mines 
	// this method iterates over my grid
	private static void calculateAdjacentMines() { // for each cell, adjacent numbers and mies are calculated
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (grid[i][j] != 'M') {
					int count = 0;
					for (int x = -1; x <= 1; x++) {
						for (int y = -1; y <= 1; y++) {
							if ((x != 0 || y != 0) && isValidCell(i + x, j + y) && grid[i + x][j + y] == 'M') {
								count++;
							}
						}
					}
					if (count > 0) {
						grid[i][j] = (char) (count + '0');
					}
				}
			}
		}
	}

	// this method checks if the user inputed numbers are a valid cell within the grid
	private static boolean isValidCell(int row, int col) { 
		   return row >= 0 && row < rows && col >= 0 && col < columns;
		}
	
	
	// this method displays the current game in the console, printing all the rows and columns
	private static void printGrid() {
		System.out.print("     ");
		for (int j = 0; j < columns; j++) {
			System.out.printf("    %3s", j); // this is the line that doesn't align
		}					// % part of string that will be replaced by formatted argument, 3 specifies minimum width
		System.out.println(); // s specifies the argument is a string
		for (int i = 0; i < rows; i++) {
			System.out.print("\t" + i);  // horizontal tab
			for (int j = 0; j < columns; j++) {
				if (grid[i][j] == 'F') { // flagged cell
					System.out.printf("\tF"); // tabs the flagged cell
				} else if (revealed[i][j]) {
					if (grid[i][j] == '-') {
						System.out.print("\t0");
					} else {
						System.out.print("\t" + grid[i][j]);
					}
				} else {
					System.out.printf("\t-");
				}
			}
			System.out.println();
		}
	}
	
	
	
	// reveals the selected cell
	private static void revealCell(int row, int col) {
		   if (!isValidCell(row, col) || revealed[row][col]) {
		       return;
		   }
		   revealed[row][col] = true;
		   remainingCells--;
		  
		               revealCell(row, col);
		           }
	
// removed the bit of revealCell that was revealed the big space if you hit an empty patch
// it was calculating if the cells around it were '-' cells, aka empty, then would reveal 	
	
	private static void revealAllCells() {
		   for (int i = 0; i < rows; i++) {
		       for (int j = 0; j < columns; j++) {
		           revealed[i][j] = true;
		       }
		   } // reveals all the cells at once for game over/finished game
		}
	
	private static void getPlayerMove() { 
		   System.out.print("Please enter row number: ");
		   move[0] = sc.nextInt();
		   System.out.print("Please enter column number: ");
		   move[1] = sc.nextInt();
		} // uses the scanner to get the users input
	
	
	// makes the game loop! initializes, and after the first move by player
	// places the mines
	public static void play() {
		   initializedGrid(); 
		   printGrid();
		   getPlayerMove();
		   int row = move[0];
		   int col = move[1];
		   placeMines();
		   calculateAdjacentMines();
		   while (!gameWon && !gameLost) {
		       if (grid[row][col] == 'M') { // checks if a mine has been stepped on
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
		       } // verifies if all non-mine cells have been revealed
		   }
		return false;
	}
	
	
	public static void main(String[] args) {
		   System.out.format("Minesweeper\n");
		   System.out.println("\n----MENU---- ");
		   System.out.println(" 1. Easy\n 2. Hard\n 3. Custom\n");
		   System.out.println("\nPlease enter a number to choose the difficulty: ");
		   int difficulty = sc.nextInt(); //scans users input for difficulty method
		   grid_size(difficulty); //calls the method right
		   play();
		}


}
