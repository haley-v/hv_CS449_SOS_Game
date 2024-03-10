package Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Main.SOS_board;

import Main.SOS_board.Cell;
import Main.SOS_board.GameState;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class Test_Gameboard {
	
	private SOS_board b;


	//check initialization of the board
	@Test
	public void testInitBoard() {
		assertEquals(GameState.PLAYING, GameState());
        assertEquals('B', b.getTurn());
        
        assertEquals(Cell.EMPTY, getCell(0,0));
        assertEquals(Cell.EMPTY, getCell(1,1));
        assertEquals(Cell.EMPTY, b.getCell(2,2));
	}
	
	private Object getCell(int i, int j) {
		return null;
	}
	
	private Object GameState() {
		return null;
	}
	
	//test to check the makeMove functionality of the board
	@Test
    public void testMakeMove() {
      
		
        assertEquals(SOS_board.Cell.S, b.getCell(0,0));
        assertFalse(b.makeMove(0,0));
        assertEquals(SOS_board.Cell.S, b.getCell(0,0));
        assertTrue(b.makeMove(0,1));
        assertEquals(SOS_board.Cell.O, b.getCell(0,1));
        assertFalse(b.makeMove(-1,0));
        assertFalse(b.makeMove(3,3));
    }

}
