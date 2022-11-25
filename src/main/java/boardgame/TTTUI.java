package boardgame;


        import javax.swing.JPanel;
        import javax.swing.JLabel;
        import javax.swing.JMenuBar;
        import javax.swing.JButton;
        import javax.swing.JOptionPane;
        import javax.swing.JMenu;
        import javax.swing.JMenuItem;
        import java.awt.GridLayout;
        import java.awt.BorderLayout;
        import java.awt.event.ActionEvent;
        import boardgame.ui.PositionAwareButton;

/**
 * Main class for interacting with user for GUI
 * @author daniellakonert
 */
public class TTTUI extends JPanel {

    private JLabel messageLabel;
    private Runner game;
    private PositionAwareButton[][] buttons;
    private JPanel buttonPanel;
    private GUI root;
    private JMenuBar menuBar;


    /**
     * TTT GUI game frame
     * @param gameFrame
     */
    public TTTUI(GUI gameFrame){
        // call the superclass constructor
        super();
        setLayout(new BorderLayout());
        root = gameFrame;
        makeMenu();
        root.setJMenuBar(menuBar);


        // instantiate the controller
        setGameController(new Runner());


        // make a new label to store messages
        messageLabel = new JLabel("Welcome to Tic Tac Toe");
        add(messageLabel, BorderLayout.NORTH);  // Messages go on top
        add(makeNewGameButton(),BorderLayout.EAST);
        add(makeButtonGrid(3,3), BorderLayout.CENTER);
    }

    /**
     * Sets controller
     * @param controller
     */
    public void setGameController(Runner controller){
        this.game = controller;
    }
    /**
     * New game Button
     * @return
     */
    private JButton makeNewGameButton(){
        JButton button = new JButton("New Game");
        button.addActionListener(e->newGame());
        return button;
    }
    /**
     * Buttons for game
     * @param tall
     * @param wide
     * @return panel of buttons
     */
    private JPanel makeButtonGrid(int tall, int wide){
        JPanel panel = new JPanel();
        buttons = new PositionAwareButton[tall][wide];
        panel.setLayout(new GridLayout(wide, tall));
        for (int y=0; y<wide; y++){
            for (int x=0; x<tall; x++){
                //Create buttons and link each button back to a coordinate on the grid
                buttons[y][x] = new PositionAwareButton();
                buttons[y][x].setAcross(x+1); //made the choice to be 1-based
                buttons[y][x].setDown(y+1);
                buttons[y][x].addActionListener(e->{
                    enterNumber(e);
                    checkGameState();
                });
                panel.add(buttons[y][x]);
            }
        }
        return panel;
    }

    /* controller methods start here */
    /**
     * Checks state of game is there a winner/tie
     */
    private void checkGameState(){
        int selection= 0;
        JOptionPane gameOver = new JOptionPane();
        if(game.isDone()){
            int winner = game.getWinner();
            String message;
            switch (winner){
                case 0:
                    message = "Tie game :( Do you want to play again?";
                    break;
                case 1:
                    message = "Player X Won! Do you want to play again?";
                    break;
                case 2:
                    message = "Player O Won! Do you want to play again?";
                    break;
                default:
                    message = "?";
                    break;
            }
            selection = gameOver.showConfirmDialog(null,
                    message, "PlayAgain?", JOptionPane.YES_NO_OPTION);
            if(selection == JOptionPane.NO_OPTION){
                root.start();
            } else{
                newGame();
            }
        }

    }
    /**
     * Updates buttons
     */
    protected void updateView(){
        //update the labels on the buttons according to the model
        for (int y=0; y<game.getHeight(); y++){
            for (int x=0; x<game.getWidth(); x++){
                buttons[y][x].setText(game.getCell(buttons[y][x].getAcross(),buttons[y][x].getDown()));
            }
        }

    }
    /**
     * New game
     */
    protected void newGame(){
        game.newGame();
        updateView();
    }


    /**
     * where players place their piece
     * @param e
     */
    private void enterNumber(ActionEvent e){
        //send input to game and update view
        PositionAwareButton clicked = ((PositionAwareButton)(e.getSource()));
        if(game.takeTurn(clicked.getAcross()-1, clicked.getDown()-1,"0")){
            clicked.setText(game.getCell(clicked.getAcross(),clicked.getDown()));
        }
    }

    /**
     * menu with load and save
     */
    public void makeMenu(){
        menuBar = new JMenuBar();
        JMenu menu = new JMenu("A submenu");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem loadItem = new JMenuItem("Load");
        menu.add(loadItem);
        menu.add(saveItem);
        menuBar.add(loadItem);
        menuBar.add(saveItem);
        loadItem.addActionListener(e->loadSomething());
        saveItem.addActionListener(e->saveSomething());
    }
    /**
     * Saves game
     */
    protected void saveSomething(){

        String input = JOptionPane.showInputDialog(null,"Enter filename to be saved ");
        if (input != null) {
            FileLoadSave.save(input, game.getStringToSave());
        }
    }
    /**
     * Loads game
     */
    protected void loadSomething(){

        String input = JOptionPane.showInputDialog(null,"Enter filename to be loaded");
        if (input != null) {
            game.loadSavedString(FileLoadSave.load(input));
            updateView();
        }
    }

}

