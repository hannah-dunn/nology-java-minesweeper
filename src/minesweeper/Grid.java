// attempt at splitting the game into a grid and game class
// diabolical



package minesweeper;

import java.util.Random;

public class Grid  {

	 private char[][] grid;
	    private boolean[][] revealed;
	    private int rows;
	    private int columns;
	    private int mines;

	    public Grid(int rows, int columns, int mines) {
	        this.rows = rows;
	        this.columns = columns;
	        this.mines = mines;
	        this.grid = new char[rows][columns];
	        this.revealed = new boolean[rows][columns];
	        initializeGrid();
	        placeMines();
	        calculateAdjacentMines();
	
	    }
	    
	    private void initializeGrid() {
	    	for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					grid[i][j] = '-';
				}
			}
			revealed = new boolean[rows][columns];
			remainingCells = rows * columns - mines;
	    }
	        
	    
	    private static void placeMines() {
			int minesPlaced = 0;
			while (minesPlaced < mines) {
				int row = (int)(Math.random() * rows);
				int col = (int)(Math.random() * columns);
				if (grid[row][col] != 'M') {
					grid[row][col] = 'M';
					minesPlaced++;
				}
			}
		}
	    
	    
	    private static void calculateAdjacentMines() {
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

	    public char[][] getGrid() {
	        return grid;
	    }

	    public boolean[][] getRevealed() {
	        return revealed;
	    }

	    public int getRows() {
	        return rows;
	    }

	    public int getColumns() {
	        return columns;
	    } 
	    
	    
	    
}
