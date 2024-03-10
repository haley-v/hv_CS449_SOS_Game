package Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class SOS_board {
    
    public int board_size;
    public enum Cell {EMPTY, S, O}
    public enum GameState {PLAYING, DRAW, B_WON, R_WON}
    public GameState currentGameState;
    public final List<int[]> redPlayerWinPatterns;
    public final List<int[]> bluePlayerWinPatterns;

    protected Cell[][] grid;
    protected char turn;
    public int totalMoves;
	public int bluePoints;
	public int redPoints;
    Random rand_num = new Random();

    public SOS_board(int size) {
    	this.board_size = size;
        
        redPlayerWinPatterns = new ArrayList<>();
        bluePlayerWinPatterns = new ArrayList<>();
        initialize_Board();
    }

    public void initialize_Board() {
        grid = new Cell[board_size][board_size];

        for (int row = 0; row < board_size; ++row) {
            for (int col = 0; col < board_size; ++col) {
                grid[row][col] = Cell.EMPTY;
            }
        }
        redPlayerWinPatterns.clear();
        bluePlayerWinPatterns.clear();
        currentGameState = GameState.PLAYING;
        turn = 'B';
        totalMoves = 0;
        bluePoints = 0;
        redPoints = 0;
    }

    public Cell getCell(int row, int column) {
        if (row >= 0 && row < board_size && column >= 0 && column < board_size) {
            return grid[row][column];
        } else {
            return null;
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
    
    /*insert computer moves function here
     * WILL DO LATER
     */
    
    public boolean makeMove(int row, int column) {
        Cell cell = getCell(row, column);
        if(cell != Cell.EMPTY){
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
		System.out.println("-------------------");
    	System.out.println(currentGameState);
        System.out.println("Blue Score-> "+bluePoints);
        System.out.println("Red  Score-> "+redPoints);
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