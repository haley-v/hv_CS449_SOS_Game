package Main;

//import swing GUI packages
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//import classes from board file
import Main.SOS_board.Cell;
import Main.SOS_board.GameState;


@SuppressWarnings("serial")
public class SOS_GUI extends JFrame implements ActionListener {
    
	//declare constant variables for GUI
	public static int CELL_SIZE = 100; 									
	public static final int GRID_WIDTH = 2; 					
	public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2;	
	public static final int CELL_PADDING = CELL_SIZE / 6;		
	public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
	public static final int SYMBOL_STROKE_WIDTH = 8;
	
	//declare radio button variables
    public static JRadioButton blue_O;							
    public static JRadioButton red_O;		
    public static JRadioButton red_S;							
    public static JRadioButton blue_S;
    public static JRadioButton computer_Blue;
    public static JRadioButton computer_Red;
	
    //game objects & components
	private SOS_board game;                           //game instance
    private GameBoardCanvas gameBoardCanvas; 		  //canvas for game board	 		
    private JLabel gameStatusBar;					  //status bar		
    private JTextField boardSize;                     //text field
    private Graphics graph; 						  //object for drawing			
    
    //button groups
    private ButtonGroup gameMode;                    //simple vs general game 
    private ButtonGroup player_typeBlue;            // user selects between human vs computer player
    private ButtonGroup player_typeRed;	
            
    
    //GUI constructor
    public SOS_GUI() {
    	this(new simple_game(3));    //default size is a 3x3 board
    }

    public SOS_GUI(SOS_board game) {

    	//JFrame properties
        this.game = game;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(100, 100, 637, 456);
        this.setLayout(null);
        this.setTitle("Haley's SOS Game");

        //GUI Components: labels/radio buttons/buttons
        JLabel sosLabel = new JLabel("SOS");
        sosLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        sosLabel.setBounds(22, 19, 33, 13);
        this.add(sosLabel);

        JRadioButton simpleGame = new JRadioButton("Simple Game");
        simpleGame.setFont(new Font("Helvetica", Font.PLAIN, 16));
        simpleGame.setBounds(61, 15, 125, 21);
        simpleGame.setFocusable(false);
        simpleGame.setActionCommand("Simple Game");
        simpleGame.setSelected(true);
        this.add(simpleGame);

        JRadioButton generalGame = new JRadioButton("General Game");
        generalGame.setFont(new Font("Helvetica", Font.PLAIN, 16));
        generalGame.setBounds(188, 15, 136, 21);
        generalGame.setFocusable(false);
        generalGame.setActionCommand("General Game");
        generalGame.setSelected(false);
        this.add(generalGame);
        
        //set up button group for simple game and general game radio buttons
        gameMode = new ButtonGroup();
        gameMode.add(simpleGame);
        gameMode.add(generalGame);
        
        //label and text field to set up board size
        JLabel bsLabel = new JLabel("Board Size");
        bsLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        bsLabel.setBounds(451, 19, 93, 13);
        this.add(bsLabel);
        
        boardSize = new JTextField();
        boardSize.setFont(new Font("Helvetica", Font.BOLD, 16));
        boardSize.setColumns(10);
        boardSize.setBackground(Color.WHITE);
        boardSize.setBounds(554, 12, 45, 26);
        boardSize.addActionListener(this);
        this.add(boardSize);

        JLabel bpLabel = new JLabel("Blue Player");
        bpLabel.setFont(new Font("Helvetica", Font.BOLD, 13));
        bpLabel.setBounds(20, 100, 150, 25);
        this.add(bpLabel);
		
		JRadioButton humanBlue = new JRadioButton("Human");
	    humanBlue.setFont(new Font("Tahoma", Font.PLAIN, 16));
	    humanBlue.setBounds(20, 140, 100, 25);
	    humanBlue.setFocusable(false);
	    humanBlue.setSelected(true);
	    humanBlue.setActionCommand("Human");
	    this.add(humanBlue);
	    
        computer_Blue = new JRadioButton("Computer");
	    computer_Blue.setFont(new Font("Tahoma", Font.PLAIN, 16));
	    computer_Blue.setBounds(20, 220, 100, 25);
	    computer_Blue.setFocusable(false);
	    computer_Blue.setSelected(false);
	    computer_Blue.setActionCommand("ComputerBlue");
	    this.add(computer_Blue);
	    
	    
	    //button group for human & computer (blue)
        player_typeBlue = new ButtonGroup();
        player_typeBlue.add(humanBlue);
        player_typeBlue.add(computer_Blue);
	    
        blue_S = new JRadioButton("S");
        blue_S.setFont(new Font("Tahoma", Font.PLAIN, 16));
        blue_S.setBounds(35, 170, 50, 25);
        blue_S.setFocusable(false);
        blue_S.addActionListener(this);
        blue_S.setSelected(true);
        this.add(blue_S);

        blue_O = new JRadioButton("O");
        blue_O.setFont(new Font("Tahoma", Font.PLAIN, 16));
        blue_O.setBounds(35, 190, 50, 25);
        blue_O.setFocusable(false);
        blue_O.addActionListener(this);
        this.add(blue_O);
        
        //button group for S & O (blue)
        ButtonGroup bluePlayer = new ButtonGroup();
        bluePlayer.add(blue_S);
        bluePlayer.add(blue_O);
        
        //red player computer and human
        JLabel rpLabel = new JLabel("Red Player");
        rpLabel.setFont(new Font("Helvetica", Font.BOLD, 13));
        rpLabel.setBounds(520, 100, 150, 25);
        this.add(rpLabel);
	    
        JRadioButton humanRed = new JRadioButton("Human");
	    humanRed.setFont(new Font("Tahoma", Font.PLAIN, 16));
	    humanRed.setBounds(505, 140, 100, 25);
	    humanRed.setFocusable(false);
	    humanRed.setSelected(true);
	    humanRed.setActionCommand("Human");
	    this.add(humanRed);
	    
        computer_Red = new JRadioButton("Computer");
	    computer_Red.setFont(new Font("Tahoma", Font.PLAIN, 16));
	    computer_Red.setBounds(505, 220, 100, 25);
	    computer_Red.setFocusable(false);
	    computer_Red.setActionCommand("ComputerRed");
	    this.add(computer_Red);
	    
	    //red player button group for human and computer
        player_typeRed = new ButtonGroup();
        player_typeRed.add(humanRed);
        player_typeRed.add(computer_Red);
	     
        //S & 0 red player
        red_S = new JRadioButton("S");
        red_S.setFont(new Font("Tahoma", Font.PLAIN, 16));
        red_S.setBounds(520, 170, 150, 25);
        red_S.setFocusable(false);
        red_S.addActionListener(this);
        red_S.setSelected(true);
        this.add(red_S);

        red_O = new JRadioButton("O");
        red_O.setFont(new Font("Tahoma", Font.PLAIN, 16));
        red_O.setBounds(520, 190, 150, 25);
        red_O.setFocusable(false);
        red_O.addActionListener(this);
        this.add(red_O);
        
        //button group for red S & O
        ButtonGroup redPlayer = new ButtonGroup();
        redPlayer.add(red_S);
        redPlayer.add(red_O);
        
        //record game check box
		JCheckBox record = new JCheckBox("Record Game");
		record.setFont(new Font("Tahoma", Font.PLAIN, 16));
		record.setBounds(18, 381, 129, 21);
		this.add(record);
        
		//current turn label
        JLabel ctLabel = new JLabel("Current Turn:");
        ctLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        ctLabel.setBounds(185, 381, 101, 13);
        this.add(ctLabel);
        
        //new game and replay buttons
        JButton newGame = new JButton("New Game");
		newGame.setFont(new Font("Tahoma", Font.PLAIN, 16));
		newGame.setBounds(501, 366, 112, 26);
		newGame.setFocusable(false);
		newGame.addActionListener(this);
		this.add(newGame);
        
		JButton replay = new JButton("Replay");
		replay.setFont(new Font("Tahoma", Font.PLAIN, 16));
		replay.setBounds(501, 335, 112, 26);
		this.add(replay);

        setContentPane();
        this.setVisible(true);
    }

