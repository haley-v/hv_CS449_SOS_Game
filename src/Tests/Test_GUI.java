package Tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.awt.*;

import Main.SOS_GUI; //import GUI from main package


public class Test_GUI {

	private SOS_GUI GUI;
	
	@Before
	public void setUp() throws Exception {
		GUI = new SOS_GUI();  //initialize GUI object
	}
	
	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void test() {
		assertNotNull(GUI);  //assert that GUI object is not null
		
		//Test JFrame elements
		assertEquals("Haley's SOS Game", GUI.getTitle());          //check title
		assertTrue(GUI.isResizable());                             //check if window is resizable
        assertFalse(GUI.isUndecorated());                          //check if JFrame is undecorated
        assertEquals(new Dimension(637, 456), GUI.getSize());      //check the JFrame size
	}
	
	
	//test to check if board is empty
	@Test
	public void testEmptyBoard() {
		new SOS_GUI();
		try {
			Thread.sleep(3000);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

}
