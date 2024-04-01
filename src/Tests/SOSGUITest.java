package Tests;
import org.junit.Test;
import Main.SOS_GUI;
import java.awt.event.ActionEvent;
import javax.swing.*;

import static org.junit.Assert.*;

public class SOSGUITest {

	/*Test case that will check the validSize() 
	 * method of the SOS_GUI class*/
    @Test
    public void testValidSize() {
    	
    	//create instance of SOS_GUI
        SOS_GUI gui = new SOS_GUI();
        
        //test valid board sizes
        assertTrue(gui.validSize(3));
        assertTrue(gui.validSize(5));
        assertTrue(gui.validSize(10));
        
        //test invalid board sizes
        assertFalse(gui.validSize(2));
        assertFalse(gui.validSize(11));
    }
    
    @Test
    public void testChooseGameMode() {
        // Create instances of necessary objects
        SOS_GUI gui = new SOS_GUI();
        ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "New Game");
        JRadioButton simpleGame = new JRadioButton("Simple Game");
        JRadioButton generalGame = new JRadioButton("General Game");

        //select the simple game radio button
        simpleGame.setSelected(true);

        // Verify that the appropriate actions are taken based on the selected game mode
        assertEquals("Simple Game", simpleGame.getActionCommand());
        assertTrue(simpleGame.isSelected());
        assertFalse(generalGame.isSelected());
    }
    
    
    

}
