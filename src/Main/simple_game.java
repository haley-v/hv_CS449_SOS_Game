package Main;

import Main.SOS_board.GameState;

public class simple_game extends SOS_board{
	
	public simple_game(int size) {
		super(size);
	}
	
	public void checkForWin() {
		int pointsToWin = 1; 
		int maxPossibleMoves = board_size * board_size;
		
		if(bluePoints >= pointsToWin)
			currentGameState = GameState.B_WON;
		else if (redPoints >= pointsToWin)
			currentGameState = GameState.R_WON;
		else if (totalMoves < maxPossibleMoves)
			currentGameState = GameState.PLAYING;
		else currentGameState = GameState.DRAW;
	}

}