    private void setContentPane() {
    	
    	//create game board canvas and dimensions
        gameBoardCanvas = new GameBoardCanvas();
        gameBoardCanvas.setPreferredSize(new Dimension(CELL_SIZE * game.board_size, CELL_SIZE * game.board_size));
        gameBoardCanvas.setBounds(150, 70, 300, 300);

        //JLabel for the status bar
        gameStatusBar = new JLabel();
        gameStatusBar.setFont(new Font("Tahoma", Font.PLAIN, 16));
        gameStatusBar.setBounds(285, 381, 150, 15);

        //get content pane of the JFrame and 
        //add the game board canvas & status bar label
        //to the content pane
        Container contentPane = getContentPane();
        contentPane.add(gameBoardCanvas);
        contentPane.add(gameStatusBar);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) { 
    	
    	//check if New Game is clicked
    	if (e.getActionCommand().equals("New Game")) {
    		
    		//Get the board size from the text field
    		int size = Integer.parseInt(boardSize.getText());
    		
    		//determine game mode
            String mode = gameMode.getSelection().getActionCommand();
            
            
            if (validSize(size)) {
                if(mode.equals("General Game")) {
                    JOptionPane.showMessageDialog(null, "This a general game.");
                    game = new general_game(size);
                }
                else if (mode.equals("Simple Game")) {
                    JOptionPane.showMessageDialog(null, "This a simple game.");
                    game = new simple_game(size);                    	
                }    
                
                //adjust cell size
                CELL_SIZE = 300 / game.board_size;
                
                //paint game board canvas
                gameBoardCanvas.paintComponent(graph);
                gameBoardCanvas.revalidate();
                gameBoardCanvas.repaint();
            }
            else {  //display error message if invalid board size
                JOptionPane.showMessageDialog(boardSize, "Please choose a valid size from 3-10", "Invalid game size", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //board size cannot be less than 3 and greater than 10
    public boolean validSize(int size) {
        return size >= 3 && size <= 10;
    }
    
    class GameBoardCanvas extends JPanel {

        GameBoardCanvas() {
            addMouseListener(new MouseAdapter() {               //mouse listener to handle user clicks
                public void mouseClicked(MouseEvent e) {
                    String blue = player_typeBlue.getSelection().getActionCommand();
                    String red = player_typeRed.getSelection().getActionCommand();

                    //calculate row & column selected based on mouse click coords.
                	int rowSelected = e.getY() / CELL_SIZE;
                    int colSelected = e.getX() / CELL_SIZE;
                    
                    if (game.getGameState() == GameState.PLAYING) {  
                    	
                    	//check which player's turn it is and handle moves accordingly
                    	if (game.getTurn()=='B') {
                    		if (blue.equals("Human"))
                    			game.makeMove(rowSelected, colSelected);
                    		}
                    		else if (blue.equals("ComputerBlue")) {
                    			game.makeComputerMove();
                    		}
                    	else if (game.getTurn()=='R') {
                    		if (red.equals("Human"))
            					game.makeMove(rowSelected, colSelected);
                    		else if (red.equals("ComputerRed")) {
                    			game.makeComputerMove();
                    		}
                    	}
                        game.updateState();      //update game state after the move
                    } else {
                        game.initialize_Board();   //if game is not ongoing, initialize board for a new game
                    }
                    repaint();
                }
             });
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(new Color(145, 225, 250));  //cell color of board is a light blue
            drawGrid(g);               //call function to draw grid lines
            drawBoard(g);                  
            graph = g; 
            printStatusBar();
        }

        private void drawGrid(Graphics g) {
            g.setColor(Color.BLACK);                   //color of grid lines
            
            //draw horizontal grid lines
            for (int row = 1; row < game.board_size + 1; row++) {
            	g.fillRoundRect(0, CELL_SIZE * row - GRID_WIDTH_HALF, 
            			CELL_SIZE * game.board_size - 1, GRID_WIDTH, GRID_WIDTH, GRID_WIDTH);
            }
            
            //draw vertical grid lines
            for (int col = 1; col < game.board_size+ 1; col++) {
                g.fillRoundRect(CELL_SIZE * col - GRID_WIDTH_HALF, 0, GRID_WIDTH,
						CELL_SIZE * game.board_size - 1, GRID_WIDTH, GRID_WIDTH);
            }
        }
       
        private void drawBoard(Graphics g) {
            Graphics2D graph_2d = (Graphics2D) g;
            
            //loop through each cell in game board 
            //and calculate coordinates for drawing symbols in the cell
            for (int row = 0; row < game.board_size; row++) {
                for (int col = 0; col < game.board_size; col++) {
                    int x1 = col * CELL_SIZE + CELL_PADDING;
					int y2 = (row + 1) * CELL_SIZE - CELL_PADDING;
					
					//draw 'S' if the cell contains 'S'. otherwise draw 'O'
					if (game.getCell(row, col) == Cell.S) {
                        graph_2d.setColor(Color.black);
                        graph_2d.setFont(new Font("Comic Sans", Font.BOLD, 20));
                        graph_2d.drawString("S", x1, y2);   
                    } 
					else if (game.getCell(row, col) == Cell.O) {
                        graph_2d.setColor(Color.black);
                        graph_2d.setFont(new Font("Comic Sans", Font.BOLD, 20));
                        graph_2d.drawString("O", x1, y2);
                    }
                }
            }
        }
        
        /*insert draw win line function here
         *  WILL DO LATER
         */
        
    
        private void printStatusBar() {
        	
        	//if the game is still ongoing, display which player's turn it is
            if (game.getGameState() == GameState.PLAYING) {
                gameStatusBar.setForeground(Color.BLACK);
                if (game.getTurn() == 'B') {
                    gameStatusBar.setText("Blue Player's Turn");
                } else {
                    gameStatusBar.setText("Red Player's Turn");
                }
                
                //if the game ends in a draw, display draw message in green
            }else if (game.getGameState() == GameState.DRAW) {
				gameStatusBar.setForeground(Color.GREEN);
				gameStatusBar.setText("It's a Draw!");
				
				//if the blue player wins, display a victory message in blue
			} else if (game.getGameState() == GameState.B_WON) {
				gameStatusBar.setForeground(Color.BLUE);
				gameStatusBar.setText("Blue Won!");
				
				//if red player wins, display a victory message in red 
			} else if (game.getGameState() == GameState.R_WON) {
				gameStatusBar.setForeground(Color.RED);
				gameStatusBar.setText("Red Won!");
			}
        }
   }
    
    /*
     * main function will run the entire game/GUI
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SOS_GUI();
            }
        });
    }
}