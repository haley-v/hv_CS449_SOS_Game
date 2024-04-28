package Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import java.io.*;;

public abstract class SOS_board {
    
    public int board_size;               //game board size
    public enum Cell {EMPTY, S, O}       //enum to represent cell states
    public enum GameState {PLAYING, DRAW, B_WON, R_WON}   //represent game states
    public GameState currentGameState;
    public final List<int[]> redPlayerWinPatterns;        //lists to store winning patterns
    public final List<int[]> bluePlayerWinPatterns;
    public String filePath = "record.txt";

    protected Cell[][] grid;          //2D array to keep track of game board
    protected char turn;             
    public int totalMoves;
	public int bluePoints;
	public int redPoints;
    Random rand_num = new Random();

    //constructor to initialize the game board
    public SOS_board(int size) {
    	this.board_size = size;
        
        redPlayerWinPatterns = new ArrayList<>();
        bluePlayerWinPatterns = new ArrayList<>();
        initialize_Board();
    }

    public void initialize_Board() {
    	//initialize with empty cells
        grid = new Cell[board_size][board_size];

        for (int row = 0; row < board_size; ++row) {
            for (int col = 0; col < board_size; ++col) {
                grid[row][col] = Cell.EMPTY;
            }
        }
        
        //clear the lists of winning patterns
        redPlayerWinPatterns.clear();
        bluePlayerWinPatterns.clear();
        
        currentGameState = GameState.PLAYING;   //set initial game to playing
        turn = 'B';       //game will start with Blue player
        
        //reset total moves and player points
        totalMoves = 0;
        bluePoints = 0;
        redPoints = 0;
    }

    public Cell getCell(int row, int column) {
        if (row >= 0 && row < board_size && column >= 0 && column < board_size) {   //check if indices are within bounds
            return grid[row][column];
        } else {
            return null;  //return null if indices are out of bounds
        }
    }

    public void setCell(int row, int column, Cell cell) {
        if (row >= 0 && row < board_size && column >= 0 && column < board_size) {
            grid[row][column] = cell;
        }
    }
    
    public char getTurn() {
        return turn;
    }	
    
    public void setTurn(char t) {
		t = turn;
	}

    public GameState getGameState() {
        return currentGameState;
    }
    
    //computer function
    public void makeComputerMove() {
    	int row = rand_num.nextInt(grid.length);
    	int col = rand_num.nextInt(grid.length);
    	if (!makeMove(row,col)) {
    		makeComputerMove();
    	}
    }
    
    public boolean makeMove(int row, int column) {
    	
    	//get the cell at the specified row and column
        Cell cell = getCell(row, column);
        if(cell != Cell.EMPTY){    //check if cell is not empty
            System.out.println("This cell is already occupied!");
            return false;
        }
        totalMoves += 1;
        int prevBluePoints = 0;
        int prevRedPoints = 0;
        if (turn == 'B') {
            if (SOS_GUI.blue_S.isSelected()) {
                setCell(row, column, Cell.S);
            } else if (SOS_GUI.blue_O.isSelected()){
                setCell(row, column, Cell.O);
            }
            prevBluePoints = bluePoints;
            bluePoints += checkSos(row, column);
            if (prevBluePoints == bluePoints) {
        		switchTurn();
        	}
        	else if (prevBluePoints < bluePoints) {
        		doNotSwitchTurn();
            	prevBluePoints += bluePoints;
        	}
        } else if (turn == 'R'){
            if (SOS_GUI.red_S.isSelected()) {
                setCell(row, column, Cell.S);
            } else if (SOS_GUI.red_O.isSelected()) {
                setCell(row, column, Cell.O);
            }
            prevRedPoints = redPoints;
            redPoints += checkSos(row, column);
            if (prevRedPoints < redPoints) {
        		doNotSwitchTurn();
        	}
        	else if (prevRedPoints == redPoints) {
        		switchTurn();
            	prevRedPoints += redPoints;
        	}
        }
        updateState();  
        
        /*
        //output game score to the console
		System.out.println("-------------------");
    	System.out.println(currentGameState);
        System.out.println("Blue Score-> "+bluePoints);
        System.out.println("Red  Score-> "+redPoints);
        */
        
        
        try {
        	File file = new File(filePath);
        	if (!file.exists()) {
        		file.createNewFile();
        	}
        	BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        	writer.write("-------------------");
        	writer.newLine();
        	writer.write(currentGameState.name());
        	writer.newLine();
        	writer.write("Blue Score-> " + bluePoints);
        	writer.newLine();
        	writer.write("Red  Score-> " + redPoints);
        	
        	writer.close();
        	
        }
        catch (IOException e) {
        	System.out.println("An Error has occurred: " + e.getMessage());
        	e.printStackTrace();
        }
        return true;
    } 

    public void updateState() {
        checkForWin();
    }
	
    public void doNotSwitchTurn() {
    	if (turn =='B')
    		turn ='B';	
    	else if (turn =='R')
    		turn ='R';
    }
    
    public void switchTurn() {
    	if (turn == 'B') 
    		turn ='R';
    	else if(turn =='R')
    		turn ='B';
    }
    
    
    /*
     * checkSos function checks to see 
     * which cells create an SOS 
     */
	public int checkSos(int row, int col)
	{
        //bound check/empty check
        Cell cell = getCell(row, col);
        if(cell == null || cell==Cell.EMPTY) return 0;

        List<int[]> winningPatterns = (getTurn()=='R') ? redPlayerWinPatterns : bluePlayerWinPatterns;

        int points = 0;
        if(cell == Cell.O){
            if(getCell(row, col-1)==Cell.S && getCell(row, col+1)==Cell.S){
                winningPatterns.add(new int[]{row, col-1, row, col+1});
                points+=1;
            }
            if(getCell(row-1, col)==Cell.S && getCell(row+1, col)==Cell.S){
                winningPatterns.add(new int[]{row-1, col, row+1, col});
                points+=1;
            }
            if(getCell(row-1, col-1)==Cell.S && getCell(row+1, col+1)==Cell.S){
                winningPatterns.add(new int[]{row-1, col-1, row+1, col+1});
                points+=1;
            }
            if(getCell(row-1, col+1)==Cell.S && getCell(row+1, col-1)==Cell.S){
                winningPatterns.add(new int[]{row-1, col+1, row+1, col-1});
                points+=1;
            }
        }
        else if(cell == Cell.S){
            if(getCell(row, col-1)==Cell.O && getCell(row, col-2)==Cell.S){
                winningPatterns.add(new int[]{row, col, row, col-2});
                points+=1;
            }
            if(getCell(row, col+1)==Cell.O && getCell(row, col+2)==Cell.S){
                winningPatterns.add(new int[]{row, col, row, col+2});
                points+=1;
            }
            if(getCell(row-1, col)==Cell.O && getCell(row-2, col)==Cell.S){
                winningPatterns.add(new int[]{row, col, row-2, col});
                points+=1;
            }
            if(getCell(row+1, col)==Cell.O && getCell(row+2, col)==Cell.S){
                winningPatterns.add(new int[]{row, col, row+2, col});
                points+=1;
            }
            if(getCell(row-1, col-1)==Cell.O && getCell(row-2, col-2)==Cell.S){
                winningPatterns.add(new int[]{row, col, row-2, col-2});
                points+=1;
            }
            if(getCell(row+1, col+1)==Cell.O && getCell(row+2, col+2)==Cell.S){
                winningPatterns.add(new int[]{row, col, row+2, col+2});
                points+=1;
            }
            if(getCell(row-1, col+1)==Cell.O && getCell(row-2, col+2)==Cell.S){
                winningPatterns.add(new int[]{row, col, row-2, col+2});
                points+=1;
            }
            if(getCell(row+1, col-1)==Cell.O && getCell(row+2, col-2)==Cell.S){
                winningPatterns.add(new int[]{row, col, row+2, col-2});
                points+=1;
            }
        }
		return points;
	}

    public abstract void checkForWin();
   
}